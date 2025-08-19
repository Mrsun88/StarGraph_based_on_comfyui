package cn.jiege.starGraph.core.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Text2ImageReqDto {

    private String clientId = "";

    // 图片比例 1=512:512 2=512:768 3=768:512
    // TODO 后续可能废弃此字段, 直接允许用户传入长宽像素
    private int scale = 1;
    // 图片张数
    @Max(message = "最多生成4张",value = 4)
    private int size = 1;
    // 步数
    private int step = 20;
    // 强度
    private int cfg = 7;
    // 采样计划 1=DPM++ SDE Karras 2=DPM++ 2S Karras 3=Euler Karras 4=DPM++ 3M SDE Karras
    private int sampler = 1;
    // 随机种子
    @Min(message = "最小值为0",value = 0)
    private long seed = 0;
    // 正向词
    @NotBlank(message = "请输入提示词")
    private String prompt = "";
    // 负向词
    private String reverse = "";
    // 模型
    // TODO 本类实现存在缺陷, 后续可能根据需求, 直接将model改为modelName字段, 更加灵活的选择模型, 届时modelName方法可能会被废弃\
    // 目前1代表二次元风格, 2代表写实风格, 后续可能直接使用模型名称
    // TODO 若后续使用模型名称, 则需要补全前后端拉去模型列表逻辑
    private int model = 1;
    // 是否需要翻译服务
    private boolean needTranslate = true;

    public String modelName(){
        switch (model){
            case 2:
                return "Noobai_oneObsession_v16Noobai.safetensors";
            default:
                return "IL_illustrij_v16.safetensors";
        }
    }

    public String samplerName(){
        switch (sampler){
            case 1:
                return "dpmpp_sde";
            case 2:
                return "dpmpp_2m";
            case 3:
                return "euler";
            case 4:
                return "dpmpp_3m_sde";
            default:
                return "euler";
        }
    }

    public String scheduler(){
        return "karras";
    }


    public int width() {
        if(scale==3){
            return 768;
        } else {
            return 512;
        }
    }

    public int height() {
        if(scale==2){
            return 768;
        } else {
            return 512;
        }
    }

}