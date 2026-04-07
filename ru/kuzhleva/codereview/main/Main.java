package ru.kuzhleva.codereview.main;

import ru.kuzhleva.codereview.errors.Check;
import ru.kuzhleva.codereview.humans.HumanProgram;
import ru.kuzhleva.codereview.humans.HumanWithFatherProgram;
import ru.kuzhleva.codereview.humans.NameProgram;
import ru.kuzhleva.codereview.polylines.LengthPolylineProgram;
import ru.kuzhleva.codereview.polylines.PolylineProgram;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Check check = new Check();

        System.out.print("""
                           ========================================================
                           ======================СПИСОК ЗАДАЧ======================
                           ========================================================
                           1. Задание 1.3 Имена
                           2. Задание 2.2 Человек с именем
                           3. Задание 2.3 Человек с родителем
                           4. Задание 3.2 Ломаная
                           5. Задание 4.9 Создаем ломаную
                           6. Задание 5.7 Длина ломаной
                           """);
        int choice = check.inputNum("Выберите номер задачи: ");
        System.out.println("Задача №" + choice + ":");

        switch (choice) {
            case(1): {
                NameProgram.NameManager manager = new NameProgram.NameManager();
                manager.run();
                break;
            }
            case(2): {
                HumanProgram.HumanManager manager = new HumanProgram.HumanManager();
                manager.run();
                break;
            }
            case(3): {
                HumanWithFatherProgram.HumanManager manager = new HumanWithFatherProgram.HumanManager();
                manager.run();
                break;
            }
            case(4): {
                PolylineProgram.PolylineManager manager = new PolylineProgram.PolylineManager();
                manager.run();
                break;
            }
            case(5): {
                PolylineProgram.PolylineManager manager = new PolylineProgram.PolylineManager();
                manager.run();
                break;
            }
            case(6): {
                LengthPolylineProgram.PolylineManager manager = new LengthPolylineProgram.PolylineManager();
                manager.run();
                break;
            }
        }
    }
}
