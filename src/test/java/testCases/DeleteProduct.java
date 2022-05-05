          package testCases;

           import org.testng.annotations.Test;
           import org.testng.asserts.SoftAssert;
           import io.restassured.path.json.JsonPath;
           import io.restassured.response.Response;
           import static io.restassured.RestAssured.*;
           import java.util.HashMap;
           import java.util.Map;

             public class DeleteProduct {
    	   	
            SoftAssert SoftAssert;
    	    Map<String, String> createpayloadMap;
    	    Map<String, String> deletepayloadMap;
    	    String expectedProductName;
    	    String expectedProductPrice;
    	    String expectedProductDescription;
    	    String firstProductId;

    	    public void CreateNewProduct() {
    		
    	     }

    	    public Map<String, String> createPayloadMap() {
    			    
    	    createpayloadMap = new HashMap<String, String>();
    	    		    
    	    createpayloadMap.put("name", "RAUL's Products available now");
    	    createpayloadMap.put("price", "500");
    	    createpayloadMap.put("description", "The best products ever.");
    	    createpayloadMap.put("category_id", "2");
    		
    		return createpayloadMap;
    	     }
    	  
    		public Map<String, String> deletepayloadMap() {
    			    
    		deletepayloadMap = new HashMap<String, String>();
    		
    		deletepayloadMap.put("id", firstProductId);	    
    		
    				
    		return deletepayloadMap;
    		     }
    	    @Test(priority = 0)
    	    public void createNewProduct() {
    		Response response = 
    		given()
    		.baseUri("https://techfios.com/api-prod/api/product")
    		.header("Content-Type", "application/json; charset=UTF-8")
    		.auth().preemptive().basic("demo@techfios.com", "abc123")
    		.body(createPayloadMap())
    		.when()
    		.post("/create.php")
    		.then()
    		.extract().response();

    		int actualResponseStatus = response.getStatusCode();
    		System.out.println("actual Response Status: " + actualResponseStatus);
    		SoftAssert.assertEquals(actualResponseStatus, 201, "Status codes are not matching!");

    		String actualResponseContentType = response.getHeader("Content-Type");
    		System.out.println("actual Response ContentType: " + actualResponseContentType);
    		SoftAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8","Response Content-Types are not matching!");

    		String actualResponseBody = response.getBody().asString();
    		System.out.println("actualResponseBody: " + actualResponseBody);

    		JsonPath jp = new JsonPath(actualResponseBody);
    		String productMessage = jp.get("message");
    		SoftAssert.assertEquals(productMessage, "Product was created.", "Product messages are not matching!");

    		SoftAssert.assertAll();
    	     }
    	    
    	    
    	    @Test(priority = 1)
    	    public void readAllProducts() {
    	    	
    	    Response response = 
    	    given()
    	    .baseUri("https://techfios.com/api-prod/api/product")
    	    .header("Content-Type","application/json; charset=UTF-8")
    	    .auth().preemptive().basic("demo@techfios.com","abc123").
    	     when()
    	    .get("/read.php")
    	    .then()
    	    .extract().response();
    	    	
    	    int actualResponseStatus = response.getStatusCode();
    	    System.out.println("actual Response Status:" + actualResponseStatus);
    	    SoftAssert.assertEquals(actualResponseStatus, 200);
    	     	
    	    String actualResponseBody = response.getBody().asString();
    	    
    	    	
    	    JsonPath jp = new JsonPath(actualResponseBody);
    	    String firstProductId = jp.get("records[0].id");
    	    System.out.println("firstProductId:" + firstProductId);
    	    SoftAssert.assertAll();
    	    }
    	    @Test(priority = 2)
            public void deleteProduct() {
    	    
    		
    	    Response response = given().baseUri("https://techfios.com/api-prod/api/product")
    		.header("Content-Type", "application/json; charset=UTF-8")
    		.auth().preemptive().basic("demo@techfios.com","abc123")
    		.body(deletepayloadMap())
    		.when()
    		.delete("/delete.php")
    		.then()
    		.extract().response();
    		

    		int actualResponseStatus = response.getStatusCode();
    		System.out.println("actual Response Status: " + actualResponseStatus);
    		SoftAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

    		String actualResponseContentType = response.getHeader("Content-Type");
    		System.out.println("actual Response ContentType: " + actualResponseContentType);
    		SoftAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");

    		String actualResponseBody = response.getBody().asString();
    		System.out.println("actualResponseBody: " + actualResponseBody);

    		JsonPath jp = new JsonPath(actualResponseBody);
    		String actualproductMessage = jp.get("message");
    		SoftAssert.assertEquals(actualproductMessage, "Product was deleted:", "Product Id not matching!");

    		
    	    
    		SoftAssert.assertAll();
    	    }
    	    @Test(priority = 3)
            public void readDeletedProduct() {
    	 
    	    Response response =
    	    given().baseUri("https://techfios.com/api-prod/api/product")
    		.header("Content-Type", "application/json")
    		.auth().preemptive().basic("demo@techfios.com","abc123")
    		.queryParam("id", firstProductId)
    		.when()
    		.get("/read_one.php")
    		.then()
    		.extract().response();
    		

    		int actualResponseStatus = response.getStatusCode();
    		System.out.println("actual Response Status: " + actualResponseStatus);
    		SoftAssert.assertEquals(actualResponseStatus, 404, "Status codes are not matching!");

    		String actualResponseContentType = response.getHeader("Content-Type");
    		System.out.println("actual Response ContentType: " + actualResponseContentType);
    		SoftAssert.assertEquals(actualResponseContentType, "application/json");

    		String actualResponseBody = response.getBody().asString();
    		System.out.println("actualResponseBody: " + actualResponseBody);

    		JsonPath jp = new JsonPath(actualResponseBody);
    		

    		String actualProductMessage = jp.get("message");
    		SoftAssert.assertEquals(actualProductMessage, "Product does not exist", "Product messages are not matching!");
    		
    		
    		
    		SoftAssert.assertAll();
    	    }

       }     
        
        
        
        
        
	 
	    
     
  
        
        
