package org.example;


/**
 * @author Viktor Shvidkiy
 */
public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] buckets;
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

    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }

        int hash = hash(key);

        if (size >= buckets.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.hash == hash && (current.key == key || current.key.equals(key))) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        buckets[index] = new Node<>(hash, key, value, buckets[index]);
        size++;
        return null;
    }

    public V get(K key){
        if (key==null){
            throw new IllegalArgumentException("Ключ не может быть null");
        }

        int hash = hash(key);
        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current!=null){
            if(current.hash==hash&&current.key.equals(key)){
                return current.value;
            }
            current=current.next;
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }

        int hash = hash(key);
        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.hash == hash && (current.key == key || current.key.equals(key))) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    private int getIndex(K key) {
        return hash(key) & (buckets.length - 1);
    }

    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[oldBuckets.length * 2];

        for (Node<K, V> head : oldBuckets) {
            Node<K, V> current = head;
            while (current != null) {
                Node<K, V> next = current.next;
                int newIndex = current.hash & (newBuckets.length - 1);
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                current = next;
            }
        }
        buckets = newBuckets;
    }

    private int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
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