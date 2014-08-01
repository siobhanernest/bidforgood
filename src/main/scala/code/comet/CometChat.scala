package code.comet

import code.actor.{ChatMessage, ChatServer}
import net.liftweb.http.SHtml._
import net.liftweb.http._

import scala.xml.NodeSeq

/**
 * @author evan
 *         Date: 2014-07-30
 */
class CometChat extends CometActor with CometListener {

  private var messages: List[ChatMessage] = Nil

  def registerWith = ChatServer

  def render =
    ".input" #> ajaxForm(SHtml.text("", sendMessage)) &
        ".messages *" #> renderMessages

  override def lowPriority = {
    case msg: List[ChatMessage] =>
      messages = msg
      reRender(sendAll = false)
  }

  private def renderMessages =
    if (messages.isEmpty) ".message" #> NodeSeq.Empty
    else ".message *" #> messages.take(10).reverse.map(_.msg)

  private def sendMessage(msg: String) = ChatServer ! ChatMessage("default", msg)
}
