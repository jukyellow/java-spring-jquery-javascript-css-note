package com.test.annotation;

import org.springframework.stereotype.Component;

public abstract class CommonAnnoJob extends Thread{
    private boolean isRunning = false;
    private Object lockObj = new Object();
    
	public boolean isAvailable() {
		synchronized (lockObj) {
			if(!isRunning) {
				isRunning = true;
				return true;
			} else {
				return false;
			}
		}
	}
	
	abstract public String doJob() throws Exception;
}
