package com.nastyabelova.tests;

import com.nastyabelova.helpers.TestDataHelper;
import com.nastyabelova.pages.RegistrationPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

/**
 * Проверка заполнения формы регистрации студента
 */
@Epic("demoqa.com/automation-practice-form")
public class RegistrationFormTests extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();

    @Test()
    @DisplayName("Тест для проверки заполнении формы регистрации студента")
    @Story("Заполнение формы")
    public void fillRegistrationFormTests() {
        registrationPage.openPage();
        registrationPage.typeFirstName(TestDataHelper.firstName)
                .typeLastName(TestDataHelper.lastName)
                .typeEmail(TestDataHelper.email)
                .typeGender("2")
                .typePhoneNumber(TestDataHelper.phone)
                .typeSubjects()
                .typeHobbies("2")
                .typeHobbies("3")
                .typeUploadPicture(TestDataHelper.PICTURE)
                .typeAddress(TestDataHelper.address)
                .typeState(TestDataHelper.STATE)
                .typeCity(TestDataHelper.CITY)
                .calender.setDate(TestDataHelper.DAY, TestDataHelper.MONTH, TestDataHelper.YEAR);
        registrationPage.submitFormRegistration();

        step("Check form results",()->{
        registrationPage.checkResultsData(TestDataHelper.expectedData);
        });
    }
}

