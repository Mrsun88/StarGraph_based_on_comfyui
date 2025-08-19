package cn.jiege.starGraph.core.service;

public interface OllamaService {

    /**
     * 帮助用户将中文提示词转换成英文提示词
     * @param prompt 中文文本
     * @return 英文文本
     */
    String translate(String prompt);
}
