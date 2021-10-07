package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: EmployeeParams 类
 * @Date: 2021/7/7
 */
@Data
@ApiModel(value = "列表返回实体")
public class IdentityInfoVo {
    /**
     * 身份对应id
     */
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * accountId
     */
    @ApiModelProperty(value = "accountId")
    private Long accountId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
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
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;
    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;
    /**
     * 工号
     */
    @ApiModelProperty(value = "工号")
    private String workNo;
    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private Integer cardType;
    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号")
    private String weChatNo;
    /**
     * 证件号
     */
    @ApiModelProperty(value = "证件号")
    private String cardNo;
    /**
     * 入职日期
     */
    @ApiModelProperty(value = "入职日期")
    private LocalDateTime entryDate;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色名")
    private String roleNames;

    /**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id")
    private String positionName;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private String deptName;

    /**
     * deptCode
     */
    @ApiModelProperty(value = "deptCode")
    private String deptCode;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色ids, 格式：001,002,003")
    private String roleIds;

    /**
     * 角色ids
     */
    @ApiModelProperty(value = "角色ids")
    private List<Long> roleListIds;

    /**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id")
    private Long positionId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;
    /**
     * 在职状态(0-在职,1-离职)
     */
    @ApiModelProperty(value = "在职状态(0-在职,1-离职)")
    private Integer positionStatus;
    /**
     * 身份状态(0-禁用1-启用)
     */
    @ApiModelProperty(value = "身份状态(0-禁用1-启用)")
    private Integer status;
    /**
     * 系统默认数据(0-可操作,1-不可操作)
     */
    @ApiModelProperty(value = "系统默认数据(0-可操作,1-不可操作)")
    private Integer systemDefault;
    /**
     * 租户编码
     */
    @ApiModelProperty(value = "租户编码")
    private String tenantCode;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
