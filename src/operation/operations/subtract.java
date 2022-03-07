package operation.operations;

import operation.Operation;

public class subtract implements Operation {
    @Override
    public String toString(){
        return "Subtract";
    }

    @Override
    public double perform(double lhs, double rhs) {
        return lhs - rhs;
    }

    @Override
    public String desc() {
        return "Subtracts the right-hand value from the left-hand";
    }
}
