    package testCases;

    import org.testng.annotations.Test;
    import org.testng.asserts.SoftAssert;
    import io.restassured.path.json.JsonPath;
    import io.restassured.response.Response;
    import static io.restassured.RestAssured.*;
    
    public class ReadAllProducts {
    SoftAssert SoftAssert;
    @Test
	public void readAllProducts() {
	Response response = given()
	.baseUri("https://techfios.com/api-prod/api/product")
	.header("Content-Type","application/json; charset=UTF-8")
	.auth().preemptive().basic("demo@techfios.com","abc123").
	 when().get("/read.php")
	.then()
	.statusCode(200)
	.extract().response();
	
	int actualResponseStatus =response.getStatusCode();
	System.out.println("actual Response Status:" + actualResponseStatus);
	SoftAssert.assertEquals(actualResponseStatus, 200);
	
	String actualResponseContenType = response.getHeader("Content-Type");
	System.out.println("actual Response ContenType: " + actualResponseContenType);
	SoftAssert.assertEquals(actualResponseContenType, "application/json; charset=UTF-8");
	
	String actualResponseBody = response.getBody().asString();
	System.out.println("actualResponseBody:" +actualResponseBody);
	
	JsonPath jp = new JsonPath(actualResponseBody);
	String firstProductId = jp.get("records[0].id");
	
	if(firstProductId !=null) {
		
	System.out.println("product exist");
		
	}else {
	System.out.println("product does not exist");
	}
  }
		
}
