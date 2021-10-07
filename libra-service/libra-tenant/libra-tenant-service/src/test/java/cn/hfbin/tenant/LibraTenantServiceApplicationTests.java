package cn.hfbin.tenant;

import cn.hfbin.tenant.entity.TrTenant;
import cn.hfbin.tenant.service.TrTenantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LibraTenantServiceApplicationTests {

	@Autowired
	TrTenantService trTenantService;

	@Test
	void contextLoads() {
		List<Long> list = trTenantService.selectMenu("HDDC");
		System.out.println(list);
	}

}
