package code.comet

import code.actor.ChatServer
import net.liftweb.http.SHtml._
import net.liftweb.http._

/**
 * @author evan
 *         Date: 2014-07-30
 */
class CometChat extends CometActor with CometListener {

  private var messages: List[ChatMessage] = Nil

  def registerWith = ChatServer

  private def renderMessages = <div>
    {messages.take(10).reverse.map(m => <li>
      {m.msg}
    </li>)}
  </div>

  def render =
    ".input" #> ajaxForm(SHtml.text("", sendMessage)) &
        ".messages *" #> renderMessages

  private def sendMessage(msg: String) = ChatServer ! ChatMessage("default", msg)

  override def lowPriority = {
    case msg: List[ChatMessage] =>
      messages = msg
      reRender(sendAll = false)
  }
}

case class ChatMessage(user: String, msg: String)
