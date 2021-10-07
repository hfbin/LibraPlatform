package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/8/8 10:32 下午
 * @description: 账号身份信息实体
 */
@Data
public class AccountVo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "身份id")
    private Long identityId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 账户状态(0-禁用1-启用,如果账号被禁用,账号所关联所有身份将无法登录)
     */
    @ApiModelProperty(value = "账户状态(0-禁用1-启用,如果账号被禁用,账号所关联所有身份将无法登录)")
    private Integer accountStatus;

    /**
     * 身份状态(0-禁用1-启用,如果账号被禁用,账号所关联所有身份将无法登录)
     */
    @ApiModelProperty(value = "身份状态(0-禁用1-启用,如果账号被禁用,账号所关联所有身份将无法登录)")
    private Integer identityStatus;
}
