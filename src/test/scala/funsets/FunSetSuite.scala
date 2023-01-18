package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *

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

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton1")
      assert(contains(s2, 2), "Singleton2")
      assert(contains(s3, 3), "Singleton3")
      assert(!contains(s1, 3), "Singleton4")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect only contains elements existing in both sets") {
    new TestSets:
      val s1AndS2 = union(s1, s2)
      val s1AndS3 = union(s1, s3)
      val s = intersect(s1AndS2, s1AndS3)
      assert(contains(s, 1), "Intersect 1")
      assert(!contains(s, 2), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
  }

  test("intersect contains elements existing in the first set but not the second set") {
    new TestSets:
      val s1AndS2 = union(s1, s2)
      val s1AndS3 = union(s1, s3)
      val s = diff(s1AndS2, s1AndS3)
      assert(!contains(s, 1), "Intersect 1")
      assert(contains(s, 2), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
  }

  test("filter returns new set contains elements accepted by a given predicate") {
    new TestSets:
      val s1AndS2 = union(s1, s2)
      val s1AndS2andS3 = union(s1AndS2, s3)
      val s = filter(s1AndS2andS3, _ >= 2)
      assert(!contains(s, 1), "Filter 1")
      assert(contains(s, 2), "Filter 2")
      assert(contains(s, 3), "Filter 3")
      assert(!contains(s, 4), "Filter 4")
  }

  test("forall indicate whether a given predicate is true for all elements of the set") {
    new TestSets:
      val s1AndS2 = union(s1, s2)
      val s = union(s1AndS2, s3)
      assert(forall(s, _ > 0), "Forall 1")
      assert(!forall(s, _ > 1), "Forall 2")
      assert(forall(s, _ < 4), "Forall 3")
  }

  test("exists indicate whether a set contains at least one element for given predicate") {
    new TestSets:
      val s1AndS2 = union(s1, s2)
      val s = union(s1AndS2, s3)
      assert(exists(s, _ == 2), "Exists 1")
      assert(!exists(s, _ == 4), "Exists 2")
      assert(exists(s, _ < 4), "Exists 3")
      assert(!exists(s, _ > 4), "Exists 4")
  }

  test("map transforms a given set into another one by applying a given function to each element") {
    new TestSets:
      val s1AndS2 = union(s1, s2)
      val s1AndS2AndS3 = union(s1AndS2, s3)
      val s = map(s1AndS2AndS3, _ + 1)
      assertEquals(FunSets.toString(s), "{2,3,4}")
  }


  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
