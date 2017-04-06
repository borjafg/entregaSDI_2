package com.sdi.tests.page_objects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;

public class PO_HoyRow {

    public Map<String, Object> findRow(WebDriver driver, int index) {
	Map<String, Object> row = new HashMap<String, Object>();

	// ------------------------------------
	// (1) Extraemos el titulo de la tarea
	// ------------------------------------

	WebElement titulo = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_titulo");

	row.put("titulo", titulo);

	// ------------------------------
	// (2) Extraemos los comentarios
	// ------------------------------

	WebElement comentario = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_comentarios");

	row.put("comentario", comentario.getText());

	// ---------------------------
	// (3) Extraemos la categoría
	// ---------------------------

	WebElement categoria = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_categoria");

	row.put("categoria", categoria.getText());

	// -----------------------------------
	// (4) Extraemos la fecha de creación
	// -----------------------------------

	WebElement fechaCreada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_creada");

	row.put("fechaCreacion", fechaCreada.getText());

	// -------------------------------------------------
	// (5) Extraemos la fecha para la que está planeada
	// -------------------------------------------------

	WebElement fechaPlaneada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_planeada");

	row.put("fechaPlaneada", fechaPlaneada.getText());

	// A parte del texto nos interesa el componente en sí mismo para
	// tener acceso a los atributos del elemento

	WebElement fechaPlaneadaWeb = MySeleniumUtils
		.waitForElementWithId(driver, "form_user:tabla_tareas:" + index
			+ ":columna_planeada");

	row.put("fechaPlaneadaWebElement", fechaPlaneadaWeb);

	// ------------------------------------------------------
	// (6) Extraemos el botón que permite finalizar la tarea
	// ------------------------------------------------------

	WebElement fechaFinalizada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_finalizar");

	row.put("fechaFinalizada", fechaFinalizada);

	// -----------------------------------------
	// (7) Devolvemos los elementos que sacamos
	// -----------------------------------------

	return row;
    }

}