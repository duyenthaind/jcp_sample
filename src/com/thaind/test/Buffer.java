package com.thaind.test;

public class Buffer<E> {
    class Node {
        E item;
        Node next;

        public Node(E item) {
            this.item = item;
        }
    }

    private Node head, last;
    private final int capacity;
    private int count;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    private void add(E item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = last = newNode;
        } else {
            last = last.next = newNode;
        }
        ++count;
    }

    private E poll() {
        Node h = head;
        head = h.next;
        h.next = null;
        --count;
        return h.item;
    }

    private void printWithThreadName(String str) {
        System.out.println("* " + Thread.currentThread().getName() + ": " + str);
    }

    public synchronized void put(E item) {
        printWithThreadName("yêu cầu đưa item " + item + " vào bộ đệm");
        while (count == capacity) {
            System.out.println("Bộ đệm đầy! Chờ đến khi có chỗ trống mới...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printWithThreadName("đưa item " + item + " vào bộ đệm và thông báo cho người tiêu dùng");
        notifyAll();
        add(item);
        System.out.print("Thông tin bộ đệm: ");
        printBuffer();
    }

    public synchronized E take() {
        printWithThreadName("Yêu cầu tiêu thụ bộ đệm");
        while (count == 0) {
            System.out.println("Bộ đệm trống! Chờ đến khi có phần tử mới...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        E item = poll();
        printWithThreadName("tiêu thụ phần tử " + item + " và thông báo cho nhà sản xuất");
        notifyAll();
        System.out.print("Thông tin bộ đệm: ");
        printBuffer();
        return item;
    }

    private void printBuffer() {
        Node q = head;
        System.out.print("[");
        while (q != null) {
            System.out.print(q.item + ", ");
            q = q.next;
        }
        System.out.println("]");
    }
}
