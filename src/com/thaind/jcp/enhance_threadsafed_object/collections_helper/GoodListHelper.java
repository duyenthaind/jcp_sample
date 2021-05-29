package com.thaind.jcp.enhance_threadsafed_object.collections_helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thaind.race_condition.lazyinit.ThreadSafe;

@ThreadSafe
public class GoodListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E element) {
        synchronized (list) {
            boolean absent = !list.contains(element);
            if (absent) {
                list.add(element);
            }
            return absent;
        }
    }
}
