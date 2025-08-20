package cn.jiege.starGraph.core.service;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;

public interface RedisService {
    /**
     * 提交任务到redis队列
     * @param comfyuiTask
     * @return
     */
    public ComfyuiTask addQueueTask(ComfyuiTask comfyuiTask);

    ComfyuiTask popQueueTask();

    /**
     * 缓存Comfyui任务id和任务对象
     * @param promptId 任务id
     * @param task 任务对象
     */
    void addStartedTask(String promptId, ComfyuiTask task);

    /**
     * 根据Comfyui任务id获取任务对象
     * @param promptId 任务id
     * @return 任务对象
     */
    ComfyuiTask getStartedTask(String promptId);

    /**
     * 判断是否有任务在队列中
     * @return
     */
    boolean hasQueueTask();
}
