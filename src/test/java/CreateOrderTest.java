import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    OrdersApi ordersApi = new OrdersApi();
    UserApi userApi = new UserApi();
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";

    }
    @Test
    public void createOrderWithAutTest(){
        userApi.createUser(firstUser);
        ordersApi.createOrderWithAut()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success",equalTo(true));;

    }

    @Test
    public void createOrderWithOutAutTest(){
        ordersApi.createOrderWOAut()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success",equalTo(true));;

    }
    @Test
    public void createOrderWithOutIngredientsTest(){
        ordersApi.createOrderWOIngredients()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("success",equalTo(false));;


    }

    @Test
    public void createOrderWithInvalidIngredientsTest(){
        ordersApi.createOrderWithInvalidIngredients()
                .then().statusCode(SC_INTERNAL_SERVER_ERROR);;

    }

    @After
    public void delUser(){

        userApi.deleteUser(firstUser);


    }

}
