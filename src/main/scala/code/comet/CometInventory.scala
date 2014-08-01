package code.comet

import code.actor.{CreateItem, InventoryServer}
import code.model.InventoryItem
import net.liftweb.http.SHtml._
import net.liftweb.http._
import net.liftweb.http.js.{JsCmd, JsCmds}

import scala.xml.NodeSeq

/**
 * @author evan
 *         Date: 2014-08-01
 */
class CometInventory extends CometActor with CometListener {

  private var items: List[InventoryItem] = Nil

  def registerWith = InventoryServer

  def render = {

    var description = ""

    def process(): JsCmd = {
      createItem(description)
      JsCmds.Noop
    }

    "@desc" #> text(description, description = _) &
        "type=submit" #> ajaxSubmit("New item", process) &
        ".items *" #> renderItems
  }

  override def lowPriority = {
    case msg: List[InventoryItem] =>
      items = msg
      reRender(sendAll = false)
  }

  private def renderItems =
    if (items.isEmpty) ".item" #> NodeSeq.Empty
    else ".item" #> items.take(5).reverse.map(item => {
      ".id *" #> item.id &
          ".desc *" #> item.description
    })

  private def createItem(description: String) =
    InventoryServer ! CreateItem(description)
}
