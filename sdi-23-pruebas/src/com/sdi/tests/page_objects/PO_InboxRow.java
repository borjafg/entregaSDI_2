package com.sdi.tests.page_objects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_InboxRow {
    public Map<String, Object> findRow(WebDriver driver, int index) {
	Map<String, Object> row = new HashMap<String, Object>();
	
	WebElement titulo = null;
	try{
	titulo = driver.findElement(By.id("form_user:tabla_tareas:"
		+ index + ":columna_titulo"));
	row.put("titulo", titulo);
	
	    
	}catch(NoSuchElementException e){
	    titulo = driver.findElement(By.id("form_user:tabla_tareas:"
			+ index + ":columna_titulo_finalizada"));
	    row.put("titulo", titulo);
	}
	
	

	WebElement comentario = driver
		.findElement(By.id("form_user:tabla_tareas:" + index
			+ ":columna_comentarios"));
	row.put("comentario", comentario.getText());

	WebElement fechaCreada = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_creada"));
	row.put("fechaCreacion", fechaCreada.getText());

	WebElement fechaPlaneada = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_planeada"));
	row.put("fechaPlaneada", fechaPlaneada.getText());

	WebElement fechaFinalizada = driver.findElement(By
		.id("form_user:tabla_tareas:" + index + ":columna_finalizada"));
	row.put("fechaFinalizada", fechaFinalizada.getText());

	// Comporbacion porque cuando un atarea está finalizada, no tiene ningún
	// boton para finalizarla

	if (fechaFinalizada.getText().length() == 0) {
	    WebElement fechaFinalizar = driver.findElement(By
		    .id("form_user:tabla_tareas:" + index
			    + ":columna_finalizar"));
	    row.put("fechaFinalizar", fechaFinalizar);
	} else {
	    row.put("fechaFinalizar", "-------");
	}
	return row;

    }
}
