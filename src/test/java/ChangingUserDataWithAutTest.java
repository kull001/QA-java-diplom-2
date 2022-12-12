import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
@RunWith(Parameterized.class)
public class ChangingUserDataWithAutTest {
    private final CreateUser user1;
    private final CreateUser user2;

    UserApi userApi = new UserApi();
    String message = "You should be authorised";

    public ChangingUserDataWithAutTest(CreateUser user1, CreateUser user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    @Parameterized.Parameters
    public static Object[][]SetUsers() {
        return new Object[][]{
                {new CreateUser("kull001@yandex.ru","12345", "kull001"),new CreateUser("kull0011@yandex.ru","12345", "kull001")},
                {new CreateUser("kull001@yandex.ru","12345", "kull001"),new CreateUser("kull001@yandex.ru","1234567", "kull001")},
                {new CreateUser("kull001@yandex.ru","12345", "kull001"),new CreateUser("kull001@yandex.ru","12345", "kull0011")},
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";

    }

    @Test
    public void changeUserWithAutTest() {
        userApi.createUser(user1);
        userApi.changingUser(user2)
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("success",equalTo(true));
    }



    @After
    public void delUser(){

        userApi.deleteUser(user1);
        userApi.deleteUser(user2);


    }
}
