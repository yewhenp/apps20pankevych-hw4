package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableList;

public class Stack {
    private ImmutableList data;
    private int size;

    public Stack(ImmutableList data) {
        this.data = data;
        size = 0;
    }

    public Object peek() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return data.get(0);
    }

    public Object pop() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        Object value = data.get(0);
        this.data = data.remove(0);
        size += 1;
        return value;
    }

    public void push(Object e) {
        this.data = data.add(0, e);
        size += 1;
    }

    public String toString() {
        return data.toString();
    }
}
