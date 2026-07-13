package ui.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static ui.dict.Elements.SIGN_IN;

public class LoginPage extends BasePage {

    private final String LOGIN = "[name=email]";
    private final String PASSWORD = "[name=password]";

    public LoginPage open() {
        Selenide.open("/login");
        waitForVisible($(LOGIN));
        return this;
    }

    public ProjectsPage login(String user, String password) {
        $(shadowCss("#accept", "#usercentrics-cmp-ui")).click();
        $(LOGIN).setValue(user);
        $(PASSWORD).setValue(password);
        $(byText(SIGN_IN)).click();
        return new ProjectsPage();
    }
}
