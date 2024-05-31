package Tools;

import java.util.Scanner;

public class userinput {

    public static String set_string (String value) {
        Scanner scanner = new Scanner(System.in);
        print(value);
        value = scanner.nextLine();

        return value;
    }
    public static int set_int (String value) {
        int integer;
        Scanner scanner = new Scanner(System.in);
        print(value);
        integer = scanner.nextInt();

        return integer;
    }
    public static double set_double (String value) {
        double integer;
        Scanner scanner = new Scanner(System.in);
        print(value);
        integer = scanner.nextDouble();

        return integer;
    }
    private static void print(String value) {
        System.out.println(value);
    }
}
