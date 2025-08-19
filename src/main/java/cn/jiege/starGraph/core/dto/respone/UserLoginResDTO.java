package cn.jiege.starGraph.core.dto.respone;


import lombok.Data;

@Data
public class UserLoginResDTO {
    Long id;
    private String token;
    private String name;
    private String avatar;
    private Integer vipLevel;
    private Integer standing;
}
