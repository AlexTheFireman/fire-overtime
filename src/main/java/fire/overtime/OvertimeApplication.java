package fire.overtime;

import fire.overtime.models.Firefighter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OvertimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvertimeApplication.class, args);
		try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
			Session session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			Firefighter firefighter = new Firefighter();
			firefighter.setFirst_name("5");
			firefighter.setLast_name("6");
			firefighter.setPatronymic("7");
			firefighter.setPosition("8");

			session.save(firefighter);
			session.getTransaction().commit();
		} catch (Throwable cause) {
			cause.printStackTrace();
		}

	}
//
	}

