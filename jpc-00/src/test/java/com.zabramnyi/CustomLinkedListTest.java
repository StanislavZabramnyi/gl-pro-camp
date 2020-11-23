package com.zabramnyi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class CustomLinkedListTest {

  private UniDirectionalLinkedList<Integer> intList = new CustomUniDirectionalLinkedList<>();

  @Test
  public void testAddIntoEmptyList() {
    intList.add(41);

    assertEquals(1, intList.size());
    assertEquals(41, intList.get(0).intValue());
  }

  @Test
  public void testGetFirstElementFromListWithOneElement() {
    intList.add(25);
    int element = intList.get(0);

    assertEquals(25, element);
  }

  @Test
  public void testAddCoupleElements() {
    intList.add(42);
    intList.add(7);
    intList.add(10);
    intList.add(115);

    assertEquals(4, intList.size());
    assertEquals(42, intList.get(0).intValue());
    assertEquals(7, intList.get(1).intValue());
    assertEquals(10, intList.get(2).intValue());
    assertEquals(115, intList.get(3).intValue());
  }

  @Test
  public void testGetElements() {
    intList = CustomUniDirectionalLinkedList.of(5, 7, 13, 999);

    int firstElement = intList.get(0);
    int secondElement = intList.get(1);
    int thirdElement = intList.get(2);
    int fourthElement = intList.get(3);

    assertEquals(5, firstElement);
    assertEquals(7, secondElement);
    assertEquals(13, thirdElement);
    assertEquals(999, fourthElement);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetFirstElementFromEmptyList() {
    intList.get(0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetElementByNegativeIndex() {
    intList.get(-2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetElementByIndexEqualsToListSize() {
    intList = CustomUniDirectionalLinkedList.of(1, 18, 23, 38);
    intList.get(4);
  }

  @Test
  public void testAddElementByIndexIntoEmptyList() {
    intList.add(0, 7);

    assertEquals(1, intList.size());
    assertEquals(7, intList.get(0).intValue());
  }

  @Test
  public void testAddElementByIndexToTheEndOfList() {
    intList = CustomUniDirectionalLinkedList.of(3, 5, 7, 10, 0);

    int newElementIndex = intList.size();
    intList.add(newElementIndex, 153);

    assertEquals(153, intList.get(newElementIndex).intValue());
    assertEquals(6, intList.size());
  }

  @Test
  public void testAddElementToTheHeadOfNotEmptyList() {
    intList = CustomUniDirectionalLinkedList.of(1, 0, 2, 8, 3, 7, 4, 6, 5);
    intList.add(0, 404);

    assertEquals(404, intList.get(0).intValue());
    assertEquals(1, intList.get(1).intValue());
    assertEquals(10, intList.size());
  }

  @Test
  public void testAddElementByIndex() {
    intList = CustomUniDirectionalLinkedList.of(1, 0, 2, 8);

    int index = 2;
    intList.add(index, 401);

    assertEquals(401, intList.get(index).intValue());
    assertEquals(1, intList.get(0).intValue());
    assertEquals(0, intList.get(1).intValue());
    assertEquals(2, intList.get(3).intValue());
    assertEquals(8, intList.get(4).intValue());
    assertEquals(5, intList.size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddElementByNegativeIndex() {
    intList.add(-1, 115);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddElementByIndexLargerThanListSize() {
    intList = CustomUniDirectionalLinkedList.of(4, 6, 5);

    int index = 7;
    intList.add(index, 115);
  }

  @Test
  public void testAddElementByIndexEqualToSize() {
    intList = CustomUniDirectionalLinkedList.of(1, 2, 3, 4, 5); // size = 5

    intList.add(5, 111);

    assertEquals(6, intList.size());
    assertEquals(111, intList.get(5).intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetFirstElementOnEmptyTree() {
    intList.set(0, 34);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetElementByIndexEqualToSize() {
    intList = CustomUniDirectionalLinkedList.of(2, 3, 4); // size = 3

    intList.set(3, 222);
  }

  @Test
  public void testSetElementByIndex() {
    intList = CustomUniDirectionalLinkedList.of(34, 78, 9, 8);

    int index = 2; // element = 78
    intList.set(index, 99);

    assertEquals(99, intList.get(index).intValue());
    assertEquals(34, intList.get(0).intValue());
    assertEquals(78, intList.get(1).intValue());
    assertEquals(8, intList.get(3).intValue());
    assertEquals(4, intList.size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRemoveElementFromEmptyList() {
    intList.remove(234);
  }

  @Test
  public void testRemoveFirstElement() {
    intList = CustomUniDirectionalLinkedList.of(4, 6, 8, 9);

    intList.remove(0);

    assertEquals(6, intList.get(0).intValue());
    assertEquals(3, intList.size());
  }

  @Test
  public void testRemoveLastElement() {
    intList = CustomUniDirectionalLinkedList.of(4, 6, 8, 9);

    intList.remove(intList.size() - 1);

    assertEquals(8, intList.get(intList.size() - 1).intValue());
    assertEquals(3, intList.size());
  }

  @Test
  public void testRemoveElement() {
    intList = CustomUniDirectionalLinkedList.of(1, 2, 3, 4, 5);

    int elementIndex = 2;
    intList.remove(elementIndex); // element = 3

    assertEquals(4, intList.get(elementIndex).intValue());
    assertEquals(4, intList.size());
  }

  @Test
  public void testContainsOnEmptyList() {
    boolean contains = intList.contains(34);

    assertFalse(contains);
  }

  @Test
  public void testContains() {
    intList = CustomUniDirectionalLinkedList.of(45, 6, 3, 6);

    boolean containsExistingElement = intList.contains(3);
    boolean containsNotExistingElement = intList.contains(54);

    assertTrue(containsExistingElement);
    assertFalse(containsNotExistingElement);
  }

  @Test
  public void testIsEmptyOnEmptyList() {
    boolean empty = intList.isEmpty();

    assertTrue(empty);
  }

  @Test
  public void testIsEmpty() {
    intList = CustomUniDirectionalLinkedList.of(12, 51, 7);

    boolean empty = intList.isEmpty();

    assertFalse(empty);
  }

  @Test
  public void testSizeOnEmptyList() {
    int size = intList.size();

    assertEquals(0, size);
  }

  @Test
  public void testSize() {
    intList = CustomUniDirectionalLinkedList.of(2, 6, 4, 9, 1);

    int size = intList.size();

    assertEquals(5, size);
  }

  @Test
  public void testClearOnEmptyList() {
    intList.clear();

    assertEquals(0, intList.size());
  }

  @Test
  public void testClearChangesTheSize() {
    intList = CustomUniDirectionalLinkedList.of(1, 8, 678, 500);

    intList.clear();

    assertEquals(0, intList.size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testClearRemovesElements() {
    intList = CustomUniDirectionalLinkedList.of(20, 1, 0, 2);

    intList.clear();
    intList.get(0);
  }
}
