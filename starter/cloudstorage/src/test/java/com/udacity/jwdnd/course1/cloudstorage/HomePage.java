package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "btnAddNewNote")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "note-title-field")
    private WebElement noteTitleFieldFilledIn;

    @FindBy(id = "note-edit-button")
    private WebElement noteEditButton;




    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver,this);
    }

    public void logout()
    {
        logoutButton.click();
    }

    public void createNote(WebDriverWait wait) throws InterruptedException {

        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
        addNoteButton.click();
        Thread.sleep(2000);
        wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
        noteTitleField.sendKeys("testnote");
        noteDescriptionField.sendKeys("blaat");
        noteSubmitButton.submit();
    }

    public void switchToNotesTab(WebDriverWait wait)
    {
        wait.until(ExpectedConditions.elementToBeClickable(noteTab));
        noteTab.click();
    }

    public void editNote(WebDriverWait wait) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(noteEditButton));
        noteEditButton.click();
        Thread.sleep(1000);
        noteTitleField.clear();
        noteTitleField.sendKeys("change");
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys("change");
        noteSubmitButton.submit();

    }
}
