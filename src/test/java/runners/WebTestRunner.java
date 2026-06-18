package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import utils.ReportManager;

import java.awt.*;
import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"steps"},
//        tags = "@Web and (@CaminhoFeliz or @BuscaFiltros)",
        tags = "@CaminhoFeliz",
//        plugin = { //Utiliza o Report Padrão do Cucumber
//                "pretty",
//                "html:target/cucumber-report.html",
//                "json:target/cucumber-report.json",
//                "rerun:target/rerun.txt"
//        },
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/rerun.txt"
        },
        monochrome = true
)

public class WebTestRunner {

        public static void main(String[] args) {
                ReportManager.rotacionarEGuardarReporteAtual();
                Result result = JUnitCore.runClasses(WebTestRunner.class);

                // Resumo no console
                System.out.println("\n📊 Total de testes: " + result.getRunCount());
                System.out.println("✅ Sucessos: " + (result.getRunCount() - result.getFailureCount()));
                System.out.println("❌ Falhas: " + result.getFailureCount());

                for (Failure failure : result.getFailures()) {
                        System.out.println("   Falha: " + failure.getMessage());
                }

                // Abre relatório automaticamente
                try {
                        File report = new File("target/extent-report/Index.html");
                        if (report.exists() && Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().browse(report.toURI());
                                System.out.println("\n📄 Novo relatório aberto automaticamente!");
                        }
                } catch (Exception e) {
                        System.out.println("⚠️ Não foi possível abrir o relatório: " + e.getMessage());
                }
        }

}