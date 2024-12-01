
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class triangle_test {

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, Равносторонний треугольник",
            "3, 3, 4, Равнобедренный треугольник",
            "3, 4, 5, Разносторонний треугольник",
            "2147483647, 2147483647, 2147483647, Равносторонний треугольник",
            "1, 2, 8888, Треугольник не существует",
            "0, 0, 0, Треугольник не существует"
    })
    void testCheckTriangle(int a, int b, int c, String expected) {
        assertEquals(expected, Triangle.checkTriangle(a, b, c),
                "Ошибка проверки треугольника с сторонами " + a + ", " + b + ", " + c);
    }
    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, Равносторонний треугольник",
            "3, 3, 4, Равнобедренный треугольник",
            "3, 4, 5, Разносторонний треугольник",
            "2147483647, 2147483647, 2147483647, Равносторонний треугольник",
    })
    void testGetTriangleType(int a, int b, int c, String expected) {
        assertEquals(expected, Triangle.getTriangleType(a, b, c),
                "Тип треугольника с сторонами " + a + ", " + b + ", " + c);
    }
    @ParameterizedTest
    @CsvSource({
            "'-3', false",
            "'2.2', false",
            "'a', false",
            "'2147483648', false",
            "')', false"
    })
    void testGetValidInput(String input, boolean isValid) {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Scanner scanner = new Scanner(System.in);

        try {
            int result = Triangle.getValidInput(scanner);


            if (!isValid) {
                fail("Expected invalid input but got valid input: " + input);
            }
        } catch (Exception e) {

            if (isValid) {
                fail("Expected valid input but got exception for input: " + input);
            }
        }


        if (!isValid) {
            String errorMessage = outputStream.toString().trim();


            if (input.startsWith("-")) {
                assertTrue(errorMessage.contains("Стороны треугольника должны быть неотрицательными числами. Попробуйте снова."),
                        "Expected error message for input: " + input + " got: " + errorMessage);
            }

            else if (input.contains(".")) {
                assertTrue(errorMessage.contains("Введено дробное число. Требуется целое число. Попробуйте снова."),
                        "Expected error message for input: " + input + " got: " + errorMessage);
            }

            else if (input.matches(".*[a-zA-ZА-Яа-яЁё].*")) {
                assertTrue(errorMessage.contains("Введены буквы или символы. Попробуйте снова."),
                        "Expected error message for input: " + input + " got: " + errorMessage);
            }

            else if (input.matches(".*\\D.*")) {
                assertTrue(errorMessage.contains("Введены недопустимые символы. Попробуйте снова."),
                        "Expected error message for input: " + input + " got: " + errorMessage);
            }

            else if (input.matches(".*[0-9]+.*") && Long.parseLong(input) > Integer.MAX_VALUE) {
                assertTrue(errorMessage.contains("Введённое число превышает Integer.MAX_VALUE[" + Integer.MAX_VALUE + "]. Попробуйте снова."),
                        "Expected error message for input: " + input + " got: " + errorMessage);
            } else {
                fail("Unexpected input: " + input);
            }
        }


        System.setIn(System.in);
        System.setOut(System.out);
    }


}



