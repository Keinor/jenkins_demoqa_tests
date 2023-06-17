package com.nastyabelova.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.nastyabelova.helpers.TestDataHelper;
import com.nastyabelova.pages.components.CalenderComponent;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;

import java.io.File;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static java.lang.String.format;


public class RegistrationPage {

    private final SelenideElement formTitle = $(".practice-form-wrapper"), firstNameInput = $("#firstName"), lastNameInput = $("#lastName"), emailInput = $("#userEmail"), userNumberInput = $("#userNumber"), subjectsInput = $("#subjectsInput"), uploadPicture = $("#uploadPicture"), addressArea = $("#currentAddress"), stateOption = $("#react-select-3-input"), cityOption = $("#react-select-4-input"), submitForm = $("#submit");
    public CalenderComponent calender = new CalenderComponent();

    @Step("Открыли главную страницу")
    public void openPage() {
        open("/automation-practice-form");
        zoom(0.7);
        formTitle.shouldHave(text(TestDataHelper.FORM_TITLE));
        step("Удалили футер с рекламой, перекрывающий кнопку Submit", () -> {
            executeJavaScript("$('#fixedban').remove()");
            executeJavaScript("$('footer').remove()");
        });
    }

    @Step("Заполнили имя")
    public RegistrationPage typeFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    @Step("Заполнили фамилию")
    public RegistrationPage typeLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    @Step("Заполнили email")
    public RegistrationPage typeEmail(String value) {
        emailInput.setValue(value);
        return this;
    }

    @Step("Выбрали гендер")
    public RegistrationPage typeGender(String key) {
        $x("//label[@for='gender-radio-" + key + "']").click();
        return this;
    }

    @Step("Ввели номер телефона")
    public RegistrationPage typePhoneNumber(String value) {
        userNumberInput.setValue(value);
        return this;
    }

    @Step("Заполнили предметы из предлагаемых")
    public RegistrationPage typeSubjects() {
        subjectsInput.setValue(TestDataHelper.SUBJECTS_INPUT).pressEnter();
        return this;
    }

    @Step("Выбрали хобби")
    public RegistrationPage typeHobbies(String key) {
        $x("//label[@for='hobbies-checkbox-" + key + "']").click();
        return this;
    }

    @Step("Загрузили картинку")
    public RegistrationPage typeUploadPicture(String value) {
        uploadPicture.uploadFile(new File("src/test/resources/img/" + value));
        return this;
    }

    @Step("Ввели адрес")
    public RegistrationPage typeAddress(String value) {
        addressArea.setValue(value);
        return this;
    }

    @Step("Выбрали штат")
    public RegistrationPage typeState(String value) {
        stateOption.setValue(value).pressEnter();
        return this;
    }

    @Step("Выбрали город согласно штату")
    public RegistrationPage typeCity(String value) {
        cityOption.setValue(value).pressEnter();
        return this;
    }

    @Step("Отправили заполненную форму")
    public RegistrationPage submitFormRegistration() {
        submitForm.click();
        return this;
    }

    @Step("Проверка результата отправленных данных")
    public void checkResultsData(Map<String, String> expectedData) {
        ElementsCollection lines = $$(".table-responsive tbody tr").snapshot();

        SoftAssertions softly = new SoftAssertions();

        for (SelenideElement line : lines) {
            String keyTd = line.$("td").text();
            String expectedValue = expectedData.get(keyTd);
            String actualValueTd = line.$("td", 1).text();

            softly.assertThat(actualValueTd).as(format("\nTable: %s contains %s, but expected %s", keyTd, expectedValue, actualValueTd)).isEqualTo(expectedValue);
        }
        softly.assertAll();
    }
}
