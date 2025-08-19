package cn.jiege.starGraph.comfyui.client.dto;

import lombok.Data;

import java.util.UUID;

/**
 * redis 中队列任务
 */
@Data
public class ComfyuiTask {

    String id = UUID.randomUUID().toString();
    String wsClientId;
    ComfyuiRequestDto comfyuiRequestDto;
    String promptId;
    long index;

    public ComfyuiTask(String wsClientId, ComfyuiRequestDto comfyuiRequestDto) {
        this.wsClientId = wsClientId;
        this.comfyuiRequestDto = comfyuiRequestDto;
    }
}
