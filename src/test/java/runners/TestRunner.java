package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"},
//        tags = "@CaminhoFeliz",
//        tags = "@BuscaFiltros",
        tags = "@Api",
//        tags = "@CaminhoFeliz or @BuscaFiltros or @Api",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json",
                "rerun:target/rerun.txt"
        },
        monochrome = true  // Adicione isso para melhor visualização
)

public class TestRunner {
}