package api;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@Epic("Api tests")
@Feature("Reqres service")
@Story("Методы для работы с пользователем")
@Link(name = "Документация сервиса", url = "https://reqres.in/")
@Owner("Кочергин Никита")
public class RequresTest {

    private final static String URL = "https://reqres.in/";

    @Test
    @Description("Проверяем что при регистрации пользователя поля id и token совпадают")
    @DisplayName("Регистрация нового пользователя")
    @Step("Отправить запрос POST /register. Позитивный тест")
    public void succesRegUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        RegisterSucces registerSucces = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(RegisterSucces.class);
        Assertions.assertNotNull(registerSucces.getId());
        Assertions.assertNotNull(registerSucces.getToken());

        Assertions.assertEquals(id, registerSucces.getId());
        Assertions.assertEquals(token, registerSucces.getToken());
    }

    @Test
    @Description("Проверяем что при регистрации пользователя без пароля в ответе будет ошибка")
    @DisplayName("Регистрация нового пользователя без пароля")
    @Step("Отправить запрос POST /register. Негативный тест")
    public void unSuccesRegUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(400));
        Register user = new Register("sydney@fife", "");
        UnSuccesRegister unSuccesRegister = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccesRegister.class);

        Assertions.assertEquals("Missing password", unSuccesRegister.getError());
    }

    @Test
    @Description("Проверяем что при изменении данных пользователя поля name и job изменились")
    @DisplayName("Обновление существующего пользователя")
    @Step("Отправить запрос PUT /users/{id}. Позитивный тест")
    public void succesUpdateUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(200));
        String name = "morpheus";
        String job = "zion resident";
        Update update = new Update(name, job);
        UpdateResponse updateResponse = given()
                .body(update)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UpdateResponse.class);

        Assertions.assertNotNull(updateResponse.getName());
        Assertions.assertNotNull(updateResponse.getJob());
        Assertions.assertNotNull(updateResponse.getUpdatedAt());

        Assertions.assertEquals(name, updateResponse.getName());
        Assertions.assertEquals(job, updateResponse.getJob());
    }

    @Test
    @Description("Проверяем что при изменении данных НЕсуществующего пользователя в ответе будет ошибка")
    @DisplayName("Обновление НЕсуществующего пользователя")
    @Step("Отправить запрос PUT /users/{id}. Негативный тест")
    public void unSuccesUpdateUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(200));
        String name = "ben";
        String job = "manager";
        Update update = new Update(name, job);
        UpdateResponse updateResponse = given()
                .body(update)
                .when()
                .put("api/users/25")
                .then().log().all()
                .extract().as(UpdateResponse.class);
        Assertions.assertNotNull(updateResponse.getName());
        Assertions.assertNotNull(updateResponse.getJob());
        Assertions.assertNotNull(updateResponse.getUpdatedAt());

        Assertions.assertEquals(name, updateResponse.getName());
        Assertions.assertEquals(job, updateResponse.getJob());
    }

    @Test
    @Description("Проверяем статус код при успешном удалении")
    @DisplayName("Удаление существующего пользователя")
    @Step("Отправить запрос DELETE /users/{id}. Позитивный тест")
    public void SuccesDeleteUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }

    @Test
    @Description("Проверяем статус код при неудачном удалении")
    @DisplayName("Удаление НЕсуществующего пользователя")
    @Step("Отправить запрос DELETE /users/{id}. Негативный тест")
    public void unSuccesDeleteUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(204));
        given()
                .when()
                .delete("api/users/30")
                .then().log().all();
    }

    @Test
    @Description("Проверяем правильность полученных данных пользователя")
    @DisplayName("Получение данных существующего пользователя")
    @Step("Отправить запрос GET /users/{id}. Позитивный тест")
    public void succesGetUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(200));
        Integer id = 2;
        UserData userData = given()
                .when()
                .get("/api/users/" + id)
                .then().log().all()
                .extract().body().jsonPath().getObject("data", UserData.class);

        Assertions.assertNotNull(userData.getId());
        Assertions.assertNotNull(userData.getEmail());
        Assertions.assertNotNull(userData.getFirst_name());
        Assertions.assertNotNull(userData.getLast_name());
        Assertions.assertNotNull(userData.getAvatar());

        Assertions.assertEquals(id, userData.getId());

    }

    @Test
    @Description("Проверяем статус код при неудачном получении данных пользователя")
    @DisplayName("Получение данных НЕсуществующего пользователя")
    @Step("Отправить запрос GET /users/{id}. Негативный тест")
    public void unSuccesGetUser() {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpec(404));
        given()
                .when()
                .get("/api/users/23")
                .then().log().all();

    }

}
