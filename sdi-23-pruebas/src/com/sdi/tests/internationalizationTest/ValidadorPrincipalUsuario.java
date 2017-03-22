package com.sdi.tests.internationalizationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.SeleniumUtils;

public class ValidadorPrincipalUsuario {

    private String idioma;
    private WebDriver driver;

    public ValidadorPrincipalUsuario(String idioma, WebDriver driver) {
	this.idioma = idioma;
	this.driver = driver;
    }

    public void comprobarTextos() {
	// validamos el boton de logout
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"id", "form_menu_superior:boton_logout", 8);
	assertTrue(
		"No se ha encontrado mensaje del boton de logout",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(idioma,
				"menu_user_logout")));

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

	
	//validamos texto de usuario: <nombre de usuario>
	    
			mensajes = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form_menu_superior:output_Text_user_login", 8);
			assertTrue(
				"No se ha encontrado mensaje del boton de cambio de idioma",
				mensajes.get(0)
					.getText()
					.equals(new PropertiesReader().getValueOf(idioma,
						"menu_user_login")));
			
			
	
	
	
	
    }

}
