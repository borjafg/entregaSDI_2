package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.SeleniumUtils;

public class ValidadorLogIn {

    private String idioma;
    private WebDriver driver;

    public ValidadorLogIn(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    // El aseterisco que se añade en las comprobaciones de los labels, lo
    // incluye prime faces
    public void comprobarTextos() {
	// (1) comprobamos los botones de cambio a registro y de idiomas
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-menuitem-text", 8);
	assertTrue(
		"No se ha encontrado mensaje de registro de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_enlace_registro")));

	// (2) Comprobamos el formulario de log-in
	// (2.1)Comprobamos el titulo de login
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-panel-title", 8);
	assertTrue(
		"No se ha encontrado mensaje de titulo de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_titulo_panel")));

	// (2.2)Comprobamos los campos del formulario de login
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:label_login", 8);
	assertTrue(
		"No se ha encontrado label de nombre de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_usuario") + "*"));
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:label_contrasena", 8);
	assertTrue(
		"No se ha encontrado label de contraseña de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_contrasena") + "*"));

	// (2.3) Comprobamos los placeholders

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:usuario", 8);

	assertTrue(
		"No se ha encontrado placeholder de usuario de login",
		mensajes.get(0)
			.getAttribute("placeholder")
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_placeholder_usuario")));

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:contrasena", 8);
	assertTrue(
		"No se ha encontrado placeholder de usuario de login",
		mensajes.get(0)
			.getAttribute("placeholder")
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_placeholder_contrasena")));

	// (2.4) Comprobamos el boton de logueo
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 8);
	assertTrue(
		"No se ha encontrado boton para logearse de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"login_boton")));

	// validamos el boton de idioma del desplegable

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_menu_superior:submenu_idiomas", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_idioma")));

	SeleniumUtils.moverRatonPorEncimaDe(driver,
		"form_menu_superior:submenu_idiomas");

	// validamos el boton de cambio de idioma a español
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_menu_superior:boton_esp", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a español",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_idioma_es")));

	// validamos el boton de cambio de idioma a ingles
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_menu_superior:boton_eng", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a ingles",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_idioma_en")));

    }

}
