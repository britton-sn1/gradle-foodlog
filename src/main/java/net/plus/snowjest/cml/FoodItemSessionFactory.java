package net.plus.snowjest.cml;

import net.plus.snowjest.cml.model.FoodItem;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@ApplicationScoped
public class FoodItemSessionFactory {
	private SessionFactory sessionFactory;

	public static void main(String[] args) {
	}

	public Session getSession() {
		return getSessionFactory().openSession();
	}

	private SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				
				Configuration configuration = new Configuration();
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
				Properties configProps = configuration.getProperties();
				configProps.put("hibernate.dialect",
						"org.hibernate.dialect.MySQLDialect");
				configProps.put("hibernate.connection.driver_class",
						"com.mysql.jdbc.Driver");
				configProps.put("hibernate.connection.url",
						"jdbc:mysql://localhost:3306/foodlog?characterEncoding=latin1");
				
				configProps.put("hibernate.connection.username", System.getenv("hibernate_connection_username"));
				configProps.put("hbm2ddl.auto", "create");
				configProps.put("hibernate.connection.password",System.getenv("hibernate_connection_password"));

				builder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = builder.build();
				sessionFactory = configuration.configure()
						.addPackage("net.plus.snowjest.cml.model")
						.addAnnotatedClass(FoodItem.class)
						.buildSessionFactory(serviceRegistry);

				// factory = new AnnotationConfiguration().configure()
				// .addPackage("net.plus.snowjest.cml.model"). // add package if
				// // used.
				// addAnnotatedClass(FoodItem.class).buildSessionFactory();
			}
			catch (Throwable ex) {
				System.err.println("Failed to create sessionFactory object." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory;
	}
}
