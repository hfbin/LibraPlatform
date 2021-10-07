package cn.hfbin.gen.controller;

import cn.hfbin.common.core.api.ResponseData;
import cn.hfbin.gen.entity.GenDatasourceConf;
import cn.hfbin.gen.service.GenDatasourceConfService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ds")
@Api(tags = "数据源管理模块")
public class GenDsConfController {

	private final GenDatasourceConfService datasourceConfService;

	/**
	 * 分页查询
	 * @param datasourceConf 数据源表
	 * @return
	 */
	@GetMapping("/page")
	public ResponseData<Page<GenDatasourceConf>> getSysDatasourceConfPage(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
												 @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
												 GenDatasourceConf datasourceConf) {
		return ResponseData.ok(datasourceConfService.page(pageNo, pageSize, datasourceConf));
	}

	/**
	 * 查询全部数据源
	 * @return
	 */
	@GetMapping("/list")
	public ResponseData<List<GenDatasourceConf>> list() {
		return ResponseData.ok(datasourceConfService.list(null));
	}

	/**
	 * 通过id查询数据源表
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	public ResponseData<GenDatasourceConf> getById(@PathVariable("id") Long id) {
		return ResponseData.ok(datasourceConfService.getById(id));
	}

	/**
	 * 新增数据源表
	 * @param datasourceConf 数据源表
	 * @return R
	 */
	@PostMapping("save")
	public ResponseData<Boolean> save(@RequestBody GenDatasourceConf datasourceConf) {
		return ResponseData.ok(datasourceConfService.saveDsByEnc(datasourceConf));
	}

	/**
	 * 修改数据源表
	 * @param conf 数据源表
	 * @return R
	 */
	@PutMapping("/edit")
	public ResponseData<Boolean> updateById(@RequestBody GenDatasourceConf conf) {
		return ResponseData.ok(datasourceConfService.updateDsByEnc(conf));
	}

	/**
	 * 通过id删除数据源表
	 * @param id id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public ResponseData<Boolean> removeById(@PathVariable Long id) {
		return ResponseData.ok(datasourceConfService.removeByDsId(id));
	}

}
