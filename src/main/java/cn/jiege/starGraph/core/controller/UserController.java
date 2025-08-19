package cn.jiege.starGraph.core.controller;


import cn.jiege.starGraph.core.dto.common.Result;
import cn.jiege.starGraph.core.dto.request.UserLoginReqDTO;
import cn.jiege.starGraph.core.dto.respone.UserLoginResDTO;
import cn.jiege.starGraph.core.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author ysjiege
 * @since 2025-08-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1.0/user")
public class UserController {
    private final IUserService userService;

    public Result<UserLoginResDTO> login ( @RequestBody UserLoginReqDTO userLoginReqDTO) throws BadRequestException {
        UserLoginResDTO userLoginResDTO = userService.loginByPassword(userLoginReqDTO);
        return Result.ok(userLoginResDTO);
    }


}
