package code.actor

import code.model.InventoryItem
import net.liftweb.actor.LiftActor
import net.liftweb.common.Loggable
import net.liftweb.http.ListenerManager

/**
 * @author evan
 *         Date: 2014-08-01
 */
object InventoryServer extends LiftActor with ListenerManager with Loggable {

  var items: List[InventoryItem] = Nil

  var nextId = 1L

  def createUpdate = items

  override def lowPriority = {
    case CreateItem(desc) =>
      logger.error(s"Creating new item id[$nextId]")
      items ::= InventoryItem(nextId, desc)
      nextId += 1L
      updateListeners()

    case UpdateItem(item) =>
      logger.error(s"Update item $item")
      updateListeners()

    case DeleteItem(id) =>
      logger.error(s"Delete item $id")
      updateListeners()
  }
}

case class CreateItem(description:String)

case class UpdateItem(item: InventoryItem)

case class DeleteItem(id:Long)
