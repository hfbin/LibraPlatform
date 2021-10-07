package cn.hfbin.gen.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.gen.entity.GenConfig;
import cn.hfbin.gen.service.GeneratorService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
@Api(value = "generator", tags = "代码生成模块")
public class GeneratorController {

	private final GeneratorService generatorService;

	/**
	 * 列表
	 * @param tableName 参数集
	 * @param dsName 数据源编号
	 * @return 数据库表
	 */
	@GetMapping("/page")
	public ResponseData<Page<List<Map<String, Object>>>> getPage(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
																  @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
																  @RequestParam(name = "keyword", required = false) String tableName,
																  @RequestParam(name = "dsName", required = false) String dsName) {
		return ResponseData.ok(generatorService.getPage(pageNo, pageSize, tableName, dsName));
	}

	/**
	 * 预览代码
	 * @param genConfig 数据表配置
	 * @return
	 */
	@GetMapping("/preview")
	public ResponseData<Map<String, String>> previewCode(GenConfig genConfig) {
		return ResponseData.ok(generatorService.previewCode(genConfig));
	}

	/**
	 * 生成代码
	 */
	@SneakyThrows
	@GetMapping("/code")
	public void generatorCode(GenConfig genConfig, HttpServletResponse response) {
		byte[] data = generatorService.generatorCode(genConfig);
		response.reset();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				String.format("attachment; filename=%s.zip", "GeneratorCode-" + DateUtil.now()));
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");

		IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
	}

}
