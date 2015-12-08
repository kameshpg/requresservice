package com.maximus.black.lab.persistence.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.internal.StandardServiceRegistryImpl;
/**
 * DataPersistenceUtil is a Singleton to obtain all the Factories required by the persistence layer
 * It encapsulates Hibernate Factories like SessionFactory, EntityManagerFactory
 * This can be enhanced to perform complex operations like persistence based on mappings, retrieval based on associations
 * etc.
 * @author kamesh
 *
 */
public class DataPersistenceUtil {
	
	private static final DataPersistenceUtil instance = new DataPersistenceUtil();
	private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("employeedata");
	private SessionFactory sessionFactory;
	
	
	private DataPersistenceUtil(){
		
		 this.sessionFactory = buildSessionFactory();
	}
	
	public static final DataPersistenceUtil getInstance(){
		return instance;
	}
	
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (final Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			//blah blah logger, I wont be using here anyway
			throw new ExceptionInInitializerError(ex);
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
	/*
	 * Returns and EntityManager
	 */
	public static EntityManager getEntityManager(){
		//Just in case if we ever needed an EntityFactory returning a Manager
		return entityManagerFactory.createEntityManager();
	}

}
