package api;

import data.Url;
import data.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {

    @Step("Создание пользователя   Post /api/auth/register")
    public Response createUser (User user) {

        Response response =
                given ()
                        .header("Content-type", "application/json")
                        .body(user)
                        .when()
                        .post(Url.CREATE_USER);
        return response;
    }

    @Step("Авторизация пользователя   Post /api/auth/login")
    public Response authUser (User user) {

        Response response =
                given ()
                        .header("Content-type", "application/json")
                        .body(user)
                        .when()
                        .post(Url.AUTH_USER);
        return response;
    }



    @Step("Удаление курьера DELETE /api/auth/user")
    public void deleteUser(String accessToken) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .delete(Url.DELETE_USER);

    }

}
