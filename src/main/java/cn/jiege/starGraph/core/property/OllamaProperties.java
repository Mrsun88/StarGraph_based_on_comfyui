package cn.jiege.starGraph.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "star-graph.ollama")
public class OllamaProperties {
    private String model = "qwen3:8b";
}
