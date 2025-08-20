package cn.jiege.starGraph.comfyui.client.dto;

import lombok.Data;

import java.util.UUID;

/**
 * redis 中队列任务
 */
@Data
public class ComfyuiTask {

    // 随机生成的id, 用于redis中保存
    String id = UUID.randomUUID().toString();
    // 前端提交, 用于标识唯一客户端id
    String wsClientId;
    ComfyuiRequestDto comfyuiRequestDto;
    // 由Comfyui提供, 生图的唯一id
    String promptId;
    long index;
    Long userId;

    public ComfyuiTask(String wsClientId, ComfyuiRequestDto comfyuiRequestDto) {
        this.wsClientId = wsClientId;
        this.comfyuiRequestDto = comfyuiRequestDto;
    }
}
