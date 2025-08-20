package cn.jiege.starGraph.core.service;

import cn.jiege.starGraph.comfyui.client.dto.ComfyuiTask;
import cn.jiege.starGraph.core.pojo.UserResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 生成的结果信息表 服务类
 * </p>
 *
 * @author ysjiege
 * @since 2025-08-20
 */
public interface IUserResultService extends IService<UserResult> {

    void insertUserResult(ComfyuiTask task, List<String> urls);
}
