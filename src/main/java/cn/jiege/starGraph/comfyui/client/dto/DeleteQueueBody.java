package cn.jiege.starGraph.comfyui.client.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeleteQueueBody {
    List<String> delete;
}
