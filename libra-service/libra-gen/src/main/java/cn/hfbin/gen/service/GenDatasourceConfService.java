package cn.hfbin.gen.service;


import cn.hfbin.gen.entity.GenDatasourceConf;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 数据源表
 */
public interface GenDatasourceConfService {

	/**
	 * 保存数据源并且加密
	 * @param genDatasourceConf
	 * @return
	 */
	Boolean saveDsByEnc(GenDatasourceConf genDatasourceConf);

	/**
	 * 更新数据源
	 * @param genDatasourceConf
	 * @return
	 */
	Boolean updateDsByEnc(GenDatasourceConf genDatasourceConf);

	/**
	 * 更新动态数据的数据源列表
	 * @param datasourceConf
	 * @return
	 */
	void addDynamicDataSource(GenDatasourceConf datasourceConf);

	/**
	 * 校验数据源配置是否有效
	 * @param datasourceConf 数据源信息
	 * @return 有效/无效
	 */
	Boolean checkDataSource(GenDatasourceConf datasourceConf);

	/**
	 * 通过数据源名称删除
	 * @param dsId 数据源ID
	 * @return
	 */
	Boolean removeByDsId(Long dsId);

	/**
	 *
	 * @description 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param datasourceConf
	 * @author huangfubin
	 * @date 2021/8/26
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.hfbin.gen.entity.GenDatasourceConf>
	 */
	Page<GenDatasourceConf> page(Integer pageNo, Integer pageSize, GenDatasourceConf datasourceConf);

	/**
	 *
	 * @description 查询列表
	 * @param
	 * @author huangfubin
	 * @date 2021/8/26
	 * @return java.util.List<cn.hfbin.gen.entity.GenDatasourceConf>
	 */
	List<GenDatasourceConf> list(GenDatasourceConf conf);

	/**
	 *
	 * @description 根据id查询
	 * @param id
	 * @author huangfubin
	 * @date 2021/8/26
	 * @return cn.hfbin.gen.entity.GenDatasourceConf
	 */
	GenDatasourceConf getById(Long id);
}
