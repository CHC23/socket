package com.chc.websocket.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext ctx; 
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringUtil.ctx==null){
			SpringUtil.ctx = applicationContext;
		}
	}
	
	public static ApplicationContext getApplicationContext() {
        return ctx;
    }
	
	public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

	public static <T> T getBean(String beanName,Class<T> clazz) {
		return getApplicationContext().getBean(beanName,clazz);
	}
}
