package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ============ VERIFICAÇÕES DE EXISTÊNCIA/VISIBILIDADE ============

    /**
     * Verifica se o elemento EXISTE no DOM (pode estar invisível)
     */
    public boolean existeElemento(WebElement elemento) {
        try {
            return elemento != null && elemento.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Verifica se o elemento está VISÍVEL (existe E está display:true)
     */
    public boolean isVisivel(WebElement elemento) {
        try {
            return elemento.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Verifica se o elemento está visível COM TIMEOUT personalizado
     */
    public boolean isVisivel(WebElement elemento, int timeoutSegundos) {
        try {
            WebDriverWait waitPersonalizado = new WebDriverWait(driver, Duration.ofSeconds(timeoutSegundos));
            waitPersonalizado.until(ExpectedConditions.visibilityOf(elemento));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Verifica se o elemento está clicável
     */
    public boolean isClicavel(WebElement elemento) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elemento));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Verifica se o elemento está habilitado
     */
    public boolean isHabilitado(WebElement elemento) {
        try {
            return elemento.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Verifica se o elemento está selecionado (checkbox/radio)
     */
    public boolean isSelecionado(WebElement elemento) {
        try {
            return elemento.isSelected();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Verifica se uma lista NÃO está vazia e o elemento na posição existe
     */
    public boolean existeElementoNaLista(List<WebElement> lista, int posicao) {
        try {
            if (lista == null || lista.isEmpty()) {
                return false;
            }
            if (posicao < 0 || posicao >= lista.size()) {
                return false;
            }
            return lista.get(posicao) != null;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Verifica se a lista está vazia
     */
    public boolean isListaVazia(List<WebElement> lista) {
        return lista == null || lista.isEmpty();
    }

    /**
     * Obtém elemento da lista com segurança (retorna null se não existir)
     */
    public WebElement getElementoSeguro(List<WebElement> lista, int posicao) {
        if (existeElementoNaLista(lista, posicao)) {
            return lista.get(posicao);
        }
        return null;
    }

    /**
     * Aguarda o elemento ficar visível (lança exceção se timeout)
     */
    public WebElement aguardarVisibilidade(WebElement elemento) {
        return wait.until(ExpectedConditions.visibilityOf(elemento));
    }

    /**
     * Aguarda o elemento ficar visível com mensagem personalizada
     */
    public WebElement aguardarVisibilidade(WebElement elemento, String mensagemErro) {
        return wait.withMessage(mensagemErro)
                .until(ExpectedConditions.visibilityOf(elemento));
    }

}