package com.trello;

import com.trello.utils.JsonPath;
import com.trello.utils.PropertiesInfo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BoardTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    String apiKey;
    String apiToken;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private String boardId = "";
    private RequestHandler request;
    @BeforeClass
    public void setUp(){
        request = new RequestHandler();
        apiKey = PropertiesInfo.getInstance().getApiKey();
        apiToken = PropertiesInfo.getInstance().getApiToken();

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        queryParams = new HashMap<String, String>();
        queryParams.put("key", apiKey);
        queryParams.put("token", apiToken);

        request.setBaseUrl(String.format("%s/%s", PropertiesInfo.getInstance().getBaseApi(),
                PropertiesInfo.getInstance().getApiVersion()));
        request.setHeaders(headers);
        request.setQueryParam(queryParams);

        requestSpec = new RequestSpecBuilder().setBaseUri(request.getBaseUrl()).build();
    }
    /*@Test(priority = 1)
    public void testCreateBoard(){
        String boardName = "boardTestRoger";
        //queryParams.put("name", boardName);
        request.setQueryParam("name", boardName);
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .post("/boards/");

        var jsonResponse = response.getBody().asPrettyString();
        System.out.println(jsonResponse);
        Assert.assertEquals(response.statusCode(), 200);

        boardId = response.getBody().path("id");
        System.out.println(String.format("boardId: %s", boardId));

    }

     */
    @Test(priority = 2)
    public void updateBoardTest(){
        String boardNameUpdate = "boardTestRogerUpdated";
        request.setQueryParam("name", boardNameUpdate);
        //queryParams.put("name", boardNameUpdate);

        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .put(String.format("/boards/%s", boardId))
                .then()
                .spec(responseSpec).extract().response();

        var jsonResponse = response.getBody().asPrettyString();
        //System.out.println(jsonResponse);
        //Assert.assertEquals(response.statusCode(), 200);
    }
    @Test(priority = 3)
    public void getBoardTest(){
        InputStream getBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/getBoardSchema.json");
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .get(String.format("/boards/%s", boardId))
                .then()
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(getBoardJsonSchema))
                .spec(responseSpec).extract().response();

        //String name = response.path("name");
        String name = JsonPath.getResult(response.getBody().asPrettyString(), "$.name");

        var jsonResponse = response.getBody().asPrettyString();
        System.out.println(jsonResponse);
        Assert.assertEquals(name, "boardTestRogerUpdated");
    }
    @Test(priority = 4)
    public void deleteBoardTest(){
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .delete(String.format("/boards/%s", boardId))
                .then()
                .spec(responseSpec).extract().response();
    }
    @Test(priority = 1)
    public void testCreateBoardSchemaValidation(){
        InputStream createBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/createBoardSchema.json");
        String boardName = "boardTestRoger";
        //queryParams.put("name", boardName);
        request.setQueryParam("name", boardName);
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .post("/boards/")
                .then()
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBoardJsonSchema))
                .extract().response();

        var jsonResponse = response.getBody().asPrettyString();
        //System.out.println(jsonResponse);
        Assert.assertEquals(response.statusCode(), 200);

        boardId = response.getBody().path("id");
        System.out.println(String.format("boardId: %s", boardId));
    }
}