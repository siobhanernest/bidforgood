package code.comet

import actor.ChatServer
import net.liftweb.http.SHtml._
import net.liftweb.http._

/**
 * @author evan
 *         Date: 2014-07-30
 */
case class ChatMessage(user: String, msg: String)

class Chat extends CometActor with CometListener {

  private var messages: List[ChatMessage] = Nil

  def registerWith = ChatServer

  private def renderMessages = <div>
    {messages.take(10).reverse.map(m => <li>
      {m.msg}
    </li>)}
  </div>

  def render = bind("chat", "input" -> ajaxForm(SHtml.text("", sendMessage)), "messages" -> renderMessages)

  private def sendMessage(msg: String) = ChatServer ! ChatMessage("default", msg)

  override def lowPriority = {
    case msg: List[ChatMessage] =>
      messages = msg
      reRender(sendAll = false)
  }
}

