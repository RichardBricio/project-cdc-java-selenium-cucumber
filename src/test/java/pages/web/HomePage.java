package pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='cep-1']")
    private WebElement campoBuscaCepPopup;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[3]/aside/section[2]/lgr-modal/div/div[1]/div[1]/div/div/div[2]/div/div[2]/div[3]/div[3]/span/span[1]/span")
    private WebElement campoBuscaCidadePopup;

    @FindBy(xpath = "/html/body/div[2]/div/div/div[3]/aside/section[2]/lgr-modal/div/div[1]/div[1]/div/div/div[2]/div/div[2]/div[3]/div[3]/span[2]/span/span[1]/input")
    private WebElement campoInputBuscaCidadePopup;

    @FindBy(xpath = "//*[@id='select2-lojas-cidade-select-modal-results']/li")
    private List<WebElement> listaBuscaCidadePopup;

    @FindBy(xpath = "//*[@id=\"equipment-search-button\"]")
    private WebElement btnBuscaEquipamento;

    @FindBy(xpath = "//*[@id=\"edit-search-api-fulltext\"]")
    private WebElement campoBuscaEquipamento;

    @FindBy(xpath = "//*[@id=\"ui-id-1\"]")
    private WebElement selecaoEquipamento;

    @FindBy(xpath = "//*[@id=\"ui-id-1\"]/li[1]")
    private WebElement selecaoPrimeiroEquipamento;

    public WebElement getCampoBuscaCepPopup() { return campoBuscaCepPopup; }

    public WebElement getCampoBuscaCidadePopup() { return campoBuscaCidadePopup; }

    public WebElement getCampoInputBuscaCidadePopup() { return campoInputBuscaCidadePopup; }

    public List<WebElement> getListaBuscaCidadePopup() { return listaBuscaCidadePopup; }

    public WebElement getBtnBuscaEquipamento() { return btnBuscaEquipamento; }

    public WebElement getCampoBuscaEquipamento() { return campoBuscaEquipamento; }

    public WebElement getSelecaoEquipamento() { return selecaoEquipamento; }

    public WebElement getSelecaoPrimeiroEquipamento() { return selecaoPrimeiroEquipamento; }

}