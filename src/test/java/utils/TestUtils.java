package utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import steps.Hooks;

public class TestUtils {

    // Instância única
    private static TestUtils instance;
    private Scenario scenario;
    private WebDriver driver;

    // Construtor privado (Singleton)
    private TestUtils() {}

    // Método para inicializar (chamar UMA VEZ no @Before do Hooks)
    public static void init(Scenario scenario) {
        if (instance == null) {
            instance = new TestUtils();
        }
        instance.scenario = scenario;
        instance.driver = Hooks.driver;
    }

    // Métodos de log SEM precisar passar parâmetros
    public static void log(String mensagem) {
        if (instance != null && instance.scenario != null) {
            instance.scenario.log(mensagem);
        }
    }

    public static void logPassou(String mensagem) {
        log("✅ " + mensagem);
    }

    public static void logErro(String mensagem) {
        log("❌ " + mensagem);
    }

    public static void logInfo(String mensagem) {
        log("ℹ️ " + mensagem);
    }

    public static void logAviso(String mensagem) {
        log("⚠️ " + mensagem);
    }

    public static void logSucesso(String mensagem) {
        log("🎉 " + mensagem);
    }

    // Screenshot SEM precisar passar parâmetros
    public static void screenshot(String nome) {
        if (instance != null && instance.driver != null && instance.scenario != null) {
            byte[] screenshot = ((TakesScreenshot) instance.driver).getScreenshotAs(OutputType.BYTES);
            instance.scenario.attach(screenshot, "image/png", nome);
        }
    }

    // Combinação: log + screenshot
    public static void logComScreenshot(String mensagem, String nomeScreenshot) {
        log(mensagem);
        screenshot(nomeScreenshot);
    }

    // Limpar ao final do cenário
    public static void cleanup() {
        instance = null;
    }
}