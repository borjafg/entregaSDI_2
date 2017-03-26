package com.sdi.tests.page_objects;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.DateUtil;
import com.sdi.tests.utils.ThreadUtil;

public class PO_CreateTask {

    public void completeFormWithoutCalendar(WebDriver driver, String nombre,
	    String comentario,  int categoria, int dia) {

	WebElement nombreC = driver.findElement(By.id("form_usuario:nombre"));

	nombreC.click();
	nombreC.clear();
	nombreC.sendKeys(nombre);

	ThreadUtil.wait(500);

	WebElement comentarioC = driver.findElement(By
		.id("form_usuario:comentarios"));
	comentarioC.click();
	comentarioC.clear();
	comentarioC.sendKeys(comentario);

	ThreadUtil.wait(500);

	WebElement categoriaSelector = driver.findElement(By
		.id("form_usuario:categoria_label"));
	ThreadUtil.wait(600);
	categoriaSelector.click();
	ThreadUtil.wait(600);
	categoriaSelector = driver.findElement(By.id("form_usuario:categoria_"
		+ categoria));

	ThreadUtil.wait(500);
	categoriaSelector.click();
	ThreadUtil.wait(600);
	Date diaCategoria = DateUtil.diasSiguientes(new Date(), dia);
	
	String fechaString = DateUtil.convertDateToString(diaCategoria).replace("/", "");
		WebElement fechaSelector = driver.findElement(By
			.id("form_usuario:planeada_input"));
	ThreadUtil.wait(600);
	fechaSelector.click();
	fechaSelector.clear();
	fechaSelector.sendKeys(fechaString);
	ThreadUtil.wait(600);
	
	WebElement botonCrear = driver.findElement(By
		.id("form_usuario:boton_crear"));
	ThreadUtil.wait(600);
	nombreC.click();//lo hacemos para cerrar el calendario
	ThreadUtil.wait(600);
	botonCrear.click();
	
    }

    //
    // String fechaString = String.valueOf(calendario
    // .get(Calendar.DAY_OF_MONTH))
    // + String.valueOf(calendario.get(Calendar.MONTH))
    // + String.valueOf(calendario.get(Calendar.YEAR));
    //

}
