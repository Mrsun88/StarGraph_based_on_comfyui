package cn.jiege.starGraph.core.service;

import cn.jiege.starGraph.core.dto.request.UserLoginReqDTO;
import cn.jiege.starGraph.core.dto.respone.UserLoginResDTO;
import cn.jiege.starGraph.core.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.coyote.BadRequestException;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author ysjiege
 * @since 2025-08-19
 */
public interface IUserService extends IService<User> {

    UserLoginResDTO loginByPassword(UserLoginReqDTO userLoginReqDTO) throws BadRequestException;
}
