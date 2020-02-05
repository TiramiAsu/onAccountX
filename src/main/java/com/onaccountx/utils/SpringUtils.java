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

import org.springframework.context.ApplicationContext;
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
public class SpringUtils {

	private static ApplicationContext context;

	static {
		context = new ClassPathXmlApplicationContext("spring.cfg.xml");
	}

	public SpringUtils() {
	}

	public static Object getBean(String beanId) {
		return context.getBean(beanId, Object.class);
	}

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
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
