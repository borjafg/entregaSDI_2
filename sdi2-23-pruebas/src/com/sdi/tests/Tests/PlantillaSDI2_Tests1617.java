package com.sdi.tests.Tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.sdi.tests.database_test.DatabaseContentsTester;
import com.sdi.tests.internationalizationTest.ValidadorLogIn;
import com.sdi.tests.internationalizationTest.ValidadorPrincipalAdministrador;
import com.sdi.tests.internationalizationTest.ValidadorPrincipalUsuario;
import com.sdi.tests.internationalizationTest.ValidadorRegistro;
import com.sdi.tests.page_objects.PO_AdminRow;
import com.sdi.tests.page_objects.PO_CreateTask;
import com.sdi.tests.page_objects.PO_EditTaskNameAndCategory;
import com.sdi.tests.page_objects.PO_HoyRow;
import com.sdi.tests.page_objects.PO_InboxRow;
import com.sdi.tests.page_objects.PO_LoginForm;
import com.sdi.tests.page_objects.PO_RegistryForm;
import com.sdi.tests.page_objects.PO_SemanaRow;
import com.sdi.tests.utils.DatabaseReload;
import com.sdi.tests.utils.DateUtil;
import com.sdi.tests.utils.Log;
import com.sdi.tests.utils.MySeleniumUtils;
import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.ThreadUtil;
import com.sdi.tests.utils.UserManager;

