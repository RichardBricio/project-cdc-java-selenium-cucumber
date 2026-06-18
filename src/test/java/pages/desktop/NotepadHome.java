package pages.desktop;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class NotepadHome extends BasePage {

    public NotepadHome(WindowsDriver driver) {
        super(driver);
    }

    @FindBy(name = "Arquivo")
    private WebElement btnMenuArquivo;

    @FindBy(name = "Sair")
    private WebElement btnSairMenuArquivo;

    public WebElement getBtnMenuArquivo() {
        return btnMenuArquivo;
    }

    public WebElement getBtnSairMenuArquivo() {
        return btnSairMenuArquivo;
    }
}
