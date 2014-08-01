package code.actor

import code.comet.ChatMessage
import net.liftweb.actor._
import net.liftweb.http._

/**
 * @author evan
 *         Date: 2014-07-31
 */
object ChatServer extends LiftActor with ListenerManager {

  var messages: List[ChatMessage] = Nil

  def createUpdate = messages

  override def lowPriority = {
    case msg: ChatMessage =>
      messages ::= msg
      updateListeners()
  }
}
