package com.sdi.tests.utils;

import java.util.List;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

    // Mueve el ratón a la opción de menú submenu(desplegable). Evento hover
    // y clicka la opcion opcionclick
    static public void ClickSubopcionMenuHover(WebDriver driver,
	    String submenu, String opcionclick) {

	// Pasamos el raton por el submenu de Gestion de alumnos para
	// que aparezca el menu desplegable
	Actions builder = new Actions(driver);

	WebElement hoverElement = driver.findElement(By.id(submenu));
	builder.moveToElement(hoverElement).perform();

	// Pinchamos la opcion opcionclick
	By locator = By.id(opcionclick);
	driver.findElement(locator).click();
    }

    static public void textoPresentePagina(WebDriver driver, String texto) {
	List<WebElement> list = driver.findElements(By
		.xpath("//*[contains(text(),'" + texto + "')]"));
	assertTrue("Texto " + texto + " no localizado!", list.size() > 0);
    }

    static public void textoNoPresentePagina(WebDriver driver, String texto) {
	List<WebElement> list = driver.findElements(By
		.xpath("//*[contains(text(),'" + texto + "')]"));
	assertTrue("Texto " + texto + " aun presente !", list.size() == 0);
    }

    static public void EsperaCargaPaginaNoTexto(WebDriver driver, String texto,
	    int timeout) {
	Boolean resultado = (new WebDriverWait(driver, timeout))
		.until(ExpectedConditions.invisibilityOfElementLocated(By
			.xpath("//*[contains(text(),'" + texto + "')]")));

	assertTrue(resultado);
    }

    static public List<WebElement> EsperaCargaPaginaxpath(WebDriver driver,
	    String xpath, int timeout) {
	WebElement resultado = (new WebDriverWait(driver, timeout))
		.until(ExpectedConditions.visibilityOfElementLocated(By
			.xpath(xpath)));
	assertTrue(resultado != null);
	List<WebElement> elementos = driver.findElements(By.xpath(xpath));

	return elementos;
    }

    /**
     * Permite buscar por <i>Id</i> o <i>Class</i>. También permite buscar un
     * <i>texto</i> en la página.<br/>
     * <br/>
     * Espera hasta que el elemento esté visible o se haya superado el límite de
     * tiempo indicado. Cuando se termina la espera (bien por que se encontró el
     * elemento o bien porque se acabo el tiempo) se comprobará con un aserto
     * que se encontró el elemento.<br />
     * <br />
     * De esta forma sirve tanto para carga de páginas enteras (esperando por un
     * elemento de la página que se debería renderizar) como para elementos que
     * están ocultos y se hacen visibles.
     * 
     * @param driver
     *            objeto que permite interactuar con el navegador web
     * @param criterio
     *            . "id" or "class" or "text"
     * @param id
     *            valor de un atributo que identifica al elemento por cuya carga
     *            hay que esperar (el criterio de búsqueda dependerá del valor
     *            del parámetro <i>criterio</i>).
     * @param timeout
     *            máximo número de segundos que se esperará por la carga del
     *            elemento
     */
    static public List<WebElement> EsperaCargaPagina(WebDriver driver,
	    String criterio, String id, int timeout) {

	String busqueda;

	if (criterio.equals("id"))
	    busqueda = "//*[contains(@id,'" + id + "')]";

	else if (criterio.equals("class"))
	    busqueda = "//*[contains(@class,'" + id + "')]";

	else
	    busqueda = "//*[contains(text(),'" + id + "')]";

	return EsperaCargaPaginaxpath(driver, busqueda, timeout);
    }

    /**
     * Permite colocar el raton encima del elemento al que le pasamos su id
     * 
     * @param driver
     *            objeto que permite interactuar con el navegador web
     * @param id
     *            identificador que nos permite obtener el objeto sobre el que
     *            queremos poner el raton por encima
     */
    static public void moverRatonPorEncimaDe(WebDriver driver, String id) {
	Actions builder = new Actions(driver);

	WebElement hoverElement = driver.findElement(By.id(id));
	builder.moveToElement(hoverElement).perform();
    }
}
