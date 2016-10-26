package com.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.base.util.EasyApplicationContextUtils;

@SuppressWarnings("unchecked")
@Component
public class ParameterInitializer implements ApplicationContextAware
{
    private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
		System.out.println("初始化EasyApplicationContextUtils");
		EasyApplicationContextUtils.setApplicationContext(this.applicationContext);
	}

}
