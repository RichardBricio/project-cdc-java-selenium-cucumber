package steps.web;

import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.web.HomePage;
import pages.web.LojaPage;
import drivers.DriverManager;
import utils.TestUtils;

public class CommonSteps {
    private WebDriver driver;
    private HomePage homePage;
    private LojaPage lojaPage;

    @Dado("que estou utilizando o navegador {string}")
    public void queEstouUtilizandoONavegador(String navegadorSelecionado) throws InterruptedException {
        driver = DriverManager.getWebDriver(navegadorSelecionado);
        TestUtils.screenshot("Navegador " + navegadorSelecionado + "aberto com sucesso");
//        Thread.sleep(2000);
    }

    @Dado("que estou na home page da Casa do Construtor")
    public void acessarHomePage() throws InterruptedException {
        if (driver == null){
            driver = DriverManager.getWebDriver();
        }
        driver.get("https://casadoconstrutor.com.br/pt-br");
        homePage = new HomePage(driver);
        Thread.sleep(1000);
        TestUtils.screenshot("Acessou com sucesso na home page");
    }

    @Quando("defino a localização para {string} e {string}")
    public void definirLocalizacao(String estado, String cidade) throws InterruptedException {
        homePage.getCampoBuscaCidadePopup().click();
        homePage.getCampoInputBuscaCidadePopup().clear();
        homePage.getCampoInputBuscaCidadePopup().sendKeys(cidade + " - " + estado);
        Thread.sleep(1000);
        TestUtils.screenshot("Cidade e Estado inserido com sucesso");
    }

    @E("defino que deve ser a loja {string}")
    public void selecionarLojaPorNome(String loja) throws InterruptedException {
        for(WebElement item : homePage.getListaBuscaCidadePopup()) {
            if(item.getText().contains(loja)) {
                item.click();
                Thread.sleep(2000);
                TestUtils.screenshot("Loja selecionada e acessada com sucesso");
                break;
            }
        }
    }

    @E("defino que deve ser a {string} loja")
    public void selecionarLojaPorPosicao(String posicaoLoja) throws InterruptedException {
        int posicao = 0;

        if (posicaoLoja.equals("segunda")) {
            posicao = 1;
        } else if (posicaoLoja.equals("terceira")) {
            posicao = 2;
        }

        if (!homePage.existeElemento(homePage.getListaBuscaCidadePopup().get(posicao))) {
            TestUtils.screenshot("Não existe loja para a posição citada." + "\nPosição: " + posicaoLoja);
            Assert.fail("Não existe loja para a posição citada");
        } else {
            homePage.getListaBuscaCidadePopup().get(posicao).click();
            Thread.sleep(2000);
            TestUtils.screenshot("Primeira loja selecionada e acessada com sucesso");
        }
    }

    @Dado("que pesquiso pelo produto {string}")
    public void pesquisarProduto(String termo) throws InterruptedException {
        lojaPage = new LojaPage(driver);
        lojaPage.getBtnPesquisaEquipamento().click();
        Thread.sleep(1000);
        lojaPage.getTextPesquisaEquipamento().sendKeys(termo);
        Thread.sleep(2000);
        TestUtils.screenshot("Nome do produto inserido com sucesso");
        lojaPage.getListaEquipamentos().get(0).click();
        Thread.sleep(2000);
        TestUtils.screenshot("Produtos retornados com sucesso");
    }

}