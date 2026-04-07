package ru.kuzhleva.codereview.humans;

import ru.kuzhleva.codereview.errors.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanProgram {

    // Класс Имя (из задачи 1.3)
    public static class Name {
        private String lastName;
        private String firstName;
        private String middleName;

        //static check check = new check();
        static Check check = new Check();

        public Name(String lastName, String firstName, String middleName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
        }

        public Name() {
        }

        // Геттеры
        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        // Сеттеры
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();

            if (check.isSet(lastName)) {
                result.append(lastName);
            }

            if (check.isSet(firstName)) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(firstName);
            }

            if (check.isSet(middleName)) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(middleName);
            }

            return result.toString();
        }

        //создание имени с клавиатуры
        public static Name fromKeyboard(Scanner scanner) {
            Name name = new Name();

            System.out.println("===============ВВОД ИМЕНИ==================");

            // Ввод фамилии
            while(true) {
                System.out.print("Введите фамилию (Enter - пропустить): ");
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    break;
                }
                // FIX_ME: отстуствуют проверки на корректность вводимых данных (в фамилии/имени/отчестве
                // не бывает цифр.)
//                if (check.checkField(lastName)){
//                    name.lastName = lastName;
//                    break;
//                }

                if (check.checkString(lastName)) {
                    name.lastName = lastName;
                    break;
                }  else {
                    System.out.println("Ошибка. Фамилия должна состоять только из букв, пробелов, '-' или '''.");
                    System.out.print("Попробуйте снова: ");
                }
            }

            // Ввод имени
            while(true) {
                System.out.print("Введите имя (Enter - пропустить): ");
                String firstName = scanner.nextLine().trim();
                if (!firstName.isEmpty()) {
                    name.setFirstName(firstName);
                }

                // FIX_ME: отстуствуют проверки на корректность вводимых данных (в фамилии/имени/отчестве
                // не бывает цифр.)
//                if (check.checkField(firstName)){
//                    name.firstName = firstName;
//                    break;
//                }

                if (check.checkString(firstName)) {
                    name.lastName = firstName;
                    break;
                }  else {
                    System.out.println("Ошибка. Имя должно состоять только из букв, пробелов, '-' или '''.");
                    System.out.print("Попробуйте снова: ");
                }
            }

            // Ввод отчества
            while(true) {
                System.out.print("Введите отчество (Enter - пропустить): ");
                String middleName = scanner.nextLine().trim();
                if (!middleName.isEmpty()) {
                    name.setMiddleName(middleName);
                }
                // FIX_ME: отстуствуют проверки на корректность вводимых данных (в фамилии/имени/отчестве
                // не бывает цифр.)
//                if (check.checkField(middleName)){
//                    name.middleName = middleName;
//                    break;
//                }
                if (check.checkString(middleName)) {
                    name.lastName = middleName;
                    break;
                }  else {
                    System.out.println("Ошибка. Отчество должно состоять только из букв, пробелов, '-' или '''.");
                    System.out.print("Попробуйте снова: ");
                }
            }

            // Проверка, что введено хотя бы одно поле
            if (!name.check.isSet(name.lastName) && !name.check.isSet(name.firstName) && !name.check.isSet(name.middleName)) {
                System.out.println("Ошибка: не введено ни одного поля");
                return null;
            }

            System.out.println("Создано имя: " + name);
            return name;
        }
    }

    // Класс Человек
    public static class Human {
        private Name name;
        private int height;

        // Конструкторы
        public Human() {}

        public Human(Name name, int height) {
            this.name = name;
            this.height = height;
        }

        // Геттеры
        public Name getName() {
            return name;
        }

        public int getHeight() {
            return height;
        }

        // Сеттеры
        public void setName(Name name) {
            this.name = name;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return name + ", рост: " + height;
        }

        // созданиe человека с клавиатуры
        public static Human fromKeyboard(Scanner scanner) {
            System.out.println("=================ВВОД ДАННЫХ ЧЕЛОВЕКА=================");

            // Создаем имя
            Name name = Name.fromKeyboard(scanner);
            if (name == null) {
                return null;
            }

            // Ввод роста
            int height = Name.check.inputNum("Введите рост (целое число) в см: ");

            // Проверка, что рост положительный
            // FIX_ME: нет проверки, что рост не подходит в определенные рамки
            //while (height <= 0) {
            while (height <= 0 || height > 272 || height < 54) {
                System.out.println("Рост должен быть положительным числом.");
                System.out.println("Рост не может быть выше 272 см.");
                System.out.println("Рост не может быть ниже 54 см.");
                height = Name.check.inputNum("Введите рост (целое число): ");
            }

            Human human = new Human(name, height);
            System.out.println("Создан человек: " + human);
            return human;
        }
    }

    // Класс для управления людьми
    public static class HumanManager {
        private List<Human> humans;
        private Scanner scanner;

        public HumanManager() {
            this.humans = new ArrayList<>();
            this.scanner = new Scanner(System.in);
            initializeSampleData();
        }

        // Начальные данные
        private void initializeSampleData() {
            Name name1 = new Name(null, "Клеопатра", null);
            Name name2 = new Name("Пушкин", "Александр", "Сергеевич");
            Name name3 = new Name("Маяковский", "Владимир", null);

            humans.add(new Human(name1, 152));
            humans.add(new Human(name2, 167));
            humans.add(new Human(name3, 189));
        }

        //меню
        public void showMenu() {
            System.out.println("====================МЕНЮ УПРАВЛЕНИЯ===================");
            System.out.println("1. Добавить нового человека");
            System.out.println("2. Вывести всех людей");
            System.out.println("3. Выход");
            System.out.print("Выберите действие (1-3): ");
        }

        // Добавить нового человека
        public void addNewHuman() {
            Human newHuman = Human.fromKeyboard(scanner);
            if (newHuman != null) {
                humans.add(newHuman);
                System.out.println("Человек добавлен в список");
            }
        }

        // Показать всех людей
        public void displayAllHumans() {
            System.out.println("\n==================СПИСОК ВСЕХ ЛЮДЕЙ====================");

            if (humans.isEmpty()) {
                System.out.println("Список людей пуст");
                return;
            }

            for (int i = 0; i < humans.size(); i++) {
                System.out.println((i + 1) + ". " + humans.get(i));
            }

            System.out.println("Всего людей: " + humans.size());
        }

        public void run() {
            System.out.println("=============ПРОГРАММА ДЛЯ РАБОТЫ С ЛЮДЬМИ==============");

            boolean running = true;

            while (running) {
                showMenu();
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addNewHuman();
                        break;
                    case "2":
                        displayAllHumans();
                        break;
                    case "3":
                        running = false;
                        System.out.println("Выход из программы");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите 1, 2 или 3");
                }
            }

            scanner.close();
        }
    }
}
