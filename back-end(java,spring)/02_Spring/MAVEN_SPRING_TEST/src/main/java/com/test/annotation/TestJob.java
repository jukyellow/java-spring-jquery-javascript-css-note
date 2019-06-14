package com.test.annotation;

import org.springframework.stereotype.Component;

@Component
public class TestJob extends CommonAnnoJob {

	@Override
	public String doJob() throws Exception {
		System.out.println("TestJob.doJob() called!");
		return "OK!";
	}

}
