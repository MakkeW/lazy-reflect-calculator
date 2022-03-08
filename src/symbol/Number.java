package symbol;

public class Number implements Symbol {
    double value;

    public Number(double value) {
        this.value = value;
    }

    public static boolean isValidInstanceOf(String str) {
        return str.matches("[0-9]+");
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public boolean isUsed() {
        return false;
    }
}
