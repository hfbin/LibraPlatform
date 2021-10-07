package cn.hfbin.base;

import cn.hfbin.ucpm.entity.Depart;
import cn.hfbin.ucpm.enums.RelationTypeEnum;
import cn.hfbin.ucpm.params.RelationRoleParams;
import cn.hfbin.ucpm.service.DepartService;
import cn.hfbin.ucpm.service.RelationRoleService;
import cn.hfbin.ucpm.vo.RelationRoleVo;
import cn.hfbin.common.redis.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LibraUcPmServiceApplicationTests {

	@Autowired
	private RelationRoleService relationRoleService;

	@Autowired
	private RedisUtil redisUtil;


	@Autowired
	private DepartService departService;


	@Test
	public void test() {

		RelationRoleParams relationRoleParams = new RelationRoleParams();
		relationRoleParams.setId(123L);
		relationRoleParams.setRelationTypeEnum(RelationTypeEnum.USER_ID);
		List<RelationRoleVo> baseRoles = relationRoleService.selectList(relationRoleParams);
	}

	@Test
	public void redisTest(){
		redisUtil.strSet("libra::test", "12234");
		RelationRoleParams relationRoleParams = new RelationRoleParams();
		relationRoleParams.setId(123L);
		relationRoleParams.setRelationTypeEnum(RelationTypeEnum.USER_ID);
		redisUtil.strSet("libra::object", relationRoleParams);
	}

	@Test
	public void setBaseDepartService(){
		Depart depart = new Depart();
		depart.setDeptName("测试部门");
		depart.setTenantCode("JACK");
		departService.insertIgnoreTr(depart);
		System.out.println(JSONObject.toJSONString(depart));
	}


}
