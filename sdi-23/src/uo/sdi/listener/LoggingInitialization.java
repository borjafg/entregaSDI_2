package uo.sdi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import alb.util.log.Log;
import alb.util.log.LogLevel;

public class LoggingInitialization implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

	String level = arg0.getServletContext().getInitParameter("logLevel");

	switch (level) {
	case "OFF":
	    Log.setLogLevel(LogLevel.OFF);
	    break;
	case "ERROR":
	    Log.setLogLevel(LogLevel.ERROR);
	    break; // Ha ocurrido algo grave que impide la normal ejecución del
		   // programa.
		   // Seguramente el programa tenga que terminar o abortar esa
		   // transacción/petición
	case "WARN":
	    Log.setLogLevel(LogLevel.WARN);
	    break; // Se ha producido algún problema no crítico para el
		   // funcionamiento del programa.
		   // El programa puede continuar pero algo raro ha ocurrido.
	case "INFO":
	    Log.setLogLevel(LogLevel.INFO);
	    break; // Se ha alcanzado algún hito importante en la ejecución del
		   // programa.
	case "DEBUG":
	    Log.setLogLevel(LogLevel.DEBUG);
	    break; // El programador necesita ver en detalle información sobre
		   // el comportamiento de la aplicación.
		   // Este nivel ya no debería estar activado cuando el programa
		   // está en producción.
	case "TRACE":
	    Log.setLogLevel(LogLevel.TRACE);
	    break; // Nivel máximo de detalle de lo que hace la aplicación.
	case "ALL":
	    Log.setLogLevel(LogLevel.ALL);
	    break;
	}
    }

}