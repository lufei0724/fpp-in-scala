package lufei.week4.patmat

class HuffmanSuite extends munit.FunSuite:
  import Huffman.*

  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }


  test("weight of a larger tree (10pts)") {
    new TestTrees:
      assertEquals(weight(t1), 5)
  }


  test("chars of a larger tree (10pts)") {
    new TestTrees:
      assertEquals(chars(t2), List('a','b','d'))
  }

  test("string2chars hello world") {
    assertEquals(string2Chars("hello, world"), List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times hello") {
    val chars = string2Chars("hello")
    val expected = List(('h', 1), ('e', 1), ('l', 2), ('o', 1))
    assertEquals(times(chars).toSet, expected.toSet)
    assertEquals(times(chars).size, expected.size)
  }

  test("make ordered leaf list for some frequency table (15pts)") {
    assertEquals(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))), List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }


  test("combine of some leaf list (15pts)") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(combine(leaflist), List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("until of some code tree list") {
    val codeTrees = List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4))
    val left = Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3)
    val right = Leaf('x', 4)
    val expected = List(Fork(left, right, List('e', 't', 'x'), 7))
    assertEquals(until(singleton, combine)(codeTrees), expected)
  }

  test("encode a tree") {
    new TestTrees:
      assertEquals(encode(t1)("ab".toList), List(0, 1))
      assertEquals(encode(t2)("abd".toList), List(0, 0, 0, 1, 1))
  }

  test("decode a tree") {
    new TestTrees:
      assertEquals(decode(t1, List(0, 1)), List('a', 'b'))
  }


  test("decode and encode a very short text should be identity (10pts)") {
    new TestTrees:
      assertEquals(decode(t1, encode(t1)("ab".toList)), "ab".toList)
  }

  test("access a code table") {
    val table: CodeTable = List(('a', List(1, 0)), ('b', List(0, 1)))
    assertEquals(codeBits(table)('b'), List(0, 1))
  }

  test("convert a tree to a code table") {
    new TestTrees:
      val expectedTable: CodeTable = List(('a', List(0, 0)), ('b', List(0, 1)), ('d', List(1)))
      assertEquals(convert(t2).toSet, expectedTable.toSet)
      assertEquals(convert(t2).size, expectedTable.size)
  }

  test("quick encode a tree") {
    new TestTrees:
      assertEquals(quickEncode(t2)("abd".toList), List(0, 0, 0, 1, 1))
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