/*
 * Ordenamos las pruebas por el nombre del método
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantillaSDI2_Tests1617 {

    private final String defaultLocale = "es";
    private final String englishLocale = "en";

    private static WebDriver driver = getDriver();

    static String URLInterno = "http://localhost:8280/sdi2-23";
    static String URLExterno = "http://localhost:8180/sdi2-23";

    public PlantillaSDI2_Tests1617() {

    }

    public static WebDriver getDriver() {
	File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");

	FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
	FirefoxProfile firefoxProfile = new FirefoxProfile();

	return driver = new FirefoxDriver(ffBinary, firefoxProfile);
    }

    // =============================================================
    // Métodos que se ejecutan antes, durante o después de los test
    // =============================================================

    @BeforeClass
    public static void esperarDespliegue() {
	// // Esperar x segundos por el despliegue
	// int segundosEspera = 12;
	//
	// esperar(segundosEspera * 1000);
    }

    @Before
    public void navegarPaginaInicio() {
	driver.navigate().to(URLExterno);

	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(400); // Esperar antes de empezar un test
    }

    @After
    public void tearDown() {
	driver.manage().deleteAllCookies();
    }

    @AfterClass
    static public void end() {
	// Espera para que la última prueba borre las cookies
	esperar(2000);

	driver.quit();
    }

    private static void esperar(int milisegundos) {
	ThreadUtil.wait(milisegundos);
    }

    // =====================================
    // PRUEBAS
    // =====================================

    // ===============================================================
    // ======================== Administador =========================
    // ===============================================================

    /*
     * PR01: Autentificar correctamente al administrador.
     */
    @Test
    public void prueba01() {
	int tiempoVerResultadoTest = 1500;

	// (1) Iniciamos sesión como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperamos a que se cargue la página del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR02: Fallo en la autenticación del administrador por introducir mal el
     * login.
     */
    @Test
    public void prueba02() {
	int tiempoVerResultadoTest = 850;

	// (1) Rellenamos los datos del formulario
	new PO_LoginForm().completeForm(driver, "admin_", "admin");

	// (2) Esperamos a que aparezca el aviso de login incorrecto
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	esperar(tiempoVerResultadoTest);

	// (3) Comprobamos que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login__usuario_no_existe")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR03: Fallo en la autenticación del administrador por introducir mal la
     * password.
     */
    @Test
    public void prueba03() {
	int tiempoVerResultadoTest = 900;

	// (1) Rellenamos los datos del formulario
	new PO_LoginForm().completeForm(driver, "admin", "admin_");

	// (2) Esperamos a que aparezca el aviso de login incorrecto
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	esperar(tiempoVerResultadoTest);

	// (3) Comprobamos que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login__usuario_no_existe")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR04: Probar que la base de datos contiene los datos insertados con
     * conexión correcta a la base de datos.
     */
    @Test
    public void prueba04() {
	int tiempoVerResultadoTest = 900;

	// (1) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperamos a que se cargue la página principal del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (3) Esperamos a que aparezca la opción de reiniciar la base de datos
	WebElement botonReinicio = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_reinicio");

	// (4) Restauramos el contenido de la base de datos
	botonReinicio.click();

	// (5) Esperar a que aparezca el mensaje de éxito
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-info-detail");

	esperar(tiempoVerResultadoTest);

	// (6) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador__exito_reinicio_base_datos")));

	// (7) Comprobar que los datos son los que deberían ser
	try {
	    new DatabaseContentsTester().test();

	    esperar(tiempoVerResultadoTest);
	}

	catch (Exception ex) {
	    Log.error("Ha ocurrido un error al comprobar que el contenido de "
		    + "la base de datos se ha reiniciado. A continuación se "
		    + "mostrará una traza del error:");
	    Log.error(ex);

	    assertTrue(false); // Ante cualquier error el test falla
	}
    }

    /*
     * PR05: Visualizar correctamente la lista de usuarios normales.
     */
    @Test
    public void prueba05() {
	int tiempoVerResultadoTest = 800;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (3) Esperamos a que se cargue la página del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (4) Comprobamos que la tabla de usuarios tiene los datos correctos
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

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de
     * entrar con el usuario que se ha desactivado.
     */
    @Test
    public void prueba06() {
	int tiempoVerResultadoTest = 800;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Deshabilitamos ese usuario

	/*
	 * El código para deshabilitar usarios está sacado a otra clase ya que
	 * se usa en otros test y de está forma no se duplica código.
	 */

	new UserManager().disable(driver, defaultLocale, 0, "user1");

	esperar(tiempoVerResultadoTest);

	// (3) Intentamos hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (4) Comprobar que no fue posible
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	assertTrue(
		"No se encontró el mensaje de usuario deshabilitado",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login__usuario_deshabilitado")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR07: Cambiar el estado de un usuario a DISABLED a ENABLED, y tratar de
     * entrar con el usuario que se ha activado.
     * 
     * Este test es dependiente del anterior. Para que funcione hay que ejecutar
     * antes el anterior.
     */
    @Test
    public void prueba07() {
	int tiempoVerResultadoTest = 750;

	// (1) Habilitamos el usuario

	/*
	 * El código para habilitar usarios está sacado a otra clase ya que se
	 * usa en otros test y de está forma no se duplica código.
	 */

	new UserManager().enable(driver, defaultLocale, 0, "user1");

	esperar(tiempoVerResultadoTest);

	// (2) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Comprobar que se pudo hacer login
	MySeleniumUtils.waitForElementWithId(driver, "panel_categorias");

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR08: Ordenar por Login
     */
    @Test
    public void prueba08() {
	int tiempoVerResultadoTest = 700;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Hacemos login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperamos a que se cargue la tabla de usuarios
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (3) Buscamos la columna título
	WebElement titulo = MySeleniumUtils.waitForElementWithId(driver,
		"tabla_usuarios:column_login_title");

	// (4) Ordenamos los usuarios por login (ASC)
	titulo.click();

	esperar(tiempoVerResultadoTest);

	// (5) Comprobamos que los datos están ordenados
	Map<String, Object> filaAnterior = new PO_AdminRow().findRow(driver, 0);
	Map<String, Object> filaActual;

	String loginAnterior = (String) filaAnterior.get("login");
	String loginActual;

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    loginActual = (String) filaActual.get("login");

	    assertTrue("Los usuarios no están ordenados.",
		    loginAnterior.compareTo(loginActual) == -1);

	    filaAnterior = filaActual;
	    loginAnterior = loginActual;
	}

	esperar(tiempoVerResultadoTest);

	// (6) Ordenamos por login (DESC)
	titulo.click();

	esperar(tiempoVerResultadoTest);

	// (4) Comprobamos que los datos están ordenados

	/*
	 * El último usuario evaluado antes es el que aparecerá ahora en la
	 * primera fila de la tabla. Por está razón no hay que extraer el valor
	 * de la primera fila de la tabla y guardarla en la variable
	 * loginAnterior.
	 */

	for (int indexUser = 1; indexUser < 3; indexUser++) {
	    filaActual = new PO_AdminRow().findRow(driver, indexUser);
	    loginActual = (String) filaActual.get("login");

	    assertTrue("Los usuarios no están ordenados.",
		    loginAnterior.compareTo(loginActual) == 1);

	    filaAnterior = filaActual;
	    loginAnterior = loginActual;
	}

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR09: Ordenar por Email
     */
    @Test
    public void prueba09() {
	int tiempoVerResultadoTest = 750;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (3) Esperamos a que se cargue la página principal del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (4) Esperar a que se cargue la página
	WebElement columnaEmail = MySeleniumUtils.waitForElementWithId(driver,
		"tabla_usuarios:column_email_title");

	// (5) Ordenar por email (ASC)
	columnaEmail.click();

	esperar(tiempoVerResultadoTest);

	// (6) Comprobar que los datos están ordenados
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

	esperar(tiempoVerResultadoTest);

	// (7) Buscamos la columna email para volver a ordenar la lista
	columnaEmail = MySeleniumUtils.waitForElementWithId(driver,
		"tabla_usuarios:column_email_title");

	// (8) Ordenar por email (DESC)
	columnaEmail.click();

	esperar(tiempoVerResultadoTest);

	// (9) Comprobar que los datos están ordenados

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

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR10: Ordenar por Status
     */
    @Test
    public void prueba10() {
	int tiempoVerResultadoTest = 700;

	/*
	 * Se cambiará el estado de este usuario para poder ver el efecto de la
	 * ordenación. Al final del test se le vuelve a habilitar.
	 */
	String login = "user1";

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Desactivamos un usuario para ver el resultado de la ordenación
	new UserManager().disable(driver, defaultLocale, 0, login);

	esperar(tiempoVerResultadoTest);

	// (2) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (3) Esperamos a que se cargue la página principal del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios:column_status_title");

	esperar(tiempoVerResultadoTest);

	// (4) Ordenar por status (ASC)
	WebElement titulo = MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios:column_status_title");

	titulo.click();

	esperar(tiempoVerResultadoTest);

	// (5) Comprobamos que los datos están ordenados
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

	esperar(tiempoVerResultadoTest);

	// (6) Ordenar por status (DESC)
	titulo.click();

	esperar(tiempoVerResultadoTest);

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

	esperar(tiempoVerResultadoTest);

	// (9) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// (10) Volver a activar el usuario
	new UserManager().enable(driver, defaultLocale, 0, login);

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR11: Borrar una cuenta de usuario normal y datos relacionados.
     */
    @Test
    public void prueba11() {
	int tiempoVerResultadoTest = 700;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (3) Esperar a que aparezca la tabla de usuarios
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (4) Buscar el botón de eliminar usuario y hacer click
	Map<String, Object> fila = new PO_AdminRow().findRow(driver, 0);

	String login = (String) fila.get("login"); // Se usa más adelante

	// ===> (4.1) Borrar el usuario
	((WebElement) fila.get("button_delete")).click();

	// ===> (4.2) Confirmar la eliminación de la cuenta
	WebElement botonBorrado = MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:confirm_delete");

	esperar(tiempoVerResultadoTest); // Esperar antes de confirmar

	botonBorrado.click();

	// (5) Esperar a que aparezca el mensaje de éxito
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-info-detail");

	esperar(tiempoVerResultadoTest);

	// (6) Comprobar que es el mensaje adecuado
	assertTrue(
		"No se encontró el mensaje de usuario eliminado",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador__exito_borrar_usuario")));

	esperar(tiempoVerResultadoTest);

	// (7) Cerrar sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (8) Esperar a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// (9) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, login, login);

	// (10) Esperar a que aparezca el mensaje apropiado
	mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	esperar(tiempoVerResultadoTest);

	// (11) Comprobar que es el mensaje correcto
	assertTrue(
		"No se encontró el mensaje de login inválido",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_login__usuario_no_existe")));

	esperar(tiempoVerResultadoTest);
    }

    // ===============================================================
    // =========================== Anónimo ===========================
    // ===============================================================

    /*
     * PR12: Crear una cuenta de usuario normal con datos válidos.
     */
    @Test
    public void prueba12() {
	int tiempoVerResultadoTest = 650;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Pasamos a la página de registro
	WebElement enlaceRegistro = MySeleniumUtils.waitForElementWithId(
		driver, "form_menu_superior:enlace_registro");

	enlaceRegistro.click();

	// (3) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (4) Rellenamos el formulario con datos válidos
	new PO_RegistryForm().completeForm(driver, "usuario4",
		"usuario4@mail.com", "password123", "password123");

	// (5) Esperamos a que nos redireccionen a la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	// (6) Esperamos a que aparezca el mensaje adecuado
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-info-detail");

	esperar(tiempoVerResultadoTest);

	// (7) Comprobamos que el mensaje que apareció es el que debería ser
	assertTrue(
		"No se ha encontrado el mensaje de registro correcto",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"registro__exito")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR13: Crear una cuenta de usuario normal con login repetido. Prueba
     * dependiente de la prueba 12
     */
    @Test
    public void prueba13() {
	int tiempoVerResultadoTest = 650;

	// (1) Pasamos a la página de registro
	WebElement enlaceRegistro = MySeleniumUtils.waitForElementWithId(
		driver, "form_menu_superior:enlace_registro");

	enlaceRegistro.click();

	// (2) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (3) Rellenamos el formulario con un login ya usado
	new PO_RegistryForm().completeForm(driver, "usuario4",
		"usuario4@mail.com", "password123", "password123");

	// (4) Esperamos a que aparezca el mensaje apropiado
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	esperar(tiempoVerResultadoTest);

	// (5) Valimos el mensaje para asegurarnos de que es el adecuado
	assertTrue(
		"No se ha encontrado el mensaje de login ya existente.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_registro__login_ya_existe")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR14: Crear una cuenta de usuario normal con Email incorrecto.
     */
    @Test
    public void prueba14() {
	int tiempoVerResultadoTest = 700;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Pasamos a la página de registro
	WebElement enlaceRegistro = MySeleniumUtils.waitForElementWithId(
		driver, "form_menu_superior:enlace_registro");

	enlaceRegistro.click();

	// (3) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (2) Rellenamos el formulario con un email inválido
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5mailcom", "password123", "password123");

	// (3) Esperamos a que aparezca el mensaje de email inválido
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-message-error-detail");

	esperar(tiempoVerResultadoTest);

	// (4) Nos aseguramos de que ese es el mensaje que apareció
	assertTrue(
		"No se ha encontrado el mensaje de email inválido.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_registro_edicion__email_no_valido")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR15: Crear una cuenta de usuario normal con Password incorrecta.
     */
    @Test
    public void prueba15() {
	int tiempoVerResultadoTest = 650;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Cambiamos a la pestaña de registro
	WebElement botonRegistro = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:enlace_registro");

	botonRegistro.click();

	// (3) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (4) Rellenamos el formulario [caso -> contraseña con sólo números]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "123456789", "123456789");

	// (5) Esperamos a que aparezca el mensaje apropiado
	WebElement mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	esperar(tiempoVerResultadoTest);

	// (6) Comprobamos que es el mensaje correcto
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_registro_edicion__password_contenido")));

	esperar(tiempoVerResultadoTest);

	// (7) Cerramos el mensaje para que no afecte a los siguientes test
	WebElement botonCerrar = MySeleniumUtils.waitForElementWithClass(
		driver, "ui-icon ui-icon-close");

	botonCerrar.click();

	esperar(tiempoVerResultadoTest);

	// (8) Rellenamos el formulario [caso -> contraseña con sólo letras]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "asdfghjkl", "asdfghjkl");

	// (9) Esperamos a que aparezca el mensaje apropiado
	mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-messages-warn-detail");

	esperar(tiempoVerResultadoTest);

	// (10) Comprobamos que el mensaje es el que debería ser
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_registro_edicion__password_contenido")));

	esperar(tiempoVerResultadoTest);

	// (11) Cerramos el mensaje para que no afecte a los siguientes test
	botonCerrar = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-close");

	botonCerrar.click();

	esperar(tiempoVerResultadoTest);

	// (12) Rellenamos el formulario [caso -> contraseña con poca longitud]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "a1", "a1");

	// (13) Esperamos a que aparezca un mensaje
	mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-message-error-detail");

	esperar(tiempoVerResultadoTest);

	// (14) Validamos el texto del mensaje
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_registro_edicion__password")));

	esperar(tiempoVerResultadoTest);

	// (15) Vamos a login y volvemos a registro para que desaparezca el
	// mensaje de contraseña inválida

	// ===> (16.1) Ir a login
	WebElement botonLogin = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:enlace_login");

	botonLogin.click();

	// ===> (15.2) Esperar a que se cargue la página
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// ===> (15.3) Volver a registro
	botonRegistro = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:enlace_registro");

	botonRegistro.click();

	// ===> (15.4) Esperar a que se cargue la página
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (16) Rellenamos el formulario [caso -> contraseñas distintas]
	new PO_RegistryForm().completeForm(driver, "usuario5",
		"usuario5@mail.com", "asdfghjkl1234", "asdfghjkl123");

	// (17) Esperamos a que aparezca un mensaje
	mensaje = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-message-error-detail");

	esperar(tiempoVerResultadoTest);

	// (18) Nos aseguramos de que sea el mensaje apropiado
	assertTrue(
		"No se ha encontrado el mensaje de contraseña inválida.",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"error_registro_edicion__password")));

	esperar(tiempoVerResultadoTest);
    }

    // ===============================================================
    // =========================== Usuario ===========================
    // ===============================================================

    /*
     * -------------------------------------------------------------
     * --------------------------- Inbox ---------------------------
     * -------------------------------------------------------------
     */

    /*
     * PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
     * categoría y que son las que tienen que. Usar paginación navegando por las
     * tres páginas.
     */
    @Test
    public void prueba16() {
	int tiempoVerResultadoTest = 600;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página del listado de tareas de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (5) Esperamos a que se cargue la tabla de tareas de inbox
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	esperar(tiempoVerResultadoTest);

	// (6) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	// comprobamos el nombre de las primeras 8 tareas
	for (int i = 0; i < 8; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea0" + (i + 1)));
	}

	// (7) Pasamos a la segunda pestaña
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (8) Esperamos a que se carguen las tareas de tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (9) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    if (i == 0) {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea09"));
	    }

	    else {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea" + (i + 9)));
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (10) Pasamos a la tercera pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (11) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea20");

	esperar(tiempoVerResultadoTest);

	// (12) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i <= 19; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i < 4; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + (i + 17)));
	}
    }

    /*
     * PR17: Funcionamiento correcto de la ordenación por fecha planeada.
     */
    @Test
    public void prueba17() {
	int tiempoVerResultadoTest = 600;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página del listado de tareas de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (5) Esperamos a que se cargue la tabla de tareas de inbox
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	esperar(tiempoVerResultadoTest);

	// (6) Ordenamos las tareas por fecha planeada
	WebElement columnaTitulo = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:columna_planeada_titulo");

	columnaTitulo.click();

	// (7) Esperamos a que carguen las tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea18");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos los elementos de la primera pestaña y los validamos
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	Date hoy = new Date();
	Date planeada;

	for (int i = 0; i < 8; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + (i + 11)));

	    planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
		    .get("fechaPlaneada"));

	    assertTrue("La fecha planeada de la tarea no es la esperada.",
		    DateUtil.sameDay(planeada, hoy));
	}

	esperar(tiempoVerResultadoTest);

	// (9) Pasamos a la segunda pestaña
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (10) Esperamos a que se carguen las tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea06");

	esperar(tiempoVerResultadoTest);

	// (11) Extraemos la lista de tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i <= 1; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + (i + 19)));

	    planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
		    .get("fechaPlaneada"));

	    assertTrue("La fecha planeada de la tarea no es la esperada.",
		    DateUtil.sameDay(planeada, hoy));
	}

	int num = 1;
	int sum = 1;

	for (int i = 2; i < 8; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea0" + (num)));

	    planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
		    .get("fechaPlaneada"));

	    assertTrue(
		    "La fecha planeada de la tarea no es la esperada.",
		    DateUtil.sameDay(planeada, DateUtil.sumDaysToDate(hoy, sum)));

	    ++num;

	    if (i % 2 != 0) {
		sum = sum + 1;
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (12) Pasamos a la tercera pestaña de la tabla
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (13) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea10");

	esperar(tiempoVerResultadoTest);

	// (14) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	/*
	 * indices 16 y 17 ===> misma fecha planeada
	 * 
	 * indice 18 ===> fecha planeada indice 16, 17 + 1 día
	 * 
	 * indice 19 ===> fecha planeada indice 18 + 1 día
	 */

	for (int i = 16; i < 20; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	/*
	 * tareas 07, 08, 09, 10
	 */

	int numBase = 7;
	int dias = 4;

	for (int i = 0; i < 4; i++) {
	    if (i != 3) {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea0" + (numBase)));

		++numBase;
	    }

	    else {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea10"));
	    }

	    if (i < 2) {
		planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
			.get("fechaPlaneada"));

		assertTrue(
			"La fecha planeada de la tarea no es la esperada.",
			DateUtil.sameDay(planeada,
				DateUtil.sumDaysToDate(hoy, dias)));
	    }

	    else {
		++dias;

		planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
			.get("fechaPlaneada"));

		assertTrue(
			"La fecha planeada de la tarea no es la esperada.",
			DateUtil.sameDay(planeada,
				DateUtil.sumDaysToDate(hoy, dias)));
	    }
	}
    }

    /*
     * PR18: Funcionamiento correcto del filtrado.
     */
    @Test
    public void prueba18() {
	int tiempoVerResultadoTest = 600;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página del listado de tareas de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (5) Esperamos a que se cargue la tabla de tareas de inbox
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	esperar(tiempoVerResultadoTest);

	// (6) Filtramos las tareas de la tabla por su título
	WebElement campoFiltrar = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:columna_titulo_titulo:filter");

	campoFiltrar.click();
	campoFiltrar.clear();

	campoFiltrar.sendKeys("1"); // Buscamos las tareas qe contienen un "1"

	// (7) Esperamos a que se las tareas hayan sido filtradas
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	int base = 10;

	for (int i = 0; i < 8; i++) {
	    if (i == 0) {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea01"));
	    }

	    else {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea" + base));

		++base;
	    }
	}

	// (9) Pasamos a la segunda pestaña de la tabla
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (10) Esperamos a que se carguen las tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea19");

	esperar(tiempoVerResultadoTest);

	// (11) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i <= 10; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i <= 2; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    ++base;
	}
    }

    /*------------------------------------------------------------
     * --------------------------- Hoy ---------------------------
     * -----------------------------------------------------------
     */

    /*
     * PR19: Funcionamiento correcto de la ordenación por categoría.
     */
    @Test
    public void prueba19() {
	int tiempoVerResultadoTest = 600;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos al listado de tareas para hoy
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	botonHoy.click();

	// (5) Esperamos a que se haya cargado la tabla
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	// (6) Ordenamos las tareas por categoría
	WebElement tituloOrden = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:columna_categoria_titulo");

	tituloOrden.click();

	// (7) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea28");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	/*
	 * ========================================
	 * 
	 * Tres primeras:categoria1
	 * 
	 * Tres siguientes: categoria2
	 * 
	 * Dos últimas: categoria3
	 * 
	 * ========================================
	 */

	int numCat = 1;

	for (int i = 0; i < 8; i++) {
	    if (i == 3)
		numCat = 2;

	    if (i == 6)
		numCat = 3;

	    assertTrue("Las categoria de la tarea no es la que debería ser.",
		    pestaña.get(i).get("categoria")
			    .equals("Categoria" + numCat));
	}

	esperar(tiempoVerResultadoTest);

	// (9) Pasamos a la segunda pestaña de la tabla
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (10) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (11) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	numCat = 3;

	for (int i = 0; i < 8; i++) {
	    if (i <= 1) {
		assertTrue(
			"La categoria de la tarea no es la que debería ser.",
			pestaña.get(i).get("categoria")
				.equals("Categoria" + numCat));
	    }

	    else {
		assertTrue("La tarea no debería tener asignada una categoría.",
			pestaña.get(i).get("categoria").equals("----------"));
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (12) Pasamos a la tercera pestaña de la tabla
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (13) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea20");

	esperar(tiempoVerResultadoTest);

	// (14) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i < 20; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i < 4; i++) {
	    assertTrue("La tarea no debería tener una categoría asignada.",
		    pestaña.get(i).get("categoria").equals("----------"));
	}
    }

    /*
     * PR20: Funcionamiento correcto de la ordenación por fecha planeada.
     */
    @Test
    public void prueba20() {
	int tiempoVerResultadoTest = 600;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la lista de tareas para hoy
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	botonHoy.click();

	// (5) Esperamos a que se cargue la tabla de tareas
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	// (6) Ordenamos las tareas por la columna "planeada"
	WebElement tituloOrden = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:columna_planeada_titulo");

	tituloOrden.click();

	// (7) Esperamos a que las tareas se ordenen
	MySeleniumUtils.waitForElementWithText(driver, "tarea21");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	int base = 27;
	for (int i = 0; i < 4; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    assertTrue(
		    "La fecha planeada debería estar en color rojo.",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").contains(
				    "elemento_color_rojo"));

	    ++base;
	}

	base = 24;

	for (int i = 4; i <= 6; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    assertTrue(
		    "La fecha planeada debería estar en color rojo.",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").contains(
				    "elemento_color_rojo"));

	    ++base;
	}

	assertTrue(
		"El título de la tarea no es el esperado.",
		((WebElement) pestaña.get(7).get("titulo")).getText().equals(
			"tarea21"));

	assertTrue("La fecha planeada debería estar en color rojo.",
		((WebElement) pestaña.get(7).get("fechaPlaneadaWebElement"))
			.getAttribute("class").contains("elemento_color_rojo"));

	esperar(tiempoVerResultadoTest);

	// (9) Pasamos a la segunda pestaña de la tabla
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (10) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (11) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	base = 11;
	int base2 = 22;

	for (int i = 0; i < 8; i++) {
	    if (i <= 1) {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea" + base2));

		assertTrue(
			"La fecha planeada debería estar en color rojo.",
			((WebElement) pestaña.get(i).get(
				"fechaPlaneadaWebElement")).getAttribute(
				"class").contains("elemento_color_rojo"));

		++base2;
	    }

	    else {
		assertTrue("El título de la tarea no es el esperado.",
			((WebElement) pestaña.get(i).get("titulo")).getText()
				.equals("tarea" + base));

		assertTrue(
			"La fecha planeada no debería estar en color rojo.",
			((WebElement) pestaña.get(i).get(
				"fechaPlaneadaWebElement")).getAttribute(
				"class").equals(""));// color negro

		++base;
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (12) Pasamos a la tercera pestaña de la tabla
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (13) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea20");

	// (14) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i <= 19; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	base = 17;

	for (int i = 0; i < 4; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    assertTrue(
		    "La fecha planeada no debería estar en color rojo.",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").equals(""));// color negro

	    ++base;
	}
    }

    /*
     * PR21: Comprobar que las tareas que NO están en rojo son las de hoy y
     * además las que deben ser.
     */
    @Test
    public void prueba21() {
	int tiempoVerResultadoTest = 620;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a página de tareas para hoy
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	botonHoy.click();

	// (5) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	/*
	 * =============================================
	 * 
	 * tarea11 - tarea20 ===> planeadas para hoy,
	 * 
	 * Otras tareas ===> restrasadas
	 * 
	 * =============================================
	 */

	// (6) Extraemos las tareas de la tabla
	Date hoy = new Date();
	Date planeada;

	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	int base = 11;

	for (int i = 0; i < 8; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
		    .get("fechaPlaneada"));

	    assertTrue("La fecha planeada de la tarea no es la esperada.",
		    DateUtil.sameDay(planeada, hoy));

	    assertTrue(
		    "La fecha planeada debería estar en color rojo",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").equals(""));// color negro

	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (7) Pasamos a la segunda pestaña de la tabla
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	// (8) Pasamos a la segunda pestaña
	botonSig.click();

	// (9) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea26");

	esperar(tiempoVerResultadoTest);

	// (10) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i <= 9; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i <= 1; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    planeada = DateUtil.convertStringToDate((String) pestaña.get(i)
		    .get("fechaPlaneada"));

	    assertTrue("La fecha planeada de la tarea debería estar en rojo.",
		    DateUtil.sameDay(planeada, hoy));

	    assertTrue(
		    "La fecha planeada de la tarea no debería estar en rojo.",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").equals(""));// color negro

	    ++base;
	}

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR22: Comprobar que las tareas retrasadas SÍ están en rojo y son las que
     * deben ser.
     */
    @Test
    public void prueba22() {
	int tiempoVerResultadoTest = 660;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de tareas para hoy
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	botonHoy.click();

	// (5) Esperamos a que se cargue el listado de tareas
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	/*
	 * =============================================
	 * 
	 * tarea11 - tarea20 ===> planeadas para hoy,
	 * 
	 * Otras tareas ===> restrasadas
	 * 
	 * =============================================
	 */

	// (6) Pasamos a la segunda pestaña
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (7) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea26");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 10; i < 16; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	int base = 21;

	for (int i = 0; i < 6; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    assertTrue(
		    "La fecha planeada de la tarea debería estar en rojo.",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").contains(
				    "elemento_color_rojo"));

	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (9) Pasamos a la tercera pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (10) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea30");

	esperar(tiempoVerResultadoTest);

	// (11) Extraemos las tareas del la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i <= 19; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i < 4; i++) {
	    assertTrue("El título de la tarea no es el esperado.",
		    ((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea" + base));

	    assertTrue(
		    "La fecha planeada de la tarea debería estar en rojo",
		    ((WebElement) pestaña.get(i).get("fechaPlaneadaWebElement"))
			    .getAttribute("class").contains(
				    "elemento_color_rojo"));

	    ++base;
	}
    }

    /*
     * --------------------------------------------------------------
     * --------------------------- Semana ---------------------------
     * --------------------------------------------------------------
     */

    /*
     * PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que
     * son las que deben ser.
     */
    @Test
    public void prueba23() {
	int tiempoVerResultadoTest = 640;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	// (2) Iniciamos sesión con un usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	WebElement botonSemana = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos al listado de tareas para esta semana
	botonSemana.click();

	// (5) Esperamos a que se cargue el listado de tareas para la semana
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_semana");

	esperar(tiempoVerResultadoTest);

	// (6) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	Date hoy = new Date();
	int base = 1;
	int sum = 1;

	for (int i = 0; i < 8; i++) {
	    assertTrue("titulo no coincide", pestaña.get(i).get("titulo")
		    .equals("tarea0" + base));

	    assertTrue("Categoria esta en rojo",
		    ((WebElement) pestaña.get(i).get("categoriaWebElement"))
			    .getAttribute("class").equals(""));

	    assertTrue("la fecha no es la correcta",
		    DateUtil.sameDay(DateUtil
			    .convertStringToDate((String) pestaña.get(i).get(
				    "fechaPlaneada")), DateUtil.sumDaysToDate(
			    hoy, sum)));

	    if (i % 2 != 0) {
		++sum;
	    }

	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (7) Pasamos a la segunda pestaña de la tabla de tareas
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (8) Esperamos a que se carguen las tareas de la siguiente pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (9) Extraemos las tareas de la tabla de tareas
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	Date fechaPlaneada;

	for (int i = 0; i < 8; i++) {
	    fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
		    .get(i).get("fechaPlaneada"));

	    if (i == 0) {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea0" + base));

		assertTrue(
			"La fecha planeada de la tarea no es la correcta",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, 5)));
	    }

	    else {
		if (i == 1) {
		    assertTrue(
			    "La fecha planeada de la tarea no es la correcta",
			    DateUtil.sameDay(fechaPlaneada,
				    DateUtil.sumDaysToDate(hoy, 6)));
		}

		else {
		    assertTrue(
			    "El título de la tarea no es el que debería ser.",
			    pestaña.get(i).get("titulo").equals("tarea" + base));

		    assertTrue(
			    "La fecha planeada de la tarea no es la correcta",
			    DateUtil.sameDay(fechaPlaneada, hoy));
		}
	    }

	    assertTrue("La categoría no debería estar en color rojo",
		    ((WebElement) pestaña.get(i).get("categoriaWebElement"))
			    .getAttribute("class").equals(""));

	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (10) Pasamos a la tercera pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (11) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea24");

	esperar(tiempoVerResultadoTest);

	// (12) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i < 20; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i <= 3; i++) {
	    assertTrue("El nombre de la categoría no debería estar en rojo.",
		    ((WebElement) pestaña.get(i).get("categoriaWebElement"))
			    .getAttribute("class").equals(""));

	    assertTrue("El título de la tarea no es el que debería ser.",
		    pestaña.get(i).get("titulo").equals("tarea" + base));

	    fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
		    .get(i).get("fechaPlaneada"));

	    assertTrue("La fecha planeada de la tarea no es correcta",
		    DateUtil.sameDay(fechaPlaneada, hoy));

	    ++base;
	}

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR24: Funcionamiento correcto de la ordenación por día.
     */
    @Test
    public void prueba24() {
	int tiempoVerResultadoTest = 650;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión con un usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	WebElement botonSemana = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página del listado de tareas para la semana
	botonSemana.click();

	// (5) Esperamos a que se cargue la lista de tareas
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_semana");

	esperar(tiempoVerResultadoTest);

	// (6) Hacemos click en la columna "planeada" para ordenar las tareas
	WebElement botonPlaneada = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:columna_planeada_titulo");

	botonPlaneada.click();

	// (7) Esperamos a que las tareas se reordenen
	MySeleniumUtils.waitForElementWithText(driver, "tarea21");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	Date hoy = new Date();

	int base = 27;
	Date fechaPlaneada;

	for (int i = 0; i < 8; i++) {
	    if (i <= 3) {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, -4)));

		++base;
	    }

	    else if (i > 3 && i <= 6) {
		if (base == 31) {
		    base = 24;
		}

		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, -3)));

		++base;
	    }

	    else {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea21"));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, -2)));
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (9) Pasamos a la segunda pestaña
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (10) Esperamos a que se cargue la lista de tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (11) Extraer las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	base = 22;

	for (int i = 0; i < 8; i++) {
	    if (i <= 1) {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser.",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, -2)));

		++base;
	    }

	    else {
		if (base == 24) {
		    base = 11;
		}

		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue("La fecha para la que está planeada la tarea no es "
			+ "la que debería ser.",
			DateUtil.sameDay(fechaPlaneada, hoy));

		++base;
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (12) Pasamos a la tercera pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (13) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea04");

	esperar(tiempoVerResultadoTest);

	// (14) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i < 24; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	base = 17;
	int sum = 1;

	for (int i = 0; i < 8; i++) {
	    if (i <= 3) {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue("La fecha para la que está planeada la tarea no es "
			+ "la que debería ser.",
			DateUtil.sameDay(fechaPlaneada, hoy));

		++base;
	    }

	    else {
		if (base == 21) {
		    base = 1;
		}

		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea0" + (base)));

		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser.",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, sum)));

		if (i % 2 != 0) {
		    ++sum;
		}

		++base;
	    }
	}

	esperar(tiempoVerResultadoTest);

	// (15) Pasamos a la cuarta pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (16) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea10");

	esperar(tiempoVerResultadoTest);

	// (17) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 24; i < 30; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	base = 5;
	sum = 3;

	for (int i = 0; i <= 5; i++) {
	    if (i == 5) {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));
	    }

	    else {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea0" + (base)));

		++base;
	    }

	    if (i != 5) {
		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser.",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, sum)));

		if (i % 2 != 0) {
		    ++sum;
		}
	    }

	    else {
		fechaPlaneada = DateUtil.convertStringToDate((String) pestaña
			.get(i).get("fechaPlaneada"));

		assertTrue(
			"La fecha para la que está planeada la tarea no es "
				+ "la que debería ser.",
			DateUtil.sameDay(fechaPlaneada,
				DateUtil.sumDaysToDate(hoy, 6)));
	    }
	}

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR25: Funcionamiento correcto de la ordenación por título.
     */
    @Test
    public void prueba25() {
	int tiempoVerResultadoTest = 650;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de tareas para la semana
	WebElement botonSemana = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:semana");

	botonSemana.click();

	// (5) Esperamos a que se cargue la lista de tareas para la semana
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_semana");

	esperar(tiempoVerResultadoTest);

	// (6) Ordenamos las tareas de la tabla por título

	/*
	 * En este paso habría que ordenar las tareas por título, pero la tabla
	 * de tareas está configurada para ordenar inicialmente las tareas por
	 * título, así que no es necesario hacer click para ordenarlas.
	 */

	// (7) Extraemos las tareas de la tabla
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	int base = 1;

	for (int i = 0; i < 8; i++) {
	    assertTrue("el nombre es incorrecto", pestaña.get(i).get("titulo")
		    .equals("tarea0" + (base)));
	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (8) Pasamos a la segunda pestaña
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (9) Esperamos a que se cargue las tareas de la siguiente pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (10) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    if (i == 0) {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea0" + (base)));
	    }

	    else {
		assertTrue("El título de la tarea no es el que debería ser.",
			pestaña.get(i).get("titulo").equals("tarea" + (base)));
	    }

	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (11) Pasamos a la tercera pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (12) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea24");

	esperar(tiempoVerResultadoTest);

	// (13) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i < 24; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("El título de la tarea no es el que debería ser.",
		    pestaña.get(i).get("titulo").equals("tarea" + (base)));

	    ++base;
	}

	esperar(tiempoVerResultadoTest);

	// (14) Pasamos a la cuarta pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (15) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea30");

	esperar(tiempoVerResultadoTest);

	// (16) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 24; i < 30; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i < 6; i++) {
	    assertTrue("El título de la tarea no es el que debería ser.",
		    pestaña.get(i).get("titulo").equals("tarea" + (base)));

	    ++base;
	}

	esperar(tiempoVerResultadoTest);
    }

    /*
     * --------------------------------------------------------------
     * --------------------- Filtrar terminadas ---------------------
     * --------------------------------------------------------------
     */

    /*
     * PR26: Finalizar una tarea, inhabilitar el filtro de tareas terminadas, ir
     * a la página donde está la tarea terminada y comprobar que se muestra.
     */
    @Test
    public void prueba26() {
	int tiempoVerResultadoTest = 800;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (5) Esperamos a que se cargue la página de inbox
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	esperar(tiempoVerResultadoTest);

	// (6) Buscamos la primera tarea [ título = tarea01 ]
	Map<String, Object> tarea01 = new PO_InboxRow().findRow(driver, 0);

	// (7) Comprobamos que es esa tarea
	assertTrue("La primera tarea de inbox debería tener ser la que tiene "
		+ "el título tarea01.", ((WebElement) tarea01.get("titulo"))
		.getText().equals("tarea01"));

	esperar(tiempoVerResultadoTest);

	// (8) Finalizamos la tarea
	((WebElement) tarea01.get("fechaFinalizar")).click();

	// (9) Comprobamos que aparece el mensaje de éxito
	WebElement mensajeExito = MySeleniumUtils.waitForElementWithClass(
		driver, "ui-messages-info-detail");

	assertTrue(
		"No se encontró el mensaje de éxito al cambiar de usuario",
		mensajeExito.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"listar_tareas__exito_finalizar")));

	esperar(tiempoVerResultadoTest);

	// (10) Volvemos al menú principal
	WebElement botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	botonVolver.click();

	// (11) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (12) Indicamos que queremos ver las tareas finalizadas
	WebElement checkTerminadas = MySeleniumUtils.waitForElementWithId(
		driver, "form_user:check_terminadas");

	checkTerminadas.click();

	// (13) Volvemos al listado de tareas de inbox
	botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	esperar(tiempoVerResultadoTest);

	botonInbox.click();

	// (14) Esperamos a que se cargue el listado
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	esperar(tiempoVerResultadoTest);

	/*
	 * -------------------------------------------------------------
	 * 
	 * (15) Ordenamos por título. Hacemos click dos veces.
	 * 
	 * ==> Con el segundo click las tareas terminadas aparecerán las
	 * primeras de la tabla
	 */

	// ---> (15.1) Primer click
	WebElement titleOrder = MySeleniumUtils.findInElementUsingClass(driver,
		"form_user:tabla_tareas:columna_titulo_titulo",
		"ui-sortable-column-icon");

	titleOrder.click();

	esperar(tiempoVerResultadoTest);

	// ---> (15.2) Segundo click
	titleOrder = MySeleniumUtils.findInElementUsingClass(driver,
		"form_user:tabla_tareas:columna_titulo_titulo",
		"ui-sortable-column-icon");

	titleOrder.click();

	// ---> (15.3) Esperamos a que se carguen las tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea14");

	esperar(tiempoVerResultadoTest);

	/*
	 * -------------------------------------------------------------
	 */

	// (16) Extraemos la primera tarea de la tabla [ título = a ]
	Date hoy = new Date();

	tarea01 = new PO_InboxRow().findRow(driver, 0);

	assertTrue("El título no coincide con el de la tarea finalizada.",
		((WebElement) tarea01.get("titulo")).getText()
			.equals("tarea01"));

	assertTrue("La fecha de finalización no coincide con el de la tarea "
		+ "finalizada.", DateUtil.sameDay(DateUtil
		.convertStringToDate((String) tarea01.get("fechaFinalizada")),
		hoy));

	assertTrue("El título de la tarea no es de color verde.",
		((WebElement) tarea01.get("titulo")).getAttribute("class")
			.contains("elemento_color_verde"));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * -------------------------------------------------------------
     * ----------------------- Añadir tareas -----------------------
     * -------------------------------------------------------------
     */

    /*
     * PR27: Crear una tarea sin categoría y comprobar que se muestra en la
     * lista Inbox.
     */
    @Test
    public void prueba27() {
	int tiempoVerResultadoTest = 750;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de creación de tareas
	WebElement botonCrear = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:menu_crear_tarea");

	botonCrear.click();

	// (5) Esperamos a que se cargue la página de creación de tareas
	MySeleniumUtils
		.waitForElementWithId(driver, "form_usuario:boton_crear");

	esperar(tiempoVerResultadoTest);

	// (6) Completamos el formulario de creación de la tarea
	new PO_CreateTask().completeFormWithoutCalendar(driver, "a", "a", 0, 1);

	/*
	 * (7) Se creó sin categoría. Esperamos a que se cargue la página
	 * correspondiente.
	 * 
	 * ==> Se redireccionará automáticamente a la página de "inbox" porque
	 * la tarea no tiene una categoría asginada.
	 */

	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	// (8) Extraemos la primera tarea de la tabla y la validamos
	Map<String, Object> tareaA = new PO_InboxRow().findRow(driver, 0);

	assertTrue("El nombre de la tarea no coincide con el de la creada.",
		((WebElement) tareaA.get("titulo")).getText().equals("a"));

	assertTrue("Los comentarios no coinciden con los de la creada.", tareaA
		.get("comentario").equals("a"));

	Date hoy = new Date();

	assertTrue(
		"La fecha de creación no coincide con la de la tarea creada.",
		DateUtil.sameDay(DateUtil.convertStringToDate((String) tareaA
			.get("fechaCreacion")), hoy));

	assertTrue("La fecha planeada no coincide con la de la tarea creada.",
		DateUtil.sameDay(DateUtil.convertStringToDate((String) tareaA
			.get("fechaPlaneada")), DateUtil.sumDaysToDate(hoy, 1)));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR28: Crear una tarea con categoría "categoria1" y fecha planeada "Hoy" y
     * comprobar que se muestra en la lista "Hoy".
     */
    @Test
    public void prueba28() {
	int tiempoVerResultadoTest = 750;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de creación de tareas
	WebElement botonCrear = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:menu_crear_tarea");

	botonCrear.click();

	// (5) Esperamos a que se cargue la página de creación de tareas
	MySeleniumUtils
		.waitForElementWithId(driver, "form_usuario:boton_crear");

	esperar(tiempoVerResultadoTest);

	// (6) Creamos un nueva tarea para hoy con categoría
	new PO_CreateTask().completeFormWithoutCalendar(driver, "a", "a", 1, 0);

	/*
	 * (7) Se creó con categoría para esta hoy. Esperamos a que se cargue la
	 * página correspondiente.
	 * 
	 * ==> Se redireccionará automáticamente a la página de "hoy" porque la
	 * tarea está planeada para hoy y tiene una categoría asginada.
	 */

	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	// (8) Comprobamos que se ha creado la tarea
	Map<String, Object> tareaA = new PO_HoyRow().findRow(driver, 0);

	assertTrue("El nombre de la tarea no coincide con el de la creada.",
		(((WebElement) tareaA.get("titulo")).getText().equals("a")));

	assertTrue("Los comentarios no coinciden con los de la creada.", tareaA
		.get("comentario").equals("a"));

	Date hoy = new Date();

	assertTrue(
		"La fecha de creación no coincide con la de la tarea creada.",
		DateUtil.sameDay(DateUtil.convertStringToDate((String) tareaA
			.get("fechaCreacion")), hoy));

	assertTrue("La fecha planeada no coincide con la de la tarea creada.",
		DateUtil.sameDay(DateUtil.convertStringToDate((String) tareaA
			.get("fechaPlaneada")), hoy));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior
     * a Hoy y comprobar que se muestra en la lista Semana.
     */
    @Test
    public void prueba29() {
	int tiempoVerResultadoTest = 750;

	// (1) Restauramos el contenido la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de creación de tareas
	WebElement botonCrear = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:menu_crear_tarea");

	botonCrear.click();

	// (5) Esperamos a que se cargue la página
	MySeleniumUtils
		.waitForElementWithId(driver, "form_usuario:boton_crear");

	esperar(tiempoVerResultadoTest);

	// (6) Completamos el formulario de creación de una tarea
	new PO_CreateTask().completeFormWithoutCalendar(driver, "a", "a", 1, 6);

	/*
	 * (7) Se creó con categoría para esta semana. Esperamos a que se cargue
	 * la página correspondiente.
	 * 
	 * ==> Se redireccionará automáticamente a la página de "semana" porque
	 * la tarea está planeada para esta semana y tiene una categoría
	 * asginada.
	 */

	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_semana");

	esperar(tiempoVerResultadoTest);

	// (8) Sacamos la primera fila (la tarea que creamos antes)
	Map<String, Object> tareaA = new PO_SemanaRow().findRow(driver, 0);

	// (9) Comprobamos que cada campo tiene el valor adecuado
	assertTrue("El título no coincide con el de la tarea creada.", tareaA
		.get("titulo").equals("a"));

	assertTrue("Los comentarios no coinciden con los de la tarea creada.",
		tareaA.get("comentario").equals("a"));

	Date hoy = new Date();

	assertTrue("La fecha de creación no coincide con la de la tarea que "
		+ "se acaba de crear.",
		DateUtil.sameDay(DateUtil.convertStringToDate((String) tareaA
			.get("fechaCreacion")), hoy));

	assertTrue("La fecha planeada no coincide con la de la tarea que "
		+ "se acaba de crear.", DateUtil.sameDay(DateUtil
		.convertStringToDate((String) tareaA.get("fechaPlaneada")),
		DateUtil.sumDaysToDate(hoy, 6)));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * -------------------------------------------------------------
     * ----------------------- Editar tareas -----------------------
     * -------------------------------------------------------------
     */

    /*
     * PR30: Editar el nombre, y categoría de una tarea (se le cambia a
     * categoría1) de la lista Inbox y comprobar que las tres pseudolista se
     * refresca correctamente.
     */
    @Test
    public void prueba30() {
	int tiempoVerResultadoTest = 850;

	// (1) Reiniciamos la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesión como usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la página de tareas de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (5) Esperamos a que se cargue la tabla de tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea08");

	esperar(tiempoVerResultadoTest);

	// (6) Extraemos la primera tarea de la tabla
	Map<String, Object> tarea01 = new PO_InboxRow().findRow(driver, 0);

	((WebElement) tarea01.get("titulo")).click();

	// (7) Esperamos a que se cargue la página de edición de la tarea
	MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:boton_editar");

	esperar(tiempoVerResultadoTest);

	// (8) Editamos los datos de la tarea
	new PO_EditTaskNameAndCategory().completeForm(driver, "a", 1);

	// (9) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (10) Volvemos a la página de inbox
	botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (11) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea08");

	esperar(tiempoVerResultadoTest);

	// (12) Comprobamos que la tarea no está en la primera pestaña
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea editada [nuevo título = a] no debería estar "
		    + "en la primera pestaña de inbox", !((WebElement) pestaña
		    .get(i).get("titulo")).getText().equals("a"));
	}

	esperar(tiempoVerResultadoTest);

	// (13) Pasamos a la segunda pestaña de la tabla de inbox
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (14) Esperamos a que se carguen las tareas de la segunda pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	esperar(tiempoVerResultadoTest);

	// (15) Comprobamos que la tarea no está en la segunda pestaña
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea editada [nuevo título = a] no debería estar "
		    + "en la segunda pestaña de inbox", !((WebElement) pestaña
		    .get(i).get("titulo")).getText().equals("a"));
	}

	esperar(tiempoVerResultadoTest);

	// (16) Pasamos a la tercera pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	botonSig.click();

	// (17) Esperamos a que se carguen las tareas de la tercera pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea20");

	esperar(tiempoVerResultadoTest);

	// (18) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i <= 18; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	for (int i = 0; i <= 2; i++) {
	    assertTrue("La tarea editada [nuevo título = a] no debería estar "
		    + "en la tercera pestaña de inbox", !((WebElement) pestaña
		    .get(i).get("titulo")).getText().equals("a"));
	}

	esperar(tiempoVerResultadoTest);

	// (19) Volvemos a la página principal del usuario
	WebElement botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	botonVolver.click();

	// (20) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (21) Pasamos al listado de tareas para hoy
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	botonHoy.click();

	// (22) Esperamos a que se cargue el listado de tareas para hoy
	MySeleniumUtils.waitForElementWithText(driver, "tarea18");

	esperar(tiempoVerResultadoTest);

	// (23) Ordenamos la lista por categoría
	WebElement columCateg = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:tabla_tareas:columna_categoria_titulo");

	columCateg.click();

	// (24) Esperamos a que se ordene la tabla
	MySeleniumUtils.waitForElementWithText(driver, "Categoria3");

	esperar(tiempoVerResultadoTest);

	// (25) Sólo miramos las tareas con categoria1
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 2; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i < 2; i++) {
	    assertTrue("La tarea editada [nuevo título = a] no debería estar "
		    + "en la lista de tareas para hoy.", !((WebElement) pestaña
		    .get(i).get("titulo")).getText().equals("a"));
	}

	esperar(tiempoVerResultadoTest);

	// (26) Volvemos a la página principal del usuario
	botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	botonVolver.click();

	// (27) Esperamos a que se cargue la página principal del usuario
	WebElement botonSemana = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (28) Pasamos al listado de tareas para esta semana
	botonSemana.click();

	// (29) Esperamos a que se cargue el listado de tareas para la semana
	MySeleniumUtils.waitForElementWithText(driver, "tarea08");

	esperar(tiempoVerResultadoTest);

	// (30) La tarea estará en la primera fila de la tabla
	Map<String, Object> tareaA = new PO_SemanaRow().findRow(driver, 0);

	assertTrue("La tarea no está en la posición que debería dentro de la "
		+ "lista de tareas para la semana", tareaA.get("titulo")
		.equals("a"));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una
     * tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan
     * correctamente.
     */
    @Test
    public void prueba31() {
	int tiempoVerResultadoTest = 850;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (2) Iniciamos sesion como usuario
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (4) Pasamos a la lista de tareas para hoy
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	botonHoy.click();

	// (5) Esperamos a que se cargue la lista de tareas para hoy
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	// (6) Pasamos a la segunda pestaña de la lista
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoVerResultadoTest);

	botonSig.click();

	// (7) Esperamos a que se carguen los elementos de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea26");

	esperar(tiempoVerResultadoTest);

	// (8) Extraemos la tarea21 (posición 10 en la tabla)
	Map<String, Object> tarea21 = new PO_HoyRow().findRow(driver, 10);

	assertTrue("La tarea con título \"tarea21\" no está en la posición en"
		+ " la que debería estar en la tabla de tareas para hoy.",
		((WebElement) tarea21.get("titulo")).getText()
			.equals("tarea21"));

	esperar(tiempoVerResultadoTest);

	// (9) Hacemos click en su título para editarla
	WebElement titulo = (WebElement) tarea21.get("titulo");

	esperar(tiempoVerResultadoTest);

	titulo.click();

	// (10) Esperamos a que se cargue la página de edición de la tarea
	MySeleniumUtils.waitForElementWithId(driver,
		"form_usuario:boton_editar");

	esperar(tiempoVerResultadoTest);

	// (11) Modificamos los datos de la tarea
	new PO_EditTaskNameAndCategory().completeForm(driver, "a", 0);

	// (12) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (13) Pasamos al listado de tareas de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	esperar(tiempoVerResultadoTest);

	botonInbox.click();

	// (14) Esperamos a que se cargue el listado de tareas
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	esperar(tiempoVerResultadoTest);

	// (15) Comprobamos que la tarea está en el listado
	tarea21 = new PO_InboxRow().findRow(driver, 0);

	assertTrue("La tarea no aparece modificada en inbox en la posición en "
		+ "la que debería estar", ((WebElement) tarea21.get("titulo"))
		.getText().equals("a"));

	esperar(tiempoVerResultadoTest);

	// (16) Volvemos a la página principal del usuario
	WebElement botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	botonVolver.click();

	// (17) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (18) Pasamos al listado de tareas para hoy
	botonHoy = MySeleniumUtils
		.waitForElementWithId(driver, "form_user:hoy");

	esperar(tiempoVerResultadoTest);

	botonHoy.click();

	// (19) Esperamos a que se cargue la lista de tareas de hoy
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_hoy");

	esperar(tiempoVerResultadoTest);

	// (20) Comprobamos que la tarea está en el listado
	tarea21 = new PO_HoyRow().findRow(driver, 0);

	assertTrue("El nombre de la tarea modificada no es el que debería ser "
		+ "o la tarea no está en la posición correcta de la tabla.",
		((WebElement) tarea21.get("titulo")).getText().equals("a"));

	assertTrue("La categoría de la tarea modificada no es el que debería"
		+ " ser o la tarea no está en la posición correcta de la "
		+ "tabla.", tarea21.get("categoria").equals("----------"));

	esperar(tiempoVerResultadoTest);

	// (21) Volvemos a la página principal del usuario
	botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	botonVolver.click();

	// (22) Esperamos a que se cargue la página principal del usuario
	WebElement botonSemana = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (23) Pasamos al listado de tareas de la semana
	botonSemana.click();

	// (24) Esperamos a que se cargue la tabla de tareas para la semana
	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_semana");

	esperar(tiempoVerResultadoTest);

	// (25) La tarea editada debería estar en la posición 29 de la tabla
	tarea21 = new PO_SemanaRow().findRow(driver, 0);

	esperar(tiempoVerResultadoTest);

	assertTrue("El nombre de la tarea modificada no es el que debería ser "
		+ "o la tarea no está en la posición correcta de la tabla.",
		tarea21.get("titulo").equals("a"));

	assertTrue("La categoría de la tarea modificada no es el que debería"
		+ " ser o la tarea no está en la posición correcta de la "
		+ "tabla.", tarea21.get("categoria").equals("----------"));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las
     * tres pseudolistas.
     */
    @Test
    public void prueba32() {
	int tiempoEsperaVerTest = 850;

	// (1) Restablecemos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoEsperaVerTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la vista
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	esperar(tiempoEsperaVerTest);

	// (4) Pasamos a la vista de tareas de inbox
	botonInbox.click();

	MySeleniumUtils.waitForElementWithId(driver, "titulo_tareas_inbox");

	// (5) Hacemos click clicamos para pasar a la siguiente pestaña
	WebElement botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (6) Esperamos a que se carguen los elementos de la segunda pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	// (7) En esa pestaña sacamos todas las tareas que hay
	List<Map<String, Object>> pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	// (8) Finalizar la tarea con título [tarea11]
	assertTrue("No se ha encontrado la tarea con titulo [tarea11] en la "
		+ "segunda fila de la segunda pestaña de la lista de "
		+ "tareas de inbox.", ((WebElement) pestaña.get(2)
		.get("titulo")).getText().equals("tarea11"));

	esperar(tiempoEsperaVerTest);

	((WebElement) pestaña.get(2).get("fechaFinalizar")).click();

	// (9) Al haber hecho click el listado se habrá refrescado, por
	// lo que hay que esperar a que termine de cargarse
	MySeleniumUtils.waitForElementWithText(driver, "tarea08");

	// -------------------------------
	// ------------ Inbox ------------
	// -------------------------------

	// (10) Vamos a la segunda pestaña (al actualizarse la tabla volvimos
	// automáticamente a la primera)
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (11) Esperamos a que se carguen los elementos de la segunda pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	// (12) Extraemos las filas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_InboxRow().findRow(driver, i));
	}

	// (13) Comprobamos que ahora el elemento de la segunda fila ya no
	// es el que tiene de título [tarea11]
	assertTrue(
		"nombre no coincide",
		!((WebElement) pestaña.get(2).get("titulo")).getText().equals(
			"tarea11"));

	// (14) Salimos de la pseudolista de inbox
	WebElement botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	esperar(tiempoEsperaVerTest);

	botonVolver.click();

	// (15) Esperamos a que se cargue la página principal del usuario
	WebElement botonHoy = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:hoy");

	esperar(tiempoEsperaVerTest);

	// -------------------------------
	// ------------- Hoy -------------
	// -------------------------------

	// (16) Pasamos al listado de tareas para hoy y esperamos a que
	// se haya cargado
	botonHoy.click();

	MySeleniumUtils.waitForElementWithText(driver, "tarea18");

	// (17) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para hoy.",
		    !((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea11"));
	}

	// (18) Pasamos a la siguiente pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (19) Esperamos a que se carguen los elementos
	MySeleniumUtils.waitForElementWithText(driver, "tarea26");

	// (20) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para hoy.",
		    !((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea11"));
	}

	// (21) Pasamos a la siguiente pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (22) Esperamos a que se carguen las tareas de la tabla
	MySeleniumUtils.waitForElementWithText(driver, "tarea26");

	// (23) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i <= 18; i++) {
	    pestaña.add(new PO_HoyRow().findRow(driver, i));
	}

	for (int i = 0; i < 3; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para hoy.",
		    !((WebElement) pestaña.get(i).get("titulo")).getText()
			    .equals("tarea11"));
	}

	// (24) Salimos de la pseudolista de hoy
	botonVolver = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:boton_volver");

	esperar(tiempoEsperaVerTest);

	botonVolver.click();

	// (25) Esperamos a que se cargue la página principal del usuario
	WebElement botonSemana = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:semana");

	esperar(tiempoEsperaVerTest);

	// --------------------------------
	// ------------ Semana ------------
	// --------------------------------

	// (26) Vamos a la página de tareas para la semana
	botonSemana.click();

	// (27) Esperamos a que se cargue la lista de tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea08");

	// (28) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 0; i < 8; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para la semana.", !pestaña.get(i).get("titulo")
		    .equals("tarea11"));
	}

	// (29) Pasamos a la siguiente pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (30) Esperamos a que se carguen las tareas de esa pestaña
	MySeleniumUtils.waitForElementWithText(driver, "tarea16");

	// (31) Extraemos todas las filas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 8; i < 16; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para la semana.", !pestaña.get(i).get("titulo")
		    .equals("tarea11"));
	}

	// (32) Pasamos a la siguiente pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (33) Esperamos a que se carguen las tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea24");

	// (34) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 16; i < 24; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i < 8; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para la semana.", !pestaña.get(i).get("titulo")
		    .equals("tarea11"));
	}

	// (35) Pasamos a la siguiente pestaña
	botonSig = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-icon ui-icon-seek-next");

	esperar(tiempoEsperaVerTest);

	botonSig.click();

	// (36) Esperamos a que se carguen las tareas
	MySeleniumUtils.waitForElementWithText(driver, "tarea30");

	// (37) Extraemos las tareas de la tabla
	pestaña = new ArrayList<Map<String, Object>>();

	for (int i = 24; i < 29; i++) {
	    pestaña.add(new PO_SemanaRow().findRow(driver, i));
	}

	for (int i = 0; i <= 4; i++) {
	    assertTrue("La tarea11 no debería aparecer en el listado de "
		    + "tareas para la semana.", !pestaña.get(i).get("titulo")
		    .equals("tarea11"));
	}

	esperar(tiempoEsperaVerTest);
    }

    /*
     * ---------------------------------------------------------------
     * ----------------------- Cerrar sesiones -----------------------
     * ---------------------------------------------------------------
     */

    /*
     * PR33: Salir de sesión desde cuenta de administrador.
     */
    @Test
    public void prueba33() {
	int tiempoVerResultadoTest = 1300;

	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Comprobamos que nos hemos logeado
	WebElement elemento = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-datatable-header ui-widget-header ui-corner-top");

	assertTrue(
		"No se ha encontrado el nombre de la tabla",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"administrador__titulo_tabla_usuarios")));

	esperar(tiempoVerResultadoTest);

	// (3) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (4) Esperamos a que se cargue la página
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	// (5) Comprobamos que hemos salido a la pestaña de login
	elemento = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-panel-title");

	assertTrue(
		"No se ha encontrado el título de la página login",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"login__titulo_panel")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR34: Salir de sesión desde cuenta de usuario normal.
     */
    @Test
    public void prueba34() {
	int tiempoVerResultadoTest = 1300;

	// (1) Reiniciamos la base de datos
	new DatabaseReload().reload(driver);

	esperar(tiempoVerResultadoTest);

	// (1) Hacer login como administrador
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (2) Comprobamos que nos hemos logeado
	WebElement elemento = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-panel-title");

	assertTrue(
		"No se ha encontrado el título de la tabla de categorías "
			+ "del sistema.",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"principal_usuario__titulo_panel")));

	esperar(tiempoVerResultadoTest);

	// (3) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (4) Esperamos a que se cargue la página
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	// (5) Comprobamos que hemos salido a la pestaña de login
	elemento = MySeleniumUtils.waitForElementWithClass(driver,
		"ui-panel-title");

	assertTrue(
		"No se ha el título de la página de login.",
		elemento.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"login__titulo_panel")));

	esperar(tiempoVerResultadoTest);
    }

    /*
     * -----------------------------------------------------------------
     * --------------------- Internacionalización ----------------------
     * -----------------------------------------------------------------
     */

    /*
     * PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas
     * vistas).
     */
    @Test
    public void prueba35() {
	int tiempoVerResultadoTest = 1200;

	// Paso inicial ==> Restaurar el contenido de la base de datos
	new DatabaseReload().reload(driver);

	// ===================================
	// (1) Comprobamos idioma por defecto
	// ===================================

	// (1.01) Comprobamos el idioma en la pagina de login
	new ValidadorLogIn("es", driver).comprobarTextos();

	// (1.02) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (1.03) Esperamos a que se cargue la página del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (1.04) Comprobamos el idioma en la pestaña principal de administrador
	new ValidadorPrincipalAdministrador("es", driver);

	// (1.05) Cerramos sesion
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (1.06) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// (1.07) Hacemos login como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (1.08) Esperamos a que se cargue la página del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (1.09) Comprobamos el idioma en la página principal de usuario
	new ValidadorPrincipalUsuario("es", driver);

	// (1.10) Cerramos sesion
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (1.10) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// (1.11) Pasamos a la página de registro
	WebElement botonIrRegistro = MySeleniumUtils.waitForElementWithId(
		driver, "form_menu_superior:enlace_registro");

	botonIrRegistro.click();

	// (1.12) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (1.13) Comprobamos el idioma en la página de registro
	new ValidadorRegistro("es", driver);

	// (1.14) Volvemos a la página de login
	WebElement loginEnlace = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:enlace_login");

	loginEnlace.click();

	// (1.15) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// ===================================
	// (2) Cambiamos de idioma
	// ===================================

	// (2.01) Hacemos click en la opción de cambio de idioma

	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (2.02) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(englishLocale, "login__boton"));

	esperar(tiempoVerResultadoTest);

	// (2.03) Validamos la página de login
	new ValidadorLogIn(englishLocale, driver);

	// (2.04) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2.05) Esperamos a que se cargue la principal del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (2.06) Comprobamos el idioma de la página del administrador
	new ValidadorPrincipalAdministrador("en", driver);

	// (2.07) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (2.08) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	/*
	 * -----------------------------------------------------
	 * 
	 * Al hacer logout se pierde la configuración de idioma
	 * 
	 * -----------------------------------------------------
	 */

	// (2.09) Volver a cambiar el idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (2.10) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(englishLocale, "login__boton"));

	// (2.11) Hacemos login como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (2.12) Esperamos a que se cargue la página del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	// (2.13) Comprobamos el idioma en la página principal de usuario
	new ValidadorPrincipalUsuario(englishLocale, driver);

	esperar(tiempoVerResultadoTest);

	// (2.14) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (2.15) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	/*
	 * -----------------------------------------------------
	 * 
	 * Al hacer logout se pierde la configuración de idioma
	 * 
	 * -----------------------------------------------------
	 */

	// (2.16) Volver a cambiar el idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (2.17) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(englishLocale, "login__boton"));

	esperar(tiempoVerResultadoTest);

	// (2.18) Pasamos a la página de registro
	botonIrRegistro = MySeleniumUtils.waitForElementWithId(driver,
		"form_menu_superior:enlace_registro");

	botonIrRegistro.click();

	// (2.19) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (2.20) Comprobamos el idioma en la página de registro
	new ValidadorRegistro(englishLocale, driver);

	esperar(tiempoVerResultadoTest);
    }

    /*
     * PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
     * idioma por defecto. (Probar algunas vistas).
     */
    @Test
    public void prueba36() {
	int tiempoVerResultadoTest = 1200;

	// Paso inicial ==> Restaurar el contenido de la base de datos
	new DatabaseReload().reload(driver);

	// ================================================
	// (1) Analizamos los textos de la página de login
	// ================================================

	// (1.1) Comprobamos el idioma por defecto
	new ValidadorLogIn(defaultLocale, driver).comprobarTextos();

	esperar(tiempoVerResultadoTest);

	// (1.2) Cambiamos de idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (1.3) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(englishLocale, "login__boton"));

	esperar(tiempoVerResultadoTest);

	// (1.4) Comprobamos los textos de la página de login
	new ValidadorLogIn(englishLocale, driver).comprobarTextos();

	// (1.5) Volvemos al primer idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");

	// (1.6) Esperamos a que se cargue la página de login
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(defaultLocale, "login__boton"));

	esperar(tiempoVerResultadoTest);

	// (1.7) Comprobamos los textos de la página de login
	new ValidadorLogIn(defaultLocale, driver).comprobarTextos();

	// ========================================================
	// (2) Analizamos los textos de la página del adminstrador
	// ========================================================

	// (2.01) Hacemos login como administrador
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2.02) Esperamos a que cargue la página principal del administrador
	MySeleniumUtils.waitForElementWithId(driver,
		"form_admin:tabla_usuarios");

	esperar(tiempoVerResultadoTest);

	// (2.03) Comprobamos el idioma por defecto
	new ValidadorPrincipalAdministrador(defaultLocale, driver)
		.comprobarTextos();

	// (2.04) Cambiamos el idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (2.05) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(englishLocale,
			"administrador__boton_eliminar_usuario"));

	esperar(tiempoVerResultadoTest);

	// (2.06) Validamos los textos de la página principal del administrador
	new ValidadorPrincipalAdministrador(englishLocale, driver)
		.comprobarTextos();

	// (2.07) Volvemos al primer idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");

	// (2.08) Esperamos a que se cargue la página del administrador
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(defaultLocale,
			"administrador__boton_eliminar_usuario"));

	esperar(tiempoVerResultadoTest);

	// (2.09) Validamos los textos de la página principal del administrador
	new ValidadorPrincipalAdministrador(defaultLocale, driver)
		.comprobarTextos();

	// (2.10) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (2.11) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// --------------------------------
	// (3) Página principal de usuario
	// --------------------------------

	// (3.1) Hacemos login como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3.2) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(tiempoVerResultadoTest);

	// (3.3) Comprobamos el idioma por defecto
	new ValidadorPrincipalUsuario(defaultLocale, driver).comprobarTextos();

	// (3.2) Cambiamos de idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (3.3) Esperamos a que se cargue la página
	MySeleniumUtils
		.waitForElementWithText(driver, new PropertiesReader()
			.getValueOf(englishLocale,
				"principal_usuario__botones_listar"));

	esperar(tiempoVerResultadoTest);

	// (3.4) Validamos los textos de la página principal del usuario
	new ValidadorPrincipalUsuario(englishLocale, driver).comprobarTextos();

	// (3.5) Volvemos al primer idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");

	// (3.6) Esperamos a que se cargue la página
	MySeleniumUtils
		.waitForElementWithText(driver, new PropertiesReader()
			.getValueOf(defaultLocale,
				"principal_usuario__botones_listar"));

	esperar(tiempoVerResultadoTest);

	// (3.7) Validamos los textos de la página principal del usuario
	new ValidadorPrincipalUsuario(defaultLocale, driver).comprobarTextos();

	// (3.8) Cerramos sesión
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_usuario",
		"form_menu_superior:boton_logout");

	// (3.9) Esperamos a que se cargue la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerResultadoTest);

	// ------------------------
	// (4) Ventana de registro
	// ------------------------

	// (4.01) Pasamos a la página de registro
	WebElement boton_registrarse = MySeleniumUtils.waitForElementWithId(
		driver, "form_menu_superior:enlace_registro");

	boton_registrarse.click();

	// (4.02) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerResultadoTest);

	// (4.03) Comprobamos el idioma por defecto
	new ValidadorRegistro(defaultLocale, driver).comprobarTextos();

	// (4.04) Cambiamos de idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_eng");

	// (4.05) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(englishLocale, "registro__boton"));

	esperar(tiempoVerResultadoTest);

	// (4.06) Validamos los textos de la página de registro
	new ValidadorRegistro(englishLocale, driver).comprobarTextos();

	// (4.07) Volvemos al primer idioma
	MySeleniumUtils.ClickSubopcionMenuHover(driver,
		"form_menu_superior:submenu_idiomas",
		"form_menu_superior:boton_esp");

	// (4.08) Esperamos a que se cargue la página
	MySeleniumUtils.waitForElementWithText(driver, new PropertiesReader()
		.getValueOf(defaultLocale, "registro__boton"));

	esperar(tiempoVerResultadoTest);

	// (4.09) Validamos los textos de la página de registro
	new ValidadorRegistro(defaultLocale, driver).comprobarTextos();

	esperar(tiempoVerResultadoTest);
    }

    /*
     * -----------------------------------------------------
     * --------------------- Seguridad ---------------------
     * -----------------------------------------------------
     */

    /*
     * PR37: Intento de acceso a un URL privado de administrador con un usuario
     * autenticado como usuario normal.
     */
    @Test
    public void prueba37() {
	int esperaVerEfectoTest = 1200;

	// (1) Restauramos el contenido de la base de datos
	new DatabaseReload().reload(driver);

	esperar(esperaVerEfectoTest);

	// (2) Iniciamos sesión como usuario sin privilegios
	new PO_LoginForm().completeForm(driver, "user1", "user1");

	// (3) Esperamos a que se cargue la página principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(esperaVerEfectoTest);

	// (4) Nos vamos a la página de inbox
	WebElement botonInbox = MySeleniumUtils.waitForElementWithId(driver,
		"form_user:inbox");

	botonInbox.click();

	// (5) Esperamos a que se cargue la tabla de tareas
	MySeleniumUtils.waitForElementWithId(driver, "form_user:tabla_tareas");

	esperar(esperaVerEfectoTest);

	// (6) Intentamos acceder a la página principal del administrador
	//
	// ==> No tiene privilegios así que volverá a su página principal

	driver.get("http://localhost:8280/sdi2-23/pages_admin/principal_administrador.xhtml");

	// (7) Esperamos a que se cargue la principal del usuario
	MySeleniumUtils.waitForElementWithId(driver, "form_user:semana");

	esperar(esperaVerEfectoTest);
    }

    /*
     * PR38: Intento de acceso a un URL privado de usuario normal con un usuario
     * no autenticado.
     */
    @Test
    public void prueba38() {
	int tiempoVerEfectoTest = 1200;

	// (1) Desde login nos vamos a registro
	WebElement botonIrRegistro = MySeleniumUtils.waitForElementWithId(
		driver, "form_menu_superior:enlace_registro");

	botonIrRegistro.click();

	// (2) Esperamos a que se cargue la página de registro
	MySeleniumUtils.waitForElementWithId(driver,
		"form_anonimo:boton_registro");

	esperar(tiempoVerEfectoTest);

	// (3) Desde registro intentamos ir a principal de usuario
	driver.get("http://localhost:8280/sdi2-23/pages_user/principal_usuario.xhtml");

	// (4) Al no habernos identificado llegaremos a la página de login
	MySeleniumUtils
		.waitForElementWithId(driver, "form_anonimo:boton_login");

	esperar(tiempoVerEfectoTest);
    }

}