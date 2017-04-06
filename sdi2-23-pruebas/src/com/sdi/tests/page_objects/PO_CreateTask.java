package com.sdi.tests.page_objects;

import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.DateUtil;
import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.ThreadUtil;

public class PO_CreateTask {

    public void completeFormWithoutCalendar(WebDriver driver, String nombre,
	    String comentario, int categoria, int dia) {

	int tiempoVerResultadoTest = 620;

	// ------------------------------------
	// (Parte 1) Completar el campo nombre
	// ------------------------------------

	WebElement nombreC = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:nombre");

	nombreC.click();
	nombreC.clear();

	nombreC.sendKeys(nombre);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -----------------------------------------
	// (Parte 2) Completar el campo comentarios
	// -----------------------------------------

	WebElement comentarioC = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:comentarios");

	comentarioC.click();
	comentarioC.clear();

	comentarioC.sendKeys(comentario);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ------------------------------------------
	// (Parte 3) Elegir la categoría de la tarea
	// ------------------------------------------

	WebElement categoriaSelector = MySeleniumUtils.waitForElementWithId(
		driver, "form_usuario:categoria_label");

	categoriaSelector.click();

	ThreadUtil.wait(tiempoVerResultadoTest);

	// Buscar la categoría elegida
	categoriaSelector = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:categoria_" + categoria);

	// Hacer click en ella para seleccionarla
	categoriaSelector.click();

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ----------------------------------
	// (Parte 4) Elegir la fecha para la
	// que está planificada la tarea
	// ----------------------------------

	// La fecha se elige respecto al día de hoy
	Date diaCategoria = DateUtil.sumDaysToDate(new Date(), dia);

	String fechaString = DateUtil.convertDateToString(diaCategoria)
		.replace("/", "");

	WebElement fechaSelector = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:planeada_input");

	fechaSelector.click();
	fechaSelector.clear();

	fechaSelector.sendKeys(fechaString);

	ThreadUtil.wait(tiempoVerResultadoTest);

	nombreC.click(); // Hay que hacerlo para cerrar el calendario

	ThreadUtil.wait(tiempoVerResultadoTest);

	// --------------------------------------------------
	// (Parte 5) Hacer click en el boton de confirmación
	// --------------------------------------------------

	WebElement botonCrear = MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:boton_crear");

	ThreadUtil.wait(tiempoVerResultadoTest);

	botonCrear.click();
    }

}