package ua.edu.ucu.collections.immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ImmutableLinkedList implements ImmutableList {
    private ListNode firstNode;
    private ListNode lastNode;
    private int size;

    public ImmutableLinkedList() {
        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        Object[] toAdd = {e};
        return addAll(toAdd);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        Object[] toAdd = {e};
        return addAll(index, toAdd);
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);

        for (Object o : c) {
            ListNode node = new ListNode(o, null, null);

            if (size == 0) {
                result.firstNode = node;
                result.lastNode = node;
                result.size = 1;
            } else {
                node.setPrev(result.lastNode);
                result.lastNode.setNext(node);
                result.lastNode = node;
                result.size += 1;
            }

        }
        return result;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);

        ListNode tempFirsNode = new ListNode(c[0], null, null);
        ListNode tempLastNode = tempFirsNode;

        for (int i = 1; i < c.length; i++) {
            ListNode newNode = new ListNode(c[i], null, tempLastNode);
            tempLastNode.setNext(newNode);
            tempLastNode = newNode;
        }

        ListNode workNode = result.firstNode;
        int i = 0;
        while (i < index) {
            workNode = workNode.getNext();
            i += 1;
        }
        if (size == 0) {
            result.firstNode = tempFirsNode;
            result.lastNode = tempLastNode;
            result.size = c.length;
        } else {
            if (index == 0) {
                result.firstNode.setPrev(tempLastNode);
                tempLastNode.setNext(result.firstNode);
                result.firstNode = tempFirsNode;
            }
            else if (index == size) {
                result.lastNode.setNext(tempFirsNode);
                tempLastNode.setPrev(result.lastNode);
                result.lastNode = tempLastNode;
            }
            else {
                tempLastNode.setNext(workNode);
                tempFirsNode.setPrev(workNode.getPrev());
                workNode.getPrev().setNext(tempFirsNode);
                workNode.setPrev(tempFirsNode);
            }
            result.size += c.length;
        }
        return result;
    }

    @Override
    public Object get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ListNode node = firstNode;
        int i = 0;
        while (i < index) {
            node = node.getNext();
            i += 1;
        }
        return node.getValue();
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);

        ListNode node = result.firstNode;
        int i = 0;
        while (i < index) {
            node = node.getNext();
            i += 1;
        }

        if (index != 0) {
            node.getPrev().setNext(node.getNext());
        } else {
            result.firstNode = result.firstNode.next;
        }
        if (index < size - 1) {
            node.getNext().setPrev(node.getPrev());
        } else {
            result.lastNode = result.lastNode.getPrev();
        }
        result.size -= 1;

        return result;

    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);

        ListNode node = result.firstNode;
        int i = 0;
        while (i < index) {
            node = node.getNext();
            i += 1;
        }
        node.setValue(e);

        return result;
    }

    @Override
    public int indexOf(Object e) {
        ListNode node = firstNode;
        int i = 0;
        while (i < size) {
            if (node.getValue().equals(e)) {
                break;
            }
            node = node.getNext();
            i += 1;
        }
        if (i == size) {
            return -1;
        }
        return i;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        ListNode node = firstNode;
        int i = 0;
        while (i < size) {
            result[i] = node.getValue();
            node = node.getNext();
            i += 1;
        }
        return result;
    }

    public ImmutableLinkedList addFirst(Object e) {
        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);
        ListNode node = new ListNode(e, result.firstNode, null);

        if (size == 0) {
            result.lastNode = node;
            result.firstNode = node;
            result.size += 1;
            return result;
        }

        result.firstNode.setPrev(node);
        result.firstNode = node;
        result.size += 1;
        return result;
    }

    public ImmutableLinkedList addLast(Object e) {
        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);
        ListNode node = new ListNode(e, null, result.lastNode);

        if (size == 0) {
            result.lastNode = node;
            result.firstNode = node;
            result.size += 1;
            return result;
        }

        result.lastNode.setNext(node);
        result.lastNode = node;
        result.size += 1;
        return result;
    }

    public Object getFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return firstNode.getValue();
    }

    public Object getLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return lastNode.getValue();
    }

    public ImmutableLinkedList removeFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);
        result.firstNode = result.firstNode.getNext();
        result.size -= 1;
        if (result.size == 0) {
            result.lastNode = null;
        }
        return result;
    }

    public ImmutableLinkedList removeLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList result = new ImmutableLinkedList();
        copyList(this, result);
        result.lastNode = result.lastNode.getPrev();
        result.size -= 1;
        if (result.size == 0) {
            result.firstNode = null;
        }
        return result;
    }

    @Getter @Setter @AllArgsConstructor
    private static class ListNode {
        private Object value;
        private ListNode next;
        private ListNode prev;
    }

    private void copyList(ImmutableLinkedList from, ImmutableLinkedList to) {
        if (from.size >= 1) {
            ListNode node = new ListNode(from.firstNode.getValue(),
                    from.firstNode.getNext(), from.firstNode.getPrev());
            to.firstNode = node;

            while (node.getNext() != null) {
                ListNode nodeNew = new ListNode(node.getNext().getValue(),
                        node.getNext().getNext(), node);
                node.setNext(nodeNew);
                node = nodeNew;
            }

            to.lastNode = node;
            to.size = from.size;
        }
    }

    @Override
    public String toString() {
        ListNode node = firstNode;
        int i = 0;
        StringBuilder result = new StringBuilder();
        while (i < size) {
            result.append(node.value.toString());
            result.append(",");
            node = node.getNext();
            i += 1;
        }
        return result.toString();
    }
}
