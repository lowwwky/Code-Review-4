package ru.kuzhleva.codereview.errors;

import java.util.Scanner;

// fix_me: названия классов пишутся с заглавной буквы.
// public class check {
public class Check {
    private static Scanner in = new Scanner(System.in);
    Scanner keyboard = new Scanner(System.in);

    public static int inputNum(String message) { //проверка на целое число
        int x;
        while (true) {
            System.out.print(message);
            if (in.hasNextInt()) { //hasNextInt()- проверяет, что след. число в потоке ввода = целое число.
                x = in.nextInt(); //считывает след. целое число из потока и возвращает его.
                break;
            } else {
                System.out.println("Ошибка. Введите целое число");
                in.next(); //очищаем неверный ввод
            }
        }
        return x;
    }

    public double checkDouble() {
        double checkNumber;
        String line;
        while (true) {
            line = keyboard.nextLine();
            try {
                checkNumber = Double.parseDouble(line);
                System.out.println();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка. Введено неверное число.");
                System.out.print("Введите число: ");
            }
        }
        return checkNumber;
    }

    public static boolean isSet(String value) { //проверка, что строка не пустая
        return value != null && !value.trim().isEmpty();
    }

    public boolean checkField(String input) {
        if (input.contains(" ")) {
            System.out.println("Ошибка, введите заново");
            return false;
        }
        return true;
    }

    public boolean checkString(String line) {
        boolean valid;
        valid = true;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (!Character.isLetter(ch) && ch != ' ' && ch != '-' && ch != '\'') {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public String checkString() {
        String line;
        boolean valid;

        while (true) {
            line = keyboard.nextLine();
            if (line != null) {
                line = line.trim();
            }

            // Проверка на пустую строку после trim()
            if (line == null || line.isEmpty()) {
                System.out.println("Ошибка. Строка не может быть пустой.");
                System.out.print("Введите новое значение: ");
                continue;
            }

            valid = true;
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (!Character.isLetter(ch) && ch != ' ' && ch != '-' && ch != '\'') {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return line;
            } else {
                System.out.println("Ошибка. Строка должна состоять только из букв, пробелов, '-' или '\''.");
                System.out.print("Введите новое значение: ");
            }
        }
    }

}
