package cn.jiege.starGraph.core.service.impl;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;
import cn.jiege.starGraph.comfyui.client.dto.MessageBase;
import cn.jiege.starGraph.core.job.RunTaskJob;
import cn.jiege.starGraph.core.service.ComfyuiMessageService;
import cn.jiege.starGraph.core.service.IUserResultService;
import cn.jiege.starGraph.core.service.RedisService;
import cn.jiege.starGraph.core.service.WsNoticeService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.data.web.OffsetScrollPositionHandlerMethodArgumentResolverSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComfyuiMessageServiceImpl implements ComfyuiMessageService {

    private final RedisService redisService;
    private final WsNoticeService wsNoticeService;
    private final IUserResultService userResultService;
    private final OffsetScrollPositionHandlerMethodArgumentResolverSupport offsetScrollPositionHandlerMethodArgumentResolverSupport;
    private final RedissonClient redissonClient;

    @Override
    public void handleMessage(MessageBase messageBase) {
        if("progress".equals(messageBase.getType())) {
            handleProgress(messageBase);
        } else if ("execute".equals(messageBase.getType())) {
            // 处理生图结果
            handleExecuted(messageBase);
        } else if("status".equals(messageBase.getType())) {
            // 处理生图状态
            handleStatus(messageBase);
        }
    }

    private void handleStatus(MessageBase messageBase) {
        HashMap<String, Object> data = messageBase.getData();
        HashMap<String, Object> status = (HashMap<String, Object>) data.get("status");
        HashMap<String, Object> execInfo = (HashMap<String, Object>) status.get("exec_info");
        Integer queueRemaining = (Integer) execInfo.get("queue_remaining");

        // 队列为0, 释放信号量
        if(queueRemaining == 0) {
            RSemaphore semaphore = redissonClient.getSemaphore(RunTaskJob.TASK_RUN_SEMAPHORE);
            semaphore.release();
        }
    }

    /**
     * 处理生图结束
     * @param messageBase
     */
    private void handleExecuted(MessageBase messageBase) {
        // 消息格式转换
        HashMap<String, Object> data = messageBase.getData();
        HashMap<String, Object> output = (HashMap<String, Object>)data.get("output");
        List<HashMap<String, Object>> images = (List<HashMap<String, Object>>) output.get("images");
        List<String> urls = images.stream().map(image -> {
            // TODO ip地址需要配置
            return String.format("http://127.0.0.1:8188/view?filename=%s&type=%s&subfolder=%s", image.get("filename"), image.get("type"), image.get("subfolder"));
        }).toList();
// redis中获取任务
        String promptId = data.get("prompt_id").toString();
        ComfyuiTask task = redisService.getStartedTask(promptId);
        if(task == null) {
            return  ;
        }
        // 推送消息到客户端
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", "imageResult");
        result.put("urls", urls);
        wsNoticeService.sendToUser(task.getWsClientId(), JSON.toJSONString(result));
        // 图片保存
        userResultService.insertUserResult(task, urls);
    }

    /**
     * 处理生图记录
     * @param messageBase
     */
    private void handleProgress(MessageBase messageBase) {
        // 消息格式转换
        HashMap<String, Object> data = messageBase.getData();
        data.put("type", "progress");
        // redis中获取任务
        String promptId = data.get("prompt_id").toString();
        ComfyuiTask task = redisService.getStartedTask(promptId);
        if(task == null) {
            return  ;
        }
        // 推送消息到客户端
        wsNoticeService.sendToUser(task.getWsClientId(), JSON.toJSONString(data));
    }
}
