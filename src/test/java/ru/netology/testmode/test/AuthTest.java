package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void successfulIfRegistretedAciveUserTest() {
        var regisretedUser = getRegisteredUser("active");
        $("[data-test-id = 'login'] input").setValue(regisretedUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(regisretedUser.getPassword());
        $("button.button").click();
        $("h2").shouldBe(Condition.text("Личный кабинет"));
    }

    @Test
    void errorIfNotregisteredUserTest() {
        var notRegisretedUser = getUser("active");
        $("[data-test-id = 'login'] input").setValue(notRegisretedUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(notRegisretedUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void errorIfBlockedUserTest() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id = 'login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void errorIfWrongLoginTest() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id = 'login'] input").setValue(wrongLogin);
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void errorIfWrongPasswordTest() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}