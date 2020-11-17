package com;

import java.lang.Iterable;
import java.util.Iterator;

public class SLinkedList<E extends DeepClone<E>> implements Iterable<E> {
    private SNode<E> head;
    private SNode<E> tail;
    private int size;

    SLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(int i, E element) {
        if ((i < 0) || (i > size))
            throw new IndexOutOfBoundsException();

        if (i == 0)
            addFirst(element);
        else if (i == size)
            addLast(element);
        else {
            SNode<E> previousNode = getNode(i - 1);     //  undefined if i==0
            SNode<E> newNode = new SNode<E>(element);
            newNode.next = previousNode.next;
            previousNode.next = newNode;
            size++;
        }
    }

    public void addFirst(E element) {
        SNode<E> newNode = new SNode<E>(element);
        size++;

        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    public void addLast(E element) {
        SNode<E> newNode = new SNode<E>(element);
        size++;
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public E get(int i) {
        return getNode(i).element;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public E remove(int i) {
        if ((i < 0) || (i >= size))
            throw new IndexOutOfBoundsException();
        else {

            //  first deal with special case that size == 1, i == 0
            if (size == 1) //  only one node in list
            {
                size--;
                SNode<E> cur = head;
                head = null;
                tail = null;
                return cur.element;
            }

            //  Now we can assume that size > 1.
            SNode<E> cur = head;
            size--;

            //  We first deal with case in which we want to remove the first element
            if (i == 0) {
                head = head.next;
                return cur.element;
            } else //  we can assume that i > 0
            {
                cur = getNode(i - 1);
                SNode<E> nodeToRemove = cur.next;
                cur.next = nodeToRemove.next;
                if (nodeToRemove.next == null)   //  removing the tail
                    tail = cur;
                else
                    nodeToRemove.next = null;   //  be safe
                return nodeToRemove.element;
            }
        }
    }


    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public void set(int i, E e) {
        if ((i < 0) || (i >= size))
            throw new IndexOutOfBoundsException();
        else
            getNode(i).element = e;
    }

    @Override
    public String toString() {
        String ret = "List:";
        // If it's an empty list
        if (head == null)
            return "";

        // Exploit E.toString()
        SNode<E> current = head;
        while (current != null) {
            ret += " " + current.element.toString();
            current = current.next;
        }

        ret += "\nSize: " + this.size;
        return ret;
    }

    public SLinkedList<E> deepClone() {
        SLinkedList<E> copy = new SLinkedList<E>();
        SNode<E> target = this.head;
        int i = 0;
        while (target != null) {
            copy.add(i, target.element.deepClone());
            i++;
            target = target.next;
        }
        copy.size = i;
        return copy;
    }

    // The following method is private. The client has no access to the nodes of the linked list,
    // but rather the client can only access the elements that are stored in the list.
    private SNode<E> getNode(int i) {
        if ((i < 0) || (i >= size))
            throw new IndexOutOfBoundsException();
        else {
            if (i == 0)   //  only one node in list
                return head;
            else {
                int index = 0;
                SNode<E> cur = head;
                while (index < i) {
                    cur = cur.next;
                    index++;
                }
                return cur;
            }
        }
    }

    // inner class defining the data type for a node in the list
    // also private since this is a data type needed only within this class.
    private class SNode<T> {   //  I have to use a different generic type since I am defining a class here.

        private T element;
        private SNode<T> next;

        SNode(T element) {
            this.element = element;
            next = null;
        }
    }

    @Override
    public SLLIterator iterator() {
        SLLIterator iter = new SLLIterator(this);
        return iter;
    }

    private class SLLIterator implements Iterator<E> {   // use a different generic type, since we're defining a new class here
        SNode<E> cur;

        SLLIterator(SLinkedList<E> list) {
            cur = list.head;
        }

        @Override
        public boolean hasNext() {
            return (cur != null);
        }

        @Override
        public E next() {
            SNode<E> tmp = cur;
            cur = cur.next;
            return tmp.element;
        }
    }
}

