package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.restassured.RestAssured;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static WebDriver webDriver;
    private static WindowsDriver desktopDriver;
    private static String tipoDriverAtivo = "NENHUM";
    private static Process winAppDriverProcess;

    public static WebDriver getWebDriver() {
        if (webDriver == null) {
            WebDriverManager.chromedriver().setup(); // Boni Garcia isolado aqui
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.manage().window().maximize();
            tipoDriverAtivo = "WEB";
        }
        return webDriver;
    }

    public static WindowsDriver getDesktopDriver() {
        if (desktopDriver == null) {
            try {
                iniciarWinAppDriverAutomaticamente();
                String caminhoApp = "C:\\Program Files\\Notepad++\\notepad++.exe";
                File arquivoApp = new File(caminhoApp);
                DesiredCapabilities caps = new DesiredCapabilities();

                caps.setCapability("platformName", "Windows");
                caps.setCapability("appium:automationName", "windows");
//                caps.setCapability("appium:app", "C:\\CCL\\CCLMultiLojasTeste-v12.06.2026.exe");
                caps.setCapability("appium:deviceName", "WindowsPC");
                caps.setCapability("appium:app", caminhoApp);

                // CORREÇÃO: Define a pasta do .exe como diretório de trabalho principal
                String pastaDoApp = arquivoApp.getParent(); // Extrai ex: "C:\Caminho"
                if (pastaDoApp != null) {
                    caps.setCapability("appium:appWorkingDir", pastaDoApp);
                }

                // O WinAppDriver deve estar rodando localmente nesta URL/Porta
                desktopDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), caps);
                desktopDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                tipoDriverAtivo = "DESKTOP";
            } catch (Exception e) {
                throw new RuntimeException("Erro ao iniciar o WinAppDriver: " + e.getMessage());
            }
        }
        return desktopDriver;
    }

    // 2. CONFIGURAÇÃO DESKTOP (Precisa instanciar)
    public static WindowsDriver getDesktopDriver(String caminhoApp) {
        if (desktopDriver == null) {
            iniciarWinAppDriverAutomaticamente();
            try {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("platformName", "Windows");
                caps.setCapability("appium:automationName", "windows");
                caps.setCapability("appium:deviceName", "WindowsPC");
                caps.setCapability("appium:app", caminhoApp);

                // CORREÇÃO: Define a pasta do .exe como diretório de trabalho principal
                File arquivoApp = new File(caminhoApp);
                String pastaDoApp = arquivoApp.getParent(); // Extrai ex: "C:\Caminho"
                if (pastaDoApp != null) {
                    caps.setCapability("appium:appWorkingDir", pastaDoApp);
                }

                desktopDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), caps);
                desktopDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                tipoDriverAtivo = "DESKTOP";
            } catch (Exception e) {
                throw new RuntimeException("Erro no WinAppDriver: " + e.getMessage());
            }
        }
        return desktopDriver;
    }

    public static void getApiDriver(String urlBase) {
        // O REST Assured gerencia tudo de forma estática
        RestAssured.baseURI = urlBase;
        tipoDriverAtivo = "API";
        System.out.println("🌐 REST Assured configurado para o endpoint: " + urlBase);
    }

    public static String getTipoDriverAtivo() {
        return tipoDriverAtivo;
    }

    public static void quitDrivers() {
        if (webDriver != null) {
            try { webDriver.quit(); } catch (Exception e) { System.err.println(e.getMessage()); }
            webDriver = null;
        }
        if (desktopDriver != null) {
            try { desktopDriver.quit(); } catch (Exception e) { System.err.println(e.getMessage()); }
            desktopDriver = null;
        }
        // Mata o processo do WinAppDriver no Windows para não deixar lixo na memória da máquina
        if (winAppDriverProcess != null) {
            winAppDriverProcess.destroy();
            winAppDriverProcess = null;
            System.out.println("🛑 Servidor WinAppDriver encerrado com sucesso.");
        }
        tipoDriverAtivo = "NENHUM";
    }

    // Método que inicia o WinAppDriver de forma nativa e silenciosa em segundo plano
    private static void iniciarWinAppDriverAutomaticamente() {
        if (winAppDriverProcess == null) {
            try {
                String caminhoWAD = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
                File wadFile = new File(caminhoWAD);

                if (!wadFile.exists()) {
                    throw new RuntimeException("WinAppDriver não encontrado em: " + caminhoWAD);
                }

                // MATAR INSTÂNCIAS PRESA: Garante que nenhuma sessão antiga travou a porta 4723
                Runtime.getRuntime().exec("taskkill /F /IM WinAppDriver.exe");
                Thread.sleep(1000);

                // Inicia o WAD explicitamente apontando para o IP local estável (127.0.0.1) na porta 4723
                ProcessBuilder builder = new ProcessBuilder(
                        "powershell",
                        "-Command",
                        "Start-Process '" + caminhoWAD + "' -ArgumentList '127.0.0.1 4723' -Verb RunAs"
                );
                winAppDriverProcess = builder.start();

                // Damos um tempo generoso de 5 segundos APENAS na primeira inicialização para o servidor estabilizar
                System.out.println("⏳ Aguardando o WinAppDriver subir como Administrador...");
                Thread.sleep(5000);
                System.out.println("🤖 WinAppDriver pronto para receber conexões!");
            } catch (Exception e) {
                throw new RuntimeException("Falha ao iniciar o WinAppDriver: " + e.getMessage());
            }
        }
    }
}