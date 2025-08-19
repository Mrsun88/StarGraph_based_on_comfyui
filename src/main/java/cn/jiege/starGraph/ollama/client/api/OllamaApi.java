package cn.jiege.starGraph.ollama.client.api;

import cn.jiege.starGraph.ollama.client.pojo.OllamaChatRequest;
import cn.jiege.starGraph.ollama.client.pojo.OllamaChatRespone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OllamaApi {

    /**
     * 获取队列中的任务信息
     * @return
     */
    @POST("/api/chat")
    Call<OllamaChatRespone> chat(@Body OllamaChatRequest body);


}
