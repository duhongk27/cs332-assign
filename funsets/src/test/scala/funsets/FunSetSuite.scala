package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4: Set = (x: Int) => x >= 0
    val s5: Set = (x: Int) => x <= 5
    val s6: Set = (x: Int) => (x >= 2 && x <= 4)
    val s7: Set = (x: Int) => (x == 2 || x == 3 || x == 4 || x == 5)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(n) contains n") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton")
      assert(!contains(s1, 3), "Singleton")
      assert(!contains(s2, 1), "Singleton")
      assert(contains(s2, 2), "Singleton")
      assert(!contains(s2, 3), "Singleton")
      assert(!contains(s3, 1), "Singleton")
      assert(!contains(s3, 2), "Singleton")
      assert(contains(s3, 3), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s12 = union(s1, s2)
      val s23 = union(s2, s3)
      val s31 = union(s3, s1)
      val s123 = union(s12, s23)
      assert(contains(s12, 1), "Union12 1")
      assert(contains(s12, 2), "Union12 2")
      assert(!contains(s12, 3), "Union12 3")
      assert(!contains(s23, 1), "Union23 1")
      assert(contains(s23, 2), "Union23 2")
      assert(contains(s23, 3), "Union23 3")
      assert(contains(s31, 1), "Union31 1")
      assert(!contains(s31, 2), "Union31 2")
      assert(contains(s31, 3), "Union31 3")
      assert(contains(s123, 1), "Union123 1")
      assert(contains(s123, 2), "Union123 2")
      assert(contains(s123, 3), "Union123 3")
    }
  }

  test("intersect contains all elements") {
    new TestSets {
      val s123: Set = (x: Int) => ((x == 1) || (x == 2) || (x == 3))
      val s11 = intersect(s123, s1)
      val s22 = intersect(s123, s2)
      val s33 = intersect(s123, s3)
      val s45 = intersect(s4, s5)


      assert(contains(s11, 1), "intersect1")
      assert(!contains(s11, 2), "intersect1")
      assert(!contains(s11, 3), "intersect1")
      assert(!contains(s22, 1), "intersect2")
      assert(contains(s22, 2), "intersect2")
      assert(!contains(s22, 3), "intersect2")
      assert(!contains(s33, 1), "intersect3")
      assert(!contains(s33, 2), "intersect3")
      assert(contains(s33, 3), "intersect3")

      assert(!contains(s45, -1000), "intersect45")
      assert(contains(s45, 0), "intersect45")
      assert(contains(s45, 1), "intersect45")
      assert(contains(s45, 2), "intersect45")
      assert(contains(s45, 3), "intersect45")
      assert(contains(s45, 4), "intersect45")
      assert(contains(s45, 5), "intersect45")
      assert(!contains(s45, 6), "intersect45")
      assert(!contains(s45, 7), "intersect45")

    }
  }

  test("diff test") {
    new TestSets {
      val s45 = intersect(s4, s5)
      val s_dif = diff(s45, s6)
      assert(contains(s_dif, 0), "diff0")
      assert(contains(s_dif, 1), "diff1")
      assert(!contains(s_dif, 2), "diff2")
      assert(!contains(s_dif, 3), "diff3")
      assert(!contains(s_dif, 4), "diff4")
      assert(contains(s_dif, 5), "diff5")

    }
  }

  test("filter test") {
    new TestSets {
      val s = filter(s6, x => x > 3)
      assert(!contains(s, 0), "filter0")
      assert(!contains(s, 1), "filter1")
      assert(!contains(s, 2), "filter2")
      assert(!contains(s, 3), "filter3")
      assert(contains(s, 4), "filter4")
      assert(!contains(s, 5), "filter5")
    }
  }

  test("forall test") {
    new TestSets {
      val p1 = (x: Int) => (x==4)
      val p2 = (x: Int) => (x==1000)
      val p3 = (x: Int) => (x>0 || x<7)

      assert(!forall(s6, p1), "forall")
      assert(!forall(s6, p2), "forall")
      assert(forall(s6, p3), "forall")
    }
  }

  test("exists test") {
    new TestSets {
      val p1 = (x: Int) => (x==4)
      val p2 = (x: Int) => (x==1000)
      val p3 = (x: Int) => (x>0 || x<7)

      assert(exists(s6, p1), "exists")
      assert(!exists(s6, p2), "exists")
      assert(exists(s6, p3), "exists")
    }
  }

  test("map test") {
    new TestSets {

      val f = (x: Int) => (7 * x)
      val s = map(s7, f)
      assert(FunSets.toString(s) == "{14,21,28,35}", "map1")
      assert(FunSets.toString(s) != "{13,24,25,35}", "map2")
    }
  }
}

