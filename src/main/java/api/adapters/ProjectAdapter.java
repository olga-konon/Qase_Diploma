package api.adapters;

import api.models.project.ProjectErrorRs;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;

import static io.restassured.RestAssured.given;

public class ProjectAdapter extends BaseAdapter {

    public static ProjectRs createProject(ProjectRq rq) {
        return given()
                .spec(spec)
                .body(rq)
                .when()
                .post("/project")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(ProjectRs.class);
        // .body(matchesJsonSchemaInClasspath("schemas/create_project_schema.json"))
    }

    public static ProjectRs createProjectExpectingError(ProjectRq rq) {
        return given()
                .spec(spec)
                .body(rq)
                .when()
                .post("/project")
                .then()
                .log().all()
                .spec(error400)
                .extract()
                .as(ProjectRs.class);
    }

    public static ProjectRs getProjectByCode(String code) {

      return  given()
                .spec(spec)
                .pathParams("code", code)
                .log().all()
                .when()
                .get("/project/{code}")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(ProjectRs.class);

    }

    public static ProjectErrorRs getProjectByCodeWithNotValidToken(String code) {

        return  given()
                .spec(specWithInvalidToken)
                .pathParams("code", code)
                .log().all()
                .when()
                .get("/project/{code}")
                .then()
                .log().all()
                .spec(error401)
                .extract()
                .as(ProjectErrorRs.class);

    }

    public static ProjectRs getProjectByNonExistingCode(String code) {

        return  given()
                .spec(spec)
                .pathParams("code", code)
                .log().all()
                .when()
                .get("/project/{code}")
                .then()
                .log().all()
                .spec(error404)
                .extract()
                .as(ProjectRs.class);

    }

    public static ProjectRs  deleteProject(String code) {
       return given()
                .spec(spec)
                .pathParams("code", code)
                .log().all()
                .when()
                .delete("/project/{code}")
                .then()
                .log().all()
                .spec(ok200)
               .extract()
               .as(ProjectRs.class);
    }
}
