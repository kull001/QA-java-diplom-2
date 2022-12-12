import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    UserApi userApi = new UserApi();
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    CreateUser invalidUser = new CreateUser("testUser1@yandex.ru","12345");
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";

    }
    @Test
    public void createUniqueUserTest(){
        userApi.createUser(firstUser)
               .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success",equalTo(true));
    }
    @Test
    public void createNotUniqueUserTest(){
        userApi.createUser(firstUser);
        userApi.createUser(firstUser)
                .then().statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("success",equalTo(false));
    }
    @Test
    public void createInvalidUserTest() {
        userApi.createUser(invalidUser)
                .then().statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("success",equalTo(false));
    }


    @After
    public void delUser(){

        userApi.deleteUser(firstUser);


    }
}
