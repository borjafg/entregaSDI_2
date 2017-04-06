package com.sdi.tests.utils;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MySeleniumUtils {

    private static final int TIMEOUT = 20; // seconds

    /**
     * Hace click en una opcion de un submenú, pero espera un momento después de
     * situarse encima del submenú para hacer click.<br />
     * <br />
     * Si hiciera click inmediatamente podría darse el caso de que el submenú no
     * estuviera visible todavía y saltará una excepción.
     * 
     */
    static public void ClickSubopcionMenuHover(WebDriver driver,
	    String submenu, String opcionclick) {

	// Pasamos el cursor por el submenú para aparezca el desplegable
	moveHoverElement(driver, submenu);

	// Esperamos antes de hacer click para que el submenú se construya
	ThreadUtil.wait(650);

	// Pinchamos la opción opcion click
	By locator = By.id(opcionclick);
	driver.findElement(locator).click();
    }

    /**
     * Permite colocar el ratón encima del elemento cuyo id indiquemos.
     * 
     * @param driver
     *            objeto que permite interactuar con el navegador web
     * @param id
     *            identificador que nos permite obtener el objeto sobre el que
     *            queremos situar el cursor
     * 
     */
    static public void moveHoverElement(WebDriver driver, String id) {
	Actions builder = new Actions(driver);

	WebElement hoverElement = driver.findElement(By.id(id));
	builder.moveToElement(hoverElement).perform();
    }

    // =====================================
    // Métodos de búsqueda
    // =====================================

    static public WebElement waitForElementWithId(WebDriver driver, String id) {
	List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
		"id", id, TIMEOUT);

	try {
	    return elementos.get(0);
	}

	catch (IndexOutOfBoundsException ex) {
	    assertTrue("No se ha encontrado el elemento con id ==>  " + id,
		    false);

	    return null;
	}
    }

    static public WebElement waitForElementWithClass(WebDriver driver,
	    String clazz) {

	List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
		"class", clazz, TIMEOUT);

	try {
	    return elementos.get(0);
	}

	catch (IndexOutOfBoundsException ex) {
	    assertTrue("No se ha encontrado el elemento que contiene la clase "
		    + "==>  " + clazz, false);

	    return null;
	}

    }

    static public WebElement waitForElementWithText(WebDriver driver,
	    String text) {

	List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
		"text", text, TIMEOUT);

	try {
	    return elementos.get(0);
	}

	catch (IndexOutOfBoundsException ex) {
	    assertTrue("No se ha encontrado el elemento que contiene el texto "
		    + "==>  " + text, false);

	    return null;
	}

    }

    /**
     * Usar en caso de que un elemento pueda estar representado por un id u otro
     * según su estado.<br/>
     * <br/>
     * Esto ocurre cuando se renderiza un componente u otro según una condicion
     * (para soportar esto jsf necesita que ambos elementos tengan diferentes
     * ids, a pesar de que en ningún momento lleguen a cargarse los dos en la
     * página al mismo tiempo).
     * 
     */
    static public WebElement waitForElementByIds(WebDriver driver, String id1,
	    String id2) {

	String busqueda1 = "//*[contains(@id,'" + id1 + "')]";
	String busqueda2 = "//*[contains(@id,'" + id2 + "')]";

	By elemento = By.xpath(busqueda1 + "|" + busqueda2);

	WebElement resultado = (new WebDriverWait(driver, TIMEOUT))
		.until(ExpectedConditions.visibilityOfElementLocated(elemento));

	assertTrue("No se ha encontrado un elemento con el id [" + id1 + "] "
		+ "o con el id [" + id2 + "]", resultado != null);

	return resultado;
    }

    // ================================================
    // Meétodos para buscar un elemento dentro de otro
    // ================================================

    /**
     * Search a element (whose class we know) within another element (whose id
     * we know)
     * 
     */
    public static WebElement findInElementUsingClass(WebDriver driver,
	    String ancestorId, String descendantClass) {

	String rutaAncestro = "//*[contains(@id,'" + ancestorId + "')]";
	String rutaDescen = "//*[contains(@class,'" + descendantClass + "')]";

	By elemento = By.xpath(rutaAncestro + rutaDescen);

	WebElement resultado = (new WebDriverWait(driver, TIMEOUT))
		.until(ExpectedConditions.visibilityOfElementLocated(elemento));

	assertTrue("No se ha encontrado un elemento con la clase ["
		+ descendantClass + "] dentro del elemento con id ["
		+ ancestorId + "]", resultado != null);

	return resultado;
    }

}