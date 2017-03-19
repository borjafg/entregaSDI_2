package com.sdi.tests.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.ThreadUtil;

public class PO_RegistryForm {

    public void completeForm(WebDriver driver, String nombre, String email,
	    String password1, String password2) {

	WebElement login = driver.findElement(By.id("form_anonimo:usuario"));

	login.click();
	login.clear();
	login.sendKeys(nombre);

	ThreadUtil.wait(500);

	WebElement correo = driver.findElement(By.id("form_anonimo:email"));

	correo.click();
	correo.clear();
	correo.sendKeys(email);

	ThreadUtil.wait(500);

	WebElement pass1 = driver
		.findElement(By.id("form_anonimo:contrasena1"));

	pass1.click();
	pass1.clear();
	pass1.sendKeys(password1);

	ThreadUtil.wait(500);
	
	WebElement pass2 = driver
		.findElement(By.id("form_anonimo:contrasena2"));

	pass2.click();
	pass2.clear();
	pass2.sendKeys(password2);

	ThreadUtil.wait(500);
	
	
	By boton = By.id("form_anonimo:boton_registry");
	driver.findElement(boton).click();
	
    }
}
