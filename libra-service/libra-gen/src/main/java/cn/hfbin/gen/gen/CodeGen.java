package cn.hfbin.gen.gen;

import cn.hfbin.gen.entity.ColumnEntity;
import cn.hfbin.gen.entity.GenConfig;
import cn.hfbin.gen.entity.TableEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Slf4j
@UtilityClass //标记静态方法类
public class CodeGen {

	private final String FEIGN_CLIENT_JAVA_VM = "FeignClient.java.vm";

	private final String FEIGN_SERVICE_CONTROLLER_JAVA_VM = "ApiController.java.vm";

	private final String ENTITY_JAVA_VM = "Entity.java.vm";

	private final String MAPPER_JAVA_VM = "Mapper.java.vm";

	private final String SERVICE_JAVA_VM = "Service.java.vm";

	private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";

	private final String CONTROLLER_JAVA_VM = "Controller.java.vm";

	private final String MAPPER_XML_VM = "Mapper.xml.vm";

	private final String MENU_SQL_VM = "menu.sql.vm";

	private final String VUE_INDEX_VUE_VM = "vue/index.vue.vm";

	private final String VUE_API_JS_VM = "vue/api.js.vm";

	private final String VUE_CONSTANTS_JS_VM = "vue/constants.js.vm";
	
	private final String PROJECT = "libra-platform";

