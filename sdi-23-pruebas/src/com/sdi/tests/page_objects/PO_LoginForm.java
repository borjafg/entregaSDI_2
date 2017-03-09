package com.sdi.tests.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.ThreadUtil;

public class PO_LoginForm {

    public void completeForm(WebDriver driver, String loginValue,
	    String passwordValue) {

	WebElement login = driver.findElement(By.id("form_anonimo:usuario"));

	login.click();
	login.clear();
	login.sendKeys(loginValue);

	ThreadUtil.wait(500); // Espera para ver el efecto del test

	WebElement password = driver.findElement(By
		.id("form_anonimo:contrasena"));

	password.click();
	password.clear();
	password.sendKeys(passwordValue);

	ThreadUtil.wait(500); // Espera para ver el efecto del test

	// ---------------------------
	// Pulsar el boton de Alta
	// ---------------------------

	By boton = By.id("form_anonimo:boton_login");
	driver.findElement(boton).click();
    }

}