package code.comet

import actor.ChatServer
import net.liftweb.actor._
import net.liftweb.common.Full
import net.liftweb.http.SHtml._
import net.liftweb.http._
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Helpers._
import net.liftweb.util._

import scala.xml.Text

/**
 * @author evan
 *         Date: 2014-07-30
 */

case object Message

class CometMessage extends CometActor {

  override def defaultPrefix = Full("comet")

  def render = bind("message" -> <span id="message">Whatever you feel like returning</span>)

  Schedule.schedule(this, Message, 10.seconds)

  override def lowPriority: PartialFunction[Any, Unit] = {
    case Message =>
      partialUpdate(SetHtml("message", Text("updated: " + now.toString)))
      Schedule.schedule(this, Message, 10.seconds)
  }
}
