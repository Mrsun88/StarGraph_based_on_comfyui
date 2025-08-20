package cn.jiege.starGraph.core.controller;

import cn.jiege.starGraph.core.dto.common.Result;
import cn.jiege.starGraph.core.dto.request.Text2ImageReqDto;
import cn.jiege.starGraph.core.dto.respone.Text2ImageResDto;
import cn.jiege.starGraph.core.service.Text2ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authed/1.0/t2i")
public class TextToImageController {

    private final Text2ImageService text2ImageService;

    /**
     * 提交文本到图片任务
     *
     * @param text2ImageReqDto 请求参数
     * @return 任务响应结果
     */
    @PostMapping
    public Result<Text2ImageResDto> t2i(@RequestBody Text2ImageReqDto text2ImageReqDto) throws Exception {
        Text2ImageResDto text2ImageResDto = text2ImageService.text2Image(text2ImageReqDto);
        return Result.ok(text2ImageResDto);
    }
}