package code.snippet

import net.liftweb.util.Helpers._

/**
 * @author evan
 *         Date: 2014-07-31
 */
class Footer {

  def render =
    ".year *" #> currentYear.toString &
        ".home [href]" #> "/"

}
