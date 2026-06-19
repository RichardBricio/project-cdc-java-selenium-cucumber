package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.restassured.RestAssured;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private static WebDriver webDriver;
    private static WindowsDriver desktopDriver;
    private static String tipoDriverAtivo = "NENHUM";
    private static Process winAppDriverProcess;
    private static Process processoWinAppDriver;

    public static WebDriver getWebDriver() {
        if (webDriver == null) {
            WebDriverManager.chromedriver().setup(); // Boni Garcia isolado aqui
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
            webDriver.manage().window().maximize();
            tipoDriverAtivo = "WEB";
        }
        return webDriver;
    }

    public static WebDriver getWebDriver(String navegador) {
        if (webDriver == null) {
            System.out.println("🌐 Inicializando o navegador: " + navegador);

            // Tratamos o texto para evitar problemas com maiúsculas/minúsculas
            switch (navegador.toLowerCase().trim()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    webDriver = new ChromeDriver();
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    webDriver = new EdgeDriver();
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriver = new FirefoxDriver();
                    break;

                default:
                    throw new IllegalArgumentException("❌ Navegador não suportado: " + navegador
                            + ". Escolha entre: Chrome, Edge ou Firefox.");
            }

            webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
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

                // 🎯 AJUSTE: Alterado para WindowsOptions (W3C Puro) para conversar com a nova versão
                io.appium.java_client.windows.options.WindowsOptions options = new io.appium.java_client.windows.options.WindowsOptions();
                options.setApp(caminhoApp);

                System.out.println("🔌 Conectando ao WinAppDriver na versão estável...");

                // Sem loops complexos, a conexão ocorre de primeira
                desktopDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), options);

                tipoDriverAtivo = "DESKTOP";
            } catch (Exception e) {
                throw new RuntimeException("Erro ao iniciar o WinAppDriver estável: " + e.getMessage());
            }
        }
        return desktopDriver;
    }

    // 2. CONFIGURAÇÃO DESKTOP (Precisa instanciar)
    public static WindowsDriver getDesktopDriver(String caminhoApp) {
        if (desktopDriver == null) {
            try {
                iniciarWinAppDriverAutomaticamente();

                // 🎯 AJUSTE: Aplicado o mesmo WindowsOptions seguro aqui no método por String
                io.appium.java_client.windows.options.WindowsOptions options = new io.appium.java_client.windows.options.WindowsOptions();
                options.setApp(caminhoApp);

                System.out.println("🔌 Conectando ao WinAppDriver na versão estável...");

                // Sem loops complexos, a conexão ocorre de primeira
                desktopDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), options);

                tipoDriverAtivo = "DESKTOP";
            } catch (Exception e) {
                throw new RuntimeException("Erro ao iniciar o WinAppDriver estável: " + e.getMessage());
            }
        }
        return desktopDriver;
    }

    public static void iniciarWinAppDriverAutomaticamente() {
        if (processoWinAppDriver == null || !processoWinAppDriver.isAlive()) {
            try {
                try {
                    new ProcessBuilder("cmd.exe", "/c", "taskkill /F /IM WinAppDriver.exe /IM node.exe").start().waitFor();
                } catch (Exception e) {
                    // Ignora se não houver processos órfãos
                }

                System.out.println("⏳ Iniciando WinAppDriver em segundo plano...");

                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "appium --log-level warn");
                builder.inheritIO();

                processoWinAppDriver = builder.start();
                System.out.println("🤖 WinAppDriver pronto e invisível em background!");

                // 🚀 O SEGREDO DA VELOCIDADE: Espera dinâmica da porta (Substitui o Thread.sleep fixo)
                // O loop tenta se conectar na porta. Assim que o Appium绑定 nela, o Java avança!
                for (int i = 0; i < 25; i++) {
                    try (java.net.Socket socket = new java.net.Socket("127.0.0.1", 4723)) {
                        System.out.println("✨ Servidor Appium respondeu! Prosseguindo...");
                        break;
                    } catch (Exception e) {
                        Thread.sleep(200); // Aguarda um instante antes de tentar o próximo ping
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Falha ao iniciar WinAppDriver via código: " + e.getMessage());
            }
        }
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
            try {
                webDriver.quit();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            webDriver = null;
        }
        if (desktopDriver != null) {
            try {
                desktopDriver.quit();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
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

    public static void fecharDesktopDriver() {
        try {
            if (desktopDriver != null) {
                System.out.println("🛑 Encerrando sessão do driver...");
                desktopDriver.quit();
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Sessão já estava fechada.");
        } finally {
            desktopDriver = null;
            matarProcessoWinAppDriver();
        }
    }

    private static void matarProcessoWinAppDriver() {
        // Primeiro tentamos a forma elegante: Destruir o processo que o Java criou
        if (processoWinAppDriver != null && processoWinAppDriver.isAlive()) {
            System.out.println("🪓 Finalizando o processo criado pelo Java...");
            processoWinAppDriver.destroyForcibly(); // Força o encerramento do processo filho
        }

        // Garantia extra: Taskkill via linha de comando para limpar fantasmas anteriores
        try {
            // 🎯 AJUSTE: Adicionado o '/IM node.exe' para limpar também o motor do Appium e o '.waitFor()' para dar tempo de limpar antes do double-check
            new ProcessBuilder("cmd.exe", "/c", "taskkill /F /IM WinAppDriver.exe /IM node.exe").start().waitFor();
        } catch (Exception e) {
            // Ignora erros de permissão
        }
        // 3. O CHECK AUTOMÁTICO: O Java investiga o Windows para você
        verificarSeProcessoAindaExiste();
    }

    private static void verificarSeProcessoAindaExiste() {
        System.out.println("🔍 Executando Double-Check de segurança...");

        // Varre todos os processos rodando no seu Windows 11 neste momento
        boolean aindaEstaVivo = ProcessHandle.allProcesses()
                .map(ProcessHandle::info)
                .map(info -> info.command().orElse(""))
                .anyMatch(comando -> comando.contains("WinAppDriver.exe"));

        if (aindaEstaVivo) {
            System.out.println("🚨 [ALERTA] O WinAppDriver ainda está rodando em segundo plano!");
        } else {
            System.out.println("✨ [SUCESSO] WinAppDriver foi encerrado completamente. Porta 4723 liberada!");
        }
    }

}