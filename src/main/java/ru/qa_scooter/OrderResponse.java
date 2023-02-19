package ru.qa_scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class OrderResponse extends RestAssuredClient{

    public static final String COURIER_PATH = "/api/v1/orders/";

    @Step("Запрос на создание заказа")
    public ValidatableResponse createOrderResponse(Order order){
        return  given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Отменяем заказ")
    public boolean cancellationOrder(TrackOrder trackOrder){
        return given()
                .spec(getBaseSpec())
                .body(trackOrder)
                .when()
                .put(COURIER_PATH + "cancel")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }

    @Step("Получаем список заказов")
    public ValidatableResponse GetListOrders(){
        return  given()
                .spec(getBaseSpec())
                .when()
                .get(COURIER_PATH)
                .then();
    }
}
