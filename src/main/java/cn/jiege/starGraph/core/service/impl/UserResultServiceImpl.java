package cn.jiege.starGraph.core.service.impl;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;
import cn.jiege.starGraph.core.pojo.User;
import cn.jiege.starGraph.core.pojo.UserResult;
import cn.jiege.starGraph.core.mapper.UserResultMapper;
import cn.jiege.starGraph.core.service.IUserResultService;
import cn.jiege.starGraph.core.utils.UserUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 生成的结果信息表 服务实现类
 * </p>
 *
 * @author ysjiege
 * @since 2025-08-20
 */
@Service
public class UserResultServiceImpl extends ServiceImpl<UserResultMapper, UserResult> implements IUserResultService {

    @Override
    @Transactional
    public void insertUserResult(ComfyuiTask task, List<String> urls) {
        List<UserResult> userResultList = urls.stream().map(url -> {
            UserResult userResult = new UserResult();
            userResult.setCreatedTime(LocalDateTime.now());
            userResult.setCollect(0);
            userResult.setUrl(url);
            userResult.setUserId(task.getUserId());
            return userResult;
        }).toList();
        saveBatch(userResultList);
    }
}
