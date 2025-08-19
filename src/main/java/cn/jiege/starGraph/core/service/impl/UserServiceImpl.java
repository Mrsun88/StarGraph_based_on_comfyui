package cn.jiege.starGraph.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.jiege.starGraph.core.dto.request.UserLoginReqDTO;
import cn.jiege.starGraph.core.dto.respone.UserLoginResDTO;
import cn.jiege.starGraph.core.pojo.User;
import cn.jiege.starGraph.core.mapper.UserMapper;
import cn.jiege.starGraph.core.service.IUserService;
import cn.jiege.starGraph.core.utils.JWTUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ysjiege
 * @since 2025-08-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public UserLoginResDTO loginByPassword(UserLoginReqDTO userLoginReqDTO) throws BadRequestException {
        // 参数校验
        if(userLoginReqDTO == null ||
           userLoginReqDTO.getUsername() == null ||
           userLoginReqDTO.getPassword() == null) {
            throw new BadRequestException("用户名或密码不能为空");
        }
        // 根据用户名和手机号查询用户
        User user = lambdaQuery()
                .or(c -> c.eq(User::getUsername, userLoginReqDTO.getUsername()))
                .or(c -> c.eq(User::getPassword, userLoginReqDTO.getPassword()))
                .one();
        // 校验密码
        if(user == null || !BCrypt.checkpw(userLoginReqDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }
        // 生成token, 返回
        return generateLoginInfo(user);
    }

    private UserLoginResDTO generateLoginInfo(User user) {
        UserLoginResDTO userLoginResDTO = new UserLoginResDTO();
        BeanUtils.copyProperties(user,userLoginResDTO);
        if(StrUtil.isEmpty(userLoginResDTO.getName())) {
            userLoginResDTO.setName(user.getUsername());
        }
        if(StrUtil.isEmpty(userLoginResDTO.getName())) {
            userLoginResDTO.setName(user.getMobile());
        }
        String token = JWTUtils.createJWT(user.getId(), userLoginResDTO.getName());
        userLoginResDTO.setToken(token);
        return userLoginResDTO;
    }
}
