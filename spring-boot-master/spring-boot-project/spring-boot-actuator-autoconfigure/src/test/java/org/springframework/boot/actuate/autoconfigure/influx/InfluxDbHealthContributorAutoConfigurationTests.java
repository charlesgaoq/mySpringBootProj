/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.autoconfigure.influx;

import org.influxdb.InfluxDB;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.influx.InfluxDbHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link InfluxDbHealthContributorAutoConfiguration}.
 *
 * @author Eddú Meléndez
 */
class InfluxDbHealthContributorAutoConfigurationTests {

	private ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withBean(InfluxDB.class, () -> mock(InfluxDB.class)).withConfiguration(AutoConfigurations
					.of(InfluxDbHealthContributorAutoConfiguration.class, HealthContributorAutoConfiguration.class));

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(InfluxDbHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.influxdb.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(InfluxDbHealthIndicator.class));
	}

}
