package operation.operations;

import operation.Operation;

public class divide implements Operation {
    @Override
    public String toString() {
        return "Divide";
    }

    @Override
    public String desc() {
        return "Divides the value of the left-hand-side with the value of the right-hand-side";
    }

    @Override
    public double perform(double lhs, double rhs) {
        return lhs / rhs;
    }
}
