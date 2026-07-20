package api.adapters;

import api.models.cases.CaseErrorRs;
import api.models.cases.CaseRq;
import api.models.cases.CaseRs;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;

import static api.adapters.BaseAdapter.*;
import static io.restassured.RestAssured.given;

public class CaseAdapter {

    public static CaseRs createTest(CaseRq rq, String code) {
        return given()
                .spec(spec)
                .pathParams("code", code)
                .body(rq)
                .when()
                .post("/case/{code}")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }

    public static CaseErrorRs createCaseExpectingError(CaseRq rq, String code) {
        return given()
                .spec(spec)
                .pathParams("code", code)
                .body(rq)
                .when()
                .post("/case/{code}")
                .then()
                .log().all()
                .spec(error422)
                .extract()
                .as(CaseErrorRs.class);
    }

    public static CaseRs updateCaseByCodeAndID(CaseRq rq,String code, int id) {
        return given()
                .spec(spec)
                .pathParams("code", code, "id", id)
                .body(rq)
                .when()
                .patch("/case/{code}/{id}")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }

    public static CaseRs getCaseByCodeAndID(String code, int id) {
        return given()
                .spec(spec)
                .pathParams("code", code, "id", id)
                .when()
                .get("/case/{code}/{id}")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }

    public static CaseRs deleteCaseByCodeAndID(String code, int id) {
        return given()
                .spec(spec)
                .pathParams("code", code, "id", id)
                .when()
                .delete("/case/{code}/{id}")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }
}
