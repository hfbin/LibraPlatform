package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: UserInfoVo 类
 * @Date: 2021/6/28
 */
@Data
@ApiModel(value = "用户信息响应对象")
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = -1L;

    private String name;

    private String mobile;

    private String avatar;

    private String introduction;

    private List<String> roles;

    private Long userId;

    private Long accountId;

    private String tenantCode;

    private Long deptId;

    private String deptCode;

    private Integer dataScope;

}
