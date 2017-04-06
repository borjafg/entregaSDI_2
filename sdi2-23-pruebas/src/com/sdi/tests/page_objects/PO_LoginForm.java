package com.sdi.tests.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.ThreadUtil;

public class PO_LoginForm {

    public void completeForm(WebDriver driver, String loginValue,
	    String passwordValue) {

	int tiempoVerEfectoTest = 700;

	// ----------------------------
	// (1) Rellenar el campo login
	// ----------------------------

	// Esperar por si no estuviera cargado
	WebElement login = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:usuario");

	login.click();
	login.clear();
	login.sendKeys(loginValue);

	ThreadUtil.wait(tiempoVerEfectoTest);

	// ---------------------------------
	// (2) Rellenar el campo contraseña
	// ---------------------------------

	WebElement password = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:contrasena");

	password.click();
	password.clear();
	password.sendKeys(passwordValue);

	ThreadUtil.wait(tiempoVerEfectoTest);

	// ----------------------------
	// (3) Pulsar el boton de Alta
	// ----------------------------

	WebElement boton = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_login");

	boton.click();
    }

}