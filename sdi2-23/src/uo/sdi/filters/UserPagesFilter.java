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

import alb.util.log.Log;
import uo.sdi.presentation.util.UserInfo;

/**
 * Este filtro impide que un usuario administrador (o un usuario que no se ha
 * logueado en el sistema) acceda las páginas de los usuarios sin privilegios.
 * 
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/pages_user/*" }, initParams = {
	@WebInitParam(name = "LoginParam", value = "/pages_no_user/login.xhtml", description = "Página de login"),
	@WebInitParam(name = "AdminParam", value = "/pages_admin/principal_administrador.xhtml", description = "Página del administrador") })
public class UserPagesFilter implements Filter {

    private FilterConfig config = null;

    /**
     * Default constructor.
     */
    public UserPagesFilter() {

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

	if (user == null) {
	    Log.debug("Un usuario no logueado ha intentado acceder a una página"
		    + " para usuarios autenticados. Redirigiendo a login");

	    String loginForm = config.getInitParameter("LoginParam");

	    responseHttp.sendRedirect(requestHttp.getContextPath() + loginForm);
	    return;
	}

	else if (user.getIsAdmin()) {
	    Log.debug("El administrador ha intentado acceder a una página para"
		    + " usuarios no administradores. Redirigiendo"
		    + " a la página principal del administrador.");

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