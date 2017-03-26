package com.sdi.tests.utils;

public class ThreadUtil {

    public static void wait(int miliseconds) {
	try {
	    Thread.sleep(miliseconds);
	}

	catch (InterruptedException ex) {

	}
    }
}