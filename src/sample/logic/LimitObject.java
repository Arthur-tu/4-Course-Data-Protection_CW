package sample.logic;

public class LimitObject {
    private Integer max;
    private Integer min;
    private boolean digits;
    private boolean lowerCase;
    private boolean upperCase;
    private boolean specialSym;

    public LimitObject() {

    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setDigits(boolean digits) {
        this.digits = digits;
    }

    public void setLowerCase(boolean lowerCase) {
        this.lowerCase = lowerCase;
    }

    public void setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
    }

    public void setSpecialSym(boolean specialSym) {
        this.specialSym = specialSym;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getMin() {
        return min;
    }

    public boolean isDigits() { return digits; }

    public boolean isLowerCase() {
        return lowerCase;
    }

    public boolean isUpperCase() {
        return upperCase;
    }

    public boolean isSpecialSym() {
        return specialSym;
    }
}
