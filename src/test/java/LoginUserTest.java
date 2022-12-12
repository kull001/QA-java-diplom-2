import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    UserApi userApi = new UserApi();
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    CreateUser notExistingUser = new CreateUser("kull002@yandex.ru","123456", "kull002");
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";

    }
    @Test
    public void loginExistingUserTest(){
        userApi.createUser(firstUser);
        userApi.loginExistingUser(firstUser)
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success",equalTo(true));
    }

    @Test
    public void loginNotExistingUserTest(){
        userApi.createUser(firstUser);
        userApi.loginExistingUser(notExistingUser)
                .then().statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("success",equalTo(false));
    }

    @After
    public void delUser(){

        userApi.deleteUser(firstUser);


    }
}