package cn.hfbin.gen.entity;

import lombok.Data;

import java.util.List;

/**
 * 生成配置
 */
@Data
public class GenConfig {

	/**
	 * 数据源name
	 */
	private String dsName;

	/**
	 * 包名
	 */
	private String packageName;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 表前缀（如果填了表名前缀匹配则生成的代码会将前缀去除）
	 */
	private String tablePrefix;

	/**
	 * 表名称
	 */
	private List<String> tableName;

	/**
	 * 表备注（若不填则以表说明为准）
	 */
	private String comments;

	/**
	 * 服务名
	 */
	private String serviceName;

	/**
	 * 客户端
	 */
	private String clientCode;

}
