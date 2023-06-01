package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            checkIndex(index);
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
            for (Node<T> node = first; node != null; node = node.next) {
                    if (node.value == object || object != null && object.equals(node.value)) {
                        unlink(node);
                        return true;
                    }
            }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index! List`s size: "
                    + size + ". You input: " + index);
        }
    }

    private void linkLast(T value) {
        Node<T> newLast = last;
        Node<T> newNode = new Node<>(newLast, value, null);
        last = newNode;
        if (newLast == null) {
            first = newNode;
        } else {
            newLast.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        Node<T> node = first.next;
        int i = 1;
        while (i < index) {
            node = node.next;
            ++i;
        }
        return node;
    }

    private void linkBefore(T value, Node<T> beforeElement) {
        Node<T> prev = beforeElement.prev;
        Node<T> newNode = new Node<>(prev, value, beforeElement);
        beforeElement.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
