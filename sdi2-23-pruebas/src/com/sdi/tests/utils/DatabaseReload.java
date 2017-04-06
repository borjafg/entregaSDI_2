package com.sdi.tests.utils;

import org.openqa.selenium.WebDriver;

import com.sdi.tests.page_objects.PO_LoginForm;

public class DatabaseReload {

    public void reload(WebDriver driver) {
	int tiempoVerResultadoTest = 700;

	// -----------------------------------
	// (1) Hacer login como administrador
	// -----------------------------------

	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// ------------------------------------------------
	// (2) Esperar a que aparezca la tabla de usuarios
	// ------------------------------------------------

	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------------------
	// (3) Reiniciar la base de datos
	// -------------------------------

	MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_reinicio").click();

	MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-info-detail");

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ------------------
	// (4) Cerrar sesión
	// ------------------

	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	ThreadUtil.wait(tiempoVerResultadoTest);
    }

}