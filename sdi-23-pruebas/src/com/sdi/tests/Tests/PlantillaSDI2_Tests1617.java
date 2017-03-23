package com.sdi.tests.Tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import alb.util.log.Log;

import com.sdi.tests.database_test.DatabaseContentsTester;
import com.sdi.tests.database_test.UserDeletedTester;
import com.sdi.tests.internationalizationTest.ValidadorLogIn;
import com.sdi.tests.internationalizationTest.ValidadorPrincipalAdministrador;
import com.sdi.tests.internationalizationTest.ValidadorPrincipalUsuario;
import com.sdi.tests.internationalizationTest.ValidadorRegistro;
import com.sdi.tests.page_objects.PO_AdminRow;
import com.sdi.tests.page_objects.PO_InboxRow;
import com.sdi.tests.page_objects.PO_LoginForm;
import com.sdi.tests.page_objects.PO_RegistryForm;
import com.sdi.tests.utils.DatabaseReload;
import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.SeleniumUtils;
import com.sdi.tests.utils.ThreadUtil;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantillaSDI2_Tests1617 {

    // WebDriver driver;
    List<WebElement> elementos = null;

    private final String defaultLocale = "es";
    private final String englishLocale = "en";

    static WebDriver driver = getDriver();

    static String URLExterno = "http://localhost:8180/sdi2-23";
    static String URLInterno = "http://localhost:8280/sdi2-23";

    public PlantillaSDI2_Tests1617() {

    }

    public static WebDriver getDriver() {

	File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");

	FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);

	FirefoxProfile firefoxProfile = new FirefoxProfile();

	return driver = new FirefoxDriver(ffBinary, firefoxProfile);
    }

    // ==================================
    // Métodos que se ejecutan antes y
    // después de los test
    // ==================================

    // @Before
    // public void run() {
    // // Este código es para ejecutar con la versión portale de Firefox 46.0
    // File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");
    //
    // FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
    // FirefoxProfile firefoxProfile = new FirefoxProfile();
    //
    // driver = new FirefoxDriver(ffBinary, firefoxProfile);
    // driver.get("http://localhost:8280/sdi2-23");
    //
    // // Este código es para ejecutar con una versión instalada de Firex 46.0
    // // driver = new FirefoxDriver();
    // // driver.get("http://localhost:8180/sdi2-23");
    // }

    @Before
    public void setUp() {
	driver.navigate().to(URLInterno);
	// driver.navigate().to(URLExterno);
    }

    // @After
    // public void end() {
    // // Cerramos el navegador
    // driver.quit();
    // }

    @After
    public void tearDown() {
	driver.manage().deleteAllCookies();
    }

    @AfterClass
    static public void end() {
	driver.quit();

    }

    // =====================================
    // PRUEBAS
    // =====================================

    // ---------------------
    // --- ADMINISTRADOR ---
    // ---------------------

    // PR01: Autentificar correctamente al administrador.
    @Test
    public void prueba01() {

	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// Esperamos a que se cargue la página del listado de usuarios

	// Ya se comprueba si no se carga en un assert que con contiene el
	// metodo que espera por la carga de la página

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios", 10);

	ThreadUtil.wait(800); // Espera para ver el efecto del test
    }

    // PR02: Fallo en la autenticación del administrador por introducir mal el
    // login.
    @Test
    public void prueba02() {
	// (1) Rellenar los datos del formulario
	new PO_LoginForm().completeForm(driver, "admin_", "admin");

	// (2) Esperar a que aparezca el aviso de login incorrecto
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-warn-detail", 8);

	WebElement mensaje = mensajes.get(0); // Solo debería haber un mensaje

	// (3) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login_usuario_no_existe")));

	ThreadUtil.wait(800); // Espera para ver el efecto del test
    }

    // PR03: Fallo en la autenticación del administrador por introducir mal la
    // password.
    @Test
    public void prueba03() {
	// (1) Rellenar los datos del formulario
	new PO_LoginForm().completeForm(driver, "admin", "admin_");

	// (2) Esperar a que aparezca el aviso de login incorrecto
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-warn-detail", 8);

	WebElement mensaje = mensajes.get(0); // Solo debería haber un mensaje

	// (3) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login_usuario_no_existe")));

	ThreadUtil.wait(800); // Espera para ver el efecto del test
    }

    // PR04: Probar que la base de datos contiene los datos insertados con
    // conexión correcta a la base de datos.
    @Test
    public void prueba04() {
	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que aparezca la opción de reiniciar la base de datos
	List<WebElement> elements = SeleniumUtils.EsperaCargaPagina(driver,
		"id", "form_menu_superior:boton_reinicio", 10);

	// (3) Hacer click en el item de menu
	elements.get(0).click();

	// (4) Esperar a que aparezca el mensaje de éxito
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-info-detail", 8);
	WebElement mensaje = mensajes.get(0);

	// (5) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador_exito_reinicio_base_datos")));

	// (6) Comprobar que los datos son los que deberían ser
	try {
	    new DatabaseContentsTester().test();

	    ThreadUtil.wait(800);
	}

	catch (Exception ex) {
	    Log.error(ex);
	    assertTrue(false); // Ante cualquier error el test falla
	}
    }

    // PR05: Visualizar correctamente la lista de usuarios normales.
    @Test
    public void prueba05() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios", 10);

	// (3) Comprobar que la página contiene los datos correctos
	Map<String, Object> fila;

	String email;
	String status = "enabled";

	for (int indexUser = 0; indexUser < 3; indexUser++) {
	    fila = new PO_AdminRow().findRow(driver, indexUser);

	    assertTrue(fila.get("login") != null
		    && fila.get("login").equals("user" + (indexUser + 1)));

	    email = "user" + (indexUser + 1) + "@mail.com";

	    assertTrue(fila.get("email") != null
		    && fila.get("email").equals(email));

	    assertTrue(fila.get("status") != null
		    && fila.get("status").equals(status));
	}

	ThreadUtil.wait(900); // Espera para ver el efecto del test
    }

    // PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de
    // entrar con el usuario que se ha desactivado.
    @Test
    public void prueba06() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios", 10);

	// (3) Buscar la primera fila y comprobar que el usuario está habilitado
	Map<String, Object> fila = new PO_AdminRow().findRow(driver, 0);

	assertTrue(fila.get("status") != null
		&& fila.get("status").equals("enabled"));

	// (4) Cambiar el estado del usuario
	((WebElement) fila.get("button_state")).click();

	// (5) Esperar a que aparezca el mensaje de éxito
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-info-detail", 8);
	WebElement mensaje = mensajes.get(0);

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador_exito_cambiar_estado")));

	ThreadUtil.wait(1000); // Espera para ver el efecto del test

	// (6) Cerrar sesión
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	// (7) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, (String) fila.get("login"),
		(String) fila.get("login"));

	// (8) Comprobar que no fue posible
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-warn-detail", 8);
	mensaje = mensajes.get(0);

	assertTrue(
		"No se encontró el mensaje de usuario deshabilitado",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login_usuario_deshabilitado")));

	ThreadUtil.wait(1000); // Espera para ver el efecto del test
    }

    // PR07: Cambiar el estado de un usuario a DISABLED a ENABLED, y tratar de
    // entrar con el usuario que se ha activado.
    @Test
    public void prueba07() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios", 10);

	// (3) Buscar la primera fila y comprobar que el usuario está habilitado
	Map<String, Object> fila = new PO_AdminRow().findRow(driver, 0);

	assertTrue(fila.get("status") != null
		&& fila.get("status").equals("disabled"));

	// (4) Cambiar el estado del usuario
	((WebElement) fila.get("button_state")).click();

	// (5) Esperar a que aparezca el mensaje de éxito
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-info-detail", 8);
	WebElement mensaje = mensajes.get(0);

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador_exito_cambiar_estado")));

	ThreadUtil.wait(800); // Espera para ver el efecto del test

	// (6) Cerrar sesión
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	// (7) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, (String) fila.get("login"),
		(String) fila.get("login"));

	// (8) Comprobar que se pudo hacer login
	SeleniumUtils.EsperaCargaPagina(driver, "id", "panel_categorias", 10);

	ThreadUtil.wait(800); // Espera para ver el efecto del test
    }

    // PR08: Ordenar por Login
    @Test
    public void prueba08() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	List<WebElement> titulos = SeleniumUtils.EsperaCargaPagina(driver,
		"id", "tabla_usuarios:column_login_title", 10);
	WebElement titulo = titulos.get(0);

	// (3) Ordenar por login (ASC)
	titulo.click();
	ThreadUtil.wait(1000); // Espera para ver el efecto del test

	// (4) Comprobar que la los datos están ordenados
	Map<String, Object> filaAnterior = new PO_AdminRow().findRow(driver, 0);
	Map<String, Object> filaActual;

	String loginAnterior = (String) filaAnterior.get("login");
	String loginActual;

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    loginActual = (String) filaActual.get("login");

	    assertTrue("Los usuarios no están ordenados",
		    loginAnterior.compareTo(loginActual) == -1);

	    filaAnterior = filaActual;
	    loginAnterior = loginActual;
	}

	ThreadUtil.wait(1000); // Espera para ver el efecto del test

	// (3) Ordenar por login (DESC)
	titulo.click();
	ThreadUtil.wait(600);

	// (4) Comprobar que los datos están ordenados

	/*
	 * El último usuario evaluado antes es el que aparecerá ahora en la
	 * primera fila de la tabla. Por está razón no hay que extraer el valor
	 * de la primera fila de la tabla y guardarla en la variable
	 * loginAnterior.
	 */

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    loginActual = (String) filaActual.get("login");

	    assertTrue("Los usuarios no están ordenados",
		    loginAnterior.compareTo(loginActual) == 1);

	    filaAnterior = filaActual;
	    loginAnterior = loginActual;
	}

	ThreadUtil.wait(1000); // Espera para ver el efecto del test
    }

    // PR09: Ordenar por Email
    @Test
    public void prueba09() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	List<WebElement> titulos = SeleniumUtils.EsperaCargaPagina(driver,
		"id", "tabla_usuarios:column_email_title", 10);
	WebElement titulo = titulos.get(0);

	// (3) Ordenar por email (ASC)
	titulo.click();
	ThreadUtil.wait(1000); // Espera para ver el efecto del test

	// (4) Comprobar que los datos están ordenados
	Map<String, Object> filaAnterior = new PO_AdminRow().findRow(driver, 0);
	Map<String, Object> filaActual;

	String emailAnterior = (String) filaAnterior.get("email");
	String emailActual;

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    emailActual = (String) filaActual.get("email");

	    assertTrue("Los usuarios no están ordenados",
		    emailAnterior.compareTo(emailActual) == -1);

	    filaAnterior = filaActual;
	    emailAnterior = emailActual;
	}

	ThreadUtil.wait(1000); // Espera para ver el efecto del test

	// (3) Ordenar por email (DESC)
	titulo.click();
	ThreadUtil.wait(600);

	// (4) Comprobar que la los datos están ordenados

	/*
	 * El último usuario evaluado antes es el que aparecerá ahora en la
	 * primera fila de la tabla. Por está razón no hay que extraer el valor
	 * de la primera fila de la tabla y guardarla en la variable
	 * emailAnterior.
	 */

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    emailActual = (String) filaActual.get("email");

	    assertTrue("Los usuarios no están ordenados",
		    emailAnterior.compareTo(emailActual) == 1);

	    filaAnterior = filaActual;
	    emailAnterior = emailActual;
	}

	ThreadUtil.wait(1000); // Espera para ver el efecto del test
    }

    // PR10: Ordenar por Status
    @Test
    public void prueba10() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios:column_status_title", 10);

	// (3) Desactivar un usuario para ver el resultado de la ordenación

	// -------------------------------------------------------

	// (3.1) Comprobar que el usuario está habilitado
	Map<String, Object> fila = new PO_AdminRow().findRow(driver, 0);

	assertTrue(fila.get("status") != null
		&& fila.get("status").equals("enabled"));

	// (3.2) Cambiar el estado del usuario
	((WebElement) fila.get("button_state")).click();

	// (3.3) Esperar a que aparezca el mensaje de éxito
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-info-detail", 8);
	WebElement mensaje = mensajes.get(0);

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador_exito_cambiar_estado")));

	ThreadUtil.wait(800); // Espera para ver el efecto del test

	// -------------------------------------------------------

	// (4) Ordenar por status (ASC)
	WebElement titulo = driver.findElement(By
		.id("form_admin:tabla_usuarios:column_status_title"));

	ThreadUtil.wait(200);

	titulo.click();
	ThreadUtil.wait(1000); // Espera para ver el efecto del test

	// (5) Comprobar que los datos están ordenados
	Map<String, Object> filaAnterior = new PO_AdminRow().findRow(driver, 0);
	Map<String, Object> filaActual;

	String statusAnterior = (String) filaAnterior.get("status");
	String statusActual;

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    statusActual = (String) filaActual.get("status");

	    assertTrue("Los usuarios no están ordenados",
		    statusAnterior.compareTo(statusActual) == 1
			    || statusAnterior.compareTo(statusActual) == 0);

	    filaAnterior = filaActual;
	    statusAnterior = statusActual;
	}

	ThreadUtil.wait(800); // Espera para ver el efecto del test

	// (6) Ordenar por status (DESC)
	titulo.click();
	ThreadUtil.wait(600);

	// (7) Comprobar que la los datos están ordenados

	/*
	 * El último usuario evaluado antes es el que aparecerá ahora en la
	 * primera fila de la tabla. Por está razón no hay que extraer el valor
	 * de la primera fila de la tabla y guardarla en la variable
	 * statusAnterior.
	 */

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    statusActual = (String) filaActual.get("status");

	    assertTrue("Los usuarios no están ordenados",
		    statusAnterior.compareTo(statusActual) == -1
			    || statusAnterior.compareTo(statusActual) == 0);

	    filaAnterior = filaActual;
	    statusAnterior = statusActual;
	}

	ThreadUtil.wait(700);

	// (8) Volver a activar el usuario

	// -------------------------------------------------------

	// (8.1) Comprobar que el usuario está habilitado
	fila = new PO_AdminRow().findRow(driver, 0);

	assertTrue(fila.get("status") != null
		&& fila.get("status").equals("disabled"));

	// (8.2) Cambiar el estado del usuario
	((WebElement) fila.get("button_state")).click();

	// (8.3) Esperar a que aparezca el mensaje de éxito
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-info-detail", 8);
	mensaje = mensajes.get(0);

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador_exito_cambiar_estado")));

	ThreadUtil.wait(400);

	// (8.4) Comprobar que el usuario está habilitado
	fila = new PO_AdminRow().findRow(driver, 0);

	assertTrue(fila.get("status") != null
		&& fila.get("status").equals("enabled"));

	// -------------------------------------------------------

	ThreadUtil.wait(800); // Espera para ver el efecto del test
    }

    // PR11: Borrar una cuenta de usuario normal y datos relacionados.
    @Test
    public void prueba11() {
	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que aparezca la tabla de usuarios
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_admin:tabla_usuarios", 10);

	ThreadUtil.wait(500);

	// (3) Reiniciar la base de datos
	((WebElement) driver.findElement(By
		.id("form_menu_superior:boton_reinicio"))).click();

	SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-info-detail", 8);

	ThreadUtil.wait(800);

	// (4) Buscar el boton de eliminar usuario y hacer click
	Map<String, Object> fila = new PO_AdminRow().findRow(driver, 0);

	String login = (String) fila.get("login");
	((WebElement) fila.get("button_delete")).click();

	ThreadUtil.wait(1000);

	driver.findElement(By.id("form_admin:confirm_delete")).click();

	ThreadUtil.wait(1000);

	// (5) Esperar a que aparezca el mensaje de éxito
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-info-detail", 8);
	WebElement mensaje = mensajes.get(0);

	// (6) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de usuario eliminado",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador_exito_borrar_usuario")));

	ThreadUtil.wait(900);

	// (7) Cerrar sesión
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	ThreadUtil.wait(500);

	// (8) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, login, login);

	// (9) Comprobar que no se puede iniciar sesión
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-warn-detail", 8);

	mensaje = mensajes.get(0);

	// (10) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login_usuario_no_existe")));

	// (11) Comprobar que realmente se borraron sus datos
	try {
	    new UserDeletedTester().test();

	    ThreadUtil.wait(400);
	}

	catch (Exception ex) {
	    Log.error(ex);
	    assertTrue(false); // Ante cualquier error el test falla
	}
    }

    // -------------------
    // ----- ANONIMO -----
    // -------------------

    // PR12: Crear una cuenta de usuario normal con datos válidos.
    @Test
    public void prueba12() {
	// (1) reiniciamos la base de datos
	new DatabaseReload().reload(driver);

	// (2) cambiamos a la pestaña de registro
	WebElement registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));
	registrarseEnlace.click();
	ThreadUtil.wait(600);

	// (3) rellenamos el formulario
	new PO_RegistryForm().completeForm(driver, "usuario4",
		"usuario4@mail.com", "password123", "password123");
	ThreadUtil.wait(1200);

	// (4) Sacamos la lista de elementos
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-info-detail", 8);
	ThreadUtil.wait(600);

	// (5) validamos la salida
	assertTrue(
		"No se ha encontrado el mensaje de registro correcto",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale, "registro_exito")));
    }

    // PR13: Crear una cuenta de usuario normal con login repetido.
    // Prueba dependiente de la prueba 12
    @Test
    public void prueba13() {
	// (1) cambiamos a la pestaña de registro
	WebElement registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));

	registrarseEnlace.click();
	ThreadUtil.wait(600);

	// (2) rellenamos el formulario
	new PO_RegistryForm().completeForm(driver, "usuario4",
		"usuario4@mail.com", "password123", "password123");
	ThreadUtil.wait(1200);

	// (3) Sacamos la lista de elementos
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-warn-detail", 8);
	ThreadUtil.wait(600);

	// (4) Validacion de la salida
	assertTrue(
		"No se ha encontrado el mensaje de login ya existente",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader()
				.getValueOf(defaultLocale,
					"error_registro_login_ya_existe")));
    }

    // PR14: Crear una cuenta de usuario normal con Email incorrecto.
    @Test
    public void prueba14() {
	// (1) cambiamos a la pestaña de registro
	WebElement registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));
	registrarseEnlace.click();
	ThreadUtil.wait(600);

	// (2) rellenamos el formulario
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5mailcom", "password123", "password123");
	ThreadUtil.wait(1200);

	// (3) Sacamos la lista de elementos
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-message-error-detail", 8);
	ThreadUtil.wait(600);

	// (4) Validacion de la salida
	assertTrue(
		"No se ha encontrado el mensaje de mail incorrecto",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader()
				.getValueOf(defaultLocale,
					"error_registro_email_no_valido")));
    }

    // PR15: Crear una cuenta de usuario normal con Password incorrecta.
    @Test
    public void prueba15() {
	// (1) reiniciamos la base de datos
	new DatabaseReload().reload(driver);

	// (2) cambiamos a la pestaña de registro
	WebElement registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));
	registrarseEnlace.click();
	ThreadUtil.wait(600);

	// (3) rellenamos el formulario [caso contraseña solo numeros]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "123456789", "123456789");
	ThreadUtil.wait(1200);

	// (4) Sacamos la lista de elementos
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-messages-warn-detail", 8);
	ThreadUtil.wait(600);

	// (5) validamos la salida
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale,
				"error_registro_password_contenido")));

	// (6) rellenamos el formulario [caso contraseña solo letras]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "asdfghjkl", "asdfghjkl");
	ThreadUtil.wait(1200);

	// (7) Sacamos la lista de elementos
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-warn-detail", 8);
	ThreadUtil.wait(600);

	// (8) validamos la salida
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale,
				"error_registro_password_contenido")));

	// (9) rellenamos el formulario [caso longitud contraseña inferior a 8
	// digitos]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "a1", "a1");

	// (10) Sacamos la lista de elementos
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-message-error-detail", 8);
	ThreadUtil.wait(600);

	// (11) validamos la salida
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale, "error_registro_password")));

	// (12) rellenamos el formulario [caso contraseñas distintas]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "asdfghjkl1234", "asdfghjkl123");
	ThreadUtil.wait(1200);

	// (13) Sacamos la lista de elementos
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-message-error-detail", 8);
	ThreadUtil.wait(600);

	// (14) validamos la salida
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale, "error_registro_password")));
    }

    // -------------------
    // ----- USUARIO -----
    // -------------------

    // PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
    // categoría y que son las que tienen que. Usar paginación navegando por las
    // tres páginas.
    @Test
    public void prueba16() {
	/*
	 * Vamos a reiniciar la base de datos, ya que es la primera prueba del
	 * bloque de pruebas con los usaurios registrados
	 */
	new DatabaseReload().reload(driver);
	ThreadUtil.wait(600);
	// Las unicas categorias que tienen tarea son de la 21 a la 30
	new PO_LoginForm().completeForm(driver, "user1", "user1");
	// clicamos en el boton de tareas dentro de Inbox
	ThreadUtil.wait(600);
	WebElement botonInbox = driver.findElement(By.id("form_user:inbox"));
	botonInbox.click();
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_user:tabla_tareas_data", 10);
	// comprobamos los elementos de la primera pagina
	List<Map<String, String>> pestaña = new ArrayList<Map<String, String>>();
	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}
	//comprobamos el nombre de las primeras 8 tareas
	for(int i = 0; i <8; i++){
	    assertTrue("Los nombres no son iguales",pestaña.get(i).get("titulo").equals("tarea0"+(i+1)));
	}
	
	SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-icon ui-icon-seek-next", 8).get(0).click();
	ThreadUtil.wait(600);
	pestaña = new ArrayList<Map<String, String>>();
	for (int i = 8; i <16 ; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}
	
	for(int i = 0; i <8; i++){
	    if(i==0){//porque es el ultimo que sigue terniendo de nombre 0X
		assertTrue("Los nombres no son iguales",pestaña.get(i).get("titulo").equals("tarea09"));
	    }else{
		assertTrue("Los nombres no son iguales",pestaña.get(i).get("titulo").equals("tarea"+(i+9)));
	    }
	}
	SeleniumUtils.EsperaCargaPagina(driver, "class", "ui-icon ui-icon-seek-next", 8).get(0).click();
	ThreadUtil.wait(600);
	pestaña = new ArrayList<Map<String, String>>();
	for (int i = 16; i <=19 ; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}
	for(int i = 0; i<4; i++){
	    assertTrue("Los nombres no son iguales",pestaña.get(i).get("titulo").equals("tarea"+(i+17)));
	}
    }

    // PR17: Funcionamiento correcto de la ordenación por fecha planeada.
    @Test
    public void prueba17() {
	assertTrue(false);
    }

    // PR18: Funcionamiento correcto del filtrado.
    @Test
    public void prueba18() {
	assertTrue(false);
    }

    // PR19: Funcionamiento correcto de la ordenación por categoría.
    @Test
    public void prueba19() {
	assertTrue(false);
    }

    // PR20: Funcionamiento correcto de la ordenación por fecha planeada.
    @Test
    public void prueba20() {
	assertTrue(false);
    }

    // PR21: Comprobar que las tareas que no están en rojo son las de hoy y
    // además las que deben ser.
    @Test
    public void prueba21() {
	assertTrue(false);
    }

    // PR22: Comprobar que las tareas retrasadas están en rojo y son las que
    // deben ser.
    @Test
    public void prueba22() {
	assertTrue(false);
    }

    // PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que
    // son las que deben ser.
    @Test
    public void prueba23() {
	assertTrue(false);
    }

    // PR24: Funcionamiento correcto de la ordenación por día.
    @Test
    public void prueba24() {
	assertTrue(false);
    }

    // PR25: Funcionamiento correcto de la ordenación por nombre.
    @Test
    public void prueba25() {
	assertTrue(false);
    }

    // PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir
    // a la pagina donde está la tarea terminada y comprobar que se muestra.
    @Test
    public void prueba26() {
	assertTrue(false);
    }

    // PR27: Crear una tarea sin categoría y comprobar que se muestra en la
    // lista Inbox.
    @Test
    public void prueba27() {
	assertTrue(false);
    }

    // PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y
    // comprobar que se muestra en la lista Hoy.
    @Test
    public void prueba28() {
	assertTrue(false);
    }

    // PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior
    // a Hoy y comprobar que se muestra en la lista Semana.
    @Test
    public void prueba29() {
	assertTrue(false);
    }

    // PR30: Editar el nombre, y categoría de una tarea (se le cambia a
    // categoría1) de la lista Inbox y comprobar que las tres pseudolista se
    // refresca correctamente.
    @Test
    public void prueba30() {
	assertTrue(false);
    }

    // PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una
    // tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan
    // correctamente.
    @Test
    public void prueba31() {
	assertTrue(false);
    }

    // PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las
    // tres pseudolistas.
    @Test
    public void prueba32() {
	assertTrue(false);
    }

    // PR33: Salir de sesión desde cuenta de administrador.
    @Test
    public void prueba33() {

	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");
	// (2) comprobamos que nos hemos logeado
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-datatable-header ui-widget-header ui-corner-top",
		8);

	assertTrue(
		"No se ha encontrado el nombre de la tabla",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale,
				"administrador_titulo_tabla_usuarios")));

	// (3) cerramos sesión
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");
	// (4) comprobamos que hemos salido a la pestaña de login

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-panel-title", 8);

	assertTrue(
		"No se ha encontrado mensaje de titulo de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale, "login_titulo_panel")));

    }

    // PR34: Salir de sesión desde cuenta de usuario normal.
    @Test
    public void prueba34() {

	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "user1", "user1");
	// (2) comprobamos que nos hemos logeado
	List<WebElement> mensajes = SeleniumUtils.EsperaCargaPagina(driver,
		"class", "ui-panel-title", 8);

	assertTrue(
		"No se ha encontrado el nombre de la tabla",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader()
				.getValueOf(defaultLocale,
					"principal_usuario_titulo_panel")));

	// (3) cerramos sesión
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (4) comprobamos que hemos salido a la pestaña de login

	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-panel-title", 8);
	assertTrue(
		"No se ha encontrado mensaje de titulo de login",
		mensajes.get(0)
			.getText()
			.equals(new PropertiesReader().getValueOf(
				defaultLocale, "login_titulo_panel")));

    }

    // PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas
    // vistas)
    @Test
    public void prueba35() {
	// (1) Comprobamos idioma por defecto
	// (1.1) comprobamos el idioma en la pagina de login
	new ValidadorLogIn("es", driver).comprobarTextos();
	// nos registramos como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");
	// (1.2) Comprobamos el idioma en la pestaña principal de administrador
	new ValidadorPrincipalAdministrador("es", driver);
	// Cerramos sesion
	ThreadUtil.wait(1500);
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	ThreadUtil.wait(1500);
	// nos logeamos como usuario normal
	new PO_LoginForm().completeForm(driver, "user1", "user1");
	// (1.3) Comprobamos el idioma en la ventana principal de usuario
	new ValidadorPrincipalUsuario("es", driver);
	// Cerramos sesion
	ThreadUtil.wait(1500);
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	ThreadUtil.wait(1500);
	// cambiamos la ventana de registro
	WebElement registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));
	registrarseEnlace.click();

	// (1.4) Comprobamos el idioma en la ventana de registro
	new ValidadorRegistro("es", driver);
	ThreadUtil.wait(1500);
	// cambiamos a la ventana de login
	WebElement loginEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_login"));
	loginEnlace.click();
	ThreadUtil.wait(1500);
	// (2) Cambiamos de idioma
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");
	// (3) Comprobamos con el otro idioma
	// (3.1) validamos el login
	new ValidadorLogIn(englishLocale, driver);
	ThreadUtil.wait(1500);
	// nos registramos como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");
	// (3.2) Comprobamos el idioma en la pestaña principal de administrador
	new ValidadorPrincipalAdministrador("en", driver);
	// Cerramos sesion
	ThreadUtil.wait(1500);
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	ThreadUtil.wait(1500);
	// nos logeamos como usuario normal
	new PO_LoginForm().completeForm(driver, "user1", "user1");
	// (3.3) Comprobamos el idioma en la ventana principal de usuario
	new ValidadorPrincipalUsuario(englishLocale, driver);
	// Cerramos sesion
	ThreadUtil.wait(1500);
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	ThreadUtil.wait(1500);
	// cambiamos la ventana de registro
	registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));
	registrarseEnlace.click();

	// (3.4) Comprobamos el idioma en la ventana de registro
	new ValidadorRegistro(englishLocale, driver);
	// cambiamos a la ventana de login
	ThreadUtil.wait(1500);
	loginEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_login"));
	loginEnlace.click();
	ThreadUtil.wait(600);

    }

    // PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
    // idioma por defecto. (Probar algunas vistas)
    @Test
    public void prueba36() {
	// form_menu_superior:boton_esp
	// (1) Ventana de login
	// (1.1) comprobamos idioma por defecto
	new ValidadorLogIn(defaultLocale, driver).comprobarTextos();
	// (1.2) cambiamos idioma y comprobamos

	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");
	ThreadUtil.wait(1000);
	new ValidadorLogIn(englishLocale, driver).comprobarTextos();
	ThreadUtil.wait(1000);
	// // (1.3)volvemos al primer idioma y comprobamos
	//
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");
	ThreadUtil.wait(1000);
	new ValidadorLogIn(defaultLocale, driver).comprobarTextos();
	// // (2) ventana principal de administrador

	new PO_LoginForm().completeForm(driver, "admin", "admin");
	// // (2.1)comprobamos idioma por defecto

	new ValidadorPrincipalAdministrador(defaultLocale, driver)
		.comprobarTextos();
	ThreadUtil.wait(1500);

	// (2.2)cambiamos idioma y comprobamos
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");
	ThreadUtil.wait(1500);
	new ValidadorPrincipalAdministrador(englishLocale, driver)
		.comprobarTextos();
	ThreadUtil.wait(1500);
	// (2.3) volvemos al primer idioma y comprobamos
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");
	ThreadUtil.wait(1500);
	new ValidadorPrincipalAdministrador("es", driver).comprobarTextos();
	// (2.4) cerramos sesion
	ThreadUtil.wait(1500);
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	// (3) ventana principal de usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");
	// (3.1)comprobamos idioma por defecto
	new ValidadorPrincipalUsuario(defaultLocale, driver).comprobarTextos();
	// (3.2)cambiamos idioma y comprobamos
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");
	ThreadUtil.wait(1500);
	new ValidadorPrincipalUsuario(englishLocale, driver).comprobarTextos();
	// // (3.3) volvemos al primer idioma y comprobamos
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");
	ThreadUtil.wait(1500);
	new ValidadorPrincipalUsuario(defaultLocale, driver).comprobarTextos();
	// (3.4) cerramos sesion
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");
	ThreadUtil.wait(1500);
	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	// (4) Ventana de registro

	WebElement registrarseEnlace = driver.findElement(By
		.id("form_menu_superior:enlace_registro"));
	registrarseEnlace.click();

	// (4.1)comprobamos idioma por defecto
	new ValidadorRegistro(defaultLocale, driver).comprobarTextos();
	// (4.2)cambiamos idioma y comprobamos
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");
	ThreadUtil.wait(1500);
	new ValidadorRegistro(englishLocale, driver).comprobarTextos();
	// (4.3) volvemos al primer idioma y comprobamos
	SeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");
	ThreadUtil.wait(1500);
	new ValidadorRegistro(defaultLocale, driver).comprobarTextos();

    }

    // PR37: Intento de acceso a un URL privado de administrador con un usuario
    // autenticado como usuario normal.
    @Test
    public void prueba37() {
	new PO_LoginForm().completeForm(driver, "user1", "user1");
	ThreadUtil.wait(600);
	new ValidadorPrincipalUsuario(defaultLocale, driver);
	driver.get("http://localhost:8280/sdi2-23/pages_admin/principal_administrador.xhtml");
	// no permitimos que el usuario se desplace por la aplicación, entonces
	// se queda en la ventana principal de usuario
	ThreadUtil.wait(600);
	new ValidadorPrincipalUsuario(defaultLocale, driver);
    }

    // PR38: Intento de acceso a un URL privado de usuario normal con un usuario
    // no autenticado.
    @Test
    public void prueba38() {
	// no permitimos que el usuario se desplace por la aplicación, entonces
	// se queda en la ventana de login
	new ValidadorLogIn(defaultLocale, driver);
	ThreadUtil.wait(600);
	driver.get("http://localhost:8280/sdi2-23/pages_user/principal_usuario.xhtml");
	ThreadUtil.wait(600);
	new ValidadorLogIn(defaultLocale, driver);
    }

}