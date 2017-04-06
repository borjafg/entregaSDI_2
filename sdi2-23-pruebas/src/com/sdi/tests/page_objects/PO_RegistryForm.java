package com.sdi.tests.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.ThreadUtil;

public class PO_RegistryForm {

    public void completeForm(WebDriver driver, String nombre, String email,
	    String password1, String password2) {

	int tiempoVerResultadoTest = 500;

	// -------------------------------
	// (1) Completamos el campo login
	// -------------------------------

	WebElement login = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:usuario");

	login.click();
	login.clear();

	login.sendKeys(nombre);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------------------
	// (2) Completamos el campo email
	// -------------------------------

	WebElement correo = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:email");

	correo.click();
	correo.clear();

	correo.sendKeys(email);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------------------------
	// (3) Completamos el campo contraseña1
	// -------------------------------------

	WebElement pass1 = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:contrasena1");

	pass1.click();
	pass1.clear();

	pass1.sendKeys(password1);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------------------------
	// (3) Completamos el campo contraseña2
	// -------------------------------------

	WebElement pass2 = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:contrasena2");

	pass2.click();
	pass2.clear();

	pass2.sendKeys(password2);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ----------------------------------------
	// (4) Confirmamos el registro del usuario
	// ----------------------------------------

	WebElement boton = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	boton.click();
    }
}