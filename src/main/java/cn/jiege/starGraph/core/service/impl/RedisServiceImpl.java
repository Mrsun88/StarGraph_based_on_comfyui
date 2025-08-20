package cn.jiege.starGraph.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;
import cn.jiege.starGraph.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final static String DISTRIBUTED_ID_KEY = "comfyui:task:id";
    private final static String TASK_KEY_PREFIX = "comfyui:task:key:";
    private final static String DISTRIBUTE_QUEUE_KEY = "comfyui:distributed:queue";
    private final static String RUN_TASK_KEY_PREFIX = "comfyui:run:task:";

    private final StringRedisTemplate redisTemplate;

    @Override
    public ComfyuiTask addQueueTask(ComfyuiTask comfyuiTask) {
        // 获取分布式自增id
        Long idx = redisTemplate.opsForValue().increment(DISTRIBUTED_ID_KEY);
        // 设置回任务对象
        if(idx == null) {
            throw new RuntimeException("获取Redis任务ID失败");
        }
        comfyuiTask.setIndex(idx);
        // 添加任务数据到redisString
        redisTemplate.opsForValue().set(TASK_KEY_PREFIX + comfyuiTask.getId(), JSONUtil.toJsonStr(comfyuiTask));
        // 任务id压入zset
        redisTemplate.opsForZSet().add(DISTRIBUTE_QUEUE_KEY, comfyuiTask.getId(), idx);
        return comfyuiTask;
    }

    @Override
    public void addStartedTask(String promptId, ComfyuiTask task) {
        redisTemplate.opsForValue().set(RUN_TASK_KEY_PREFIX + promptId, JSONUtil.toJsonStr(task), Duration.ofMinutes(10L));
    }

    @Override
    public ComfyuiTask getStartedTask(String promptId) {
        String json = redisTemplate.opsForValue().get(RUN_TASK_KEY_PREFIX + promptId);
        if(StrUtil.isNotEmpty(json)) {
            return JSONUtil.toBean(json, ComfyuiTask.class);
        }
        return null;
    }

    /**
     * 取出序号最小任务
     * @return
     */
    @Override
    public ComfyuiTask popQueueTask() {
        // 判断是否有任务
        Long size = redisTemplate.opsForZSet().size(DISTRIBUTE_QUEUE_KEY);
        if (size == null || size <= 0) {
            return null;
        }
        // 获取队列中的最小id
        ZSetOperations.TypedTuple<String> task = redisTemplate.opsForZSet().popMin(DISTRIBUTE_QUEUE_KEY);
        if(task == null) {
            return null;
        }
        String taskId = task.getValue();
        // 从redisString中获取任务对象
        String taskJson = redisTemplate.opsForValue().getAndDelete(TASK_KEY_PREFIX + taskId);
        if(StrUtil.isNotEmpty(taskJson)) {
            return JSONUtil.toBean(taskJson, ComfyuiTask.class);
        }
        // 如果没有获取到任务对象, 则返回null
        return null;
    }

    @Override
    public boolean hasQueueTask() {
        return redisTemplate.opsForZSet().size(DISTRIBUTE_QUEUE_KEY) > 0;
    }
}
