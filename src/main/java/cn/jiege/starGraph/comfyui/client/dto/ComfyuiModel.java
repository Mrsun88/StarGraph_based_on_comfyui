package cn.jiege.starGraph.comfyui.client.dto;

import lombok.Data;

@Data
public class ComfyuiModel {
    private long seed;
    private int step;
    private int cfg;
    private String samplerName;
    private String scheduler;
    private String modelName;
    private int width;
    private int height;
    private int size;
    private String prompt;
    private String reverse;
}
