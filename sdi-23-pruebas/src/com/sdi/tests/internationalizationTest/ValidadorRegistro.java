package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.SeleniumUtils;

public class ValidadorRegistro {

    private String idioma;
    private WebDriver driver;

    public ValidadorRegistro(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    public void comprobarTextos() {

	// comprobamos el idioma del boton de cambio a ventana de login
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"id", "form_menu_superior:enlace_login", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_enlace_login")));

	// comprobamos el idioma del titulo del formulario de registro

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:panel_registry_header", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio a login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_titulo_panel")));

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

	// validamos el boton de idioma del desplegable

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_menu_superior:submenu_idiomas", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_idioma")));

	// validamos label del nombre
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:label_login", 8);
	assertTrue(
		"No se ha encontrado label de nombre de registro ",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_usuario") + "*"));
	// validamos label contraseña1
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:label_contrasena1", 8);
	assertTrue(
		"No se ha encontrado label de contraseña1 de registro",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_contrasena") + "*"));
	// validamos label contraseña2
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:label_contrasena2", 8);
	assertTrue(
		"No se ha encontrado label de contraseña2 de registro",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_contrasena_rep") + "*"));

	// validamos label email
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:label_email", 8);
	assertTrue(
		"No se ha encontrado label de contraseña2 de registro",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_email") + "*"));

	// validamos los placeholders

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:usuario", 8);

	assertTrue(
		"No se ha encontrado placeholder de usuario de registro",
		mensajes.get(0)
			.getAttribute("placeholder")
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_placeholder_usuario")));
	// --
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:email", 8);

	assertTrue(
		"No se ha encontrado placeholder de email de registro",
		mensajes.get(0)
			.getAttribute("placeholder")
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_placeholder_email")));
	// --
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:contrasena1", 8);

	assertTrue(
		"No se ha encontrado placeholder de contraseña1 de registro",
		mensajes.get(0)
			.getAttribute("placeholder")
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_placeholder_contrasena")));
	// --
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:contrasena2", 8);

	assertTrue(
		"No se ha encontrado placeholder de contraseña2 de registro",
		mensajes.get(0)
			.getAttribute("placeholder")
			.equals(new PropertiesReader().getValueOf(idioma,
				"registro_placeholder_contrasena_rep")));
	// --

    }
}
