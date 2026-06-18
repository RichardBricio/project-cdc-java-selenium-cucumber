package steps;

import drivers.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.TestUtils;

public class Hooks {
    private static int scenarioCounter = 0;

    @Before("@Web")
    public void setUpWeb(Scenario scenario) {
        DriverManager.getWebDriver(); // Inicia a Web via Boni Garcia se tiver a tag @web
        configurarCenario(scenario);
    }

    @Before("@Desktop")
    public void setUpDesktop(Scenario scenario) {
        DriverManager.getDesktopDriver(); // Inicia o Delphi via WinAppDriver se tiver a tag @desktop
        configurarCenario(scenario);
    }

    private void configurarCenario(Scenario scenario) {
        scenarioCounter++;
        TestUtils.init(scenario);
        TestUtils.logInfo("🏁 Cenário " + scenarioCounter + ": " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                TestUtils.logErro("❌ CENÁRIO FALHOU: " + scenario.getName());

                // Só pega URL se o driver ativo for Web (Evita quebrar no Delphi)
                if ("WEB".equals(DriverManager.getTipoDriverAtivo())) {
                    TestUtils.logErro("URL: " + DriverManager.getWebDriver().getCurrentUrl());
                }

                TestUtils.screenshot("FALHA_" + scenario.getName());
            } else {
                TestUtils.logSucesso("✔️ CENÁRIO PASSOU: " + scenario.getName());
            }

            TestUtils.cleanup();

        } catch (Exception e) {
            System.err.println("Erro no @After: " + e.getMessage());
        }

        // Se você quiser fechar o navegador/sistema a CADA cenário (Recomendado para evitar poluição de memória):
        DriverManager.quitDrivers();

        // OBS: Se você preferir manter a sua lógica antiga de acumular cenários e fechar tudo só no final,
        // basta controlar o 'scenarioCounter' aqui e chamar o 'DriverManager.quitDrivers()' quando zerar.
    }

    @After("@Desktop")
    public void tearDownDesktop(Scenario cenario) {
        System.out.println("🏁 Cenário '" + cenario.getName() + "' finalizado. Status: " + cenario.getStatus());

        // 1. SE FALHOU, TIRA O PRINT PRIMEIRO (Enquanto o driver e o app ainda estão vivos)
        if (cenario.isFailed()) {
            try {
                System.out.println("📸 Capturando print da falha...");
                // Seu código atual que tira o print/embed aqui, ex:
                 byte[] screenshot = ((TakesScreenshot) DriverManager.getDesktopDriver()).getScreenshotAs(OutputType.BYTES);
                 cenario.attach(screenshot, "image/png", "FALHA_" + cenario.getName());
            } catch (Exception e) {
                System.out.println("⚠️ Não foi possível tirar o print: " + e.getMessage());
            }
        }

        // 2. AGORA SIM, DEPOIS DO PRINT, LIMPA TUDO E MATA O PROCESSO
        DriverManager.fecharDesktopDriver();
    }

}