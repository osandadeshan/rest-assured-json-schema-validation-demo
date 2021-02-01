# REST Assured JSON Schema Validation Demo

### Introduction
When exposing REST or HTTP based service APIs it’s important to validate that the API behaves correctly and that the exposed data format is structured in an expected manner. Using REST Assured it has always been easy to validate that the API behaves correctly and returns the expected data values. Validating that the structure of the document is correct has also been easy when it comes to JSON.

### Example
```
public class UserTest {

    private static final String BASE_URI = "https://gorest.co.in/public-api/";

    @Test
    public void validateUserDetailsJsonSchema() {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get("users/2")
                .then();

        System.out.println("GET Response:\n" + response.extract().body().asString());

        // Verify the status code
        Assert.assertEquals(response.extract().statusCode(), 200);

        // Verify the response attributes
        assertNotNull("'id' should not be null", response.extract().body().jsonPath().get("data.id"));
        assertNotNull("'name' should not be null", response.extract().body().jsonPath().get("data.name"));
        assertNotNull("'email' should not be null", response.extract().body().jsonPath().get("data.email"));

        // Validate the JSON Schema
        assertThat(response.extract().body().asString(), matchesJsonSchemaInClasspath("user-info.json"));
    }
}
```
**Note: `user-info.json` should be located in `src/test/resources`**
