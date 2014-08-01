package code.comet

import net.liftweb.http._
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Helpers._
import net.liftweb.util._

import scala.xml.Text

/**
 * @author evan
 *         Date: 2014-07-30
 */
class CometMessage extends CometActor {

  Schedule.schedule(this, Message, 5.seconds)

  def render =
    ".message" #> <span id="message">Whatever you feel like returning</span>

  override def lowPriority: PartialFunction[Any, Unit] = {
    case Message =>
      partialUpdate(SetHtml("message", Text("updated: " + now.toString)))
      Schedule.schedule(this, Message, 10.seconds)
  }
}

case object Message
