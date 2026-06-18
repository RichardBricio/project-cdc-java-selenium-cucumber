package pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BuscaProdutosPage extends BasePage {

    public BuscaProdutosPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "(//div[@class='view-content row m-0'])[1]/div")
    private List<WebElement> gridEquipamentosEncontrados;

    @FindBy(xpath = "(//div[@class='view-content row m-0'])[1]/div[1]")
    private WebElement itemPrimeiroEquipamentoEncontrado;

    @FindBy(xpath = "(//div[@class='product-cart-texts'])[1]")
    private WebElement textNomePrimeiroEquipamentoEncontrado;

    @FindBy(xpath = "(//div[@class='product-values-number'])[1]")
    private WebElement textValorPrimeiroEquipamentoEncontrado;

    public List<WebElement> getGridEquipamentosEncontrados() {
        return gridEquipamentosEncontrados;
    }

    public WebElement getItemPrimeiroEquipamentoEncontrado() {
        return itemPrimeiroEquipamentoEncontrado;
    }

    public WebElement getTextItemPrimeiroEquipamentoEncontrado() {
        return textNomePrimeiroEquipamentoEncontrado;
    }

    public WebElement getTextValorPrimeiroEquipamentoEncontrado() {
        return textValorPrimeiroEquipamentoEncontrado;
    }

}