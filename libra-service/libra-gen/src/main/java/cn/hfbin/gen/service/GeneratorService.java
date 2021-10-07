package cn.hfbin.gen.service;

import cn.hfbin.gen.entity.GenConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public interface GeneratorService {

	/**
	 * 生成代码
	 * @param tableNames 表名称
	 * @return
	 */
	byte[] generatorCode(GenConfig tableNames);

	/**
	 * 分页查询表
	 * @param pageNo , pageSize分页信息
	 * @param tableName 表名
	 * @param name 数据源ID
	 * @return
	 */
	Page<List<Map<String, Object>>> getPage(Integer pageNo, Integer pageSize, String tableName, String name);

	/**
	 * 预览代码
	 * @param genConfig 查询条件
	 * @return
	 */
	Map<String, String> previewCode(GenConfig genConfig);

}
