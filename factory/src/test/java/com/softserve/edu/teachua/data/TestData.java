package com.softserve.edu.teachua.data;

import java.util.List;

public class TestData {
    public static final String PRIVATE_URL = "https://www.privat24.ua/liqpay/uk/privat24wallet/login/";
    public static final String HELP_US_URL = "https://secure.wayforpay.com/payment/s0f2891d77061";
    public static final String TERMINAL_URL = "https://www.liqpay.ua/en/checkout/cash/";
    public static final String PAYMENT = "https://secure.wayforpay.com/closing";
    public static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    public static final String BASE_CLUBS_URL = BASE_URL + "clubs";
    public static final String TEACH_CHALLENGE_URL = BASE_URL + "challenges/2";
    public static final String ONLY_CHALLENGE_URL = BASE_URL + "challenges/1";
    public static final String NEWS_URL = BASE_URL + "news";
    public static final String ABOUT_US_URL = BASE_URL + "about";
    public static final String SERVICE_URL = BASE_URL + "service";
    public static final String COMMON_HEADER = "Ініціатива \"Навчай українською\"";
    public static final String BEGIN_TEACH_LABEL_MESSAGE = "Ініціатива";
    public static final String NEWS_HEADER = "Новини";
    public static final String[][] ERROR_MESSAGES = {{"", "Вкажіть суму"}, {"150001", "Сума не повинна перевищувати 150000 %s"}};
    public static final String EXPECTED_MASTER_PASS_HEADER = "Операція неуспішна";
    public static final String EXPECTED_MASTER_PASS_DESCRIPTION = "На жаль, спроба авторизації в гаманці MasterPass була невдалою";
    public static final String VISA_MESSAGE = "Найпростіший спосіб для сплати онлайн";

    public static final int[][] MATRIX = {
            {0, 1, 2, 3, 4, 5},
            {4, 5, 6},
            {4, 5, 6},
            {7, 8},
            {}
    };

    public static final List<String> EXPECTED_ERROR_MESSAGES = List.of(
            "Невiрна картка",
            "Невірний термін дії",
            "Введiть CVV2",
            "Введіть ПІБ власника картки",
            "Номер телефону надто короткий",
            "Введіть email",
            "Введіть ПІБ платника",
            "Введіть номер телефону",
            "Вкажіть пароль"
    );

    public static final List<String> ERROR_EXPECTED_MESSAGES = List.of(
            "Ініціатива \"Навчай українською\"",
            "Будь ласка, зверніться до Вашого банку для уточнення причини відмови у сплаті, або спробуйте зробити оплату іншою карткою"
    );

    public static final List<String> ERROR_EXPECTED_PRIVAT_MESSAGES = List.of(
            "Я підтверджую, що ознайомлений(а) з Умовами та Правилами, та даю згоду на обробку моїх персональних даних у Банку\n" +
                    "QR-код для входу через смартфон"
    );
    public static final List<String> ERROR_TERMINAL_MESSAGES = List.of(
            "Pay in PrivatBank self-service terminals",
            "Terminals on the map",
            "Instruction",
            "Phone number for settling a payment account",
            "By clicking the «Pay» button, you confirm that you are familiar with the list of information about the service and accept the terms of the Terms and conditions"
    );
}
