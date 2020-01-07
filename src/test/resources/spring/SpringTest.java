package resources.spring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.onaccountx.mvc.model.dao.MemberDAO;

/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */

/**
 * <pre>
 * [Spring Test] 2020-01-04 15:52
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@SuppressWarnings("resource")
public class SpringTest {

	public static void main(String[] args) {
//		MemberDAO memberDAO = new MemberDAOImpl();
		ApplicationContext context = new ClassPathXmlApplicationContext("./spring.cfg.xml");
		MemberDAO memberDAO = (MemberDAO)context.getBean("txp_memberDAO");
		check(memberDAO);
	}
	
	public static void check(Object o) {
		if (o == null) {
			System.out.println(o);
			System.out.println(">>> Check Failed <<<");
		} else {
			System.out.println(o);
			System.out.println(">>> Check Success <<<");
		}
	}

}
