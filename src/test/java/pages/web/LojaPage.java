package pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LojaPage extends BasePage {

    public LojaPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@id='equipment-search-button']")
    private WebElement btnPesquisaEquipamento;

    @FindBy(xpath = "//input[@id='edit-search-api-fulltext']")
    private WebElement textPesquisaEquipamento;

    @FindBy(xpath = "//ul[@id='ui-id-1']//li")
    private List<WebElement> listaEquipamentos;

    public WebElement getBtnPesquisaEquipamento() { return btnPesquisaEquipamento; }

    public WebElement getTextPesquisaEquipamento() { return textPesquisaEquipamento; }

    public List<WebElement> getListaEquipamentos() { return listaEquipamentos; }

}