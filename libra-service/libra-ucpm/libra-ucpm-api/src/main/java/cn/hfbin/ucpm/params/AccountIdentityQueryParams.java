package cn.hfbin.ucpm.params;

import cn.hfbin.common.core.enums.IdentityEnum;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: huangfubin
 * @Description: AccountParams 类
 * @Date: 2021/7/21
 */
@Data
public class AccountIdentityQueryParams {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 类型 GrantTypeConstant
     */
    @ApiModelProperty(value = "类型")
    private String type;

    /**
     * 身份类型
     */
    private IdentityEnum identityType;


}
