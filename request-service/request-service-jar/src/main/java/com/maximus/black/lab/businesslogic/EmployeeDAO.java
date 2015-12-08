package com.maximus.black.lab.businesslogic;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maximus.black.lab.entity.Employee;
import com.maximus.black.lab.persistence.utils.DataPersistenceUtil;
/**
 * A data access object to talk with the persistence layer.
 * This DAO provides methods to facilitate CRUD on Entities.
 * Don't mind the logging statements. They are for debugging purpose on an integrated environment.
 * 
 */
public class EmployeeDAO {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	Transaction tx;
	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees() throws Exception {
		logger.info("================= EmployeeDAO.getAllEmployees ===================");
		List<Employee> allEmployees = null;
		SessionFactory factory = DataPersistenceUtil.getInstance()
				.getSessionFactory();
		Session session = factory.openSession();
		tx = session.beginTransaction();
		try {
			
			Query query = session.createQuery("FROM Employee");
			logger.info("================= EmployeeDAO.getAllEmployees- Executing get from Employee ===================");
			allEmployees = (List<Employee>) query.list();
			logger.info("===================== EmployeeDAO.getAllEmployees- Result after query : {}",allEmployees.size());
			tx.commit();
			session.close();
			factory.close();

		} catch (Throwable e) {
			tx.rollback();
			throw new Exception("Operation Failed");
		}
		return allEmployees;

	}

	public boolean createEmployees(List<Employee> listOfEmployees)
			throws Exception {
		logger.info("================= EmployeeDAO.createEmployees ===================");
		boolean isSuccess = false;
		SessionFactory factory = DataPersistenceUtil.getInstance()
				.getSessionFactory();
		Session session = factory.openSession();
		tx = session.beginTransaction();
		try {
			Query query = session.createQuery("delete FROM Employee");
			query.executeUpdate(); // Clean up, in case if something is hangin' around.
			for (Employee employee : listOfEmployees) {
				logger.info("================= EmployeeDAO.createEmployees - Persisting Entities =================== {} ", employee.toString());	
				session.save(employee);
				
			}
			
			/* Below LOC to check if the save was successful and data retrievable.
			Query query = session.createQuery("FROM Employee");
			logger.info("================= EmployeeDAO.createEmployees- Executing get from Employee ===================");
			@SuppressWarnings("unchecked")
			List<Employee> allEmployees = (List<Employee>) query.list();
			logger.info("===================== EmployeeDAO.createEmployees- Result after query : {}",allEmployees.size());*/
			tx.commit();
			isSuccess = true;
		} catch (Throwable e) {
			tx.rollback();
			e.printStackTrace();
		} 
		return isSuccess;

	}
}
