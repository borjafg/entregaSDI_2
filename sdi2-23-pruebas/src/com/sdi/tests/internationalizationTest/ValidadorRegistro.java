package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.PropertiesReader;

public class ValidadorRegistro {

    private String idioma;
    private WebDriver driver;

    public ValidadorRegistro(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    public void comprobarTextos() {
	// ------------------------------------
	// (1) Comprobamos el idioma del botón
	// de paso a la ventana de login
	// ------------------------------------

	WebElement mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:enlace_login");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__enlace_login")));

	// -------------------------------------
	// (2) comprobamos el idioma del título
	// del formulario de registro
	// -------------------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:panel_registry_header");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__titulo_panel")));

	// ---------------------------
	// (3) Validamos el botón de
	// idioma del desplegable
	// ---------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:submenu_idiomas");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		mensaje.getText().equals(
			new PropertiesReader()
				.getValueOf(idioma, "menu_idioma")));

	MySeleniumUtils.moveHoverElement(driver,
		"form_menu_superior:submenu_idiomas");

	// ---------------------------
	// (4) Validamos el botón de
	// cambio de idioma a español
	// ---------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_esp");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a español",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_es")));

	// --------------------------
	// (5) Validamos el botón de
	// cambio de idioma a inglés
	// --------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_eng");

	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a ingles",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_en")));

	// -----------------------
	// (6) Validamos la label
	// del campo nombre
	// -----------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:label_login");

	assertTrue(
		"No se ha encontrado label de nombre de registro ",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__usuario") + "*"));

	// -----------------------
	// (7) Validamos la label
	// del campo contraseña1
	// -----------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:label_contrasena1");

	assertTrue(
		"No se ha encontrado label de contraseña1 de registro",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__contrasena") + "*"));

	// -----------------------
	// (8) Validamos la label
	// del campo contraseña2
	// -----------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:label_contrasena2");

	assertTrue(
		"No se ha encontrado label de contraseña2 de registro",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__contrasena_rep") + "*"));

	// -----------------------
	// (9) Validamos la label
	// del campo email
	// -----------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:label_email");

	assertTrue(
		"No se ha encontrado label de contraseña2 de registro",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__email") + "*"));

	// ------------------------------
	// (10) Validamos el placeholder
	// del campo "nombre de usuario"
	// ------------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:usuario");

	assertTrue(
		"No se ha encontrado placeholder de usuario de registro",
		mensaje.getAttribute("placeholder").equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__placeholder_usuario")));

	// ------------------------------
	// (11) Validamos el placeholder
	// del campo email
	// ------------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:email");

	assertTrue(
		"No se ha encontrado placeholder de email de registro",
		mensaje.getAttribute("placeholder").equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__placeholder_email")));

	// ------------------------------
	// (12) Validamos el placeholder
	// del campo contraseña1
	// ------------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:contrasena1");

	assertTrue(
		"No se ha encontrado placeholder de contraseña1 de registro",
		mensaje.getAttribute("placeholder").equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__placeholder_contrasena")));

	// ------------------------------
	// (12) Validamos el placeholder
	// del campo contraseña2
	// ------------------------------

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:contrasena2");

	assertTrue(
		"No se ha encontrado placeholder de contraseña2 de registro",
		mensaje.getAttribute("placeholder").equals(
			new PropertiesReader().getValueOf(idioma,
				"registro__placeholder_contrasena_rep")));
    }

}