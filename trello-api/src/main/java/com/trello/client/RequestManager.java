package com.trello.client;

import com.trello.ApiRequestHandler;
import com.trello.utils.PropertiesInfo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestManager {

    private static RequestSpecification requestSpec = init();
    private ApiRequestHandler request;
    public static RequestSpecification init() {
        if(RequestManager.requestSpec == null){
            requestSpec = new RequestSpecBuilder().setBaseUri(String.format("%s/%s", PropertiesInfo.getInstance().getBaseApi(),
                    PropertiesInfo.getInstance().getApiVersion())).build();
        }
        return requestSpec;
    }
    public static Response post(ApiRequestHandler apiRequest){
         return RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(apiRequest.getHeaders())
                .queryParams(apiRequest.getQueryParams())
                .post(apiRequest.getEndpoint());
    }

    public static Response put(ApiRequestHandler apiRequest) {
        return RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(apiRequest.getHeaders())
                .queryParams(apiRequest.getQueryParams())
                .put(apiRequest.getEndpoint());
    }

    public static Response get(ApiRequestHandler apiRequest) {
        return RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(apiRequest.getHeaders())
                .queryParams(apiRequest.getQueryParams())
                .get(apiRequest.getEndpoint());
    }

    public static Response delete(ApiRequestHandler apiRequest) {
        return RestAssured.given().log().all().when()
                .spec(requestSpec)
                .headers(apiRequest.getHeaders())
                .queryParams(apiRequest.getQueryParams())
                .delete(apiRequest.getEndpoint());
    }
}
