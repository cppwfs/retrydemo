/*
 * Copyright 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.spring.demo;


import java.net.UnknownHostException;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RiverInfo {

//	@Retryable(
//			value = {UnknownHostException.class, Exception.class},
//			maxAttempts = 3,
//			backoff = @Backoff(delay = 1000))
	public void retryService() throws Exception {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		System.out.println("Retrieving River Data...");
		httpRequestFactory.setReadTimeout(1000);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		String result = restTemplate.getForObject("https://waterservices.usgs.gov/nwis/stat/?format=rdb&sites=02335757&statReportType=daily&statTypeCd=all", String.class);
		System.out.println(result);
//		throw new Exception("ugh");
	}

	@Recover
	public void recover(Exception exception) {
		System.out.println("Probably a raging river of death.  Stay home and work on your Reactive talk");
	}
}
