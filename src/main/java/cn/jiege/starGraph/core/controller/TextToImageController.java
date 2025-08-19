package cn.jiege.starGraph.core.controller;

import cn.jiege.starGraph.core.utils.UserUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authed/1.0/t2i")
public class TextToImageController {

    @PostMapping("/list")
    public String list() {
        System.out.println("用户ID是："+ UserUtils.getUser().getId());
        return "hello";
    }
}