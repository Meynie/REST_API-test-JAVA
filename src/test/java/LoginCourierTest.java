import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qa_scooter.api.client.CourierClient;
import ru.qa_scooter.api.model.Courier;
import ru.qa_scooter.api.util.CourierCredentials;

import static org.apache.http.HttpStatus.*;

public class LoginCourierTest {
    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Successful authorization of the courier")
    @Description("Успешная авторизация курьера")
    public void testSuccessfulAuthorizationCourier() {
        Courier courier = Courier.getRandom();

        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Courier authorization with an empty password field")
    @Description("Авторизация курьера c пустым полем пароля")
    public void testCourierAuthorizationWithEmptyPasswordField() {
        Courier courier = Courier.getRandom();

        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierClient.loginResponse(new CourierCredentials(courier.login, "")).statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Courier authorization with an empty login field")
    @Description("Авторизация курьера c пустым полем логина")
    public void testCourierAuthorizationWithEmptyLoginField() {
        Courier courier = Courier.getRandom();

        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierClient.loginResponse(new CourierCredentials("", courier.password)).statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Authorization with incorrect courier login")
    @Description("Авторизация с неправильным логин курьера")
    public void testAuthorizationWithIncorrectCourierLogin() {
        Courier courier = Courier.getRandom();

        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierClient.loginResponse(new CourierCredentials(courier.login + "1", courier.password)).statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Authorization with incorrect courier password")
    @Description("Авторизация с неправильным паролем курьера")
    public void testAuthorizationWithIncorrectCourierPassword() {
        Courier courier = Courier.getRandom();

        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierClient.loginResponse(new CourierCredentials(courier.login, courier.password + "0"))
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Authorization without specifying a password")
    @Description("Авторизация без указания пароля")
    public void AuthorizationWithoutSpecifyingPassword(){
        Courier courier = Courier.getRandom();

        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .extract()
                .path("id");
        courierClient.loginResponse(new CourierCredentials(courier.login, null)).statusCode(SC_BAD_REQUEST);
    }
}
