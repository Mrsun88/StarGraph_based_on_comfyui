package cn.jiege.starGraph.core.service.impl;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiModel;
import cn.jiege.starGraph.core.service.FreemarkerService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FreemarkerServiceImpl implements FreemarkerService {

    private final Configuration configuration;

    @Override
    public String renderText2Image(ComfyuiModel model) throws IOException, TemplateException {
        // 读取模板文件 TODO: 修改为动态的读取
        Template template = configuration.getTemplate("t2i_base.ftl");
        StringWriter writer = new StringWriter();
        template.process(Map.of("config", model), writer);
        return writer.toString();
    }

}
