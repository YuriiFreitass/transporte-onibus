package com.yurifreitas.transporte_onibus;


import com.yurifreitas.transporte_onibus.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfig.class)
public class TransporteOnibusApplicationIT {

	@Test
	void deveCarregarContextoComMySqlContainer() {

	}
}
