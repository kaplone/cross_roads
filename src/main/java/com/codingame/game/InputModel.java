package com.codingame.game;

import java.util.HashMap;
import java.util.Map;

public class InputModel {

    Map<Integer, String> title = new HashMap<>();
    String testIn;
    Boolean isTest;
    Boolean isValidator;

    public Map<Integer, String> getTitle() {
        return title;
    }

    public void setTitle(Map<Integer, String> title) {
        this.title = title;
    }

    public String getTestIn() {
        return testIn;
    }

    public void setTestIn(String testIn) {
        this.testIn = testIn;
    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public Boolean getValidator() {
        return isValidator;
    }

    public void setValidator(Boolean validator) {
        isValidator = validator;
    }
}
