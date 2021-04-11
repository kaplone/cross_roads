package com.codingame.game;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class LineFifo extends LinkedList<Car> {

    private Integer id;
    private Integer liberation_in;
    private Integer liberation_out;
    private Integer liberation_before_in;

    public LineFifo(Integer id, Integer liberation_in, Integer liberation_out, Integer liberation_before_in) {
        super();
        this.id = id;
        this.liberation_in = liberation_in;
        this.liberation_out = liberation_out;
        this.liberation_before_in = liberation_before_in;
    }
}
