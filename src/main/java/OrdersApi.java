import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersApi {
    Order order = new Order();
    UserApi userApi = new UserApi();
    final static String LOGIN_USER_URI = "api/auth/login";
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    final static String GET_LIST_INGREDIENTS_URI = "api/ingredients";
    final static String CREATE_ORDER_URI = "api/orders";


   public Response createOrderWithAut(){

       ListIngridients listIngridients = given()
               .get(GET_LIST_INGREDIENTS_URI)
               .body().as(ListIngridients.class);

       String[] myIngredients = {listIngridients.getData().get(0).get_id(),listIngridients.getData().get(1).get_id()};
       order.setIngredients(myIngredients);
       GetAccessToken accessToken = given()
               .header("Content-type", "application/json")
               .body(firstUser)
               .post(LOGIN_USER_URI)
               .body().as(GetAccessToken.class);
       return given()
               .header("Content-type", "application/json")
               .header("Authorization", ""+ accessToken.getAccessToken())
               .body(order)
               .post(CREATE_ORDER_URI);
   }

    public Response createOrderWOAut(){

        ListIngridients listIngridients = given()
                .get(GET_LIST_INGREDIENTS_URI)
                .body().as(ListIngridients.class);

        String[] myIngredients = {listIngridients.getData().get(2).get_id(),listIngridients.getData().get(3).get_id()};
        order.setIngredients(myIngredients);

        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER_URI);
    }

    public Response createOrderWOIngredients(){

        String[] myIngredients = {};
        order.setIngredients(myIngredients);

        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER_URI);
    }
    public Response createOrderWithInvalidIngredients(){

        String[] myIngredients = {"aaaaaaaaaaaaaaaa","bbbbbbbbbbbbbbb"};
        order.setIngredients(myIngredients);

        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER_URI);
    }
    public Response getListOrdersWithAut(){
        GetAccessToken accessToken = given()
                .header("Content-type", "application/json")
                .body(firstUser)
                .post(LOGIN_USER_URI)
                .body().as(GetAccessToken.class);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", ""+ accessToken.getAccessToken())
                .get(CREATE_ORDER_URI);
    }
    public Response getListOrdersWOAut(){
        return given()
                .header("Content-type", "application/json")
                .get(CREATE_ORDER_URI);
    }


}



