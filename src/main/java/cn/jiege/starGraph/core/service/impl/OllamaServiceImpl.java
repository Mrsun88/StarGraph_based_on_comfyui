package cn.jiege.starGraph.core.service.impl;


import cn.jiege.starGraph.core.property.OllamaProperties;
import cn.jiege.starGraph.core.service.OllamaService;
import cn.jiege.starGraph.ollama.client.api.OllamaApi;
import cn.jiege.starGraph.ollama.client.pojo.OllamaChatRequest;
import cn.jiege.starGraph.ollama.client.pojo.OllamaChatRespone;
import cn.jiege.starGraph.ollama.client.pojo.OllamaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {

    private final OllamaApi ollamaApi;
    private final OllamaProperties properties;

    @Override
    public String translate(String prompt) {
        // 构建消息体
        OllamaMessage message = new OllamaMessage();
        message.setRole("user");
        message.setContent("请你为根据ai绘画中文提示词翻译英文提示词, 你应当多使用单词或者词组, 而非句子.每组提示词使用逗号隔开. \n下面是用户输入的提示词(请翻译中文部分, 若用户输入有英语, 请保留英文部分):\n" + prompt);

        // 构建请求
        OllamaChatRequest body = new OllamaChatRequest();
        body.setModel(properties.getModel());
        body.setMessages(List.of(message));
        body.setStream(false);
        // 发送请求
        Call<OllamaChatRespone> call = ollamaApi.chat(body);
        Response<OllamaChatRespone> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response.isSuccessful()) {
            return getContentWithoutThinkLabel(response);
        } else {
            throw new RuntimeException("翻译失败, 请稍后重试. " +  response.message());
        }
    }

    private String getContentWithoutThinkLabel(Response<OllamaChatRespone> response) {
        String originalContent = getContentWithThinkLabel(response);
        int index = originalContent.indexOf("</think>") + "</think>".length() ;
        return originalContent.substring(index).trim();
    }

    private String getContentWithThinkLabel(Response<OllamaChatRespone> response) {
        OllamaChatRespone body = response.body();
        if (body == null) {
            throw new RuntimeException("翻译失败, 响应体为空或格式不正确. " );
        }
        return body.getMessage().getContent();
    }
}
