import operation.Operation;
import symbol.Number;
import symbol.Register;
import symbol.Symbol;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class lazyCalculator {
    static Set<Operation> ops;

    public static void main(String[] args) throws IOException {
        // write your code here
        BufferedReader reader;
        ops = getAllOperations();
        if (args.length == 0) {
            //manual mode
            printHelp();
            reader = new BufferedReader(new InputStreamReader(System.in));
        } else {
            try {
                reader = new BufferedReader(new FileReader(args[0]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }

        HashMap<String, Register> registers = new HashMap<>();

        String line;
        while (true) {
            line = reader.readLine();
            if (line == null) return;
            line = line.toLowerCase(Locale.ROOT);
            String[] tokens = line.split(" ");

            switch (tokens.length) {
                case 1 -> {
                    if (tokens[0].equals("quit")) {
                        return;
                    } else {
                        System.err.printf("Invalid command: '%s'%n", tokens[0]);
                    }
                }
                case 2 -> {
                    if (tokens[0].equals("print")) {
                        if (!Register.isValidInstanceOf(tokens[1])) {
                            System.err.printf("'%s' is not a valid(alphanumeric) name for a register %n", tokens[1]);
                            continue;
                        }
                        System.out.println(registers.getOrDefault(tokens[1], new Register()).getValue()); //Prints zero if no register
                    } else {
                        System.err.printf("Invalid command: '%s' %n", tokens[0]);
                    }
                }
                case 3 -> {

                    if (!Register.isValidInstanceOf(tokens[0])) {
                        System.err.printf("'%s' is not a valid(alphanumeric) name for a register %n", tokens[0]);
                        continue;
                    }

                    registers.putIfAbsent(tokens[0], new Register());
                    Register reg = registers.get(tokens[0]);

                    Operation op = getOperation(tokens[1] + ".class");
                    if (op == null) {
                        continue;
                    }

                    Symbol rhs;

                    if (Number.isValidInstanceOf(tokens[2])) {
                        rhs = new Number(Double.parseDouble(tokens[2]));
                    } else if (Register.isValidInstanceOf(tokens[2])) {
                        registers.putIfAbsent(tokens[2], new Register());
                        rhs = registers.get(tokens[2]);
                    } else {
                        System.err.printf("'%s' is not a valid register(alphanumeric) or value(numeric) %n", tokens[2]);
                        continue;
                    }
                    reg.addOperation(op, rhs);
                }
            }

        }
    }

    public static Set<Operation> getAllOperations() {
        String packageName = "operation.operations";
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) {
            return new HashSet<>();
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(lazyCalculator::getOperation)
                    .collect(Collectors.toSet());
        }
    }

    static void printHelp() {

        System.out.printf("""
                Welcome to the calculator
                Please use one of the following commands:
                quit
                	Quits the program
                print REGISTER
                	Prints the value of the supplied REGISTER
                	Where REGISTER is an alpha(numeric) name, with a default value of 0
                REGISTER_1 OPERATION VALUE/REGISTER_2
                	Performs the supplied OPERATION on the supplied register(s)/value
                	There's currently %d operations:
                """, ops.size());
        for (Operation op : ops) {
            System.out.printf("""
                        %s
                            %s
                    """, op, op.desc());

        }
    }

    private static Operation getOperation(String className) {
        try {
            Class<?> c1 = Class.forName("operation.operations" + "."
                    + className.substring(0, className.lastIndexOf('.')));
            return (Operation) c1.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.printf("'%s' is not a operation %n", className);
        }
        return null;
    }
}
