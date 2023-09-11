package brows.data;

import static com.codeborne.selenide.Selenide.*;


public class ChangingLanguage {

    public void changingLanguageToRussian() {
        sleep(500);
        $("[name=Languages]").click();
        $("[value=ru]").click();
        sleep(500);
    }

    public void changingLanguageToEnglish() {
        sleep(500);
        $("[name=Languages]").click();
        $("[value=en]").click();
        sleep(500);
    }

}
