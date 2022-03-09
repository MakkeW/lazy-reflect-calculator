import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LazyCalculatorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);

    }

    @Test
    void test1() throws IOException {
        lazyCalculator.main(new String[]{"src/test1.txt"});
        String[] outputs = outContent.toString().split("\r\n");
        assertArrayEquals(outputs, new String[]{"5.0", "3.0", "6.0"});
    }

    @Test
    void test2() throws IOException {
        lazyCalculator.main(new String[]{"src/test2.txt"});
        String[] outputs = outContent.toString().split("\r\n");
        assertArrayEquals(outputs, new String[]{"90.0"});
    }

    @Test
    void errorTest() throws IOException {
        lazyCalculator.main(new String[]{"src/test3.txt"});
        String[] outputs = errContent.toString().split("\r\n");
        assertArrayEquals(outputs, new String[]{
                "Invalid command: 'a!'",
                "'a!' is not a valid(alphanumeric) name for a register ",
                "Invalid command: 'pr1nt' ",
                "'a_' is not a valid(alphanumeric) name for a register ",
                "'adds' is not a operation ",
                "'t_' is not a valid register(alphanumeric) or value(numeric) "
        });
    }

    @Test
    void cycleTest() throws IOException {
        lazyCalculator.main(new String[]{"src/test4.txt"});
        String[] outputs = outContent.toString().split("\r\n");
        assertArrayEquals(outputs, new String[]{"10.0", "10.0", "10.0"});
    }

    @Test
    void nonCycleTest() throws IOException {
        lazyCalculator.main(new String[]{"src/test5.txt"});
        String[] outputs = outContent.toString().split("\r\n");
        assertArrayEquals(outputs, new String[]{"30.0"});
    }

    @Test
    void divisionTest() throws IOException {
        lazyCalculator.main(new String[]{"src/test6.txt"});
        String[] outputs = outContent.toString().split("\r\n");
        assertArrayEquals(outputs, new String[]{"5.0"});
    }


}