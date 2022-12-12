import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangingUserDataWithoutAutTest {

    UserApi userApi = new UserApi();
    String message = "You should be authorised";
    CreateUser firstUser = new CreateUser("kull001@yandex.ru","12345", "kull001");
    CreateUser newUser = new CreateUser("kull001@yandex.ru","1234567", "kull001");
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";

    }
    @Test
    public void changeUserWithoutAutCodeTest(){
        userApi.invalidChangingUser()
                .then().statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("success",equalTo(false));;
    }

    @Test
    public void changeUserWithoutAutMessageTest(){
        userApi.invalidChangingUser()
                .then().assertThat().body("message", equalTo(message));
    }


    @After
    public void delUser(){

        userApi.deleteUser(firstUser);
        userApi.deleteUser(newUser);


    }
}
