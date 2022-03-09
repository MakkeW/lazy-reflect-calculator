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

    /**
     * Calculates the value of the register using the queued operations, a Register in process of being
     * calculated can not be used in the calculation of another in order to avoid infinite cycles, in that case it will
     * be skipped. All registers start at zero.
     * @return The value of the register
     */
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

    /**
     * Add an operation/value pair to the queue of operations to be performed on the register
     * @param op Operation to perform
     * @param val Value/Register to use as right hand side of the operation
     */
    public void addOperation(Operation op, Symbol val) {
        operationQueue.add(new OperationPair(op, val));
    }

    /**
     * Help class used for the queue.
     */
    class OperationPair {
        Operation op;
        Symbol val;

        public OperationPair(Operation op, Symbol val) {
            this.op = op;
            this.val = val;
        }


    }
}
