package recfun

import collection.mutable.ListBuffer
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
  def getLongList(in: List[List[Int]]): List[Int] = in.tail.tail match {
    case Nil => if (in.head.length > in.tail.head.length) in.head else in.tail.head
    case _ => {
      val temp = getLongList(in.tail)
      if (in.head.length > temp.length) in.head else temp
    }
  }

  def cardTrick(in: Array[Int]) = {
    val len = in.length
    val sorted = in.sorted
    val bsearch = binarySearch(sorted, 0, len, _: Int)

    def getAllLength(in: Array[Int]): List[List[Int]] = {
      val resList: ListBuffer[ListBuffer[Int]] = ListBuffer.empty[ListBuffer[Int]]
      resList += ListBuffer(in.head)

      for (i <- 1 until len) {
        val place = bsearch(in(i))
        if (place == 0) ListBuffer(in(i)) +=: resList
        else {
          val before = sorted(place - 1)
          var flag = false
          for (j <- 0 until resList.length) {
            if (resList(j).head == before) {
              flag = true
              in(i) +=: resList(j)
            }
          }
          if (!flag) ListBuffer(in(i)) +=: resList
        }
      }
      resList.map(x=>x.toList).toList
    }
    val maxList = getLongList(getAllLength(in))
    val count = sorted.length-maxList.length
    println(count)
    val first = bsearch(maxList.last)
    val second = bsearch(maxList.head)
    for (i <- 0 until first) println(sorted(first-i-1) + "B")
    for (i <- second+1 until sorted.length) println(sorted(i) + "T")
    count
  }
}
