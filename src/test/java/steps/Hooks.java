package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.TestUtils;

import java.time.Duration;

public class Hooks {
    public static WebDriver driver;
    private static int scenarioCounter = 0;

    @Before
    public void setUp(Scenario scenario) {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }
        scenarioCounter++;

        // INICIALIZA O TESTUTILS UMA VEZ POR CENÁRIO
        TestUtils.init(scenario);
        TestUtils.logInfo("🌐 Cenário " + scenarioCounter + ": " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                TestUtils.logErro("CENÁRIO FALHOU: " + scenario.getName());
                TestUtils.logErro("URL: " + driver.getCurrentUrl());
                TestUtils.screenshot("FALHA_" + scenario.getName());
            } else {
                TestUtils.logSucesso("CENÁRIO PASSOU: " + scenario.getName());
            }

            TestUtils.cleanup(); // Limpa a instância

        } catch (Exception e) {
            System.err.println("Erro no @After: " + e.getMessage());
        }

        // Só fecha o navegador após o último cenário
        if (--scenarioCounter == 0 && driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Erro ao fechar driver: " + e.getMessage());
            }
            driver = null;
        }
    }
}