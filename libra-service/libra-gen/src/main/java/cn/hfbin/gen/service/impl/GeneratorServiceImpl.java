package cn.hfbin.gen.service.impl;

import cn.hfbin.gen.entity.GenConfig;
import cn.hfbin.gen.gen.CodeGen;
import cn.hfbin.gen.mapper.GeneratorMapper;
import cn.hfbin.gen.service.GeneratorService;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

	@Autowired
	private GeneratorMapper generatorMapper;


	/**
	 * 分页查询表
	 * @param tableName 表名
	 * @param dsName 数据源名字
	 * @return
	 */
	@Override
	@DS("#dsName")
	public Page<List<Map<String, Object>>> getPage(Integer pageNo, Integer pageSize, String tableName, String dsName) {
		Page<Object> page = new Page(pageNo, pageSize);
		return generatorMapper.queryList(page, tableName);
	}

	/**
	 * 预览代码
	 * @param genConfig 查询条件
	 * @return
	 */
	@Override
	public Map<String, String> previewCode(GenConfig genConfig) {
		String tableName = genConfig.getTableName().get(0);
		if(StrUtil.isBlank(tableName)){
			return MapUtil.empty();
		}
		// 查询表信息
		Map<String, String> table = generatorMapper.queryTable(tableName, genConfig.getDsName());
		// 查询列信息
		List<Map<String, String>> columns = generatorMapper.queryColumns(tableName, genConfig.getDsName());
		// 生成代码
		return CodeGen.generatorCode(genConfig, table, columns, null);
	}

	/**
	 * 生成代码
	 * @param genConfig 生成配置
	 * @return
	 */
	@Override
	public byte[] generatorCode(GenConfig genConfig) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		genConfig.getTableName().forEach(data -> {
			// 查询表信息
			Map<String, String> table = generatorMapper.queryTable(data, genConfig.getDsName());
			// 查询列信息
			List<Map<String, String>> columns = generatorMapper.queryColumns(data, genConfig.getDsName());
			// 生成代码
			CodeGen.generatorCode(genConfig, table, columns, zip);
		});
		IoUtil.close(zip);
		return outputStream.toByteArray();
	}

}
