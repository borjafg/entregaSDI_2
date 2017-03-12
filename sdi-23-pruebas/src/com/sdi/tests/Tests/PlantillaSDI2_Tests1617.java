package com.sdi.tests.Tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.After;
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
import com.sdi.tests.page_objects.PO_AdminRow;
import com.sdi.tests.page_objects.PO_LoginForm;
import com.sdi.tests.utils.PropertiesReader;
import com.sdi.tests.utils.SeleniumUtils;
import com.sdi.tests.utils.ThreadUtil;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantillaSDI2_Tests1617 {

    WebDriver driver;
    List<WebElement> elementos = null;

    private final String defaultLocale = "es";

    public PlantillaSDI2_Tests1617() {

    }

    // ==================================
    // Métodos que se ejecutan antes y
    // después de los test
    // ==================================

    @Before
    public void run() {
	// Este código es para ejecutar con la versión portale de Firefox 46.0
	File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");

	FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
	FirefoxProfile firefoxProfile = new FirefoxProfile();

	driver = new FirefoxDriver(ffBinary, firefoxProfile);
	driver.get("http://localhost:8280/sdi2-23");

	// Este código es para ejecutar con una versión instalada de Firex 46.0
	// driver = new FirefoxDriver();
	// driver.get("http://localhost:8180/sdi2-23");
    }

    @After
    public void end() {
	// Cerramos el navegador
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

	SeleniumUtils.EsperaCargaPagina(driver, "id", "tabla_usuarios", 10);

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
				"login_usuario_no_existe")));

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
				"login_usuario_no_existe")));

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
		"id", "form_menu_administrador:boton_reinicio", 10);

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
	SeleniumUtils.EsperaCargaPagina(driver, "id", "tabla_usuarios", 10);

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

	ThreadUtil.wait(800); // Espera para ver el efecto del test
    }

    // PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de
    // entrar con el usuario que se ha desactivado.
    @Test
    public void prueba06() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	SeleniumUtils.EsperaCargaPagina(driver, "id", "tabla_usuarios", 10);

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

	// (5) Cerrar sesión
	WebElement logout = driver.findElement(By
		.id("form_menu_administrador:boton_logout"));
	logout.click();

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	// (6) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, (String) fila.get("login"),
		(String) fila.get("login"));

	// (7) Comprobar que no fue posible
	mensajes = SeleniumUtils.EsperaCargaPagina(driver, "class",
		"ui-messages-warn-detail", 8);
	mensaje = mensajes.get(0);

	assertTrue(
		"No se encontró el mensaje de usuario deshabilitado",
		mensaje.getText().equals(
			new PropertiesReader().getValueOf(defaultLocale,
				"login_usuario_deshabilitado")));

	ThreadUtil.wait(1000); // Espera para ver el efecto del test
    }

    // PR07: Cambiar el estado de un usuario a DISABLED a ENABLED, y tratar de
    // entrar con el usuario que se ha activado.
    @Test
    public void prueba07() {
	// (1) Hacer login
	new PO_LoginForm().completeForm(driver, "admin", "admin");

	// (2) Esperar a que se cargue la página
	SeleniumUtils.EsperaCargaPagina(driver, "id", "tabla_usuarios", 10);

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

	// (5) Cerrar sesión
	WebElement logout = driver.findElement(By
		.id("form_menu_administrador:boton_logout"));
	logout.click();

	SeleniumUtils.EsperaCargaPagina(driver, "id",
		"form_anonimo:boton_login", 10);

	// (6) Intentar hacer login con ese usuario
	new PO_LoginForm().completeForm(driver, (String) fila.get("login"),
		(String) fila.get("login"));

	// (7) Comprobar que pudo hacer login
	// SeleniumUtils.EsperaCargaPagina(driver, "id", "idPaginaUsuario", 10);

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
		"tabla_usuarios:column_status_title", 10);

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
		.id("tabla_usuarios:column_status_title"));

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
	assertTrue(false);
    }

    // -------------------
    // ----- ANONIMO -----
    // -------------------

    // PR12: Crear una cuenta de usuario normal con datos válidos.
    @Test
    public void prueba12() {
	assertTrue(false);
    }

    // PR13: Crear una cuenta de usuario normal con login repetido.
    @Test
    public void prueba13() {
	assertTrue(false);
    }

    // PR14: Crear una cuenta de usuario normal con Email incorrecto.
    @Test
    public void prueba14() {
	assertTrue(false);
    }

    // PR15: Crear una cuenta de usuario normal con Password incorrecta.
    @Test
    public void prueba15() {
	assertTrue(false);
    }

    // -------------------
    // ----- USUARIO -----
    // -------------------

    // PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
    // categoría y que son las que tienen que. Usar paginación navegando por las
    // tres páginas.
    @Test
    public void prueba16() {
	assertTrue(false);
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
	assertTrue(false);
    }

    // PR34: Salir de sesión desde cuenta de usuario normal.
    @Test
    public void prueba34() {
	assertTrue(false);
    }

    // PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas
    // vistas)
    @Test
    public void prueba35() {
	assertTrue(false);
    }

    // PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
    // idioma por defecto. (Probar algunas vistas)
    @Test
    public void prueba36() {
	assertTrue(false);
    }

    // PR37: Intento de acceso a un URL privado de administrador con un usuario
    // autenticado como usuario normal.
    @Test
    public void prueba37() {
	assertTrue(false);
    }

    // PR38: Intento de acceso a un URL privado de usuario normal con un usuario
    // no autenticado.
    @Test
    public void prueba38() {
	assertTrue(false);
    }

}