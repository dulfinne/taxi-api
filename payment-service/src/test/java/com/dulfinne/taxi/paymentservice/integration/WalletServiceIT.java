package com.dulfinne.taxi.paymentservice.integration;

import com.dulfinne.taxi.paymentservice.dto.request.MoneyRequest;
import com.dulfinne.taxi.paymentservice.dto.response.CanPayByCardResponse;
import com.dulfinne.taxi.paymentservice.dto.response.WalletResponse;
import com.dulfinne.taxi.paymentservice.util.HeaderConstants;
import com.dulfinne.taxi.paymentservice.util.WalletTestData;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@Sql(
    scripts = {"classpath:sql/delete-data.sql", "classpath:sql/insert-data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class WalletServiceIT extends IntegrationTestBase {

  @LocalServerPort private int port;

  private final String BASE_URL = "api/v1/payments/wallets";

  private RequestSpecification withAuth(String username) {
    return given()
        .header(HeaderConstants.USERNAME_HEADER, username)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .port(port);
  }

  @Nested
  class GetWallet {

    @Test
    void givenExistingWalletAuth_whenFindByUsername_thenReturnWalletResponse() {
      WalletResponse expected = WalletTestData.getWalletResponse();

      WalletResponse result =
          withAuth(WalletTestData.WALLET_USERNAME)
              .when()
              .get(BASE_URL)
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(WalletResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingWalletAuth_whenFindByUsername_thenReturnErrorResponse() {
      withAuth(WalletTestData.NON_EXISTING_WALLET_USERNAME)
          .when()
          .get(BASE_URL)
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Wallet not found"))
          .extract();
    }
  }

  @Nested
  class GetWalletByUsername {

    @Test
    void givenExistingWalletUsername_whenFindByUsername_thenReturnWalletResponse() {
      WalletResponse response = WalletTestData.getWalletResponse();

      WalletResponse result =
          withAuth(WalletTestData.ADMIN_USERNAME)
              .pathParam("username", WalletTestData.WALLET_USERNAME)
              .when()
              .get(BASE_URL + "/{username}")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(WalletResponse.class);

      assertThat(result).isEqualTo(response);
    }

    @Test
    void givenNonExistingWalletUsername_whenFindByUsername_thenReturnErrorResponse() {
      withAuth(WalletTestData.ADMIN_USERNAME)
          .pathParam("username", WalletTestData.NON_EXISTING_WALLET_USERNAME)
          .when()
          .get(BASE_URL + "/{username}")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Wallet not found"))
          .extract();
    }
  }

  @Nested
  class CreateWallet {

    @Test
    void givenNonExistingWalletAuth_whenCreateWallet_thenReturnWalletResponse() {
      WalletResponse expected = WalletTestData.getSecondWalletResponse();

      WalletResponse result =
          withAuth(WalletTestData.SECOND_WALLET_USERNAME)
              .when()
              .post(BASE_URL)
              .then()
              .statusCode(HttpStatus.CREATED.value())
              .extract()
              .as(WalletResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenExistingWalletAuth_whenCreateWallet_thenReturnErrorResponse() {
      withAuth(WalletTestData.WALLET_USERNAME)
          .when()
          .post(BASE_URL)
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("Wallet already exists"))
          .extract();
    }
  }

  @Nested
  class CreditMoney {

    @Test
    void givenExistingWalletAuth_whenCreditMoney_thenReturnWalletResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.CREDIT_MONEY_AMOUNT);
      WalletResponse expected = WalletTestData.getCreditedWalletResponse();

      WalletResponse result =
          withAuth(WalletTestData.WALLET_USERNAME)
              .contentType(ContentType.JSON)
              .body(request)
              .when()
              .post(BASE_URL + "/credit")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(WalletResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenInvalidMoneyAmount_whenCreditMoney_thenReturnErrorResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.INVALID_MONEY_AMOUNT);

      withAuth(WalletTestData.WALLET_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL + "/credit")
          .then()
          .body("message", containsString("The amount must be between"))
          .extract();
    }

    @Test
    void givenNonExistingWalletAuth_whenCreditMoney_thenReturnErrorResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.CREDIT_MONEY_AMOUNT);

      withAuth(WalletTestData.NON_EXISTING_WALLET_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL + "/credit")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Wallet not found"))
          .extract();
    }
  }

  @Nested
  class DebitMoney {

    @Test
    void givenExistingWalletAuth_whenDebitMoney_thenReturnWalletResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.NORMAL_DEBIT_MONEY_AMOUNT);
      WalletResponse expected = WalletTestData.getDebitedWalletResponse();

      WalletResponse result =
          withAuth(WalletTestData.WALLET_USERNAME)
              .contentType(ContentType.JSON)
              .body(request)
              .when()
              .post(BASE_URL + "/debit")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(WalletResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenInvalidMoneyAmount_whenDebitMoney_thenReturnErrorResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.INVALID_MONEY_AMOUNT);

      withAuth(WalletTestData.WALLET_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL + "/debit")
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("The amount must be between"))
          .extract();
    }

    @Test
    void givenNonExistingWalletAuth_whenDebitMoney_thenReturnErrorResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.CREDIT_MONEY_AMOUNT);

      withAuth(WalletTestData.NON_EXISTING_WALLET_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL + "/debit")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Wallet not found"))
          .extract();
    }

    @Test
    void givenDebitAmountExceedBalance_whenDebitMoney_thenReturnErrorResponse() {
      MoneyRequest request = new MoneyRequest(WalletTestData.TOO_BIG_DEBIT_MONEY_AMOUNT);

      withAuth(WalletTestData.WALLET_USERNAME)
          .contentType(ContentType.JSON)
          .body(request)
          .when()
          .post(BASE_URL + "/debit")
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("The amount should be less than"))
          .extract();
    }
  }

  @Nested
  class RepayDebt {

    @Test
    void givenExistingWalletAuth_whenRepayDebt_thenReturnWalletResponse() {
      WalletResponse expected = WalletTestData.getRepayedWalletResponse();

      WalletResponse result =
          withAuth(WalletTestData.WALLET_USERNAME)
              .when()
              .post(BASE_URL + "/repay-debt")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(WalletResponse.class);

      assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNotHavingDebtWallet_whenRepayDebt_thenReturnErrorResponse() {
      withAuth(WalletTestData.NO_DEBT_WALLET_USERNAME)
          .when()
          .post(BASE_URL + "/repay-debt")
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("You can not repay debt: debt = 0"))
          .extract();
    }

    @Test
    void givenNonExistingWalletAuth_whenRepayDebt_thenReturnErrorResponse() {
      withAuth(WalletTestData.NON_EXISTING_WALLET_USERNAME)
          .when()
          .post(BASE_URL + "/repay-debt")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Wallet not found"))
          .extract();
    }

    @Test
    void givenDebtAmountExceedBalance_whenRepayDebt_thenReturnErrorResponse() {
      withAuth(WalletTestData.DEBT_EXCEED_BALANCE_WALLET_USERNAME)
          .when()
          .post(BASE_URL + "/repay-debt")
          .then()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("message", containsString("You do not have enough to repay this debt"))
          .extract();
    }
  }

  @Nested
  class CanPayWithCard {

    @Test
    void givenNotHavingDebtWallet_whenCanPayWithCard_thenReturnCanPayByCardResponse() {
      CanPayByCardResponse result =
          withAuth(WalletTestData.ADMIN_USERNAME)
              .pathParam("username", WalletTestData.NO_DEBT_WALLET_USERNAME)
              .when()
              .post(BASE_URL + "/{username}/card-payment-check")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(CanPayByCardResponse.class);

      assertThat(result.canPayByCard()).isTrue();
    }

    @Test
    void givenHavingDebtWallet_whenCanPayWithCard_thenReturnCanPayByCardResponse() {
      CanPayByCardResponse result =
          withAuth(WalletTestData.ADMIN_USERNAME)
              .pathParam("username", WalletTestData.WALLET_USERNAME)
              .when()
              .post(BASE_URL + "/{username}/card-payment-check")
              .then()
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(CanPayByCardResponse.class);

      assertThat(result.canPayByCard()).isFalse();
    }

    @Test
    void givenNonExistingWalletUsername_whenCanPayWithCard_thenReturnErrorResponse() {
      withAuth(WalletTestData.ADMIN_USERNAME)
          .pathParam("username", WalletTestData.NON_EXISTING_WALLET_USERNAME)
          .when()
          .post(BASE_URL + "/{username}/card-payment-check")
          .then()
          .statusCode(HttpStatus.NOT_FOUND.value())
          .body("message", containsString("Wallet not found"))
          .extract();
    }
  }
}
