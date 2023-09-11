package brows.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MyProfilePage {
    private SelenideElement loginField = $("[id=sign-in-email-input]");
    private SelenideElement passwordField = $("[id=sign-in-password-input]");
    private SelenideElement buttonLogin = $("[id=sign-in-submit]");
//    private SelenideElement errorMassage = $("[data-test-id='error-notification']"); //изменить на мою

    public MyProfilePage() {
        $("[id=profile-link-to-settings-profile]")
                .shouldHave(Condition.text("Настройки профиля"), Duration.ofMillis(5000))
                .shouldBe(visible, Duration.ofMillis(5000));
    }

}
