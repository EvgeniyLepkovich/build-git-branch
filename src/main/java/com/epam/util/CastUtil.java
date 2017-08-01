package com.epam.util;

import java.util.ArrayList;
import java.util.Collections;

public class CastUtil<T> {
    public ArrayList<T> castIterableToArrayList(Iterable<T> ts) {
        ArrayList<T> result = new ArrayList<>();
        ts.iterator().forEachRemaining(result::add);
        return result;
    }
}
