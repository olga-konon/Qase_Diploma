package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class ImportCasePage extends BasePage {

    private final String UPLOAD_FILE_INPUT = "input[type='file'][accept='.xml,.json,.csv']";
    private final String PATH_NAME = "src/test/resources/test-data/";
    private final String IMPORT_CASE_BUTTON = "button[type='submit']";
    private final String REPLACE_CHECKBOX = "label[data-sentry-element='CheckboxAria']";

    public ImportCasePage isPageOpened() {
        waitForVisible($(IMPORT_CASE_BUTTON));
        return this;
    }

    @Step("Upload file: '{fileName}'")
    public ImportCasePage uploadFile(String fileName) {
        log.info("Uploading file: {}", fileName);
        $(UPLOAD_FILE_INPUT)
                .uploadFile(new File(PATH_NAME + fileName));
        return this;
    }

    @Step("Click replace checkbox")
    public ImportCasePage clickCheckBox() {
        log.info("Clicking replace checkbox");
        $(REPLACE_CHECKBOX).click();
        return this;
    }

    @Step("Click import case button")
    public CasesPage clickImportCaseButton() {
        log.info("Clicking import case button");
        $(IMPORT_CASE_BUTTON).click();
        return new CasesPage();
    }

    @Step("Click import case button with invalid file")
    public ImportCasePage clickImportCaseButtonWithInvalidFile() {
        log.info("Click import case button with invalid file");
        $(IMPORT_CASE_BUTTON).click();
        return this;
    }
}
