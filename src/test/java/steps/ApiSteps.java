package steps;

import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.TestUtils;

public class ApiSteps {

    private static final String BASE_URL = "https://api-dev-oci.casadoconstrutor.com.br/api-booster";
    private Response response;
    private String idCapturado;

    // Consulta geral

    @Quando("consulto a lista de áreas")
    public void consultarListaAreas() {
        TestUtils.logInfo("Consultando lista de áreas...");

        response = RestAssured
                .given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/v1/areas");

        TestUtils.logInfo("Status: " + response.getStatusCode());
        TestUtils.logInfo("Tempo: " + response.getTime() + "ms");
    }

    @Então("o status code deve ser {int}")
    public void validarStatusCode(int statusEsperado) {
        int statusObtido = response.getStatusCode();
        boolean statusOk = statusObtido == statusEsperado;

        if (statusOk) {
            TestUtils.logSucesso("✅ Status Code: " + statusObtido);
        } else {
            TestUtils.logErro("❌ Status esperado: " + statusEsperado + ", obtido: " + statusObtido);
            Assert.fail("Status code incorreto. Esperado: " + statusEsperado + ", obtido: " + statusObtido);
        }
    }

    @E("o tempo de resposta deve ser menor que {int} segundos")
    public void validarTempoResposta(int segundos) {
        long tempoMs = response.getTime();
        long limiteMs = segundos * 1000;
        boolean tempoOk = tempoMs < limiteMs;

        if (tempoOk) {
            TestUtils.logSucesso("Tempo: " + tempoMs + "ms (limite: " + limiteMs + "ms)");
        } else {
            TestUtils.logErro("❌ Tempo excedido: " + tempoMs + "ms (limite: " + limiteMs + "ms)");
            Assert.fail("Tempo de resposta excedeu " + segundos + "s. Tempo: " + tempoMs + "ms");
        }
    }

    @Então("o corpo da resposta não está vazio")
    public void validarCorpoNaoVazio() {
        String corpo = response.getBody().asString();

        if (corpo != null && !corpo.isEmpty()) {
            TestUtils.logSucesso("Corpo da resposta contém dados");
        } else {
            TestUtils.logErro("❌ Corpo da resposta vazio");
            Assert.fail("Corpo da resposta está vazio");
        }
    }

    @E("capturo o ID do primeiro item")
    public void capturarPrimeiroId() {
        try {
            idCapturado = response.jsonPath().getString("data[0].idArea");

            if (idCapturado == null || idCapturado.isEmpty()) {
                TestUtils.logErro("❌ ID não encontrado no primeiro item");
                TestUtils.logInfo("Primeiro item: " + response.jsonPath().getString("[0]"));
                Assert.fail("Não foi possível capturar o ID do primeiro item");
            } else {
                TestUtils.logSucesso("ID Capturado: " + idCapturado);
            }
        } catch (Exception e) {
            TestUtils.logErro("❌ Erro ao capturar ID: " + e.getMessage());
            Assert.fail("Falha ao processar resposta: " + e.getMessage());
        }
    }

    // Consulta Específica

    @Quando("consulto a área pelo ID capturado")
    public void consultarAreaPorId() {
        TestUtils.logInfo("Consultando área ID: " + idCapturado);

        response = RestAssured
                .given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/v1/areas/" + idCapturado);

        TestUtils.logInfo("Status: " + response.getStatusCode());
        TestUtils.logInfo("Tempo: " + response.getTime() + "ms");
    }

    @Então("o ID retornado deve ser igual ao ID capturado")
    public void validarIdCorrespondente() {
        String idRetornado = response.jsonPath().getString("data[0].idArea");

        if (idCapturado.equals(idRetornado)) {
            TestUtils.logSucesso("ID corresponde: " + idRetornado);
        } else {
            TestUtils.logErro("❌ ID divergente! Capturado: " + idCapturado + ", Retornado: " + idRetornado);
            Assert.fail("ID retornado (" + idRetornado + ") não corresponde ao capturado (" + idCapturado + ")");
        }
    }

    // Health Check

    @Quando("consulto o health check")
    public void consultarHealthCheck() {
        TestUtils.logInfo("Consultando health check...");

        response = RestAssured
                .given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/actuator/health");

        TestUtils.logInfo("Status: " + response.getStatusCode());
        TestUtils.logInfo("Tempo: " + response.getTime() + "ms");
    }

    @Então("o status do serviço é {string}")
    public void validarStatusServico(String statusEsperado) {
        String statusObtido = response.jsonPath().getString("status");

        if (statusEsperado.equalsIgnoreCase(statusObtido)) {
            TestUtils.logSucesso("Serviço está " + statusObtido);
        } else {
            TestUtils.logErro("❌ Status do serviço: " + statusObtido + " (esperado: " + statusEsperado + ")");
            Assert.fail("Status esperado: " + statusEsperado + ", obtido: " + statusObtido);
        }
    }
}