package steps.desktop;

import drivers.DriverManager;
import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.desktop.NotepadHome;
import pages.web.BuscaProdutosPage;
import pages.web.ProdutoPage;
import utils.TestUtils;

public class NovoAtendimentoSteps {
    private WindowsDriver driver;
    private NotepadHome notepadHome;

    @Dado("que estou com o app CCL aberto")
    public void selecionarPrimeiroProduto() throws InterruptedException {String caminhoExe = "";
        driver = DriverManager.getDesktopDriver();
        Thread.sleep(1000);
    }

    @Dado("que eu abro o aplicativo {string}")
    public void queEuAbroOAplicativo(String nomeApp) throws InterruptedException {
        String caminhoExe = "";

        // Mapeia o nome amigável do Gherkin para o caminho real do executável
        switch (nomeApp.toUpperCase()) {
            case "CCL":
                caminhoExe = "C:\\Program Files\\Notepad++\\notepad++.exe";
                break;
            default:
                throw new IllegalArgumentException("Aplicativo não cadastrado no framework: " + nomeApp);
        }

        // Inicializa o driver abrindo o app escolhido
        driver = DriverManager.getDesktopDriver(caminhoExe);
        notepadHome = new NotepadHome(driver);
//        Thread.sleep(2000);
        TestUtils.screenshot("Abertura do aplicativo realizado com sucesso!");
    }

    @Quando("acesso a tela {string}")
    public void validarNomeProduto(String tela) {

    }

    @Quando("tento interagir com o aplicativo")
    public void deveInteragirComONotepadPlusPlus() {
        try {

            notepadHome.getBtnMenuArquivo().click();

            TestUtils.screenshot("Click no menu Arquivo realizado com sucesso!");

            notepadHome.getBtnSairMenuArquivo().click();

        } catch (Exception e) {
            System.out.println("Erro na interação com Notepad++: " + e.getMessage());
        }
    }

}