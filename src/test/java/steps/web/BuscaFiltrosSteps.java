package steps.web;

import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.web.BuscaProdutosPage;
import pages.web.LojaPage;
import drivers.DriverManager;
import utils.TestUtils;

public class BuscaFiltrosSteps {
    private LojaPage lojaPage;
    private BuscaProdutosPage buscaPage;

    private WebDriver driver() {
        return DriverManager.getWebDriver();
    }

    @Então("valido os resultados de produtos exibidos na grid")
    public void validarResultadosExibidos() throws InterruptedException {
        buscaPage =  new BuscaProdutosPage(driver());
//        if (!buscaPage.existeElemento(buscaPage.getGridEquipamentosEncontrados().get(1))) {
        if (buscaPage.isListaVazia(buscaPage.getGridEquipamentosEncontrados())) {
            TestUtils.screenshot("Nenhum produto foi retornado!");
            Assert.fail("❌ Nenhum produto foi retornado!");
        } else {
            Thread.sleep(2000);
            TestUtils.screenshot(buscaPage.getGridEquipamentosEncontrados().size() + " Produtos disponibilizados com sucesso!");
        }
    }

    @Dado("que retorno ao campo de pesquisa e o limpo")
    public void limparBusca() throws InterruptedException {
        lojaPage =  new LojaPage(driver());
        lojaPage.getBtnPesquisaEquipamento().click();
        Thread.sleep(1000);
        lojaPage.getTextPesquisaEquipamento().clear();
        TestUtils.screenshot("Campo de pesquisa selecionado e limpo");
    }

    @Quando("que pesquiso por um produto inexistente como: {string}")
    public void pesquisaTermoInexistenteProduto(String termo) throws InterruptedException {
        for (char c : termo.toCharArray()) {
            lojaPage.getTextPesquisaEquipamento().sendKeys(String.valueOf(c));
             Thread.sleep(400);
        }
        Thread.sleep(2000);
        TestUtils.screenshot("Nome do produto inserido com sucesso");
    }

    @Então("valido mensagem de que nenhum resultado foi encontrado na busca")
    public void validarMensagemNenhumResultado() {
        String mensagem = "Nenhum resultado foi encontrado na busca";
        if (!driver().findElement(By.xpath("//*[@id=\"block-cdc-theme-formularioexpostobusca-de-equipamentosblock-1\"]/div/div[2]/div[1]/ul/li")).getText().equals(mensagem)) {
            TestUtils.screenshot("Mensagem que não foi encontrado produto buscado está divergênte!");
            Assert.fail("❌ Mensagem encontrada: " + lojaPage.getListaEquipamentos().get(0).getText());
        } else {
            TestUtils.screenshot("Mensagem que não foi encontrado produto confere com a regra!");
        }
    }

}