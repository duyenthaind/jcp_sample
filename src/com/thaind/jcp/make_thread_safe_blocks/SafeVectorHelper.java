package com.thaind.jcp.make_thread_safe_blocks;

import java.util.Vector;

import com.thaind.race_condition.lazyinit.ThreadSafe;

@ThreadSafe
public class SafeVectorHelper {
    public static Object getLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
    }
}
