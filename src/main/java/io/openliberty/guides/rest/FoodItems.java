package io.openliberty.guides.rest;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import net.plus.snowjest.cml.Main;
import net.plus.snowjest.cml.model.FoodItem;

@Path("fooditems")
public class FoodItems {

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<FoodItem> getFoodItems() {
   	Session session = Main.getSession();
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<FoodItem> criteria = builder.createQuery(FoodItem.class);
      criteria.from(FoodItem.class);
      List<FoodItem> data = session.createQuery(criteria).getResultList();
      return data;
   }
   
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public void addFoodItem(List<FoodItem> foodItems) {
   	Session session = Main.getSession();
   	session.beginTransaction();
   	foodItems.stream().forEach(fi -> session.save(fi));
   	session.getTransaction().commit();
   }
}
