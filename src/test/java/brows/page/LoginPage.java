package brows.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;


import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class LoginPage {
    private SelenideElement loginField = $("[id=sign-in-email-input]");
    private SelenideElement passwordField = $("[id=sign-in-password-input]");
    private SelenideElement buttonLogin = $("[id=sign-in-submit]");
    private SelenideElement errorMessageEmail = $$("[id=sign-up-userName-error]").first();
    private SelenideElement errorMessagePass = $$("[id=sign-up-userName-error]").last();
    private SelenideElement alertError = $("[role=alert]");

    public LoginPage() {
        sleep(1000);
        $("[name=Languages]").click();                                                          // СМЕНА ЯЗЫКА
        $("[value=ru]").click();
        $("[id=sign-in-link-sign-up]")
                .shouldHave(Condition.text("Регистрация"))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldEmail(String textError) {
        errorMessageEmail
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldPassword(String textError) {
        errorMessagePass
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void getAlertError(String textError) {
        alertError
                .shouldHave(Condition.text(textError), Duration.ofMillis(5000))
                .shouldBe(visible);
    }

    public MyProfilePage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        buttonLogin.click();
        return new MyProfilePage();
    }

    public void invavalidLoginOrPassword(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        buttonLogin.click();

    }

}
