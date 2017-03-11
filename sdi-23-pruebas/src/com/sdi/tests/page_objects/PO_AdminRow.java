package com.sdi.tests.page_objects;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AdminRow {

    /**
     * Extracts a row from the user table of the administrator's home page.
     * 
     * @param driver
     *            driver used Object that allows interacting with the web
     *            browser
     * @param index
     *            number that identifies the row you want to get
     * 
     * @return A map with this structure:<br />
     *         -> <b>'login'</b>: A string with the value extracted from the
     *         login column <br />
     * <br />
     *         -> <b>'email'</b>: A string with the value extracted from the
     *         email column <br />
     * <br />
     *         -> <b>'status'</b>: A string with the value extracted from the
     *         status column <br />
     * <br />
     *         -> <b>'button_state'</b>: A WebElement (button) that allows to
     *         change the status of the user <br />
     * <br />
     *         -> <b>'button_delete'</b>: A WebElement (button) that allows to
     *         delete a user and all his/her tasks and categories <br />
     * 
     */
    public Map<String, Object> findRow(WebDriver driver, int index) {
	Map<String, Object> row = new HashMap<String, Object>();

	// ------------------------------
	// Información del usuario
	// ------------------------------

	WebElement login = driver.findElement(By.id("tabla_usuarios:" + index
		+ ":column_login"));
	row.put("login", login.getText());

	WebElement email = driver.findElement(By.id("tabla_usuarios:" + index
		+ ":column_email"));
	row.put("email", email.getText());

	WebElement state = driver.findElement(By.id("tabla_usuarios:" + index
		+ ":column_status"));
	row.put("status", state.getText());

	// -------------------------------------------
	// Botones que permiten alterarlo
	// -------------------------------------------

	row.put("button_state",
		driver.findElement(By.id("tabla_usuarios:" + index
			+ ":form_status:button_status")));

	row.put("button_delete",
		driver.findElement(By.id("tabla_usuarios:" + index
			+ ":form_delete:button_delete")));

	return row;
    }
}