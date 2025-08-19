package cn.jiege.starGraph.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiege.starGraph.comfyui.client.dto.ComfyuiModel;
import cn.jiege.starGraph.comfyui.client.dto.ComfyuiRequestDto;
import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;
import cn.jiege.starGraph.core.dto.request.Text2ImageReqDto;
import cn.jiege.starGraph.core.service.FreemarkerService;
import cn.jiege.starGraph.core.service.OllamaService;
import cn.jiege.starGraph.core.service.Text2ImageService;
import cn.jiege.starGraph.core.utils.UserUtils;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Text2ImageServiceImpl implements Text2ImageService {

    public static final String COMFYUI_CLIENT_ID_TEMPLATE = "star-graph-";

    private final OllamaService ollamaService;
    private final FreemarkerService freemarkerService;

    /**
     * 将前端传入的参数封装为redis任务对象
     * @param text2ImageReqDto 前端传入的参数
     * @return redis任务对象(包含Comfyui生图的任务对象)
     */
    public ComfyuiTask text2Image(Text2ImageReqDto text2ImageReqDto) throws Exception {
        // 1. 将text2ImageReqDto转换为ComfyuiModel
        ComfyuiModel model = BeanUtil.toBean(text2ImageReqDto, ComfyuiModel.class);
        model.setWidth(text2ImageReqDto.width());
        model.setHeight(text2ImageReqDto.height());
        model.setSamplerName(text2ImageReqDto.samplerName());
        model.setScheduler(text2ImageReqDto.scheduler());
        // 封装提示词
        String prompt = text2ImageReqDto.getPrompt();
        String reverse = text2ImageReqDto.getReverse();
        if(StrUtil.isEmpty(prompt)){
            prompt = "";
        }
        if(StrUtil.isEmpty(reverse)){
            reverse = "worst quality,normal quality,anatomical nonsense,bad anatomy,interlocked fingers,extra fingers,watermark,simple background,transparent,low quality,logo,text,signature,face backlighting,backlighting,";
        }
        if(text2ImageReqDto.isNeedTranslate()) {
            prompt = ollamaService.translate(prompt);
            reverse = ollamaService.translate(reverse);
        }
        model.setPrompt(prompt);
        model.setReverse(reverse);
        // 2. 利用ComfyuiModel结合模板使用Freemarker渲染Comfyui json
        String comfyuiJson = freemarkerService.renderText2Image(model);
        // 3. 构建Comfyui生图需要的对象ComfyuiRequestDto
        // 模板+用户id作为clientId
        ComfyuiRequestDto comfyuiRequestDto = new ComfyuiRequestDto(COMFYUI_CLIENT_ID_TEMPLATE.concat(UserUtils.getUser().getId().toString()), JSON.parseObject(comfyuiJson));
        // 4. 构建ComfyuiTask对象 -- 此处wsClientId使用前端传入的clientId
        return new ComfyuiTask(text2ImageReqDto.getClientId(), comfyuiRequestDto);
    }

}
