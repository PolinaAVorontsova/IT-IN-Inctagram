package brows.test;

import brows.data.User;
import brows.page.LoginPage;
import brows.page.RegistrationPage;
import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.netty.util.internal.StringUtil;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import static brows.data.User.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class RegressTest {
    private static Faker faker;
    public String URL_signUpEn = "https://inctagram.vercel.app/sign-up";
    public String URL_signUpRu = "https://inctagram.vercel.app/ru/sign-up";
    public String URL_signInRu = "https://inctagram.vercel.app/ru/sign-in";
    public String wrongLogin = "dajoje8283@wlmycnn.com";
    public String wrongPass = "Qwe1231!";

    User validUser1 = new User(lastUserName, lastUserEmail, "Qwe!123");


    @BeforeEach
    public void setUp() {
        sleep(300);
        Configuration.holdBrowserOpen = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    public void tearDownEach() {
        SelenideLogger.removeListener("allure");
//        closeWindow();
        sleep(300);
    }

    @Order(1)
    @Test
    @Owner("Fedor Sharov")
    @Feature("Регистрация и авторизация")
    @Story("IN-14 / IN-23")
    @Description("Происходит автоматическая генерация нового email (используется dropEmail), Адрес пользователя сохраняется в файле: src/test/java/brows/data/emailAddressesOfRegisteredUsers.txt")
    void autoRegistrationAndAuthorization() throws IOException {
        faker = new Faker(new Locale("en"));
        User createUser = new User("", "", "");
        createUser.setName(faker.name().username().replace(".", ""));
        Configuration.holdBrowserOpen = true;
        open("https://dropmail.me/ru/");
        $(".has-zc")
                .click();
        executeJavaScript("window.open()");
        switchTo().window(1);

        var registrationPage = open(URL_signUpEn, RegistrationPage.class);

        Clipboard clipboard = clipboard();
        createUser.setEmail(clipboard().getText());
        createUser.setPassword("Qwe!123");

        FileWriter writer = new FileWriter("src/test/java/brows/data/emailAddressesOfRegisteredUsers.txt", true);
        writer.write("\n");
        writer.write("UserName: " + createUser.getName() + "\n");
        writer.write("Email: " + createUser.getEmail() + "\n");
        writer.write("_______________________________________");
        writer.close();

        registrationPage.processRegistration(createUser.getName(), createUser.getEmail(), createUser.getPassword(), createUser.getPassword());

        $("[id=sign-up-agreemets]").click();
        $("[id=sign-up-submit]").click();
        $(".modal__header")
                .shouldHave(Condition.text("Email sent"))
                .shouldBe(visible);

        $(".modal__body")
                .shouldBe(visible)
                .shouldHave(Condition.text("Мы отправили ссылку для подтверждения на почту: " + createUser.getEmail()));
        sleep(5000);
        switchTo().window(0);
        $("[rel=noopener]").click();
        switchTo().window(2);
        $("h1 ").shouldHave(Condition.text("Поздравляем!"));
        $("h1 ").shouldHave(Condition.text("Поздравляем!"));
        $("body").shouldHave(Condition.text("Ваша почта была подтверждена успешно"));
        $("[href='/sign-in']").click();

        $("[id=sign-in-email-input]").setValue(createUser.getEmail());
        $("[id=sign-in-password-input]").setValue(createUser.getPassword());
        $("[id=sign-in-submit]").click();

        $("[id=profile-link-to-settings-profile]")
                .shouldHave(Condition.text("Настройки профиля"))
                .shouldBe(visible);

        switchTo().window(0).close();
        switchTo().window(0).close();
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-25")
    @Description("Информирование пользователя - email не верный")
    void wrongLogin() {
        var loginPage = open(URL_signInRu, LoginPage.class);

        loginPage.invalidLoginOrPassword(wrongLogin, validUser1.getPassword());
        loginPage.getAlertError("Auth error");
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-24")
    @Description("Информирование пользователя - password не верный")
    void wrongPass() {
        var loginPage = open(URL_signInRu, LoginPage.class);

        loginPage.invalidLoginOrPassword(lastUserEmail, wrongPass);
        loginPage.getErrorMassageFieldEmail("Неверный пароль или почта");
        loginPage.getErrorMassageFieldPassword("Неверный пароль или почта");
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-15")
    @Description("Информирование пользователя - пользователь с таким email уже существует")
    void ErrorIfEmailExistInSystem() {
        faker = new Faker(new Locale("en"));
        User newUser = new User(faker.name().username().replace(".", ""), validUser1.getEmail(), validUser1.getPassword());

        var registrationPage = open(URL_signUpEn, RegistrationPage.class);
        registrationPage.processRegistration(newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPassword());
        $("[id=sign-up-agreemets]").click();
        $("[id=sign-up-submit]").click();

        registrationPage.getErrorMassageFieldEmailOrPassword("Пользователь с такой почтой уже существует");
        registrationPage.buttonRegistrationDisabled();
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-18")
    @Description("Информирование пользователя - пользователь с таким userName уже существует")
    void ErrorIfUserNameExistInSystem() {
        faker = new Faker(new Locale("en"));
        User newUser = new User(validUser1.getName(), faker.internet().emailAddress(), validUser1.getPassword());

        var registrationPage = open(URL_signUpEn, RegistrationPage.class);
        registrationPage.processRegistration(newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPassword());
        $("[id=sign-up-agreemets]").click();
        $("[id=sign-up-submit]").click();

        registrationPage.getErrorMassageFieldUserName("Пользователь с таким именем уже существует");
        registrationPage.buttonRegistrationDisabled();
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-19")
    @Description("Информирование пользователя - пароли не совпадают")
    void ErrorIfPasswordsNotSame() {
        faker = new Faker(new Locale("en"));
        User newUser = new User(faker.name().username().replace(".", ""), faker.internet().emailAddress(), validUser1.getPassword());

        var registrationPage = open(URL_signUpEn, RegistrationPage.class);
        registrationPage.processRegistration(newUser.getName(), newUser.getEmail(), newUser.getPassword(), StringUtils.reverse(newUser.getPassword()));
        $("[id=sign-up-agreemets]").click();

        registrationPage.getErrorMassageFieldPassConfirm("Пароли не совпадают");
        registrationPage.buttonRegistrationDisabled();
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-20")
    @Description("Информирование пользователя - в имя пользователя больше 30 символов ")
    void ErrorUserNameLonger30Characters() {
        faker = new Faker(new Locale("en"));
        User newUser = new User(faker.lorem().characters(31, true, true), faker.internet().emailAddress(), validUser1.getPassword());

        var registrationPage = open(URL_signUpEn, RegistrationPage.class);
        $("[id=sign-up-userName]").setValue(newUser.getName());
        $("[id=sign-up-email]").click();

        registrationPage.getErrorMassageFieldUserName("Имя пользователя не должно превышать 30 символов");
        registrationPage.buttonRegistrationDisabled();
    }

    @Test
    @Owner("Fedor Sharov")
    @Feature("Авторизация")
    @Story("IN-20")
    @Description("Информирование пользователя - пароль пользователя больше 20 символов ")
    void ErrorPasswordLonger20Characters() {
        faker = new Faker(new Locale("en"));
        User newUser = new User(faker.lorem().characters(30, true, true), faker.internet().emailAddress(), faker.lorem().characters(21, true, true));

        var registrationPage = open(URL_signUpEn, RegistrationPage.class);
        $("[id=sign-up-userName]").setValue(newUser.getName());
        $("[id=sign-up-email]").setValue(newUser.getEmail());
        $("[id=sign-up-password]").setValue(newUser.getPassword());
        $("[id=sign-up-passwordConfirm]").click();

        registrationPage.getErrorMassageFieldEmailOrPassword("Пароль не должен превышать 20 символов");
        registrationPage.buttonRegistrationDisabled();
    }
}
