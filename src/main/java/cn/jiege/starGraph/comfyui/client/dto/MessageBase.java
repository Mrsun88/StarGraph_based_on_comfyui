package cn.jiege.starGraph.comfyui.client.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class MessageBase {
    private String type;
    private HashMap<String, Object> data;
}
