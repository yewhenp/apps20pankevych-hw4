package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableList;

public class Queue {
    private ImmutableList data;
    private int size;

    public Queue(ImmutableList data) {
        this.data = data;
        size = 0;
    }

    public Object peek() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return data.get(0);
    }

    public Object dequeue() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        Object value = data.get(0);
        this.data = data.remove(0);
        size += 1;
        return value;
    }

    public void enqueue(Object e) {
        this.data = data.add(e);
        size += 1;
    }

    public String toString() {
        return data.toString();
    }
}
