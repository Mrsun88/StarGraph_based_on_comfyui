package cn.jiege.starGraph.core.dto.request;

import lombok.Data;

@Data
public class CancelTaskReqDto {

    private String tempId = "";
    private Long index;

}