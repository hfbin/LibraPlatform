package cn.hfbin.gen.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数数据源解析 @DS("#dsName)
 * 默认有三个职责链来处理动态参数解析器 header->session->spel
 * @DS("#session.tenantName")//从session获取
 * public List selectSpelBySession() {}
 *
 * @DS("#header.tenantName")//从header获取
 * public List selectSpelByHeader() {}
 *
 * @DS("#tenantName")//使用spel从参数获取
 * public List selectSpelByKey(String tenantName) {}
 *
 * @DS("#user.tenantName")//使用spel从复杂参数获取
 * public List selecSpelByTenant(User user) {}
 */
public class DsHeaderProcessor extends DsProcessor {

	private static final String HEADER_PREFIX  = "#dsName";

	/**
	 * 抽象匹配条件 匹配才会走当前执行器否则走下一级执行器
	 * @param key DS注解里的内容
	 * @return 是否匹配
	 */
	@Override
	public boolean matches(String key) {
		return key.startsWith(HEADER_PREFIX);
	}

	/**
	 * 抽象最终决定数据源（如果没有指定则走默认数据源）
	 * @param invocation 方法执行信息
	 * @param key DS注解里的内容
	 * @return 数据源名称
	 */
	@Override
	public String doDetermineDatasource(MethodInvocation invocation, String key) {
		Object[] arguments = invocation.getArguments();
		return String.valueOf(arguments[arguments.length - 1]);
	}


	/**
	 *  多租户可通过header处理方式
	 * @param invocation
	 * @param key
	 * @return
	 */
//	@Override
//	public String doDetermineDatasource(MethodInvocation invocation, String key) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		return request.getHeader(key.substring(8));
//	}

}
