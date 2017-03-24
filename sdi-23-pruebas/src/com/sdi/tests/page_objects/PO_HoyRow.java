package com.sdi.tests.page_objects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_HoyRow {
    public Map<String, Object> findRow(WebDriver driver, int index) {
	Map<String, Object> row = new HashMap<String, Object>();

	WebElement titulo = driver.findElement(By.id("form_user:tabla_tareas:"
		+ index + ":columna_titulo"));
	row.put("titulo", titulo.getText());

	WebElement comentario = driver
		.findElement(By.id("form_user:tabla_tareas:" + index
			+ ":columna_comentarios"));
	row.put("comentario", comentario.getText());

	WebElement categoria = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_categoria"));

	row.put("categoria", categoria.getText());

	WebElement fechaCreada = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_creada"));
	row.put("fechaCreacion", fechaCreada.getText());

	WebElement fechaPlaneada = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_planeada"));

	row.put("fechaPlaneada", fechaPlaneada.getText());
	
	WebElement fechaPlaneadaWeb = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_planeada"));

	row.put("fechaPlaneadaWebElement", fechaPlaneadaWeb);

	WebElement fechaFinalizada = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_finalizar"));
	row.put("fechaFinalizada", fechaFinalizada);

	return row;

    }
}