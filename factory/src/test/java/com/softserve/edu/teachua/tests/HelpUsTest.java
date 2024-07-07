package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.data.PayPalOptionMenuContents;
import com.softserve.edu.teachua.data.PaymentMenuContents;
import com.softserve.edu.teachua.data.TestData;
import com.softserve.edu.teachua.data.User;
import com.softserve.edu.teachua.pages.component.form.PaymentFormComponent;
import com.softserve.edu.teachua.pages.component.option.CurrencyOptions;
import com.softserve.edu.teachua.pages.main.HelpUsPage;
import com.softserve.edu.teachua.pages.main.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.softserve.edu.teachua.data.TestData.EXPECTED_ERROR_MESSAGES;
import static com.softserve.edu.teachua.data.TestData.MATRIX;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HelpUsTest extends TestRunner {
    private static PayPalOptionMenuContents[] menuContents;
    private static PaymentMenuContents[] paymentMenuContents;

    @BeforeAll
    static void beforeAll() {
        menuContents = PayPalOptionMenuContents.values();
        paymentMenuContents = PaymentMenuContents.values();
    }

    @Test
    @DisplayName("Test Navigation to Help Us Page, URL and Title Assertion, and Opening Other Payment Options")
    void checkDisablePayButton() {
        PaymentFormComponent menu = new HomePage(driverWrapper.getDriver())
                .visit()
                .gotoHelpUsPage()
                .assertCurrentURL()
                .assertCurrentTitle()
                .openOtherWayPayment();

        Assertions.assertTrue(menu.isDisplayedPayButton(),
                "Pay button is not displayed on the payment form component.");
    }

    @Test
    @DisplayName("Test URL and Title Verification in Other Ways to Pay Menu")
    void checkUrlAndTitleOtherPayMenu() {
        new HomePage(driverWrapper.getDriver())
                .visit()
                .gotoHelpUsPage()
                .openOtherWayPayment()
                .assertPaymentOptionsText(menuContents)
                .assertPaymentOptionsUrl(menuContents);
    }

    static Stream<? extends Arguments> optionMenuContents() {
        return IntStream.range(1, PayPalOptionMenuContents.values().length)
                .mapToObj(i -> arguments(PayPalOptionMenuContents.values()[i]));
    }

    @ParameterizedTest
    @MethodSource("optionMenuContents")
    @DisplayName("Test Payment Option Selection and Assertion in Other Ways to Pay Menu")
    void checkEachOptionOtherPayMenus(PayPalOptionMenuContents contents) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .openOtherWayPayment()
                .assertOptionsIsContains(contents)
                .selectPayment(contents)
                .assertOptionsText(contents);
    }

    static Stream<? extends Arguments> payPalOptionMenuContents() {
        return Stream.of(PayPalOptionMenuContents.values()).map(Arguments::of);
    }

    @MethodSource("payPalOptionMenuContents")
    @ParameterizedTest(name = "Check Each in Payment Menu: {0}")
    @DisplayName("Test Check of Options Containing Specific Contents in Other Ways to Pay Menu")
    void checkEachOptionPayMenuIsContainsInDropdown(PayPalOptionMenuContents contents) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .openOtherWayPayment()
                .assertOptionsIsContains(contents);
    }

    @Test
    @DisplayName("Test Create Options in Other Ways to Pay Menu by using Factory")
    void checkTitlePaymentOptionsWithFactory() {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .openOtherWayPayment()
                .assertFactoryOptions(menuContents, paymentMenuContents);
    }

    @Test
    @DisplayName("Test PayPal Liquid Card Option Selection and Assertion of Option Text")
    void checkEachOptionOtherPayMenu() {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .openOtherWayPayment()
                .selectPayment(PayPalOptionMenuContents.LIQUID_CARD)
                .assertOptionsText(PayPalOptionMenuContents.LIQUID_CARD);
    }

    static Stream<Arguments> amountAndExpectedMessages() {
        return Stream.of(
                Arguments.of("", "Вкажіть суму"),
                Arguments.of("3", "Сума повинна бути не меншою 5 UAH"),
                Arguments.of("150001", "Сума не повинна перевищувати 150000 UAH")
        );
    }

    @ParameterizedTest
    @MethodSource("amountAndExpectedMessages")
    @DisplayName("Test Unsuccessful Amount Input and Validation Message with Default Currency Google Payment")
    void testUnsuccessfulAmountInputAndValidationMessageWithDefaultCurrencyGooglePayment(String amount, String expectedMessage) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(amount)
                .openOtherWayPayment()
                .submitGPayment()
                .assertHelpBlockLabel(expectedMessage)
                .assertFreePayAmountInputHasRedBorder();
    }

    static Stream<Arguments> currencyOptions() {

        return Stream.of(CurrencyOptions.values()).map(Arguments::of);
    }

    @MethodSource("currencyOptions")
    @ParameterizedTest(name = "Check validation messages for different amounts {0}")
    @DisplayName("Test Payment Amount Error Messages for Different Currencies")
    void testUnsuccessfulAmountBufferAndValidationMessageWithCurrencyUSD(CurrencyOptions currency) {
        PaymentFormComponent form = new HelpUsPage(driverWrapper.getDriver())
                .visit()
                .openOtherWayPayment()
                .selectCurrency(currency);

        Arrays.stream(TestData.ERROR_MESSAGES).forEach(data ->
                form.assertPaymentAmountMessage(data[0], String.format(data[1], currency.getOptionName())));
    }

    @ValueSource(ints = {6, 7, 75000, 149999, 150000})
    @ParameterizedTest(name = "Check BVA validation messages for different amounts {0}")
    @DisplayName("Test Google Payment Option through Other Ways to Pay Menu: Fill amount, submit Google payment, switch to Google window, and assert header")
    void testSuccessfulAmountInputAndValidationMessageWithDefaultCurrencyGooglePayment(int amount) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(String.valueOf(amount))
                .openOtherWayPayment()
                .submitGPayment()
                .switchToGoogleWindow()
                .assertHeader();
    }

    private List<String> getExpectedMessages(int[] indices) {
        return Arrays.stream(indices)
                .mapToObj(EXPECTED_ERROR_MESSAGES::get)
                .toList();
    }

    @ValueSource(ints = {6, 75000, 150000})
    @ParameterizedTest(name = "Check Each Error Input Messages {0}")
    @DisplayName("Test Error Messages for All Payment Options through Other Ways to Pay Menu")
    void checkOptionOtherPayMenuErrorInputMessages(int amount) {
        HelpUsPage page = new HelpUsPage(driverWrapper.getDriver()).visit();

        IntStream.range(0, paymentMenuContents.length).forEach(i -> page
                .fillAmount(String.valueOf(amount))
                .openOtherWayPayment()
                .selectPayment(paymentMenuContents[i])
                .submitNext()
                .assertErrorInputMessageLabels(getExpectedMessages(MATRIX[i])));
    }

    static Stream<Arguments> userData() {
        return Stream.of(User.values()).map(Arguments::of);
    }

    @MethodSource("userData")
    @ParameterizedTest(name = "Check Option in Other Payment way Credit_CARD: {arguments}")
    @DisplayName("Test Card Payment Option through Other Ways to Pay Menu: Fill card details, submit payment, and assert submission")
    void checkOptionOtherPayMenu(User user) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(user.amount())
                .openOtherWayPayment()
                .selectPayment(paymentMenuContents[0])
                .fillCardNumberInput(user.cardNumber())
                .fillCardValidityInput(user.expiredAt())
                .fillCVVInput(user.cvv())
                .fillCardHolderInput(user.fullName())
                .fillCardPhoneInput(user.phone())
                .fillCardEmailInput(user.email())
                .checkEmptyErrorMessages()
                .submitPayment()
                .assertSubmit();
    }

    @MethodSource("userData")
    @ParameterizedTest(name = "Check Option in Other Payment way Privat24: {arguments}")
    @DisplayName("Test Privat24 Payment Option through Other Ways to Pay Menu: Fill name, phone, and email, submit Privat24 payment, and assert submission")
    void checkOptionOtherPayMenuPrivate24(User user) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(user.amount())
                .openOtherWayPayment()
                .selectPayment(paymentMenuContents[1])
                .fillNameInput(user.fullName())
                .fillPhoneInput(user.phone())
                .fillEmailInput(user.email())
                .checkEmptyErrorMessages()
                .submitPrivatPayment()
                .assertSubmitPrivat();
    }

    @MethodSource("userData")
    @ParameterizedTest(name = "Check Option in Other Payment way Terminal: {0}")
    @DisplayName("Test Terminal Payment Option through Other Ways to Pay Menu: Fill name, phone, and email, submit terminal payment, and assert submission")
    void checkOptionOtherPayMenuTerminal(User user) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(user.amount())
                .openOtherWayPayment()
                .selectPayment(paymentMenuContents[2])
                .fillNameInput(user.fullName())
                .fillPhoneInput(user.phone())
                .fillEmailInput(user.email())
                .checkEmptyErrorMessages()
                .submitTerminalPayment()
                .assertSubmitTerminal();
    }

    @MethodSource("userData")
    @ParameterizedTest(name = "Check Option in Other Payment way MasterPass: {0}")
    @DisplayName("Test MasterPass Payment Option through Other Ways to Pay Menu: Fill phone and CVV, submit MasterPass payment, and assert submission")
    void checkOptionOtherPayMenuMasterPass(User user) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(user.amount())
                .openOtherWayPayment()
                .selectPayment(paymentMenuContents[3])
                .fillPhoneInput(user.phone())
                .fillPasswordInput(user.cvv())
                .checkEmptyErrorMessages()
                .submitMasterPassPayment()
                .assertSubmitMasterPass();
    }

    @MethodSource("userData")
    @ParameterizedTest(name = "Check Option through Other Ways to Pay Menu: {arguments}")
    @DisplayName("Test Visa Payment Option through Other Ways to Pay Menu: Submit next, submit Visa payment, and assert submission")
    void checkOptionOtherPayMenuVisa(User user) {
        new HelpUsPage(driverWrapper.getDriver()).visit()
                .fillAmount(user.amount())
                .openOtherWayPayment()
                .selectPayment(paymentMenuContents[4])
                .submitNext()
                .submitVisaPayment()
                .assertSubmitVisa();
    }
}
