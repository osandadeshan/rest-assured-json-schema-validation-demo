# REST Assured JSON Schema Validation Demo

### Introduction
When exposing REST or HTTP based service APIs it’s important to validate that the API behaves correctly and that the exposed data format is structured in an expected manner. Using REST Assured it has always been easy to validate that the API behaves correctly and returns the expected data values. Validating that the structure of the document is correct has also been easy when it comes to JSON.

### Example
```
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
```
**Note: `user-details.json` should be located in `\target\classes`**
