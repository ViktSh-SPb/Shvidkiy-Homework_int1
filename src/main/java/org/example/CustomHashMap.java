package org.example;


/**
 * @author Viktor Shvidkiy
 */
public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<E>[] buckets;
    private int size;

    CustomHashMap() {
        this(DEFAULT_CAPACITY);
    }

    CustomHashMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Задан некорректный размер:" + initialCapacity);
        }

        this.buckets = (Node<E>[]) new Node[initialCapacity];
        this.size = 0;
    }

    private static class Node<E> {
        final int hash;
        final E value;
        Node<E> next;

        Node(int hash, E value, Node<E> next) {
            this.hash = hash;
            this.value = value;
            this.next = next;
        }
    }

    public boolean add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Данная коллекция не поддерживает элементы null");
        }

        int hash = hash(element);

        if (size >= buckets.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(element);
        Node<E> current = buckets[index];

        while (current != null) {
            if (current.hash == hash && (current.value == element || current.value.equals(element))) {
                return false;
            }
            current = current.next;
        }
        buckets[index] = new Node<>(hash, element, buckets[index]);
        size++;
        return true;
    }

    public boolean remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Данная коллекция не поддерживает элементы null");
        }

        int hash = hash(element);
        int index = getIndex(element);
        Node<E> current = buckets[index];
        Node<E> prev = null;

        while (current != null) {
            if (current.hash == hash && (current.value == element || current.value.equals(element))) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev=current;
            current=current.next;
        }
        return false;
    }

    private int getIndex(E element) {
        return hash(element) & (buckets.length - 1);
    }

    private void resize() {
        Node<E>[] oldBuckets = buckets;
        Node<E>[] newBuckets = (Node<E>[]) new Node[oldBuckets.length * 2];

        for (Node<E> head : oldBuckets) {
            Node<E> current = head;
            while (current != null) {
                Node<E> next = current.next;
                int newIndex = current.hash & (newBuckets.length - 1);
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                current = next;
            }
        }
        buckets = newBuckets;
    }

    private int hash(Object element) {
        if (element == null) {
            return 0;
        }
        int h = element.hashCode();
        return h ^ (h >>> 16);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomHashSet");

        boolean firstElement = true;

        for (Node<E> head : buckets) {
            Node<E> current = head;
            while (current != null) {
                if (!firstElement) {
                    sb.append(", ");
                }
                sb.append("\n\t").append(current.value);
                firstElement = false;
                current = current.next;
            }
        }
        return sb.toString();
    }
}