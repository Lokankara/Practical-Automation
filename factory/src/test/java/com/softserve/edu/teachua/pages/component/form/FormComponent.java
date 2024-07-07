package com.softserve.edu.teachua.pages.component.form;

import com.softserve.edu.teachua.pages.component.option.factory.MenuContents;
import com.softserve.edu.teachua.pages.top.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.PaymentMenuContents.DEFAULT_CARD;

public class FormComponent extends BaseModel {
    InputContainer inputContainer;

    public FormComponent(WebDriver driver) {
        super(driver);
        this.inputContainer = new InputContainer();
    }

    public FormComponent(WebDriver driver, MenuContents<PaymentFormComponent> option) {
        super(driver);
        this.inputContainer = new InputContainer();
        initFields(option);
    }


    private void initFields(MenuContents<PaymentFormComponent> option) {
        String selectorPrefix = option.getSelector();
        if (option.equals(DEFAULT_CARD)) {
            inputContainer.add(selectorPrefix + "-cardnumber", createInputComponent(selectorPrefix + "-cardnumber"));
            inputContainer.add(selectorPrefix + "-cardsecure", createInputComponent(selectorPrefix + "-cardsecure"));
            inputContainer.add(selectorPrefix + "-validity", createInputComponent(selectorPrefix + "-validity"));
            inputContainer.add(selectorPrefix + "-cardholder", createInputComponent(selectorPrefix + "-cardholder"));
            inputContainer.add(selectorPrefix + "-clientphone", createInputComponent(selectorPrefix + "-clientphone"));
            inputContainer.add(selectorPrefix + "-clientemail", createInputComponent(selectorPrefix + "-clientemail"));
        }
    }

    private InputComponent createInputComponent(String id) {
        return new InputComponent(getDriver(), getDriver().findElement(By.id(id)));
    }
}
