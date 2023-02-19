import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qa_scooter.Courier;
import ru.qa_scooter.CourierClient;
import ru.qa_scooter.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setup(){
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        if (courierId > 0){
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Successful creation of a courier")
    @Description("Успешное создание курьера")
    public void testSuccessfulCreationCourier(){
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.createResponse(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");

        assertTrue("Курьер не создан", isCreated);

        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Checking the status code when the courier is successfully created")
    @Description("Проверка кода ответа при успешном создании курьера")
    public void testSuccessfulCreationCourierCheckingStatusCode(){
        Courier courier = Courier.getRandom();

        ValidatableResponse createResponse = courierClient.createResponse(courier);
        createResponse.statusCode(SC_CREATED);

        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Checking of the response body upon successful creation of the courier")
    @Description("Проверка тела ответа при успешном создании курьера")
    public void testSuccessfulCreationCourierCheckingBodyTrue(){
        Courier courier = Courier.getRandom();

        ValidatableResponse createResponse = courierClient.createResponse(courier);
        createResponse.assertThat().body("ok", equalTo(true));

        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Cannot create two identical couriers")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void testCannotCreateTwoIdenticalCouriers(){
        Courier courier = Courier.getRandom();

        boolean isCreatedFirstCourier = courierClient.createResponse(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");

        assertTrue("Курьер не создан", isCreatedFirstCourier);

        courierId = courierClient.loginResponse(CourierCredentials.getCourierCredentials(courier))
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");

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