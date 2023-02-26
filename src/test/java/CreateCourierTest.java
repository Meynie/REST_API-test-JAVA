import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qa_scooter.api.client.CourierClient;
import ru.qa_scooter.api.model.Courier;
import ru.qa_scooter.api.util.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

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
    @DisplayName("Checking of the response body and the status code on successful creation of the courier")
    @Description("Успешное создание курьера, проверка тела и кода ответа")
    public void testSuccessfulCreationCourierCheckingBodyTrue() {
        Courier courier = Courier.getRandom();
        ValidatableResponse createResponse = courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier)).extract().path("id");

        createResponse.statusCode(SC_CREATED).and().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Cannot create two identical couriers")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void testCannotCreateTwoIdenticalCouriers() {
        Courier courier = Courier.getRandom();
        courierClient.createResponse(courier);
        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier)).extract().path("id");

        ValidatableResponse createResponse = courierClient.createResponse(courier);
        createResponse.statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Can't create a courier without a login")
    @Description("Нельзя создать курьера без логина")
    public void testCannotCreateCourierWithoutLogin() {
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier("", password, firstName);

        courierClient.createResponse(courier).statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Can't create a courier without a password")
    @Description("Нельзя создать курьера без пароля")
    public void testCannotCreateCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(login, "", firstName);

        courierClient.createResponse(courier).statusCode(SC_BAD_REQUEST);
    }
}