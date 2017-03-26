package com.sdi.tests.utils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static PrintStream normalOutput = System.out;
    private static PrintStream errorOutput = System.err;

    private static final String TAB = "   "; // Tabulacion

    private static SimpleDateFormat sdf = new SimpleDateFormat(
	    "dd/MM/yyyy HH:mm");

    private Log() {
	// No deberían crearse instancias de él
    }

    public static void debug(String message, Object... params) {
	normalOutput.printf(getCabecera("DEBUG") + message + "\n", params);
    }

    public static void error(String message, Object... params) {
	errorOutput.printf(getCabecera("ERROR") + message + "\n", params);
    }

    public static void error(Exception ex) {
	errorOutput.println("\n--- Traza del error ---\n");
	ex.printStackTrace(errorOutput);
	errorOutput.println();
    }

    private static String getCabecera(String nivel) {
	String cabec = "\n[" + nivel + "]";

	cabec += TAB + "--";
	cabec += TAB + sdf.format(new Date());
	cabec += TAB + "--" + TAB;

	return cabec;
    }

}