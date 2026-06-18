package utils;

import drivers.DriverManager; // <-- Importamos o seu novo gerenciador
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestUtils {

    // Instância única
    private static TestUtils instance;
    private Scenario scenario;

    private TestUtils() {}

    // Método para inicializar (chamado UMA VEZ no @Before do Hooks)
    public static void init(Scenario scenario) {
        if (instance == null) {
            instance = new TestUtils();
        }
        instance.scenario = scenario;
        // Removemos a atribuição fixa do driver aqui!
    }

    // Métodos de log (Permanecem idênticos e limpos)
    public static void log(String mensagem) {
        if (instance != null && instance.scenario != null) {
            instance.scenario.log(mensagem);
        }
    }

    public static void logPassou(String mensagem) { log("✅ " + mensagem); }
    public static void logErro(String mensagem) { log("❌ " + mensagem); }
    public static void logInfo(String mensagem) { log("ℹ️ " + mensagem); }
    public static void logAviso(String mensagem) { log("⚠️ " + mensagem); }
    public static void logSucesso(String mensagem) { log("🎉 " + mensagem); }

    // CORREÇÃO CRUCIAL: Captura de tela dinâmica (Web ou Desktop)
    public static void screenshot(String nome) {
        if (instance != null && instance.scenario != null) {
            WebDriver driverAtivo = null;

            // Identifica qual driver está rodando no cenário atual
            if ("WEB".equals(DriverManager.getTipoDriverAtivo())) {
                driverAtivo = DriverManager.getWebDriver();
            } else if ("DESKTOP".equals(DriverManager.getTipoDriverAtivo())) {
                driverAtivo = DriverManager.getDesktopDriver();
            }

            // Se houver um driver ativo (seja Web ou Windows), tira o print
            if (driverAtivo != null) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driverAtivo).getScreenshotAs(OutputType.BYTES);
                    instance.scenario.attach(screenshot, "image/png", nome);
                } catch (Exception e) {
                    System.err.println("Erro ao capturar screenshot: " + e.getMessage());
                }
            }
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