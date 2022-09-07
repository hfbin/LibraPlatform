package cn.hfbin.gen.service.impl;

import cn.hfbin.common.core.context.SpringContextUtils;
import cn.hfbin.common.core.exception.LibraException;
import cn.hfbin.gen.entity.GenDatasourceConf;
import cn.hfbin.gen.enums.GenExceptionCode;
import cn.hfbin.gen.mapper.GenDatasourceConfMapper;
import cn.hfbin.gen.service.GenDatasourceConfService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据源表
 */
@Slf4j
@Service
public class GenDatasourceConfServiceImpl extends ServiceImpl<GenDatasourceConfMapper, GenDatasourceConf> implements GenDatasourceConfService {

	@Autowired
	private StringEncryptor stringEncryptor;

	@Autowired
	private DataSourceCreator hikariDataSourceCreator;



	/**
	 * 保存数据源并且加密
	 * @param conf
	 * @return
	 */
	@Override
	public Boolean saveDsByEnc(GenDatasourceConf conf) {
		// 校验配置合法性
		checkDataSource(conf);

		// 添加动态数据源
		addDynamicDataSource(conf);

		// 更新数据库配置
		conf.setPassword(stringEncryptor.encrypt(conf.getPassword()));
		this.baseMapper.insert(conf);
		return Boolean.TRUE;
	}

	/**
	 * 更新数据源
	 * @param conf 数据源信息
	 * @return
	 */
	@Override
	public Boolean updateDsByEnc(GenDatasourceConf conf) {
		checkDataSource(conf);
		// 先移除
		SpringContextUtils.getBean(DynamicRoutingDataSource.class)
				.removeDataSource(baseMapper.selectById(conf.getId()).getName());

		// 再添加
		addDynamicDataSource(conf);

		// 更新数据库配置
		if (StrUtil.isNotBlank(conf.getPassword())) {
			conf.setPassword(stringEncryptor.encrypt(conf.getPassword()));
		}
		this.baseMapper.updateById(conf);
		return Boolean.TRUE;
	}

	/**
	 * 通过数据源名称删除
	 * @param dsId 数据源ID
	 * @return
	 */
	@Override
	public Boolean removeByDsId(Long dsId) {
		SpringContextUtils.getBean(DynamicRoutingDataSource.class)
				.removeDataSource(baseMapper.selectById(dsId).getName());
		this.baseMapper.deleteById(dsId);
		return Boolean.TRUE;
	}

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
	@Override
	public Page<GenDatasourceConf> page(Integer pageNo, Integer pageSize, GenDatasourceConf datasourceConf) {
		Page<GenDatasourceConf> page = new Page<>(pageNo, pageSize);
		return baseMapper.selectPage(page, Wrappers.lambdaQuery(datasourceConf));
	}

	/**
	 *
	 * @description 查询列表
	 * @param conf
	 * @author huangfubin
	 * @date 2021/8/26
	 * @return java.util.List<cn.hfbin.gen.entity.GenDatasourceConf>
	 */
	@Override
	public List<GenDatasourceConf> list(GenDatasourceConf conf) {
		return baseMapper.selectList(null);
	}

	/**
	 *
	 * @description 根据id查询
	 * @param id
	 * @author huangfubin
	 * @date 2021/8/26
	 * @return cn.hfbin.gen.entity.GenDatasourceConf
	 */
	@Override
	public GenDatasourceConf getById(Long id) {
		return baseMapper.selectById(id);
	}


	/**
	 * 添加动态数据源
	 * @param conf 数据源信息
	 */
	@Override
	public void addDynamicDataSource(GenDatasourceConf conf) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		// 连接池名称
		dataSourceProperty.setPoolName(conf.getName());
		dataSourceProperty.setUrl(conf.getUrl());
		dataSourceProperty.setUsername(conf.getUsername());
		dataSourceProperty.setPassword(conf.getPassword());
		dataSourceProperty.setLazy(true);
		dataSourceProperty.setDriverClassName("com.mysql.cj.jdbc.Driver");
		DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
		SpringContextUtils.getBean(DynamicRoutingDataSource.class).addDataSource(dataSourceProperty.getPoolName(),
				dataSource);
	}

	/**
	 * 校验数据源配置是否有效
	 * @param conf 数据源信息
	 * @return true有效、false无效
	 */
	@Override
	public Boolean checkDataSource(GenDatasourceConf conf) {
		try {
			DriverManager.getConnection(conf.getUrl(), conf.getUsername(), conf.getPassword());
		}
		catch (SQLException e) {
			log.error("数据源配置 {} , 获取链接失败", conf.getName(), e);
			throw new LibraException(GenExceptionCode.DS_ERROR);
		}
		return Boolean.TRUE;
	}

}
