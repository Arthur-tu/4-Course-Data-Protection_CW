package sample.logic;

import sample.сontrollers.Controller;

import java.util.regex.Pattern;

public class KeyAnalyzer {
    private String key;

    public KeyAnalyzer(String key) {
        this.key = key;
    }

    public boolean analyze() {
        boolean res1, res2, res3, res4, res5;

        res1 = key.length() <= Controller.limitObject.getMax() && key.length() >= Controller.limitObject.getMin();
        String regex = "[^0-9]+";
        Pattern pattern = Pattern.compile(regex);

        if (!Controller.limitObject.isDigits())
            res2 = pattern.matcher(key).matches();
        else
            res2 = !pattern.matcher(key).matches();

        regex = "[^a-z]+";
        pattern = Pattern.compile(regex);

        if (!Controller.limitObject.isLowerCase())
            res3 = pattern.matcher(key).matches();
        else
            res3 = !pattern.matcher(key).matches();

        regex = "[^A-Z]+";
        pattern = Pattern.compile(regex);

        if (!Controller.limitObject.isUpperCase())
            res4 = pattern.matcher(key).matches();
        else
            res4 = !pattern.matcher(key).matches();

        regex = "[^!@^#$%&*()_+=|<>?{}\\\\[\\\\]~-]+";
        pattern = Pattern.compile(regex);

        if (!Controller.limitObject.isSpecialSym())
            res5 = pattern.matcher(key).matches();
        else
            res5 = !pattern.matcher(key).matches();

        return res1 && res2 && res3 && res4 && res5;
    }

    public boolean validLetters() {
        boolean res1, res2;
        String regex = "[^а-я]+";
        Pattern pattern = Pattern.compile(regex);
        res1 = pattern.matcher(key).matches();

        regex = "[^А-Я]+";
        pattern = Pattern.compile(regex);
        res2 = pattern.matcher(key).matches();
        return res1 && res2;
    }
}
