package ua.edu.ucu.collections.immutable;

public class ImmutableArrayList implements ImmutableList {
    private final int size;
    private final Object[] data;

    public ImmutableArrayList(int size) {
        this.size = size;
        this.data = new Object[size];
    }

    public ImmutableArrayList() {
        this.size = 0;
        this.data = new Object[size];
    }

    @Override
    public ImmutableArrayList add(Object e) {
        ImmutableArrayList result = new ImmutableArrayList(size + 1);
        copyValues(this, result);
        result.data[result.size - 1] = e;
        return result;
    }

    @Override
    public ImmutableArrayList add(int index, Object e) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableArrayList result = new ImmutableArrayList(size + 1);
        int fromIndex = 0;
        for (int i = 0; i < size + 1; i++) {
            if (i != index) {
                result.data[i] = data[fromIndex];
                fromIndex += 1;
            } else {
                result.data[i] = e;
            }
        }
        return result;
    }

    @Override
    public ImmutableArrayList addAll(Object[] c) {
        ImmutableArrayList result = new ImmutableArrayList(size + c.length);
        copyValues(this, result);

        for (int i = 0; i < c.length; i++) {
            result.data[result.size - (c.length - i)] = c[i];
        }
        return result;
    }

    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableArrayList result = new ImmutableArrayList(size + c.length);
        int fromIndex = 0;
        int cIndex = 0;
        for (int i = 0; i < size + c.length; i++) {
            if (i < index || i >= index + c.length) {
                result.data[i] = data[fromIndex];
                fromIndex += 1;
            } else {
                result.data[i] = c[cIndex];
                cIndex += 1;
            }
        }
        return result;
    }

    @Override
    public Object get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    @Override
    public ImmutableArrayList remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableArrayList result = new ImmutableArrayList(size - 1);
        int toIndex = 0;
        for (int i = 0; i < size; i++) {
            if (i != index) {
                result.data[toIndex] = data[i];
                toIndex += 1;
            }
        }
        return result;
    }

    @Override
    public ImmutableArrayList set(int index, Object e) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableArrayList result = new ImmutableArrayList(size);
        copyValues(this, result);
        result.data[index] = e;
        return result;
    }

    @Override
    public int indexOf(Object e) {
        int index = -1;

        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableArrayList clear() {
        return new ImmutableArrayList(0);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(data[i].toString());
            result.append(",");
        }
        return result.toString();
    }

    private void copyValues(ImmutableArrayList from, ImmutableArrayList to) {
        System.arraycopy(from.data, 0, to.data, 0, from.size);
    }
}
