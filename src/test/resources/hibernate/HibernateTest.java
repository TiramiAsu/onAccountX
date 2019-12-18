/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package resources.hibernate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [Hibernate] 2019-12-18 01:37
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class HibernateTest {

	@Test
	final void testConfiguration() {
		String[] expecteds = new String[] {
				"jdbc:postgresql://127.0.0.1:5432/onaccountx",
				"org.hibernate.dialect.PostgreSQLDialect",
				"thread",
				"org.hibernate.cache.NoCacheProvider",
				"true"
				};
		Configuration cfg = new Configuration().configure();
		String[] actuals = new String[] {
				cfg.configure().getProperty("hibernate.connection.url"),
				cfg.configure().getProperty("hibernate.dialect"),
				cfg.configure().getProperty("current_session_context_class"),
				cfg.configure().getProperty("cache.provider_class"),
				cfg.configure().getProperty("show_sql")
				};
		/*
		System.out.println(cfg.configure().getProperty("hibernate.connection.url"));
		System.out.println(cfg.configure().getProperty("hibernate.dialect"));
		System.out.println(cfg.configure().getProperty("current_session_context_class"));
		System.out.println(cfg.configure().getProperty("cache.provider_class"));
		System.out.println(cfg.configure().getProperty("show_sql"));
		*/
		assertArrayEquals(expecteds, actuals);
	}

	@SuppressWarnings("deprecation")
	@Test
	final void testCreate() {
		Member member = new Member("Asu", "xxxx@gmail.com", "0987654321",
				new Date(), new Date());
		try {
			Configuration cfg = new Configuration().configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.getCurrentSession();
			Transaction tx = session.beginTransaction();
			session.save(member);
			tx.commit();
		} catch (Exception e) {
			fail(">>> Hibernate handle Error <<<");
		}
	}

}
