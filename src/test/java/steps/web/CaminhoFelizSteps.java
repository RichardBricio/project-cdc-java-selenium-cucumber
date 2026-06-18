package steps.web;

import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.web.BuscaProdutosPage;
import pages.web.ProdutoPage;
import drivers.DriverManager;
import utils.TestUtils;

public class CaminhoFelizSteps {
    private WebDriver driver = DriverManager.getWebDriver();
    private BuscaProdutosPage buscaPage;
    private ProdutoPage produtoPage;
    private String nomeArmazenado;

    @E("seleciono o primeiro produto disponível na grid de resultados")
    public void selecionarPrimeiroProduto() throws InterruptedException {
        buscaPage =  new BuscaProdutosPage(driver);
        nomeArmazenado = buscaPage.getGridEquipamentosEncontrados().get(0).findElements(By.xpath("./div/div")).get(1).getText().replace("\n"," ").trim();
        buscaPage.getGridEquipamentosEncontrados().get(0).click();
        Thread.sleep(2000);
        TestUtils.screenshot("Primeiro produto da grid acessado com sucesso");
    }

    @Então("valido que nome selecionado na busca está contido ao nome do primeiro produto")
    public void validarNomeProduto() {
        produtoPage =  new ProdutoPage(driver);
        if (!produtoPage.getTextNomeProduto().getText().trim().equals(nomeArmazenado)) {
            TestUtils.screenshot("Nome do produto divergênte ao nome selecionado em busca de produtos!");
            Assert.fail("❌ Página do produto: " + produtoPage.getTextNomeProduto().getText().trim() +
                    "|| Nome Armazenado da Grid: " + nomeArmazenado);
        } else {
            TestUtils.screenshot("Nome do produto confere ao nome selecionado em busca de produtos!" +
                    " || Página do produto: " + produtoPage.getTextNomeProduto().getText().trim() +
                    " || Nome Armazenado da Grid: " + nomeArmazenado);
        }
    }

}