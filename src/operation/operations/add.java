package operation.operations;

import operation.Operation;

public class add implements Operation {
    @Override
    public String toString() {
        return "Add";
    }

    @Override
    public double perform(double lhs, double rhs) {
        return lhs + rhs;
    }

    @Override
    public String desc() {
        return "Adds the right hand value to the left hand value";
    }
}
