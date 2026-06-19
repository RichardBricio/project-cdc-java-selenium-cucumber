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
        this.wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        PageFactory.initElements(driver, this);
    }

    public boolean existeElemento(WebElement elemento) {
        try {
            return elemento != null && elemento.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isVisivel(WebElement elemento) {
        try {
            return elemento.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isVisivel(WebElement elemento, int timeoutSegundos) {
        try {
            WebDriverWait waitPersonalizado = new WebDriverWait(driver, Duration.ofMillis(15000));
            waitPersonalizado.until(ExpectedConditions.visibilityOf(elemento));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isClicavel(WebElement elemento) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elemento));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isHabilitado(WebElement elemento) {
        try {
            return elemento.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isSelecionado(WebElement elemento) {
        try {
            return elemento.isSelected();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

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

    public boolean isListaVazia(List<WebElement> lista) {
        return lista == null || lista.isEmpty();
    }

    public WebElement getElementoSeguro(List<WebElement> lista, int posicao) {
        if (existeElementoNaLista(lista, posicao)) {
            return lista.get(posicao);
        }
        return null;
    }

    public WebElement aguardarVisibilidade(WebElement elemento) {
        return wait.until(ExpectedConditions.visibilityOf(elemento));
    }

    public WebElement aguardarVisibilidade(WebElement elemento, String mensagemErro) {
        return wait.withMessage(mensagemErro)
                .until(ExpectedConditions.visibilityOf(elemento));
    }

}