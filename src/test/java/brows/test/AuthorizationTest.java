package brows.test;

import brows.page.LoginPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.Configuration;
import brows.data.User;

import static brows.data.User.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationTest {
    User validUser1 = new User(lastUserName, lastUserEmail, "Qwe!123");
    public String URL_signInRu = "https://inctagram.vercel.app/ru/sign-in";

    @BeforeEach
    public void setUp() {
        sleep(500);
        Configuration.holdBrowserOpen = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterEach
    public void close() {
        closeWindow();
    }

    @Test       /* Успешная авторизация */
    void successfulAuthorization() {
        var loginPage = open(URL_signInRu, LoginPage.class);
        var myProfilePage = loginPage.validLogin(validUser1.getEmail(), validUser1.getPassword());
    }

    @Test
    void UpdateHappyPath() {
        var loginPage = open(URL_signInRu, LoginPage.class);
        //Это заглушка теста
    }


    @Test
    void wrongLogin() {
        var loginPage = open(URL_signInRu, LoginPage.class);

        loginPage.invalidLoginOrPassword(wrongLogin, validUser1.getPassword());
        loginPage.getAlertError("Auth error");
    }

    @Test
    void wrongPass() {
        var loginPage = open(URL_signInRu, LoginPage.class);

        loginPage.invalidLoginOrPassword(validUser1.getEmail(), wrongPass);
        loginPage.getErrorMassageFieldEmail("Неверный пароль или почта");
        loginPage.getErrorMassageFieldPassword("Неверный пароль или почта");
    }

}
