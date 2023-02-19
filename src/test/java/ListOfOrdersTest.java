import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.qa_scooter.OrderResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.apache.http.HttpStatus.SC_OK;

public class ListOfOrdersTest {
    /*
    public OrderResponse orderResponse;

    @Before
    public void setup(){
        orderResponse = new OrderResponse();
    }

    @Test
    @DisplayName("Проверка списка заказов")
    public void CheckingOrderList(){
        ValidatableResponse listOrder = orderResponse.GetListOrders();
        listOrder.statusCode(SC_OK);
        Assert.assertNotNull("Список заказов пуст", listOrder);
        // проверка списка заказов по orders, количество заказов в теле ответа запроса
        ArrayList ordersFromPage = listOrder.extract().path("orders");
        Assert.assertTrue("Список заказов пуст", ordersFromPage.size() > 0);
        for (int i = 0; i < ordersFromPage.size(); i++){
            LinkedHashMap id = (LinkedHashMap) ordersFromPage.get(i);
            Assert.assertNotNull("ID заказа пустое", id.get("id"));
        }
    }

     */
}
