package com.creativecoders.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.creativecoders.ecommerce.entity.Country;
import com.creativecoders.ecommerce.entity.Order;
import com.creativecoders.ecommerce.entity.Product;
import com.creativecoders.ecommerce.entity.ProductCategory;
import com.creativecoders.ecommerce.entity.State;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	private EntityManager entityManager;

	@Value("${allowed.origins}") // allowed.origins coming from application.properties
	private String[] theAllowedOrigins;

	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH };

		// Disable put, post and delete http methods
		disableHttpMethods(Product.class, config, theUnsupportedActions);

		disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

		disableHttpMethods(Country.class, config, theUnsupportedActions);

		disableHttpMethods(State.class, config, theUnsupportedActions);
		
		disableHttpMethods(Order.class, config, theUnsupportedActions);


		// call internal helper method
		exposeIds(config);

		// configure cors mapping
		cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
	}

	private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config,
			HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration().forDomainType(theClass)
				.withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		// get list of all entity classes from entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		// create array of entity types
		List<Class> entityClasses = new ArrayList<>();

		// get entity types for entities
		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}

		// expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);

		config.exposeIdsFor(domainTypes);
	}

}
