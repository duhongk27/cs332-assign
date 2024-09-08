package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1 - to solve rCc
   */
  def pascal(c: Int, r: Int): Int = {
    if(c == r || c == 0) 1
    else pascal(c - 1, r - 1) + pascal (c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def check(chars: List[Char], cnt: Int = 0): Boolean = {
      if(chars.isEmpty) cnt == 0
      else {
        if (chars.head == '(') check(chars.tail, cnt + 1)
        else if (chars.head == ')') {
          if (cnt == 0) false
          else check(chars.tail, cnt - 1)
        }
        else check(chars.tail, cnt)
      }
    }
    check(chars)
  }
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def use_coin(money: Int, coins: List[Int]): Int = {
      if(money == 0) 1
      else if (coins.isEmpty) 0
      else if (money < 0) 0
      else {
        val select_this_coin = use_coin(money - coins.head, coins)
        val not_select_this_coin = use_coin(money, coins.tail)
        select_this_coin + not_select_this_coin
      }
    }
    if (money > 0 && !(coins.isEmpty)) use_coin(money, coins)
    else 0
  }
}
