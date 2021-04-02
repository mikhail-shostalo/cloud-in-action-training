package com.training.cloud;

import com.training.cloud.application.controller.HealthCheckController;
import com.training.cloud.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CloudApplicationTests extends AbstractControllerTest {

	@Autowired
	private HealthCheckController healthCheckController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
		assertThat(healthCheckController).isNotNull();
	}

	@Test
	public void shouldReturnMessageFromProperties() throws Exception {
		mockMvc.perform(get("/health-check")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("Test running"));
	}

}
