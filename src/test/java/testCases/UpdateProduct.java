        package testCases;

        import org.testng.annotations.Test;
        import org.testng.asserts.SoftAssert;
        import io.restassured.path.json.JsonPath;
        import io.restassured.response.Response;
        import static io.restassured.RestAssured.*;
        import java.util.HashMap;
        import java.util.Map;

        public class UpdateProduct {
	   	
        SoftAssert SoftAssert;
	    Map<String, String> createpayloadMap;
	    Map<String, String> updatepayloadMap;
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
	  
		public Map<String, String> updatepayloadMap() {
			    
		updatepayloadMap = new HashMap<String, String>();
		
		updatepayloadMap.put("id", firstProductId);	    
		updatepayloadMap.put("name", "RAUL's Products available everyday");
		updatepayloadMap.put("price", "600");
		updatepayloadMap.put("description", "The best products ever anytime.");
	    updatepayloadMap.put("category_id", "2");
				
		return updatepayloadMap;
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
        public void updateProduct() {
	    
		
	    Response response = given().baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json; charset=UTF-8")
		.auth().preemptive().basic("demo@techfios.com","abc123")
		.body(updatepayloadMap())
		.when()
		.put("/update.php")
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
		SoftAssert.assertEquals(actualproductMessage, "Product was updated:", "Product Id not matching!");

		
	    
		SoftAssert.assertAll();
	    }
	    @Test(priority = 3)
        public void readOneProduct() {
	    expectedProductName = updatepayloadMap.get("name");
		System.out.println("expected ProductName:" + expectedProductName);
				
		expectedProductPrice = updatepayloadMap.get("price");
		System.out.println("expected Product price:" + expectedProductPrice);
				
		expectedProductDescription = updatepayloadMap.get("description");
		System.out.println("expected Product description:" + expectedProductDescription);
	    
	    Response response = given().baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type", "application/json")
		.auth().preemptive().basic("demo@techfios.com","abc123")
		.queryParam("id", firstProductId)
		.when()
		.get("/read_one.php")
		.then()
		.extract().response();
		

		int actualResponseStatus = response.getStatusCode();
		System.out.println("actual Response Status: " + actualResponseStatus);
		SoftAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");

		String actualResponseContentType = response.getHeader("Content-Type");
		System.out.println("actual Response ContentType: " + actualResponseContentType);
		SoftAssert.assertEquals(actualResponseContentType, "application/json");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody: " + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String actualproductId = jp.get("id");
		SoftAssert.assertEquals(actualproductId, firstProductId, "Product Id not matching!");

		String actualproductName = jp.get("name");
		SoftAssert.assertEquals(actualproductName, expectedProductName, "Product names are not matching!");

		String actualproductPrice = jp.get("price");
		SoftAssert.assertEquals(actualproductPrice, expectedProductPrice, "Product Prices are not matching!");
		
		String actualProductDescription = jp.get("description");
		SoftAssert.assertEquals(actualProductDescription, expectedProductDescription, "Product descriptions are not matching!");
		
		SoftAssert.assertAll();
	    }

   } 
         
        
        
        
        
        
	 
	    
     
  
        
        
