package com.zabramnyi;

public class CustomUniDirectionalLinkedList<T> implements UniDirectionalLinkedList<T> {

  private static final class Node<T> {
    private T element;
    private Node<T> next;

    private Node(T element) {
      this.element = element;
    }

    static <T> Node<T> valueOf(T element) {
      return new Node<>(element);
    }
  }

  private Node<T> head;
  private int size;

  /**
   * This method creates a list of provided elements
   *
   * @param elements elements to add
   * @param <T> generic type
   * @return a new list of elements the were passed as method parameters
   */
  public static <T> UniDirectionalLinkedList<T> of(T... elements) {
    UniDirectionalLinkedList<T> list = new CustomUniDirectionalLinkedList<>();
    for (T element : elements) {
      list.add(element);
    }
    return list;
  }

  @Override
  public void add(T element) {
    add(size, element);
  }

  @Override
  public void add(int index, T element) {
    Node<T> newNode = Node.valueOf(element);
    if (index == 0) {
      newNode.next = head;
      head = newNode;
    } else {
      Node<T> node = findNodeByIndex(index - 1);
      newNode.next = node.next;
      node.next = newNode;
    }
    size++;
  }

  @Override
  public void set(int index, T element) {
    Node<T> node = findNodeByIndex(index);
    node.element = element;
  }

  @Override
  public T get(int index) {
    Node<T> node = findNodeByIndex(index);
    return node.element;
  }

  private Node<T> findNodeByIndex(int index) {
    checkIndex(index, size);
    Node<T> currentNode = head;
    for (int i = 0; i < index; i++) {
      currentNode = currentNode.next;
    }
    return currentNode;
  }

  @Override
  public void remove(int index) {
    if (index == 0) {
      checkIndex(index, size);
      head = head.next;
    } else {
      Node<T> previousNode = findNodeByIndex(index - 1);
      previousNode.next = previousNode.next.next;
    }
    size--;
  }

  @Override
  public boolean contains(T element) {
    Node<T> currentNode = head;
    while (currentNode != null) {
      if (currentNode.element.equals(element)) {
        return true;
      }
      currentNode = currentNode.next;
    }
    return false;
  }

  @Override
  public boolean isEmpty() {
    return head == null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void clear() {
    head = null;
    size = 0;
  }

  private void checkIndex(int index, int size) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index" + index + " is out of list size " + size);
  }
}
