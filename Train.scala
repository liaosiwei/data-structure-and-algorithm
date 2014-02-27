package recfun

import scala.collection.mutable.ListBuffer

/**
 * User: sweeliao@gmail.com
 * Date: 14-2-27
 * Time: 下午5:28
 */
object Train {

  def getLongStr(in: String, out: String): ListBuffer[ListBuffer[Int]] = {
    val resList: ListBuffer[ListBuffer[Int]] = ListBuffer.empty[ListBuffer[Int]]

    def indexFirst(in: String, c: Char, index: Int): Int = in match {
      case "" => -1
      case x => {
        if (x.head == c) index
        else indexFirst(in.tail, c, index+1)
      }
    }

    def findIndex(in: String, target: String, count: Int): (Int, Int) = in match {
      case "" => (-1, -1)
      case x => {
        val index = indexFirst(target, x.head, 0)
        if (index == -1) findIndex(x.tail, target, count+1)
        else (index, count)
      }
    }

    val first = findIndex(in, out, 0)
    if (first._1 == -1) return resList
    ListBuffer(first._1) +=: resList

    for (i <- first._2+1 until in.length) {
      if (out.contains(in(i))) {
        var flag = false
        var left = 0
        for(j <- 0 until resList.length) {
          val right = out.indexOf(in(i), resList(j).head+1)
          left = out.substring(0, resList(j).head+1).indexOf(in(i))
          if (right != -1) {
            flag = true
            right +=: resList(j)
          }
          if (left != -1) flag = false
        }
        if (!flag) ListBuffer(left) +=: resList
      }
    }
    resList
  }
  def train(in: String, out: String): Int = getLongStr(in, out).map(x=>x.length).max
}
