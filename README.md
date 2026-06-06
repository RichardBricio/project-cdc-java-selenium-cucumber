# 🏗️ CDC Test Automation Framework

Framework de automação de testes para a **Casa do Construtor**, combinando testes **Web (UI)** com **Selenium WebDriver** e **Cucumber** para escrita de cenários em linguagem natural.
Collection.json de configuração para Postman anexado ao projeto para centralizar informações.

---

## 📋 Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração Inicial](#configuração-inicial)
- [Executando os Testes](#executando-os-testes)
- [Executando Cenários Específicos](#executando-cenários-específicos)
- [Relatórios e Evidências](#relatórios-e-evidências)
- [Benefícios do Framework](#benefícios-do-framework)
- [Possibilidades de Expansão](#possibilidades-de-expansão)
- [Solução de Problemas](#solução-de-problemas)

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|------------|--------|-------------|
| **Java** | 11 | Linguagem base do projeto |
| **Maven** | 3.8+ | Gerenciador de dependências e build |
| **Selenium WebDriver** | 4.15.0 | Automação de navegador |
| **Cucumber** | 7.13.0 | BDD para escrita de cenários em linguagem natural |
| **JUnit** | 4.13.2 | Executor dos testes |
| **WebDriverManager** | 5.5.3 | Gerenciamento automático de drivers |
| **RestAssured** | 5.3.2 | Automação API |
| **SLF4J** | 2.0.9 | Logging estruturado |
| **Jackson** | 2.15.2 | Manipulação de JSON |

---

## 📁 Estrutura do Projeto

Directory structure:
└── richardbricio-project-cdc-java-selenium-cucumber/
    ├── README.md
    ├── API_Collection_cdc.json
    ├── pom.xml
    └── src/
        └── test/
            ├── java/
            │   ├── pages/
            │   │   ├── BasePage.java
            │   │   ├── BuscaProdutosPage.java
            │   │   ├── HomePage.java
            │   │   ├── LojaPage.java
            │   │   └── ProdutoPage.java
            │   ├── runners/
            │   │   └── TestRunner.java
            │   ├── steps/
            │   │   ├── ApiSteps.java
            │   │   ├── BuscaFiltrosSteps.java
            │   │   ├── CaminhoFelizSteps.java
            │   │   ├── CommonSteps.java
            │   │   └── Hooks.java
            │   └── utils/
            │       └── TestUtils.java
            └── resources/
                ├── config.properties
                └── features/
                    ├── ApiBooster.feature
                    ├── BuscaFiltros.feature
                    └── CaminhoFeliz.feature

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 11+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)
- [Google Chrome](https://www.google.com/chrome/) (última versão)

---

##📊 Relatórios e Evidências
Após cada execução, o framework gera automaticamente:

1. Relatório HTML (target/cucumber-report.html)
•Visualização amigável dos cenários
•Status de cada step (✅/❌)
•Tempo de execução
•Screenshots incorporados

2. Relatório JSON (target/cucumber-report.json)
•Para integração com ferramentas de CI/CD

3. Arquivo rerun (target/rerun.txt)
•Lista de cenários que falharam
•Permite reexecução apenas dos falhos

4. Screenshots automáticos
•Capturados em cada passo importante
•Incorporados ao relatório HTML
•Nomeados com prefixo FALHA_ quando o cenário falha

##✨ Benefícios do Framework

BDD com Cucumber	- Cenários escritos em português, acessíveis para não-técnicos
Page Objects	- Manutenção facilitada e reaproveitamento de código
WebDriverManager	- Sem necessidade de baixar drivers manualmente
Screenshots automáticos	- Evidências visuais de cada passo
Logs estruturados	- Com emojis para fácil identificação de status
Configuração externalizada	- Mude URLs e timeouts sem recompilar
Auto-abertura do relatório	- Agilidade na análise dos resultados
Tratamento robusto de exceções	- Menos falsos negativos
Rest Assured - Pronto para integração de testes API
Singleton Pattern	- Uso eficiente de recursos (WebDriver compartilhado)
Waits explícitos	- Testes mais estáveis e confiáveis

##🔮 Possibilidades de Expansão
O framework foi projetado para ser facilmente extensível.

📞 Suporte
Responsável: Bricio
Projeto: CDC - Casa do Construtor

🎉 Agradecimentos
Framework desenvolvido utilizando as melhores práticas do mercado:

Selenium WebDriver
Cucumber BDD
Page Objects Pattern
WebDriverManager
API Rest Assured
Singleton Pattern