	/**
	 * 配置
	 * @return
	 */
	private List<String> getTemplates() {
		List<String> templates = new ArrayList<>();
		templates.add("template/FeignClient.java.vm");
		templates.add("template/ApiController.java.vm");
		templates.add("template/Entity.java.vm");
		templates.add("template/Mapper.java.vm");
		templates.add("template/Mapper.xml.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		templates.add("template/Controller.java.vm");
		templates.add("template/menu.sql.vm");
		templates.add("template/vue/api.js.vm");
		templates.add("template/vue/index.vue.vm");
		templates.add("template/vue/constants.js.vm");
		return templates;
	}

	/**
	 * 生成代码
	 * @param genConfig
	 * @param table
	 * @param columns
	 * @param zip
	 * @return
	 *
	 *  若不使用@SneakyThrows注解，newInsstance方法会要求抛出InstantiationException,
	 *  IllegalAccessException异常，且调用sneakyThrowsTest()的地方需要捕获这些异常，
	 *  加上@SneakyThrows注解之后就不需要捕获异常信息。
	 *
	 */
	@SneakyThrows
	public Map<String, String> generatorCode(GenConfig genConfig, Map<String, String> table,
											 List<Map<String, String>> columns, ZipOutputStream zip) {
		// 配置信息
		Configuration config = getConfig();
		boolean hasBigDecimal = false;
		// 表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));

		if (StrUtil.isNotBlank(genConfig.getComments())) {
			tableEntity.setComments(genConfig.getComments());
		}
		else {
			tableEntity.setComments(table.get("tableComment"));
		}

		String tablePrefix;
		if (StrUtil.isNotBlank(genConfig.getTablePrefix())) {
			tablePrefix = genConfig.getTablePrefix();
		}
		else {
			tablePrefix = config.getString("tablePrefix");
		}

		// 表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), tablePrefix);
		tableEntity.setCaseClassName(className);
		tableEntity.setLowerClassName(StringUtils.uncapitalize(className));

		// 列信息
		List<ColumnEntity> columnList = new ArrayList<>();
		for (Map<String, String> column : columns) {
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));
			columnEntity.setNullable("NO".equals(column.get("isNullable")));
			columnEntity.setColumnType(column.get("columnType"));
			columnEntity.setHidden(Boolean.FALSE);
			// 列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setCaseAttrName(attrName);
			columnEntity.setLowerAttrName(StringUtils.uncapitalize(attrName));

			// 列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
				hasBigDecimal = true;
			}
			// 是否主键
			if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
				tableEntity.setPk(columnEntity);
			}

			columnList.add(columnEntity);
		}
		tableEntity.setColumns(columnList);

		// 没主键，则第一个字段为主键
		if (tableEntity.getPk() == null) {
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		// 封装模板数据
		Map<String, Object> map = new HashMap<>(16);
		map.put("tableName", tableEntity.getTableName());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getCaseClassName());
		map.put("classname", tableEntity.getLowerClassName());
		map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("hasBigDecimal", hasBigDecimal);
		map.put("datetime", DateUtil.today());
		map.put("serviceName", genConfig.getServiceName());
		map.put("clientCode", genConfig.getClientCode());

		if (StrUtil.isNotBlank(genConfig.getComments())) {
			map.put("comments", genConfig.getComments());
		}
		else {
			map.put("comments", tableEntity.getComments());
		}

		if (StrUtil.isNotBlank(genConfig.getAuthor())) {
			map.put("author", genConfig.getAuthor());
		}
		else {
			map.put("author", config.getString("author"));
		}

		if (StrUtil.isNotBlank(genConfig.getModuleName())) {
			map.put("moduleName", genConfig.getModuleName());
		}
		else {
			map.put("moduleName", config.getString("moduleName"));
		}

		if (StrUtil.isNotBlank(genConfig.getPackageName())) {
			map.put("package", genConfig.getPackageName());
			map.put("mainPath", genConfig.getPackageName());
		}
		else {
			map.put("package", config.getString("package"));
			map.put("mainPath", config.getString("mainPath"));
		}

		// 渲染数据
		return renderData(zip, tableEntity, map);
	}

	/**
	 * 渲染数据
	 * @param zip 流 （为空，直接返回Map）
	 * @param tableEntity 表基本信息
	 * @param map 模板参数
	 * @return map key-filename value-contents
	 * @throws IOException
	 */
	private Map<String, String> renderData(ZipOutputStream zip,
			TableEntity tableEntity, Map<String, Object> map) throws IOException {
		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		VelocityContext context = new VelocityContext(map);

		// 获取模板列表
		List<String> templates = getTemplates();
		Map<String, String> resultMap = new HashMap<>(16);

		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
			tpl.merge(context, sw);

			// 添加到zip
			String fileName = getFileName(template, tableEntity.getCaseClassName(), map.get("package").toString(),
					map.get("moduleName").toString());

			if (zip != null) {
				zip.putNextEntry(new ZipEntry(Objects.requireNonNull(fileName)));
				IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
				IoUtil.close(sw);
				zip.closeEntry();
			}
			resultMap.put(template, sw.toString());
		}

		return resultMap;
	}

	/**
	 * 列名转换成Java属性名
	 */
	public String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 * @param tableName
	 * @param tablePrefix
	 * @return
	 */
	private String tableToJava(String tableName, String tablePrefix) {
		if (StringUtils.isNotBlank(tablePrefix)) {
			tableName = tableName.replaceFirst(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	private Configuration getConfig() {
		try {
			return new PropertiesConfiguration("generator.properties");
		}
		catch (ConfigurationException e) {
			throw new RuntimeException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	private String getFileName(String template, String className, String packageName, String moduleName) {
		String packagePath = PROJECT + File.separator + "src" + File.separator + "main"
				+ File.separator + "java" + File.separator;

		if (StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
		}

		if (template.contains(FEIGN_CLIENT_JAVA_VM)) {
			return packagePath + "client" + File.separator + className + "ServiceClient.java";
		}

		if (template.contains(FEIGN_SERVICE_CONTROLLER_JAVA_VM)) {
			return packagePath + "api" + File.separator + className + "ApiService.java";
		}

		if (template.contains(ENTITY_JAVA_VM)) {
			return packagePath + "entity" + File.separator + className + ".java";
		}

		if (template.contains(MAPPER_JAVA_VM)) {
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}

		if (template.contains(SERVICE_JAVA_VM)) {
			return packagePath + "service" + File.separator + className + "Service.java";
		}

		if (template.contains(SERVICE_IMPL_JAVA_VM)) {
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		if (template.contains(CONTROLLER_JAVA_VM)) {
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		if (template.contains(MAPPER_XML_VM)) {
			return PROJECT + File.separator + "src" + File.separator + "main" + File.separator
					+ "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
		}

		if (template.contains(MENU_SQL_VM)) {
			return className.toLowerCase() + "_menu.sql";
		}

		if (template.contains(VUE_INDEX_VUE_VM)) {
			return PROJECT + File.separator + "src" + File.separator + "views"
					+ File.separator + moduleName + File.separator + className.toLowerCase() + File.separator
					+ "index.vue";
		}

		if (template.contains(VUE_API_JS_VM)) {
			return PROJECT + File.separator + "src" + File.separator + "views"
					+ File.separator + "api" + File.separator
					+ className.toLowerCase() + ".js";
		}

		if (template.contains(VUE_CONSTANTS_JS_VM)) {
			return PROJECT + File.separator + "src" + File.separator + "views"
					+ File.separator + moduleName + File.separator + className.toLowerCase()
					+ File.separator + "js" + File.separator + "constants.js";
		}
		return null;
	}

}
