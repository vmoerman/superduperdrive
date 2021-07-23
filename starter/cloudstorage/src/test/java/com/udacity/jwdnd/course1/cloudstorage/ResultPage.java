package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(xpath = "/html/body/div/div/span[2]/a")
    private WebElement returnButton;

    public ResultPage(WebDriver webDriver)
    {
        PageFactory.initElements(webDriver,this);
    }

    public void returnToHome(WebDriverWait wait)
    {
        wait.until(ExpectedConditions.elementToBeClickable(returnButton));
        returnButton.click();
    }
}
