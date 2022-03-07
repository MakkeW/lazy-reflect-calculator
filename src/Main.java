import operation.Operation;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        BufferedReader reader;
        if(args.length == 0){
            //manual mode
            Set<Operation> ops = getAllOperations();
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
            reader = new BufferedReader(new InputStreamReader(System.in));
        }else{
            try {
                reader = new BufferedReader(new FileReader(args[0]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        String line = reader.readLine();
        while (line != null){
            String[] tokens = line.split(" ");

            switch (tokens.length) {
                case 1 -> {
                    System.out.println("quit");
                    return;
                }
                case 2 -> System.out.println("print");
                case 3 -> {
                    System.out.println("ops");
                    Operation op = getOperation(tokens[1] + ".class");
                    System.out.println(op.perform(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[2])));
                }
            }
            line = reader.readLine();
        }
    }

    public static Set<Operation> getAllOperations() {
        String packageName = "operation.operations";
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getOperation(line))
                .collect(Collectors.toSet());
    }

    private static Operation getOperation(String className) {
        try {
            Class<?> c1 = Class.forName("operation.operations" + "."
                    + className.substring(0, className.lastIndexOf('.')));
            return  (Operation) c1.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            // handle the exception
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
