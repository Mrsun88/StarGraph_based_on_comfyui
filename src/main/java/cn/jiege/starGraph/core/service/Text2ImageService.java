package cn.jiege.starGraph.core.service;

import cn.jiege.starGraph.core.dto.request.Text2ImageReqDto;
import cn.jiege.starGraph.core.dto.respone.Text2ImageResDto;

public interface Text2ImageService {

    /**
     * 文生图方法
     * @param text2ImageReqDto
     * @return
     */
    Text2ImageResDto text2Image(Text2ImageReqDto text2ImageReqDto) throws Exception;
}
