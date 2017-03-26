package uo.sdi.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.presentation.util.UserInfo;
import alb.util.log.Log;

/**
 * Este filtro impide que un usuario logueado vaya a la página de login o de
 * registro sin haber cerrado antes su sesión.
 * 
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/pages_no_user/*" }, initParams = {
	@WebInitParam(name = "UserParam", value = "/pages_user/principal_usuario.xhtml", description = "Página del usuario"),
	@WebInitParam(name = "AdminParam", value = "/pages_admin/principal_administrador.xhtml", description = "Página del administrador") })
public class NoUserPages implements Filter {

    private FilterConfig config = null;

    /**
     * Default constructor.
     */
    public NoUserPages() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {

	if (!(request instanceof HttpServletRequest)) {
	    chain.doFilter(request, response);
	    return;
	}

	HttpServletRequest requestHttp = (HttpServletRequest) request;
	HttpServletResponse responseHttp = (HttpServletResponse) response;

	UserInfo user = (UserInfo) requestHttp.getSession()
		.getAttribute("user");

	if (user != null && !user.getIsAdmin()) {
	    Log.debug("El usuario [%s] (que no tiene privilegios de "
		    + "administrador) ha intentado acceder a una página para "
		    + "usuarios no logueados. Redirigiendo a la página "
		    + "principal del usuario.", user.getLogin());

	    String userPage = config.getInitParameter("UserParam");

	    responseHttp.sendRedirect(requestHttp.getContextPath() + userPage);
	    return;
	}

	else if (user != null && user.getIsAdmin()) {
	    Log.debug("Un usuario administrador ha intentado acceder a una "
		    + "página para usuarios no logueados. Redirigiendo a la "
		    + "página principal del administrador.");

	    String adminPage = config.getInitParameter("AdminParam");

	    responseHttp.sendRedirect(requestHttp.getContextPath() + adminPage);
	    return;
	}

	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	config = fConfig;
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

}