package cn.jiege.starGraph.comfyui.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComfyuiRequestDto {

    @JsonProperty("client_id")
    String clientId;
    Object prompt;

}
