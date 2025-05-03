package com.tradewing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "my.secret.jwt=rtki7P9/Nr+1ZgZogLo/h87DmNLocYoc0OTHKFlsYC40"
})
class TradewingApplicationTests {

	@Test
	void contextLoads() {
	}

}
