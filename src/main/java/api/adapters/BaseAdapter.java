package api.adapters;

import helpers.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAdapter {

    public static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://api.qase.io")
            .setBasePath("v1")
            .setContentType(ContentType.JSON)
            .addHeader("Token", Config.getToken())
            .addFilter(new AllureRestAssured())
            .build();

    public  static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
