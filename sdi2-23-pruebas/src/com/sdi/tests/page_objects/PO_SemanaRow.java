package com.sdi.tests.page_objects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;

public class PO_SemanaRow {

    public Map<String, Object> findRow(WebDriver driver, int index) {
	Map<String, Object> row = new HashMap<String, Object>();

	// ----------------------------------
	// (1) Extraer el título de la tarea
	// ----------------------------------

	WebElement titulo = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_titulo");

	row.put("titulo", titulo.getText());

	// ----------------------------------------
	// (2) Extraer los comentarios de la tarea
	// ----------------------------------------

	WebElement comentario = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_comentarios");

	row.put("comentario", comentario.getText());

	// --------------------------------------
	// (3) Extraer el nombre de la categoría
	// --------------------------------------

	WebElement categoria = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_categoria");

	row.put("categoria", categoria.getText());

	// ----------------------------------------------------------------
	// (4) Extraer el elemento categoria (nos interesan sus atributos)
	// ----------------------------------------------------------------

	WebElement categoriaWebElement = MySeleniumUtils.waitForElementWithId(
		driver, "form_user:tabla_tareas:" + index
			+ ":columna_categoria");

	row.put("categoriaWebElement", categoriaWebElement);

	// ---------------------------------------------
	// (5) Extraer la fecha de creación de la tarea
	// ---------------------------------------------

	WebElement fechaCreada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_creada");

	row.put("fechaCreacion", fechaCreada.getText());

	// --------------------------------------------------------
	// (6) Extraer la fecha para la que está planeada la tarea
	// --------------------------------------------------------

	WebElement fechaPlaneada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_planeada");

	row.put("fechaPlaneada", fechaPlaneada.getText());

	// ----------------------------------------------------
	// (7) Extraer el botón que permite finalizar la tarea
	// ----------------------------------------------------

	WebElement botonFinalizar = MySeleniumUtils.waitForElementWithId(
		driver, "form_user:tabla_tareas:" + index
			+ ":columna_finalizar");

	row.put("fechaFinalizada", botonFinalizar);

	// -----------------------------------------
	// (8) Devolvemos los elementos que sacamos
	// -----------------------------------------

	return row;
    }

}