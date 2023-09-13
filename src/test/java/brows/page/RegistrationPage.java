package brows.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
//import static com.codeborne.selenide.Selenide.$$;

public class RegistrationPage {
    private SelenideElement userNameField = $("[id=sign-up-userName]");
    private SelenideElement emailField = $("[id=sign-up-email]");
    private SelenideElement passwordField = $("[id=sign-up-password]");
    private SelenideElement passwordConfirmField = $("[id=sign-up-passwordConfirm]");
    private SelenideElement showPassButton = $$("[id=sign-up-password-showPassImage-closeAye]").first();
    private SelenideElement showPassConfirmButton = $$("[id=sign-up-password-showPassImage-closeAye]").last();
    private SelenideElement checkBoxButton = $("[id=sign-up-agreemets]");
    private SelenideElement registrationButton = $("[id=sign-up-submit]");

    private SelenideElement backToLoginButton = $("[id=sign-up-link-to-sign-in]");
    private SelenideElement errorMessageUserName = $$("[id=sign-up-userName-error]").first();       // НУЖЕН ОТДЕЛЬНЫЙ ID для поля пароля и емайл
    private SelenideElement errorMessageEmailOrPassword = $("[id=sign-up-userName-error]");         // НУЖЕН ОТДЕЛЬНЫЙ ID для поля пароля и емайл
    private SelenideElement errorMessageEmail = $$("[id=sign-up-userName-error]").get(1);           // НУЖЕН ОТДЕЛЬНЫЙ ID для поля пароля и емайл
    private SelenideElement errorMessagePass = $$("[id=sign-up-userName-error]").get(2);
    private SelenideElement errorMessagePassConfirm = $$("[id=sign-up-userName-error]").last();


    private SelenideElement buttonLogin = $("[id=sign-in-submit]");

    //private SelenideElement alertError = $("[role=alert]");

    public RegistrationPage() {
        open("https://inctagram.vercel.app/sign-up");
        sleep(1000);
        $("[name=Languages]").click();                                                          // СМЕНА ЯЗЫКА
        $("[value=ru]").click();
        backToLoginButton
                .shouldHave(Condition.text("Логин"))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldUserName(String textError) {
        errorMessageUserName
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldEmail(String textError) {
        errorMessageEmail
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldPass(String textError) {
        errorMessagePass
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldEmailOrPassword(String textError) {
        errorMessageEmailOrPassword
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void getErrorMassageFieldPassConfirm(String textError) {
        errorMessagePassConfirm
                .shouldHave(Condition.text(textError))
                .shouldBe(visible);
    }

    public void buttonRegistrationDisabled() {
        registrationButton
                .shouldBe(disabled);
    }

    public void processRegistration(String userName, String email, String password, String passwordConfirmation) {
        userNameField.click();
        userNameField.setValue(userName);

        emailField.click();
        emailField.setValue(email);

        passwordField.click();
        passwordField.setValue(password);

        passwordConfirmField.click();
        passwordConfirmField.setValue(passwordConfirmation);
    }
}
