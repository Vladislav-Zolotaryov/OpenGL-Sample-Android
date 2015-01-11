package com.soft.wz.sample.utils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wz on 01/10/2015.
 */
public class CircularIterator<E> implements Iterator<E> {

    private List<E> list;
    private int index = -1;

    public CircularIterator(List<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public E next() {
        if (++index > (list.size() - 1)) {
            index = 0;
        }
        return list.get(index);
    }

    @Override
    public void remove() {
        list.remove(index);
    }
}
