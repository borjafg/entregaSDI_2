package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.PropertiesReader;

public class ValidadorLogIn {

    private String idioma;
    private WebDriver driver;

    public ValidadorLogIn(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    /*
     * El asterisco que se añade en las comprobaciones de los labels, lo añade
     * primefaces cuando el valor de una entrada es requerido.
     */
    public void comprobarTextos() {
	// --------------------------------------------------------------
	// (1) Comprobamos los botones de cambio a registro y de idiomas
	// --------------------------------------------------------------

	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-menuitem-text");

	assertTrue(
		"No se ha encontrado mensaje de registro de login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"login__enlace_registro")));

	// --------------------------------------------------------------
	// (2) Comprobamos el formulario de log-in
	// --------------------------------------------------------------

	// (2.1) Comprobamos el titulo de login
	mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-panel-title");

	assertTrue(
		"No se ha encontrado mensaje de titulo de login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"login__titulo_panel")));

	// (2.2) Comprobamos los campos del formulario de login
	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:label_login");

	assertTrue(
		"No se ha encontrado label de nombre de login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"login__usuario") + "*"));

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:label_contrasena");

	assertTrue(
		"No se ha encontrado label de contraseña de login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"login__contrasena") + "*"));

	// (2.3) Comprobamos los placeholders
	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:usuario");

	assertTrue(
		"No se ha encontrado placeholder de usuario de login",
		mensaje.getAttribute("placeholder").equals(
			new PropertiesReader().getValueOf(idioma,
				"login__placeholder_usuario")));

	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:contrasena");

	assertTrue(
		"No se ha encontrado placeholder de usuario de login",
		mensaje.getAttribute("placeholder").equals(
			new PropertiesReader().getValueOf(idioma,
				"login__placeholder_contrasena")));

	// (2.4) Comprobamos el boton de logueo
	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_login");

	assertTrue(
		"No se ha encontrado boton para logearse de login",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"login__boton")));

	// (2.5) Validamos el botón de idioma del desplegable
	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:submenu_idiomas");

	assertTrue(
		"El texto del submenú de cambio de idioma no es el que debería"
			+ " ser",
		mensaje.getText().equals(
			new PropertiesReader()
				.getValueOf(idioma, "menu_idioma")));

	MySeleniumUtils.moveHoverElement(driver,
		"form_menu_superior:submenu_idiomas");

	// (2.6) Validamos el botón de cambio de idioma a español
	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_esp");

	assertTrue(
		"El texto del botón de cambio de idioma (a español) no es el "
			+ "que debería ser.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_es")));

	// (2.7) validamos el botón de cambio de idioma a inglés
	mensaje = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_eng");

	assertTrue(
		"El texto del botón de cambio de idioma (a inglés) no es el "
			+ "que debería ser.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(idioma,
				"menu_idioma_en")));
    }

}