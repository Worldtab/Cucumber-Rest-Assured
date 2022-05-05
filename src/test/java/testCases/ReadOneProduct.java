package testCases;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadOneProduct {
	
	SoftAssert SoftAssert;
	
	public ReadOneProduct() {}

	@Test
	public void readOneProduct() {
		
		
		
		
		
Response response =
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Cotent-Type","application/json")
			.auth().basic("demo@techfios.com","abc123")
			.queryParam("id","4134").
		when()
				.get("/read_one.php").
		then()
				.extract().response();

int actualResponseStatus = response.getStatusCode();
System.out.println("actualResponseStatus: " + actualResponseStatus);
	Assert.assertEquals(actualResponseStatus, 200);
	SoftAssert.assertEquals(actualResponseStatus, 200, "Status codes are not matching!");
	
	String actualResponseContentType = response.getHeader("Content-Type");
	System.out.println("actualResponseContentType: " + actualResponseContentType);
	Assert.assertEquals(actualResponseContentType,"application/json");
	SoftAssert.assertEquals(actualResponseContentType,"application/json");
	
	String actualResponseBody = response.getBody().asString();
	System.out.println("actualResponseBody: " + actualResponseBody);
	
	JsonPath jp = new JsonPath(actualResponseBody);
	String productId = jp.get("id");
	Assert.assertEquals(productId, "4078");
	SoftAssert.assertEquals(productId, "4079", "Product Id not matching!");
	
	String productName = jp.get("name");
	
	SoftAssert.assertEquals(productName,  "MD's Amazing Pillow 2.0", "Product names are not matching");
	
	String productPrice = jp.get("price");
	Assert.assertEquals(productPrice,  "199");
	SoftAssert.assertEquals(productPrice,  "199");
	System.out.println("product Price:" + "producPrice");
	
	SoftAssert.assertAll();
	
//	JsonPath jp = new JsonPath(actualResponseBody);
//	String firstProductID = jp.get("records[0].id");
//	
//	if (firstProductID!=null) {
//		System.out.println("Product exist.");
//		}else {
//			System.out.println("Product does not exist.");
//		}
//	
//	
	}

}

