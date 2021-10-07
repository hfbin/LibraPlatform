package cn.hfbin.gen.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态数据源切换配置
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

	/**
	 * 动态配置
	 * @param stringEncryptor
	 * @param properties
	 * @return
	 */
	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider(StringEncryptor stringEncryptor,
															   DataSourceProperties properties) {
		return new JdbcDynamicDataSourceProvider(stringEncryptor, properties);
	}

	/**
	 * 可重写解析顺序的解析处理器
	 * @return
	 */
	@Bean
	public DsProcessor dsProcessor() {
		return new DsHeaderProcessor();
	}

}
