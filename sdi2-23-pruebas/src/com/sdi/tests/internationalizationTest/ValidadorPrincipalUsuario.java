package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.PropertiesReader;

public class ValidadorPrincipalUsuario {

    private String idioma;
    private WebDriver driver;

    public ValidadorPrincipalUsuario(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    public void comprobarTextos() {
	// -------------------------------------------------
	// (1) Validamos el botón de idioma del desplegable
	// -------------------------------------------------

	WebElement elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:submenu_idiomas");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		elemento.getText().equals(
			new PropertiesReader()
				.getValueOf(idioma, "menu_idioma")));

	MySeleniumUtils.moveHoverElement(driver,
		"form_menu_superior:submenu_idiomas");

	// -----------------------------------------------------
	// (2) Validamos el botón de cambio de idioma a español
	// -----------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_esp");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a español",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_es")));

	// ----------------------------------------------------
	// (3) Validamos el botón de cambio de idioma a inglés
	// ----------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_eng");

	assertTrue(
		"No se ha encontrado mensaje del botón de cambio a inglés",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_en")));

	// -----------------------------------------------------------
	// (4) Validamos el texto del desplegable "cuenta de usuario"
	// -----------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:submenu_usuario");

	assertTrue(
		"No se ha encontrado mensaje del titulo del desplegable "
			+ "cuenta de usuario",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_user__cuenta")));

	MySeleniumUtils.moveHoverElement(driver,
		"form_menu_superior:submenu_usuario");

	// ------------------------------------------------------
	// (5) Validamos el texto [usuario: <nombre de usuario>]
	// ------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:output_Text_user_login");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_user__login")));

	// ---------------------------------
	// (6) Validamos el botón de logout
	// ---------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_logout");

	assertTrue(
		"No se ha encontrado mensaje del boton de logout",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_user__logout")));

	// --------------------------------------------------------------
	// (7) Validamos el titulo de la tabla de categorías del sistema
	// --------------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:titulo_tabla_usuario");

	assertTrue(
		"No se ha encontrado mensaje del titulo de tabla de categorias"
			+ " del sistema.",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"principal_usuario__titulo_panel")));

	// -----------------------------------------------------------
	// (8) validamos texto del boton de listado en categoria: hoy
	// -----------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tareas_hoy");

	assertTrue(
		"No se ha encontrado mensaje del boton para listar tareas de "
			+ "hoy",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"principal_usuario__hoy")));

	// -------------------------------------------------------------
	// (9) Validamos texto del boton de listado en categoria: inbox
	// -------------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tareas_inbox");

	assertTrue(
		"No se ha encontrado mensaje del boton para listar tareas de "
			+ "inbox",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"principal_usuario__inbox")));

	// ---------------------------------------------------------------
	// (10) Validamos texto del boton de listado en categoria: semana
	// ---------------------------------------------------------------

	elemento = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tareas_semana");

	assertTrue(
		"No se ha encontrado mensaje del boton para listar tareas de "
			+ "semana",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"principal_usuario__semana")));
    }

}