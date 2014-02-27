import collection.mutable.ListBuffer
import java.io.{File, PrintWriter}

/**
 * User: sweeliao@gmail.com
 * Date: 14-2-27
 * Time: 下午3:29
 */

/*
事实上，问题的关键在于我们要移动哪些牌。这个问题确定之后，只需把需要移动的牌排排序，依次移到头和尾即可。
如何决定移动哪些牌呢？题目要求移动牌的数量尽量少。牌的总数是一定的，这就要求*不需要移动的牌*（下面称之为有序牌）的数量尽量多。只要你能让有序牌的数量最多，那么就不可能有更优的解。
如何找到有序牌？排序完成之后的牌堆由三段组成：被移到顶端的牌、没有移动过的有序牌、被移到低端的牌。可以看出，有序牌之间的相对顺序将保持不变，而且有序牌在排好序的牌堆中必须相邻。
于是问题等价于：在排好序的牌堆中找到最长一段连续的牌，这些牌在未排序的牌堆中位置序号一次增加。
这构成了解题代码的第一部分：给未排序的牌组附加一个位置序号，排序，从头到尾扫排序了的牌组，找到最长的一段位置序号增加的牌。这一段牌就是有序牌。
然后，输出需要移动的牌的数量。从有序牌出发，往后依次输出顶部的牌的移动操作，往前依次输出底部的牌的移动操作。*/

object CardTrick {
  def binarySearch(in: Array[Int], left: Int, right: Int, t: Int): Int = {
    val mid = (left + right) / 2
    if (in(mid) > t) binarySearch(in, left, mid-1, t)
    else if (in(mid) < t) binarySearch(in, mid+1, right, t)
    else mid
  }

  def longestIncreasingSubsequence(in: List[Int])(implicit ordering: Ordering[Int]): List[Int] = {
    // We can use the following instead:
    // zipWithIndex.map(t => (t._2, List(t._1))).toMap
    // http://stackoverflow.com/questions/17828431/convert-scalas-list-into-map-with-indicies-as-keys
    def init(i: Int, l: List[Int], m: Map[Int, List[Int]]): Map[Int, List[Int]] =
      if (l.isEmpty) m
      else init(i + 1, l.tail, m + (i -> List(l.head)))

    def loop(i: Int, l: List[Int], m: Map[Int, List[Int]]): List[Int] =
      if (l.isEmpty) m.maxBy(_._2.length)._2.reverse
      else {
        val f = m.filter(p => p._1 < i && ordering.lt(p._2.head, l.head))
        if (f.isEmpty) loop(i + 1, l.tail, m)
        else {
          val (_, ll) = f.maxBy(_._2.length)
          loop(i + 1, l.tail, m + (i -> (l.head::ll)))
        }
      }

    if (in.isEmpty) List.empty
    else loop(1, in.tail, init(0, in, Map[Int, List[Int]]()))
  }

  def cardTrick(in: Array[Int], w: PrintWriter) = {
    val len = in.length
    val sorted = in.sorted

    val bsearch = binarySearch(sorted, 0, len, _: Int)

    val maxList = longestIncreasingSubsequence(in.toList)
    val count = sorted.length-maxList.length
    w.write(count + "\n")
    val first = bsearch(maxList.last)
    val second = bsearch(maxList.head)
    for (i <- 0 until first) w.write(sorted(first-i-1) + "B\n")
    for (i <- second+1 until sorted.length) w.write(sorted(i) + "T\n")
    count
  }

/*  def readLine(str: String): List[Int] = str match {
    case "" => Nil
    case _ => if (str.head.isWhitespace)
  }*/

  def main(args: Array[String]): Unit = {
    val f = io.Source.fromFile("E:\\test.txt").getLines()
    val writer = new PrintWriter(new File("E:\\output.txt"))
    for (line <- f) {
      if (line == ";") writer.write(";\n")
      else if (!line.isEmpty) cardTrick(line.split(" ").map(x=>x.toInt), writer)
    }
    writer.close()
  }
}
