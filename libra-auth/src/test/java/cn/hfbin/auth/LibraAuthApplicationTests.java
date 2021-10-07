package cn.hfbin.auth;

import cn.hfbin.ucpm.client.AccountServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class LibraAuthApplicationTests {
	@Autowired
	private AccountServiceClient baseAccountServiceClient;
	@Test
	void contextLoads() {
		baseAccountServiceClient.getById(31312312L);

	}

}
