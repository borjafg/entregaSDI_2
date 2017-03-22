package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.SeleniumUtils;

public class ValidadorPrincipalAdministrador {

    private String idioma;
    private WebDriver driver;

    public ValidadorPrincipalAdministrador(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    public void comprobarTextos() {

	// validamos el titulo de la lista de usuarios
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-datatable-header ui-widget-header ui-corner-top",
		8);
	assertTrue(
		"No se ha encontrado mensaje de titulo de la tabla de usuarios",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"administrador_titulo_tabla_usuarios")));

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

	// validamos texto de usuario: <nombre de usuario>

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_menu_superior:output_Text_user_login", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de cambio de idioma",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_user_login")));

	// validamos texto del titulo de la tabla de categorias de tarea

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_user:titulo_tabla_usuario", 8);
	assertTrue(
		"No se ha encontrado mensaje del titulo de tabla de categorias",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"principal_usuario_titulo_panel")));

	// validamos texto del boton de listado en categoria: hoy

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_user:tareas_hoy", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton para listar tareas de hoy",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"principal_usuario_hoy")));

	// validamos texto del boton de listado en categoria: inbox

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_user:tareas_inbox", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton para listar tareas de inbox",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"principal_usuario_inbox")));

	// validamos texto del boton de listado en categoria: semana

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_user:tareas_semana", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton para listar tareas de inbox",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"principal_usuario_semana")));

	// validamos el boton de logout

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_menu_superior:boton_logout", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de logout",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_user_logout")));

    }

}
