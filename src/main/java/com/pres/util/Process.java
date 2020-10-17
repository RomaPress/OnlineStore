package com.pres.util;

import com.pres.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class Process {

    public static List<Product> cutList(List<Product> list, int frequency, int step) {
        int first = (step - 1) * frequency;
        int last = frequency;
        return list.stream()
                .skip(first)
                .limit(last)
                .collect(Collectors.toList());
    }
}
