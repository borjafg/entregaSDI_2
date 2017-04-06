package com.sdi.tests.page_objects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;

public class PO_InboxRow {

    public Map<String, Object> findRow(WebDriver driver, int index) {
	Map<String, Object> row = new HashMap<String, Object>();

	// ---------------------------------
	// (1) Extrer el título de la tarea
	// ---------------------------------

	String titulo1 = "form_user:tabla_tareas:" + index + ":columna_titulo";
	String titulo2 = "form_user:tabla_tareas:" + index
		+ ":columna_titulo_finalizada";

	row.put("titulo",
		MySeleniumUtils.waitForElementByIds(driver, titulo1, titulo2));

	// ------------------------------------------
	// (2) Extraemos los comentarios de la tarea
	// ------------------------------------------

	WebElement comentario = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_comentarios");

	row.put("comentario", comentario.getText());

	// -----------------------------------------------
	// (3) Extraemos la fecha de creación de la tarea
	// -----------------------------------------------

	WebElement fechaCreada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_creada");

	row.put("fechaCreacion", fechaCreada.getText());

	// ----------------------------------------------------------
	// (4) Extraemos la fecha para la que está planeada la tarea
	// ----------------------------------------------------------

	WebElement fechaPlaneada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:" + index + ":columna_planeada");

	row.put("fechaPlaneada", fechaPlaneada.getText());

	// ---------------------------------------------------
	// (5) Extraemos la fecha de finalización de la tarea
	// ---------------------------------------------------

	String idFecha = "form_user:tabla_tareas:" + index
		+ ":columna_finalizada";

	String idTexto = "form_user:tabla_tareas:" + index
		+ ":columna_finalizada_no_acabada";

	WebElement fechaFinalizada = MySeleniumUtils.waitForElementByIds(
		driver, idFecha, idTexto);

	row.put("fechaFinalizada", fechaFinalizada.getText());

	// -------------------------------------------------------
	// (6) Extraemos el botón que permite finalizar una tarea
	// -------------------------------------------------------

	String idBoton = "form_user:tabla_tareas:" + index
		+ ":columna_finalizar";

	idTexto = "form_user:tabla_tareas:" + index
		+ ":columna_finalizar_acabada";

	WebElement fechaFinalizar = MySeleniumUtils.waitForElementByIds(driver,
		idBoton, idTexto);

	row.put("fechaFinalizar", fechaFinalizar);

	// --------------------------------
	// (7) Devolvemos todos los campos
	// --------------------------------

	return row;
    }

}