package cn.jiege.starGraph.ollama.client.pojo;

import lombok.Data;

@Data
public class OllamaMessage {

    private String role;
    private String content;
    private String images;

}
