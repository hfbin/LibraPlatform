package cn.hfbin.ucpm.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangfubin
 * @Description: LoginParams 类
 * @Date: 2021/6/10 12:34
 */
@Data
@ApiModel(value = "登录模块")
public class LoginParams {

    @ApiModelProperty(value = "code")
    private String code;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "password: 账号密码、mobile: 手机号、refresh_token: 刷新token、openId")
    private String grantType;
    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;
}
