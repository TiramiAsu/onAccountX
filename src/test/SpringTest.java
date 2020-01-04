import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
		ApplicationContext context = new FileSystemXmlApplicationContext("./src/main/resources/spring.cfg.xml");
		MemberDAO memberDAO = (MemberDAO)context.getBean("memberDAO");
		check(memberDAO);
		
	}
	
	public static void check(Object o) {
		if (o == null) {
			System.out.println("Failed");
		} else {
			System.out.println("Success");
		}
		System.out.println("Finish");
	}

}
