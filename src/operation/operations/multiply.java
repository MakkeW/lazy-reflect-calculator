package operation.operations;

import operation.Operation;

public class multiply implements Operation {
    @Override
    public String toString() {
        return "Multiply";
    }

    @Override
    public String desc() {
        return "Multiplies the value of the left-hand-side with the value of the right-hand-side";
    }

    @Override
    public double perform(double lhs, double rhs) {
        return lhs * rhs;
    }
}
