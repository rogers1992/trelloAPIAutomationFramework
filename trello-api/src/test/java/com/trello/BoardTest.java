package com.trello;

import com.trello.utils.PropertiesInfo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;


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

    @BeforeClass
    public void setUp(){
        apiKey = PropertiesInfo.getInstance().getApiKey();
        apiToken = PropertiesInfo.getInstance().getApiToken();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(String.format("%s/%s", PropertiesInfo.getInstance().getBaseApi(),
                    PropertiesInfo.getInstance().getApiVersion())).build();
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        queryParams = new HashMap<String, String>();
        queryParams.put("key", apiKey);
        queryParams.put("token", apiToken);
    }
    @Test(priority = 1)
    public void testCreateBoard(){
        String boardName = "boardTestRoger";
        queryParams.put("name", boardName);
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(headers)
                .queryParams(queryParams)
                .post("/boards/");

        var jsonResponse = response.getBody().asPrettyString();
        System.out.println(jsonResponse);
        Assert.assertEquals(response.statusCode(), 200);

        boardId = response.getBody().path("id");
        System.out.println(String.format("boardId: %s", boardId));

    }
    @Test(priority = 2)
    public void updateBoardTest(){
        String boardNameUpdate = "boardTestRogerUpdated";
        queryParams.put("name", boardNameUpdate);

        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(headers)
                .queryParams(queryParams)
                .put(String.format("/boards/%s", boardId))
                .then()
                .spec(responseSpec).extract().response();

        var jsonResponse = response.getBody().asPrettyString();
        System.out.println(jsonResponse);
        //Assert.assertEquals(response.statusCode(), 200);
    }
    @Test(priority = 3)
    public void getBoardTest(){
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(headers)
                .queryParams(queryParams)
                .get(String.format("/boards/%s", boardId))
                .then()
                .spec(responseSpec).extract().response();

        String name = response.path("name");

        var jsonResponse = response.getBody().asPrettyString();
        System.out.println(jsonResponse);
        Assert.assertEquals(name, "boardTestRogerUpdated");
    }
    @Test(priority = 4)
    public void deleteBoardTest(){
        var response = RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(headers)
                .queryParams(queryParams)
                .delete(String.format("/boards/%s", boardId))
                .then()
                .spec(responseSpec).extract().response();
    }
}