package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.PropertiesReader;

public class ValidadorPrincipalAdministrador {

    private String idioma;
    private WebDriver driver;

    public ValidadorPrincipalAdministrador(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    public void comprobarTextos() {
	// ------------------------------------------------
	// (1) Validamos el título de la lista de usuarios
	// ------------------------------------------------

	WebElement elemento = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-datatable-header ui-widget-header ui-corner-top");

	assertTrue(
		"No se ha encontrado mensaje de titulo de la tabla de usuarios",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"administrador__titulo_tabla_usuarios")));

	// -------------------------------------------------
	// (2) Validamos el botón de idioma del desplegable
	// -------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:submenu_idiomas");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		elemento.getText().equals(
			new PropertiesReader()
				.getValueOf(idioma, "menu_idioma")));

	MySeleniumUtils.moveHoverElement(driver,
		"form_menu_superior:submenu_idiomas");

	// -----------------------------------------------------
	// (3) Validamos el botón de cambio de idioma a español
	// -----------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_esp");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a español",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_es")));

	// ----------------------------------------------------
	// (4) Validamos el botón de cambio de idioma a inglés
	// ----------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_eng");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a inglés",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_en")));

	// ---------------------------------------------------------------
	// (5) Validamos texto de principal desplegable cuenta de usuario
	// ---------------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:submenu_usuario");

	assertTrue(
		"No se ha encontrado mensaje del título del desplegable cuenta"
			+ " de usuario",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_user__cuenta")));

	MySeleniumUtils.moveHoverElement(driver,
		"form_menu_superior:submenu_usuario");

	// ------------------------------------------------------
	// (6) Validamos el texto [usuario: <nombre de usuario>]
	// ------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:output_Text_user_login");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_user__login")));

	// ---------------------------------
	// (7) Validamos el botón de logout
	// ---------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_logout");

	assertTrue(
		"No se ha encontrado mensaje del boton de logout",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_user__logout")));
    }

}