package cn.jiege.starGraph.core.service;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiModel;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface FreemarkerService {

    /**
     * 封装comfyui提交任务的参数
     * @param model 模型数据
     * @return 模版渲染后的数据
     */
    public String renderText2Image(ComfyuiModel model) throws IOException, TemplateException;
}
