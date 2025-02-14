package com.dulfinne.taxi.driverservice.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import com.dulfinne.taxi.driverservice.dto.request.DriverRequest;
import com.dulfinne.taxi.driverservice.dto.response.DriverResponse;
import com.dulfinne.taxi.driverservice.model.Driver;
import com.dulfinne.taxi.driverservice.repository.DriverRepository;
import com.dulfinne.taxi.driverservice.util.DriverTestData;
import com.dulfinne.taxi.driverservice.util.HeaderConstants;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@Sql(
    scripts = {"classpath:sql/delete-data.sql", "classpath:sql/insert-data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@RequiredArgsConstructor
class DriverServiceIT extends IntegrationTestBase {

  private final DriverRepository driverRepository;
  @LocalServerPort private int port;

  private final String BASE_URL = "api/v1/drivers";

  private RequestSpecification withAuth(String username) {
    return given()
        .header(HeaderConstants.USERNAME_HEADER, username)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .port(port);
  }

  @Nested
  class GetPassenger {

    @Test
    void givenExistingDriverAuth_whenFindByUsername_thenReturnDriverResponse() {
      DriverResponse expected = DriverTestData.getResponse().build();

      DriverResponse result =
          withAuth(DriverTestData.USERNAME)
              .when()
              .get(BASE_URL)
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(DriverResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingDriverAuth_whenFindByUsername_thenReturnErrorResponse() {
      withAuth(DriverTestData.NON_EXISTING_USERNAME)
          .when()
          .get(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Driver not found"))
          .extract();
    }
  }

  @Nested
  class GetPassengerByUsername {

    @Test
    void givenExistingDriverAuth_whenFindByUsername_thenReturnDriverResponse() {
      DriverResponse expected = DriverTestData.getResponse().build();

      DriverResponse result =
          withAuth(DriverTestData.ADMIN_USERNAME)
              .pathParam("username", DriverTestData.USERNAME)
              .when()
              .get(BASE_URL + "/{username}")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(DriverResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingDriverAuth_whenFindByUsername_thenReturnErrorResponse() {
      withAuth(DriverTestData.ADMIN_USERNAME)
          .pathParam("username", DriverTestData.NON_EXISTING_USERNAME)
          .when()
          .get(BASE_URL + "/{username}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Driver not found"))
          .extract();
    }
  }

  @Nested
  class SaveDriver {

    @Test
    void givenExistingDriverRequest_whenSaveDriver_thenReturnDriverResponse() {
      DriverRequest request = DriverTestData.getCreateSecondRequest().build();
      DriverResponse expected = DriverTestData.getCreatedSecondResponse().build();

      DriverResponse result =
          withAuth(DriverTestData.SECOND_USERNAME)
              .contentType(ContentType.JSON)
              .body(request)
              .when()
              .post(BASE_URL)
              .then()
              .statusCode(HttpStatus.CREATED.value())
              .extract()
              .as(DriverResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingDriverRequest_whenSaveDriver_thenReturnErrorResponse() {
      DriverRequest request = DriverTestData.getCreateSecondRequest().build();

      withAuth(DriverTestData.USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Driver already exists: username"))
          .extract();
    }

    @Test
    void givenExistingPhoneNumberDriverRequest_whenSaveDriver_thenReturnErrorResponse() {
      DriverRequest request = DriverTestData.getCreateFirstRequest().build();

      withAuth(DriverTestData.SECOND_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Driver already exists: phoneNumber"))
          .extract();
    }
  }

  @Nested
  class UpdateDriver {

    @Test
    void givenExistingDriverRequest_whenUpdateDriver_thenReturnDriverResponse() {
      DriverRequest request = DriverTestData.getUpdateRequest().build();
      DriverResponse expected = DriverTestData.getUpdatedResponse().build();

      DriverResponse result =
          withAuth(DriverTestData.USERNAME)
              .contentType(ContentType.JSON)
              .body(request)
              .when()
              .put(BASE_URL)
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(DriverResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingDriverRequest_whenUpdateDriver_thenReturnErrorResponse() {
      DriverRequest request = DriverTestData.getUpdateRequest().build();

      withAuth(DriverTestData.NON_EXISTING_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .put(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Driver not found: username"))
          .extract();
    }

    @Test
    void givenExistingPhoneNumberDriverRequest_whenUpdateDriver_thenReturnErrorResponse() {
      String existingPhone = DriverTestData.EXISTING_PHONE_NUMBER;
      DriverRequest request = DriverTestData.getUpdateRequest().phoneNumber(existingPhone).build();

      withAuth(DriverTestData.USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .put(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Driver already exists: phoneNumber"))
          .extract();
    }
  }

  @Nested
  class DeleteDriver {

    @Test
    void givenExistingDriverAuth_whenDeleteDriver_thenReturnDriverResponse() {
      withAuth(DriverTestData.USERNAME)
          .when()
          .delete(BASE_URL)
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());

      Optional<Driver> driver = driverRepository.findByUsername(DriverTestData.USERNAME);
      assertThat(driver).isEmpty();
    }

    @Test
    void givenNonExistingDriverAuth_whenDeleteDriver_thenReturnErrorResponse() {
      withAuth(DriverTestData.NON_EXISTING_USERNAME)
          .when()
          .delete(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Driver not found"))
          .extract();
    }
  }
}
