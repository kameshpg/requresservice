package com.maximus.black.lab.presentation.server;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.json.JSONObject;*/

/**
 * Integration suite for the application.
 * Fires URLs from the cases to the web module deployed in a JBoss container.
 * Expects and asserts responses. Handy way to test an E2E flow.
 */

import com.maximus.black.lab.service.dto.EmployeeData;

@RunWith(Arquillian.class)
public class ReqResponseIntegrationTest {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected URL baseUrl;

	@Deployment(name = "restfulInterface", order = 1)
	public static Archive<?> createDeployment() {
		
		final File nhmWarFile = new File(ClassLoader.getSystemClassLoader().getResource("requestresponse-rest-service-war-1.0.1-SNAPSHOT.war").getFile());
				//Maven.resolver().resolve("com.maximus.black.lab.presentation.server:requestresponse-rest-service-war:war").withTransitivity().asFile();
		WebArchive archive = null;
		if(nhmWarFile.exists())
			archive = ShrinkWrap.createFromZipFile(WebArchive.class,nhmWarFile);
		archive.addClass(ReqResponseIntegrationTest.class);
		return archive;
	}

	//@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test 
	@RunAsClient 
	@InSequence(1)
	public void testCreateEmployeeData(@ArquillianResource  final URL url) {
		setupTestInputData(url,2);
		final Response testResponse = executeCreateRequest();
		logger.info("=============================================  Response Received =============================================");
		Assert.assertEquals("Response code not 200 OK!", Status.OK.getStatusCode(), testResponse.getStatus());
		logger.info("============================================= FINISHED TEST ===============================================");
	}
	
	@SuppressWarnings("unchecked")
	@Test 
	@RunAsClient 
	@InSequence(2)
	public void testGetAllEmployees(@ArquillianResource  final URL url) {
		setupTestInputData(url,2);
		final Response testResponse = executeGetRequest();
		logger.info("=============================================  Response Received =============================================");
		Assert.assertEquals("Response code not 200 OK!", Status.OK.getStatusCode(), testResponse.getStatus());
		final List<EmployeeData> responseListEntity = testResponse.readEntity(List.class);
		Assert.assertTrue("Result list was empty!", responseListEntity.size()>0);
		logger.info("============================================= FINISHED TEST =================================================== Result size {}",responseListEntity.size());
	}
	
	
	private Response executeCreateRequest() {
		final ResteasyClient client = new ResteasyClientBuilder().build();
		final String endPoint = new String(baseUrl.toString()+"rest/employee/create");
		logger.info("========= EndPoint = "+endPoint+" =========");
		final ResteasyWebTarget targetUrl = client.target(endPoint);
		logger.info("========= TargetURL = "+targetUrl.getUri().getPath()+" =========");
		final Builder request = targetUrl.request();
		request.header("Content-Type", MediaType.APPLICATION_JSON);
		return request.put(Entity.entity(employeeJsonBase, MediaType.APPLICATION_JSON));
	}
	
	
	private Response executeGetRequest() {
		final ResteasyClient client = new ResteasyClientBuilder().build();
		final String endPoint = new String(baseUrl.toString()+"rest/employee/getAllEmployee");
		logger.info("========= EndPoint = "+endPoint+" =========");
		final ResteasyWebTarget targetUrl = client.target(endPoint);
		logger.info("========= TargetURL = "+targetUrl.getUri().getPath()+" =========");
		final Builder request = targetUrl.request();
		return request.get();
	}
	
	List<EmployeeData> employeeJsonBase = new ArrayList<EmployeeData>();
	
	private void setupTestInputData(final URL url, final int noOfEmployeesToAdd) {
		setBaseURL(url);
		final EmployeeData reqObj1 = new EmployeeData("James", 1, "Accounting");
        final EmployeeData reqObj2 = new EmployeeData("Dave", 2, "Auditing");
		
        employeeJsonBase.add(reqObj1);
        employeeJsonBase.add(reqObj2);
		
		logger.info("======================================================= BaseURL = "+baseUrl+" ======================================");
		
	}
	
	
	protected void setBaseURL(final URL url) {
		baseUrl = url; //Needed due to ArquillianResource injection not working correctly
		if(baseUrl == null){
			Assert.fail("The base URL was null!");
		}
		logger.info("The Base URL to access "+ baseUrl.toString());
	}

}
