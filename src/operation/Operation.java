package operation;

public interface Operation {
    @Override
    String toString();

    String desc();

    double perform(double lhs, double rhs);
}