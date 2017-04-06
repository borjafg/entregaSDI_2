package com.sdi.tests.utils;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sdi.tests.page_objects.PO_AdminRow;
import com.sdi.tests.page_objects.PO_LoginForm;

public class UserManager {

    public void disable(WebDriver driver, String locale, int index, String login) {
	int tiempoVerResultadoTest = 700;

	// --------------------------------------
	// (1) Iniciar sesión como administrador
	// --------------------------------------

	iniciarSesion(driver);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// --------------------------------------------------------
	// (2) Comprobamos que el usuario está en la fila indicada
	// --------------------------------------------------------

	Map<String, Object> fila = new PO_AdminRow().findRow(driver, index);

	String loginTable = (String) fila.get("login");

	assertTrue("En la fila de la tabla de usuarios de la página principal "
		+ "del administrador no está el usuario con login [" + login
		+ "]. No se deshabilitará el usuario indicado.",
		loginTable.equals(login));

	// ---------------------------------------
	// (3) Comprobamos que está habilitado
	// ---------------------------------------

	String status = (String) fila.get("status");

	assertTrue("El usuario con login [" + login + "] ya se encuentra "
		+ "habilitado.", status.equals("enabled"));

	// --------------------------------------
	// (4) Cambiamos el estado del usuario
	// --------------------------------------

	WebElement botonCambiarEstado = (WebElement) fila.get("button_state");

	botonCambiarEstado.click();

	// --------------------------------------------------
	// (5) Esperamos a que aparezca el mensaje apropiado
	// --------------------------------------------------

	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-info-detail");

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ------------------------------------------
	// (6) Comprobamos que es el que debería ser
	// ------------------------------------------

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(locale,
				"administrador__exito_cambiar_estado")));

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------
	// (7) Hacemos logout
	// -------------------

	cerrarSesion(driver);
    }

    public void enable(WebDriver driver, String locale, int index, String login) {
	int tiempoVerResultadoTest = 700;

	// --------------------------------------
	// (1) Iniciar sesión como administrador
	// --------------------------------------

	iniciarSesion(driver);

	ThreadUtil.wait(tiempoVerResultadoTest);

	// --------------------------------------------------------
	// (2) Comprobamos que el usuario está en la fila indicada
	// --------------------------------------------------------

	Map<String, Object> fila = new PO_AdminRow().findRow(driver, index);

	String loginTable = (String) fila.get("login");

	assertTrue("En la fila de la tabla de usuarios de la página principal "
		+ "del administrador no está el usuario con login [" + login
		+ "]. No se habilitará el usuario indicado.",
		loginTable.equals(login));

	// ---------------------------------------
	// (3) Comprobamos que está deshabilitado
	// ---------------------------------------

	String status = (String) fila.get("status");

	assertTrue("El usuario con login [" + login + "] ya se encuentra "
		+ "habilitado.", status.equals("disabled"));

	// --------------------------------------
	// (4) Cambiamos el estado del usuario
	// --------------------------------------

	WebElement botonCambiarEstado = (WebElement) fila.get("button_state");

	botonCambiarEstado.click();

	// --------------------------------------------------
	// (5) Esperamos a que aparezca el mensaje apropiado
	// --------------------------------------------------

	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-info-detail");

	ThreadUtil.wait(tiempoVerResultadoTest);

	// ------------------------------------------
	// (6) Comprobamos que es el que debería ser
	// ------------------------------------------

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(locale,
				"administrador__exito_cambiar_estado")));

	ThreadUtil.wait(tiempoVerResultadoTest);

	// -------------------
	// (7) Hacemos logout
	// -------------------

	cerrarSesion(driver);
    }

    private void iniciarSesion(WebDriver driver) {
	// (1) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperamos a que aparezca la tabla de usuarios
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");
    }

    private void cerrarSesion(WebDriver driver) {
	// (1) Pinchar en el botón de logout
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (2) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");
    }

}