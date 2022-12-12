import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetListOrdersTest {

    OrdersApi ordersApi = new OrdersApi();
    UserApi userApi = new UserApi();
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";

    }
    @Test
    public void createOrdersWithAutTest(){
        userApi.createUser(firstUser);
        ordersApi.getListOrdersWithAut()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success",equalTo(true));

    }
    @Test
    public void createOrdersWOAutTest(){
        ordersApi.createOrderWOIngredients()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("success",equalTo(false));

    }
    @After
    public void delUser(){

        userApi.deleteUser(firstUser);


    }
}
