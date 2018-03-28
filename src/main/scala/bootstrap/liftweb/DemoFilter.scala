
package bootstrap.liftweb

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * A filter that does:
 * - a session invalidate + redirect toward statfull  on "/redirect"
 * - a session invalidate + redirect toward stateless on "/destroy"
 */
class DemoFilter extends Filter {
  def destroy(): Unit = {}
  def init(config: FilterConfig): Unit = {}
  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {

    (request, response) match {
      case (req: HttpServletRequest, resp: HttpServletResponse) =>

        if(req.getRequestURI == req.getContextPath + "/redirect") {
          println("==> HttpServletRequest.changeSessionId + redirect towards statefull")

          // force swap session id
          // It seems to be what is causing the problem!
          req.changeSessionId()
          // now create a new session so that "id swap" ensue
          req.getSession(true).setAttribute("user", "Alice")
          // redirect
          resp.sendRedirect(req.getContextPath() + "/statefull.html");
        } else if(req.getRequestURI == req.getContextPath + "/destroy") {
          println("invalidate + redirect towards stateless")

          //remove JSESSIONID from req and resp, destroy session
          req.getSession(false) match {
            case null    => //ok
            case session => session.invalidate()
          }
          //redirect
          resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
          chain.doFilter(request, response)
        }
      case _ => chain.doFilter(request, response)
    }
  }
}
