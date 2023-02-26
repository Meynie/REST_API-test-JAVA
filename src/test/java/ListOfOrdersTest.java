import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.qa_scooter.api.client.OrderResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ListOfOrdersTest {
    public OrderResponse orderResponse;

    @Before
    public void setup() {
        orderResponse = new OrderResponse();
    }

    @Test
    @DisplayName("Проверка списка заказов")
    public void CheckingOrderList() {
        ValidatableResponse listOrder = orderResponse.GetListOrders();

        ArrayList ordersFromPage = listOrder.extract().path("orders");
        for (int i = 0; i < ordersFromPage.size(); i++) {
            LinkedHashMap id = (LinkedHashMap) ordersFromPage.get(i);
            Assert.assertNotNull("ID заказа пустое", id.get("id"));
        }
    }
}
