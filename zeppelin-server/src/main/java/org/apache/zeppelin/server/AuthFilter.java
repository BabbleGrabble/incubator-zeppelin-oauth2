package org.apache.zeppelin.server;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Auth filter
 *
 */
public class AuthFilter implements Filter {

  private ServletContext context;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String uri = req.getRequestURI();
    this.context.log("Requested Resource::" + uri);

    HttpSession session = req.getSession(false);

    if (session == null) {
      this.context.log("Unauthorized access request");
      res.sendRedirect("login.html");
    } else {
      // pass the request along the filter chain
      filterChain.doFilter(request, response);
    }

  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

    this.context = filterConfig.getServletContext();
    this.context.log("AuthenticationFilter initialized");
  }
}
