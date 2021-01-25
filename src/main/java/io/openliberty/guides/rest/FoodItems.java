package io.openliberty.guides.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.FoodItem;

@ApplicationScoped
@Path("fooditems")
public class FoodItems {
	
	@Inject
	private FoodItemSessionFactory foodItemSessionFactory;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<FoodItem> getFoodItems() {
   	//TODO Use spring factory bean to get session 
   	Session session = foodItemSessionFactory.getSession();
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<FoodItem> criteria = builder.createQuery(FoodItem.class);
      criteria.from(FoodItem.class);
      List<FoodItem> data = session.createQuery(criteria).getResultList();
      return data;
   }
   
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public void addFoodItem(List<FoodItem> foodItems) {
   	Session session = foodItemSessionFactory.getSession();
   	session.beginTransaction();
   	foodItems.stream().forEach(fi -> session.save(fi));
   	session.getTransaction().commit();
   }
}
