package ui.pages;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static ui.dict.Elements.SIGN_IN;

@Log4j2
public class LoginPage extends BasePage {

    private final String LOGIN = "[name=email]";
    private final String PASSWORD = "[name=password]";

    @Step("Open login page")
    public LoginPage open() {
        log.info("Opening login page");
        Selenide.open("/login");
        waitForVisible($(LOGIN));
        return this;
    }

    @Step("Login with: '{user}'")
    public ProjectsPage login(String user, String password) {
        log.info("Logging in as user: {}", user);
        $(shadowCss("#accept", "#usercentrics-cmp-ui")).click();
        $(LOGIN).setValue(user);
        $(PASSWORD).setValue(password);
        $(byText(SIGN_IN)).click();
        return new ProjectsPage();
    }
}
