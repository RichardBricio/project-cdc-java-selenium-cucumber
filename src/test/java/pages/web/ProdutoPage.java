package pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ProdutoPage extends BasePage {

    public ProdutoPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='field field--name-title field--type-string field--label-hidden field__item']")
    private WebElement textNomeProduto;

    @FindBy(xpath = "//*[@id=\"form-prod-detail-infos\"]/div[3]/div[3]")
    private WebElement textValorProduto;

    public WebElement getTextNomeProduto() {
        return textNomeProduto;
    }

    public WebElement getTextValorProduto() {
        return textValorProduto;
    }

}