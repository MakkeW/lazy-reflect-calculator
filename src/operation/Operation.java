package operation;

public interface Operation {
    @Override
    String toString();

    String desc();

    /**
     * Performs the operation as follows lhs OPERATION rhs
     * For example lhs + rhs and returns the result
     * @param lhs left hand side
     * @param rhs right hand side
     * @return result
     */
    double perform(double lhs, double rhs);
}