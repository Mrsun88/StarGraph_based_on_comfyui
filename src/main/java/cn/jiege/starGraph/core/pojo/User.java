package cn.jiege.starGraph.core.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author ysjiege
 * @since 2025-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sg_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 手机
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 登录密码
     */
    @TableField("password")
    private String password;

    /**
     * 状态 0 正常  1超时锁定  2锁定  9无效 
     */
    @TableField("status")
    private Integer status;

    /**
     * 姓名
     */
    @TableField("username")
    private String username;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 会员等级
     */
    @TableField("vip_level")
    private Integer vipLevel;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别
     */
    @TableField("gender")
    private Integer gender;


}
