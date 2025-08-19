package cn.jiege.starGraph.comfyui.client.api;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiRequestDto;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComfyuiApiTest {
    @Autowired
    private ComfyuiApi comfyuiApi;

    @Test
    void testGetSystemStatus() throws IOException {
        Response<HashMap> resp = comfyuiApi.getSystemStats().execute();
        HashMap body = resp.body();
        System.out.println(JSON.toJSONString(body));
    }

    @Test
    void testHistory() throws IOException {
        Response<HashMap> response = comfyuiApi.getHistoryTasks(2).execute();
        HashMap body = response.body();
        assertNotNull(body);
        System.out.println(JSON.toJSONString(body));
    }

    @Test
    void testPrompt() throws IOException {
        ComfyuiRequestDto requestDto = new ComfyuiRequestDto("123456", JSON.parse(getPrompt()));
        Response<HashMap> response = comfyuiApi.addQueueTask(requestDto).execute();
        HashMap body = response.body();
        assertNotNull(body);
        System.out.println(JSON.toJSONString(body));
    }

    private String getPrompt() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\ysjie\\Downloads\\修脸放大工作流.json");
        byte[] bytes = fis.readAllBytes();
        if(!(bytes.length > 0)) {
            throw new IOException();
        }
        return new String(bytes);
    }
}