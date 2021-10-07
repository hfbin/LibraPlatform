package cn.hfbin.gen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据源表
 *
 */
@Data
@TableName("gen_datasource_conf")
public class GenDatasourceConf{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * jdbc url
	 */
	private String url;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 创建人
	 */
	private Long create_by;

	/**
	 * 创建时间
	 */
    private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	private Long update_by;

	/**
	 * 更新
	 */
    private LocalDateTime updateTime;

	/**
	 * 删除标记
	 */
	@TableLogic
	private String delFlag;

}
