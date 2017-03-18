package com.sdi.tests.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.page_objects.PO_LoginForm;

public class DatabaseReload {

    public void reload(WebDriver driver) {
	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que aparezca la tabla de usuarios
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios", 10);

	ThreadUtil.wait(600);

	// (3) Reiniciar la base de datos
	((WebElement) driver.findElement(By
		.id("form_menu_superior:boton_reinicio"))).click();

	SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-info-detail", 8);

	ThreadUtil.wait(600);

	// (4) cerrar sesión
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	ThreadUtil.wait(1000);
    }

}
