package cn.jiege.starGraph.core.job;

import cn.jiege.starGraph.comfyui.client.api.ComfyuiApi;
import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;
import cn.jiege.starGraph.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class RunTaskJob {

    private final RedisService redisService;
    private final ComfyuiApi comfyuiApi;
    private final RedissonClient redissonClient;
    private static final String SPRING_TASK_LOCK = "lock:task";
    public static final String TASK_RUN_SEMAPHORE = "TASK_RUN_SEMAPHORE";


    // 每5秒执行一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void task() {
        RLock lock = redissonClient.getLock(SPRING_TASK_LOCK);
        if(lock.tryLock()) {
            try {
                // 尝试获取信号量
                if(redisService.hasQueueTask()) {
                    RSemaphore semaphore = redissonClient.getSemaphore(TASK_RUN_SEMAPHORE);
                    if(semaphore.tryAcquire()) {
                        addTaskQueue();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 添加任务到Comfyui
     */
    private void addTaskQueue() {
        try {
            // 取出任务
            ComfyuiTask comfyuiTask = redisService.popQueueTask();
            if(comfyuiTask==null){
                return;
            }

            Response<HashMap> response = comfyuiApi.addQueueTask(comfyuiTask.getComfyuiRequestDto()).execute();
            if(response.isSuccessful()){
                HashMap  body = response.body();
                String promptId = body.get("prompt_id").toString();
                comfyuiTask.setPromptId(promptId);
                log.info("任务已经发送到Comfyui, 任务ID: {}, 生图ID: {}", comfyuiTask.getId(), promptId);
                redisService.addStartedTask(promptId,comfyuiTask);
            } else {
                String error = response.errorBody().string();
                log.error("添加任务到队列失败, error: {}", error);
                // 释放信号量
                RSemaphore semaphore = redissonClient.getSemaphore(TASK_RUN_SEMAPHORE);
                semaphore.release();
            }
        } catch (Exception e) {
            log.error("添加任务到Comfyui失败:",  e);
        }

    }

}
