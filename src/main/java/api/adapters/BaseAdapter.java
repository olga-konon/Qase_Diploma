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

    public static RequestSpecification specWithInvalidToken = new RequestSpecBuilder()
            .setBaseUri("https://api.qase.io")
            .setBasePath("v1")
            .setContentType(ContentType.JSON)
            .addHeader("Token", "12345")
            .addFilter(new AllureRestAssured())
            .build();

    public static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification error400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification error401 = new ResponseSpecBuilder()
            .expectStatusCode(401)
            .build();

    public static ResponseSpecification error404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification error422 = new ResponseSpecBuilder()
            .expectStatusCode(422)
            .build();
}
