package com.nastyabelova.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.nastyabelova.config.CredentialsConfig;
import com.nastyabelova.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {

    @BeforeAll
    @Tag("properties")
    static void beforeAll() {
        String url = System.getProperty("url", "selenoid.autotests.cloud/wd/hub");
        String browserSize = System.getProperty("browserSize", "1920x1080");
        String browser = System.getProperty("browser", "chrome");
        String version = System.getProperty("version", "100.0");

        /**
         *Объявляем переменную с типом интерфейс для логина/пароля
         */
        CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class);

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = browserSize;
        Configuration.timeout = 10000;
        Configuration.remote = "https://" + credentials.login() + ":" + credentials.password() + "@" + url;
        Configuration.browser = browser;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        capabilities.setCapability("browserVersion", version);
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
