package brows.test;

import brows.page.LoginPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.Configuration;
import brows.data.User;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationTest {
    User validUser1 = new User("dajoje", "rtnzsfzzc@laste.ml", "Qwe!123");
    public String wrongLogin = "dajoje8283@wlmycnn.com";
    public String wrongPass = "Qwe1231!";


    @BeforeEach
    public void setUp() {
        sleep(1000);
        Configuration.holdBrowserOpen = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test       /* Успешная авторизация */
    void successfulAuthorization() {
        var loginPage = open("https://inctagram.vercel.app/ru/sign-in", LoginPage.class);
        var myProfilePage = loginPage.validLogin(wrongLogin, validUser1.getPassword());
        closeWindow();
    }


    @Test
    void wrongLogin() {
        var loginPage = open("https://inctagram.vercel.app/ru/sign-in", LoginPage.class);

        loginPage.invavalidLoginOrPassword(wrongLogin, validUser1.getPassword());
        loginPage.getAlertError("Auth error");
        closeWindow();
    }

    @Test
    void wrongPass() {
        var loginPage = open("https://inctagram.vercel.app/ru/sign-in", LoginPage.class);

        loginPage.invavalidLoginOrPassword(validUser1.getEmail(), wrongPass);
        loginPage.getErrorMassageFieldEmail("Неверный пароль или почта");
        loginPage.getErrorMassageFieldPassword("Неверный пароль или почта");
        closeWindow();
    }

}
