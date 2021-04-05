package com.codingame.game;

import java.util.Stack;

public class LineStack extends Stack<Car> {

    private Integer id;
    private Integer liberation_in;
    private Integer liberation_out;
    private Integer liberation_before_in;

    public LineStack(Integer id, Integer liberation_in, Integer liberation_out, Integer liberation_before_in) {
        super();
        this.id = id;
        this.liberation_in = liberation_in;
        this.liberation_out = liberation_out;
        this.liberation_before_in = liberation_before_in;
    }
}
