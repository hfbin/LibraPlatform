package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangfubin
 * @Description: AccountParams 类
 * @Date: 2021/7/21
 */
@Data
public class AccountParams {

    /**
     * accountId
     */
    @ApiModelProperty(value = "accountId")
    private Long id;
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
     * 手机号前缀(可扩展其它国家前缀,默认1大陆+86)
     */
    @ApiModelProperty(value = "手机号前缀(可扩展其它国家前缀,默认1大陆+86)")
    private Integer mobileType;
    /**
     * 邮箱账号
     */
    @ApiModelProperty(value = "邮箱账号")
    private String email;
    /**
     * 是否自动创建
     */
    @ApiModelProperty(value = "是否自动创建")
    private boolean autoCreate = false;
}
