package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      Menu.i("Stateless") / "index" >> Stateless,
      Menu.i("Statefull") / "statefull" ,

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"),
	       "Static Content"))
    )

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))


    LiftRules.statelessReqTest.append {
      case StatelessReqTest(           Nil, _ ) => true
      case StatelessReqTest("index" :: Nil, _ ) => true
    }

    LiftSession.afterSessionCreate ::=
     ( (s:LiftSession, r:Req) => println(s"=====> CREATE Session [${s.uniqueId} // ${s.underlyingId}] with [${r.request.url}]") )

    LiftSession.onBeginServicing ::=
     ( (s:LiftSession, r:Req) => println(s"=====> Processing [${s.uniqueId} // ${s.underlyingId}] request: ${r.request.url}") )

    LiftSession.onShutdownSession ::=
     ( (s:LiftSession) => println(s"=====> DESTROY Session [${s.uniqueId} // ${s.underlyingId}]") )

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery1113
    JQueryModule.init()

    LiftRules.securityRules = () => SecurityRules(
        https               = None
      , content             = None
      , frameRestrictions   = None
      , enforceInOtherModes = false
      , logInOtherModes     = false
      , enforceInDevMode    = false
      , logInDevMode        = false  // this is to check that nothing is reported on dev.
    )

  }
}
