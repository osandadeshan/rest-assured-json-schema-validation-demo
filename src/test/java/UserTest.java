import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 2/9/2020
 * Time            : 1:04 PM
 * Description     :
 **/


public class UserTest {

    private static final String BASE_URI = "https://maxsoft-mock-server-demo.web.app/";

    @Test
    public void validateUserDetailsJsonSchema() {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get("users/1")
                .then();

        System.out.println("GET Response:\n" + response.extract().body().asString());

        // Validation for the status code
        Assert.assertEquals(response.extract().statusCode(), 200);

        // Validation for the JSON Schema
        assertThat(response.extract().asString(), matchesJsonSchemaInClasspath("user-details.json"));

        // Validations for the response attributes
        assertNotNull("'userId' should not be null", response.extract().body().jsonPath().get("userId"));
        assertNotNull("'name' should not be null", response.extract().body().jsonPath().get("name"));
        assertNotNull("'email' should not be null", response.extract().body().jsonPath().get("email"));
    }


}
