package com.bai.controller;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SearchControllerTest {
    @Test
    public void SearchControllerTest(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        ArrayList<Integer> integers1 = new ArrayList<>();
        integers1.add(2);
        integers1.add(3);
        integers.addAll(integers1);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }
}
