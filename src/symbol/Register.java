package symbol;

import operation.Operation;

import java.util.ArrayList;

public class Register implements Symbol {

    ArrayList<OperationPair> operationQueue = new ArrayList<>();
    boolean isUsed = false;

    public static boolean isValidInstanceOf(String str) {
        if (str.matches("[0-9]+")) return false; //Can't be only numbers
        return str.matches("[A-Za-z0-9]+");
    }

    @Override
    public double getValue() {
        double value = 0;
        isUsed = true;
        for (OperationPair OpPair : operationQueue) {
            if (!OpPair.val.isUsed()) {   //Ignore used ones to avoid cycles
                value = OpPair.op.perform(value, OpPair.val.getValue());
            }
        }
        isUsed = false;
        return value;
    }

    @Override
    public boolean isUsed() {
        return isUsed;
    }

    public void addOperation(Operation op, Symbol val) {
        operationQueue.add(new OperationPair(op, val));
    }

    class OperationPair {
        Operation op;
        Symbol val;

        public OperationPair(Operation op, Symbol val) {
            this.op = op;
            this.val = val;
        }


    }
}
