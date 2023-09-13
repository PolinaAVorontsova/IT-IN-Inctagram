package brows.data;

import com.codeborne.selenide.Clipboard;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.switchTo;

public class User {
    private String userName;
    private String email;
    private String password;

    private static Faker faker;

    public User(String name, String email, String password) {
        this.userName = name;
        this.email = email;
        this.password = password;
    }
    public static String wrongLogin = "dajoje8283@wlmycnn.com";
    public static String  wrongPass = "Qwe1231!";

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ЗДЕСЬ ЧИТАЕМ ПОСЛЕДНЮЮ ЗАПИСЬ О ЗАРЕГИСТРИРОВАННОМ ПОЛЬЗОВАТЕЛЕ И ЗАПИСЫВАЕМ ИХ В ПЕРЕМЕННЫЕ
    public static String lastUserName;
    public static String lastUserEmail;
    //---------------
    static {
        try {
            // Открываем файл для чтения
            BufferedReader reader = new BufferedReader(new FileReader("src/test/java/brows/data/emailAddressesOfRegisteredUsers.txt"));
            String line;
            String tempUserName = null;
            String tempUserEmail = null;

            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("UserName: ")) {
                    // Записываем значение UserName
                    tempUserName = line.substring("UserName: ".length());
                } else if (line.startsWith("Email: ")) {
                    // Записываем значение Email
                    tempUserEmail = line.substring("Email: ".length());
                }
            }

            // Закрываем файл
            reader.close();

            // Устанавливаем значения в глобальные переменные
            lastUserName = tempUserName;
            lastUserEmail = tempUserEmail;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
