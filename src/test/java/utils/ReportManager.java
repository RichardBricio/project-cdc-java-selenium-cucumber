package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ReportManager {

    private static final String PASTA_HISTORICO = "target/historico-execucoes/";
    private static final int MAX_REPORTES = 5;

    public static void rotacionarEGuardarReporteAtual() {
        try {
            File arquivoReporte = new File("target/cucumber-report.html");
            if (!arquivoReporte.exists()) return;

            // Cria diretório de histórico se não existir
            Files.createDirectories(Paths.get(PASTA_HISTORICO));

            // Copia o reporte recém-gerado com carimbo de data/hora
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path destino = Paths.get(PASTA_HISTORICO + "report_" + timestamp + ".html");
            Files.copy(arquivoReporte.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

            // Gerencia o teto de 5 arquivos
            File pasta = new File(PASTA_HISTORICO);
            File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".html"));

            if (arquivos != null && arquivos.length > MAX_REPORTES) {
                // Ordena por data de modificação (mais antigos primeiro)
                Arrays.sort(arquivos, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

                // Remove o mais antigo excedente
                for (int i = 0; i < arquivos.length - MAX_REPORTES; i++) {
                    arquivos[i].delete();
                    System.out.println("🗑️ Histórico antigo removido para manter limite de 5: " + arquivos[i].getName());
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ Falha ao gerenciar histórico de relatórios: " + e.getMessage());
        }
    }
}