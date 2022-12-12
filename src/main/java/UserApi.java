import io.restassured.response.Response;

import java.net.CacheResponse;

import static io.restassured.RestAssured.given;

public class UserApi {
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    CreateUser newUser = new CreateUser("kull001@yandex.ru","1234567", "kull001");
    final static String CREATE_USER_URI = "api/auth/register";
    final static String LOGIN_USER_URI = "api/auth/login";
    final static String DELETE_USER_URI = "api/auth/user";

    public Response createUser(CreateUser user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(CREATE_USER_URI);
    }
    public void deleteUser(CreateUser user){
        GetAccessToken accessToken = given()
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN_USER_URI)
                .body().as(GetAccessToken.class);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", ""+ accessToken.getAccessToken())
                .delete(DELETE_USER_URI);
    }

    public Response loginExistingUser(CreateUser user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_URI);
    }

    public Response changingUser(CreateUser user){

        GetAccessToken accessToken = given()
                .header("Content-type", "application/json")
                .body(firstUser)
                .post(LOGIN_USER_URI)
                .body().as(GetAccessToken.class);
    return
        given()
                .header("Content-type", "application/json")
                .header("Authorization", ""+ accessToken.getAccessToken())
                .body(user)
                .patch(DELETE_USER_URI);
    }
    public Response invalidChangingUser(){
        return
                given()
                        .header("Content-type", "application/json")
                        .body(newUser)
                        .patch(DELETE_USER_URI);
    }


}
