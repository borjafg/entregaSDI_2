package com.sdi.tests.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.ThreadUtil;

public class PO_EditTaskNameAndCategory {

    public void completeForm(WebDriver driver, String nombre,
	    int categoria) {

	WebElement nombreC = driver.findElement(By.id("form_usuario:nombre"));

	nombreC.click();
	nombreC.clear();
	nombreC.sendKeys(nombre);

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
	
	
	WebElement botonEditar = driver.findElement(By
		.id("form_usuario:boton_editar"));
	ThreadUtil.wait(600);
	botonEditar.click();
	
	
	
    }

}
