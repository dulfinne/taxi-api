package com.dulfinne.taxi.passengerservice.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import com.dulfinne.jooq.generated.tables.records.PassengerRecord;
import com.dulfinne.taxi.passengerservice.dto.request.PassengerRequest;
import com.dulfinne.taxi.passengerservice.dto.response.PassengerResponse;
import com.dulfinne.taxi.passengerservice.repository.PassengerRepository;
import com.dulfinne.taxi.passengerservice.util.HeaderConstants;
import com.dulfinne.taxi.passengerservice.util.PassengerTestData;
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
class PassengerServiceIT extends IntegrationTestBase {

  private final PassengerRepository passengerRepository;

  @LocalServerPort private int port;

  private final String BASE_URL = "api/v1/passengers";

  private RequestSpecification withAuth(String username) {
    return given()
        .header(HeaderConstants.USERNAME_HEADER, username)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .port(port);
  }

  @Nested
  class GetPassenger {

    @Test
    void givenExistingPassengerAuth_whenFindByUsername_thenReturnPassengerResponse() {
      PassengerResponse expected = PassengerTestData.getFirstResponse().build();

      PassengerResponse result =
          withAuth(PassengerTestData.FIRST_USERNAME)
              .when()
              .get(BASE_URL)
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(PassengerResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingPassengerAuth_whenFindByUsername_thenReturnErrorResponse() {
      withAuth(PassengerTestData.NON_EXISTING_PASSENGER_USERNAME)
          .when()
          .get(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Passenger not found"))
          .extract();
    }
  }

  @Nested
  class GetPassengerByUsername {

    @Test
    void givenExistingPassengerAuth_whenFindByUsername_thenReturnPassengerResponse() {
      PassengerResponse expected = PassengerTestData.getFirstResponse().build();

      PassengerResponse result =
          withAuth(PassengerTestData.ADMIN_USERNAME)
              .pathParam("username", PassengerTestData.FIRST_USERNAME)
              .when()
              .get(BASE_URL + "/{username}")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(PassengerResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingPassengerAuth_whenFindByUsername_thenReturnErrorResponse() {
      withAuth(PassengerTestData.ADMIN_USERNAME)
          .pathParam("username", PassengerTestData.NON_EXISTING_PASSENGER_USERNAME)
          .when()
          .get(BASE_URL + "/{username}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Passenger not found"))
          .extract();
    }
  }

  @Nested
  class SavePassenger {

    @Test
    void givenValidPassengerRequest_whenSavePassenger_thenReturnPassengerResponse() {
      PassengerRequest request = PassengerTestData.getSecondCreateRequest().build();
      PassengerResponse expected = PassengerTestData.getSecondCreatedResponse().build();

      PassengerResponse result =
          withAuth(PassengerTestData.SECOND_USERNAME)
              .contentType(ContentType.JSON)
              .body(request)
              .when()
              .post(BASE_URL)
              .then()
              .statusCode(HttpStatus.CREATED.value())
              .extract()
              .as(PassengerResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenExistingUsernamePassengerRequest_whenSavePassenger_thenReturnErrorResponse() {
      PassengerRequest request = PassengerTestData.getSecondCreateRequest().build();

      withAuth(PassengerTestData.FIRST_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Passenger already exists: username"));
    }

    @Test
    void givenExistingPhoneNumberPassengerRequest_whenSavePassenger_thenReturnErrorResponse() {
      String existingPhone = PassengerTestData.FIRST_PHONE_NUMBER;
      PassengerRequest request =
          PassengerTestData.getSecondCreateRequest().phoneNumber(existingPhone).build();

      withAuth(PassengerTestData.SECOND_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Passenger already exists: phoneNumber"));
    }
  }

  @Nested
  class UpdatePassenger {

    @Test
    void givenValidPassengerRequest_whenUpdatePassenger_thenReturnPassengerResponse() {
      String notUpdatedPhone = PassengerTestData.FIRST_PHONE_NUMBER;
      PassengerRequest request =
          PassengerTestData.getUpdateFirstRequest().phoneNumber(notUpdatedPhone).build();
      PassengerResponse expected =
          PassengerTestData.getUpdatedFirstResponse().phoneNumber(notUpdatedPhone).build();

      PassengerResponse result =
          withAuth(PassengerTestData.FIRST_USERNAME)
              .contentType(ContentType.JSON)
              .body(request)
              .when()
              .put(BASE_URL)
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(PassengerResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingPassengerRequest_whenUpdatePassenger_thenReturnErrorResponse() {
      PassengerRequest request = PassengerTestData.getUpdateFirstRequest().build();

      withAuth(PassengerTestData.NON_EXISTING_PASSENGER_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .put(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Passenger not found: username"));
    }

    @Test
    void givenExistingPhoneNumberPassengerRequest_whenUpdatePassenger_thenReturnErrorResponse() {
      String existingPhone = PassengerTestData.EXISTING_PHONE_NUMBER;
      PassengerRequest request =
          PassengerTestData.getUpdateFirstRequest().phoneNumber(existingPhone).build();

      withAuth(PassengerTestData.FIRST_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .put(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Passenger already exists: phoneNumber"));
    }
  }

  @Nested
  class DeletePassenger {

    @Test
    void givenExistingPassengerAuth_whenDeleteByUsername_thenReturnPassengerResponse() {
      withAuth(PassengerTestData.FIRST_USERNAME)
          .when()
          .delete(BASE_URL)
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());

      Optional<PassengerRecord> passenger =
          passengerRepository.findByUsername(PassengerTestData.FIRST_USERNAME);
      assertThat(passenger).isEmpty();
    }

    @Test
    void givenNonExistingPassengerAuth_whenDeleteByUsername_thenReturnErrorResponse() {
      withAuth(PassengerTestData.NON_EXISTING_PASSENGER_USERNAME)
          .when()
          .delete(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Passenger not found"));
    }
  }
}
