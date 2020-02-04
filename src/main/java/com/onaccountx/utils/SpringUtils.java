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
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.onaccountx.mvc.service.MemberService;

/**
 * <pre>
 * [Spring 工具] 2019-12-31 19:28
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context;
	
	static {
		context = new ClassPathXmlApplicationContext("spring.cfg.xml");
	}
	
	public SpringUtils() {}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T getBean(String beanId){
		return (T) context.getBean(beanId);
	}
	
	public static <T>T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}
	
	public static <T>T getBean(String beanId, Class<T> clazz){
		return context.getBean(beanId, clazz);
	}
	
	public static void main(String[] args) {
//		MemberService memberService = SpringUtils.getBean("memberService");
//		MemberService memberService = SpringUtils.getBean("memberService", MemberService.class);
		MemberService memberService = SpringUtils.getBean(MemberService.class);
		if (memberService == null) {
			System.out.println(">>> null <<<");
		} else {
			System.out.println(">>> Success <<<");
		}
	}
	
}
