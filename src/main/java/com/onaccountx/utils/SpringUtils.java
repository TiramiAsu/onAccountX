/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.onaccountx.mvc.service.MemberService;

/**
 * <pre>
 * [Spring 工具] 2019-12-31 19:28
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context = new FileSystemXmlApplicationContext("./src/main/resources/spring.cfg.xml");

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.context = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T getBean(String beanName){
		return (T) SpringUtils.context.getBean(beanName);
	}
	
	public static void main(String[] args) {
		MemberService memberService = SpringUtils.getBean("memberService");
		if (memberService == null) {
			System.out.println(">>> null <<<");
		} else {
			System.out.println(">>> Success <<<");
		}
	}
	
}
