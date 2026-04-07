package ru.kuzhleva.codereview.humans;

import ru.kuzhleva.codereview.errors.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanWithFatherProgram {
    // Класс Имя
    static Check check = new Check();
    public static class Name {
        private String lastName;
        private String firstName;
        private String middleName;
        //static check check = new check();

        public Name() {}

        public Name(String lastName, String firstName, String middleName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
        }

        // Геттеры
        public String getLastName() { return lastName; }
        public String getFirstName() { return firstName; }
        public String getMiddleName() { return middleName; }

        // Сеттеры
        public void setLastName(String lastName) { this.lastName = lastName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public void setMiddleName(String middleName) { this.middleName = middleName; }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();

            if (check.isSet(lastName)) result.append(lastName);
            if (check.isSet(firstName)) {
                if (result.length() > 0) result.append(" ");
                result.append(firstName);
            }
            if (check.isSet(middleName)) {
                if (result.length() > 0) result.append(" ");
                result.append(middleName);
            }

            return result.toString();
        }

        // Статический метод для создания имени с клавиатуры
        public static Name fromKeyboard(Scanner scanner) {
            Name name = new Name();

            System.out.println("=================ВВОД ИМЕНИ====================");

            // Ввод фамилии
            while(true) {
                System.out.print("Введите фамилию (Enter - пропустить): ");
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) break;
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
                if (firstName.isEmpty()) break;

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
                if (middleName.isEmpty()) break;
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

            if (!check.isSet(name.lastName) && !check.isSet(name.firstName) && !check.isSet(name.middleName)) {
                System.out.println("Ошибка: не введено ни одного поля");
                return null;
            }

            System.out.println("Создано имя: " + name);
            return name;
        }
    }

    // Класс Человек с отцом
    public static class Human {
        private Name name;
        private int height;
        private Human father; // Отец - тоже человек

        // Конструкторы
        public Human() {}

        public Human(Name name, int height) {
            this.name = name;
            this.height = height;
        }

        public Human(Name name, int height, Human father) {
            this.name = name;
            this.height = height;
            this.father = father;
            updateNameFromFather(); // Обновляем имя на основе отца
        }

        // Геттеры
        public Name getName() { return name; }
        public int getHeight() { return height; }
        public Human getFather() { return father; }

        // Сеттеры
        public void setName(Name name) { this.name = name; }
        public void setHeight(int height) { this.height = height; }

        public void setFather(Human father) {
            this.father = father;
            updateNameFromFather(); // Обновляем имя при установке отца
        }

        // Метод для обновления имени на основе отца
        private void updateNameFromFather() {
            if (father != null && father.name != null) {
                // Если у человека нет фамилии, а у отца есть - берем фамилию отца
                if (!check.isSet(name.getLastName()) && check.isSet(father.name.getLastName())) {
                    name.setLastName(father.name.getLastName());
                }

                // Если у человека нет отчества, а у отца есть имя - создаем отчество
                if (!check.isSet(name.getMiddleName()) && check.isSet(father.name.getFirstName())) {
                    String fatherName = father.name.getFirstName();
                    String middleName = fatherName + "ович"; // Простой суффикс
                    name.setMiddleName(middleName);
                }
            }
        }

        @Override
        public String toString() {
            return name + ", рост: " + height + (father != null ? " (сын)" : "");
        }

        // Статический метод для создания человека с клавиатуры
        public static Human fromKeyboard(Scanner scanner) {
            System.out.println("=================ВВОД ДАННЫХ ЧЕЛОВЕКА=================");

            Name name = Name.fromKeyboard(scanner);
            if (name == null) return null;

            int height = check.inputNum("Введите рост (целое число): ");
            // FIX_ME: нет проверки, что рост не подходит в определенные рамки
            //while (height <= 0) {
            while (height <= 0 || height > 272 || height < 54) {
                System.out.println("Рост должен быть положительным числом.");
                System.out.println("Рост не может быть выше 272 см.");
                System.out.println("Рост не может быть ниже 54 см.");
                height = HumanProgram.Name.check.inputNum("Введите рост (целое число): ");
            }

            return new Human(name, height);
        }
    }

    // Класс для управления людьми
    public static class HumanManager {
        private List<Human> humans;
        private Scanner scanner;

        public HumanManager() {
            this.humans = new ArrayList<>();
            this.scanner = new Scanner(System.in);
        }

        // Создание людей по заданию
        public void createTaskPeople() {
            // 1. Создать людей: Чудова Ивана, Чудова Петра, Бориса
            Human ivan = new Human(new Name("Чудов", "Иван", null), 175);
            Human petr = new Human(new Name("Чудов", "Петр", null), 180);
            Human boris = new Human(new Name(null, "Борис", null), 170);

            // 2. Сделать Ивана отцом Петра, а Петра отцом Бориса
            petr.setFather(ivan);
            boris.setFather(petr);

            humans.add(ivan);
            humans.add(petr);
            humans.add(boris);

        }

        //меню
        public void showMenu() {
            System.out.println("====================МЕНЮ УПРАВЛЕНИЯ===================");
            System.out.println("1. Добавить нового человека");
            System.out.println("2. Вывести всех людей");
            System.out.println("3. Установить отца для человека");
            System.out.println("4. Создать людей по заданию");
            System.out.println("5. Выход");
            System.out.print("Выберите действие (1-5): ");
        }

        // Добавить нового человека
        public void addNewHuman() {
            Human newHuman = Human.fromKeyboard(scanner);
            if (newHuman != null) {
                humans.add(newHuman);
                System.out.println("Человек добавлен в список");
            }
        }

        // Установить отца для человека
        public void setFatherForHuman() {
            if (humans.size() < 2) {
                System.out.println("Нужно как минимум 2 человека в списке");
                return;
            }

            System.out.println("Выберите человека:");
            for (int i = 0; i < humans.size(); i++) {
                System.out.println((i + 1) + ". " + humans.get(i));
            }

            int humanIndex = check.inputNum("Номер человека: ") - 1;
            if (humanIndex < 0 || humanIndex >= humans.size()) {
                System.out.println("Неверный номер");
                return;
            }

            System.out.println("Выберите отца:");
            for (int i = 0; i < humans.size(); i++) {
                if (i != humanIndex) {
                    System.out.println((i + 1) + ". " + humans.get(i));
                }
            }

            int fatherIndex = check.inputNum("Номер отца: ") - 1;
            if (fatherIndex < 0 || fatherIndex >= humans.size() || fatherIndex == humanIndex) {
                System.out.println("Неверный номер");
                return;
            }

            humans.get(humanIndex).setFather(humans.get(fatherIndex));
            System.out.println("Отец установлен");
        }

        // Показать всех людей
        public void displayAllHumans() {
            System.out.println("\n==================СПИСОК ВСЕХ ЛЮДЕЙ====================");

            if (humans.isEmpty()) {
                System.out.println("Список людей пуст");
                return;
            }

            for (int i = 0; i < humans.size(); i++) {
                Human human = humans.get(i);
                String fatherInfo = "";
                if (human.getFather() != null) {
                    fatherInfo = " (сын " + human.getFather().getName().getFirstName() + ")";
                }
                System.out.println((i + 1) + ". " + human + fatherInfo);
            }
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
                        setFatherForHuman();
                        break;
                    case "4":
                        createTaskPeople();
                        break;
                    case "5":
                        running = false;
                        System.out.println("Выход из программы");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите 1-5");
                }
            }

            scanner.close();
        }
    }
}
