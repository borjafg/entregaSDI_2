package com.sdi.tests.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.ThreadUtil;

public class PO_EditTaskNameAndCategory {

    public void completeForm(WebDriver driver, String nombre, int categoria) {
	int tiempoVerResultadoTest = 700;

	// ------------------------------------
	// (1) Completar el título de la tarea
	// ------------------------------------

	WebElement nombreC = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:nombre");

	nombreC.click();
	nombreC.clear();

	nombreC.sendKeys(nombre);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -----------------------------------------
	// (2) Seleccionar la categoría de la tarea
	// -----------------------------------------

	// ===> Hacer click para que se depliegue la lista de categorías

	WebElement categoriaSelector = MySeleniumUtils.waitForElementWithId(
		driver, "form_usuario:categoria_label");

	categoriaSelector.click();

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ===> Seleccionar una categoría

	categoriaSelector = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:categoria_" + categoria);

	categoriaSelector.click();

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------------------------
	// (3) Confirmar la edición de la tarea
	// -------------------------------------

	WebElement botonEditar = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:boton_editar");

	ThreadUtil.wait(tiempoVerResultadoTest);

	botonEditar.click();
    }

}