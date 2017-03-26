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
 * Este filtro impide que un usuario sin privilegios de administrador (o que no
 * se ha logueado en el sistema) acceda las páginas del administrador.
 * 
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/pages_admin/*" }, initParams = {
	@WebInitParam(name = "LoginParam", value = "/pages_no_user/login.xhtml", description = "Página de login"),
	@WebInitParam(name = "UserParam", value = "/pages_user/principal_usuario.xhtml", description = "Página del usuario") })
public class AdminPagesFilter implements Filter {

    private FilterConfig config = null;

    /**
     * Default constructor.
     */
    public AdminPagesFilter() {

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
		    + " del administrador. Redirigiendo a login");

	    String loginForm = config.getInitParameter("LoginParam");

	    responseHttp.sendRedirect(requestHttp.getContextPath() + loginForm);
	    return;
	}

	else if (!user.getIsAdmin()) {
	    Log.debug("El usuario [%s] (que no es un administrador) ha "
		    + "intentado intentado acceder a una página del "
		    + "administrador. Redirigiendo a la página principal"
		    + "del usuario.", user.getLogin());

	    String userPage = config.getInitParameter("UserParam");

	    responseHttp.sendRedirect(requestHttp.getContextPath() + userPage);
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