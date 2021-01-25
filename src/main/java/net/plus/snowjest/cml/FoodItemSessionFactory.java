package net.plus.snowjest.cml;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import net.plus.snowjest.cml.model.FoodItem;
import net.plus.snowjest.cml.model.Meal;

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
				configuration.configure();

				Properties configProps = configuration.getProperties();
				configProps.put("hibernate.connection.username",
						System.getenv("hibernate_connection_username"));
				configProps.put("hibernate.connection.password",
						System.getenv("hibernate_connection_password"));

				builder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = builder.build();
				sessionFactory = configuration
						.addPackage("net.plus.snowjest.cml.model")
						.addAnnotatedClass(FoodItem.class)
						.addAnnotatedClass(Meal.class)
						// .addAnnotatedClass(MealFoodItem.class)
						.buildSessionFactory(serviceRegistry);

			}
			catch (Throwable ex) {
				System.err.println("Failed to create sessionFactory object." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory;
	}
}
