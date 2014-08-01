package code.comet

import code.actor.ChatServer
import net.liftweb.common.Full
import net.liftweb.http.SHtml._
import net.liftweb.http._

/**
 * @author evan
 *         Date: 2014-07-30
 */
case class ChatMessage(user: String, msg: String)

class CometChat extends CometActor with CometListener {

  private var messages: List[ChatMessage] = Nil

  override def defaultPrefix = Full("chat")

  def registerWith = ChatServer

  private def renderMessages = <div>
    {messages.take(10).reverse.map(m => <li>
      {m.msg}
    </li>)}
  </div>

  def render =
    "input" #> ajaxForm(SHtml.text("", sendMessage)) &
        "messages" #> renderMessages

  private def sendMessage(msg: String) = ChatServer ! ChatMessage("default", msg)

  override def lowPriority = {
    case msg: List[ChatMessage] =>
      messages = msg
      reRender(sendAll = false)
  }
}

