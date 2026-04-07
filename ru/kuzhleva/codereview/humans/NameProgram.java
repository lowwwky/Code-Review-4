package ru.kuzhleva.codereview.humans;

import ru.kuzhleva.codereview.errors.Check;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class NameProgram {
    public static class Name {
        private String lastName;
        private String firstName;
        private String middleName;

        // FIX_ME: В Google Code Style название всех классов пишется большой буквы
        //static check check = new check(); //проверки
        static Check check = new Check(); //

        public Name() {
        }

        public Name(String lastName, String firstName, String middleName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
        }

        //методы для получения приватных полей
        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        //методы для изменения приватных полей
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        @Override //приведение объекта к строке
        public String toString() {
            StringBuilder result = new StringBuilder();

            // Добавляем фамилию, если она задана
            if (check.isSet(lastName)) {
                result.append(lastName);
            }

            // Добавляем имя, если оно задано
            if (check.isSet(firstName)) {
                if (result.length() > 0) {
                    result.append(" ");  // Добавляем пробел, если уже есть фамилия
                }
                result.append(firstName);
            }

            // Добавляем отчество, если оно задано
            if (check.isSet(middleName)) {
                if (result.length() > 0) {
                    result.append(" ");  // Добавляем пробел, если уже есть другие части
                }
                result.append(middleName);
            }
            return result.toString();

        }

        public static Name fromKeyboard(Scanner scanner) {
            Name name = new Name();

            System.out.println("\n============ДОБАВЛЕНИЕ НОВОГО ИМЕНИ===============");

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

            System.out.println("Успешно добавлено: " + name);
            return name;
        }
    }

    //класс для управления
    public static class NameManager {
        private List<Name> names;  // Коллекция для хранения имен
        private Scanner scanner;   // ввод с клавиатуры

        // Конструктор
        public NameManager() {
            this.names = new ArrayList<>();
            this.scanner = new Scanner(System.in);
            initializeSampleData(); // Добавляем примеры при создании
        }

        // Инициализация начальными данными
        private void initializeSampleData() {
            names.add(new Name(null, "Клеопатра", null));
            names.add(new Name("Пушкин", "Александр", "Сергеевич"));
            names.add(new Name("Маяковский", "Владимир", null));
            System.out.println("Добавлены примеры из задания:");
            System.out.println("1. Клеопатра");
            System.out.println("2. Пушкин Александр Сергеевич");
            System.out.println("3. Маяковский Владимир");
        }

        // Показать меню
        public void showMenu() {
            System.out.println("\n====================МЕНЮ УПРАВЛЕНИЯ===================");
            System.out.println("1. Добавить новое ФИО");
            System.out.println("2. Вывести все ФИО");
            System.out.println("3. Выход");
            System.out.print("Выберите действие (1-3): ");
        }

        // Добавить новое имя
        public void addNewName() {
            Name newName = Name.fromKeyboard(scanner);
            if (newName != null) {
                names.add(newName);
                System.out.println("Имя добавлено в список");
            }
        }

        // Показать все имена
        public void displayAllNames() {
            System.out.println("\n===================СПИСОК ВСЕХ ИМЕН====================");
            if (names.isEmpty()) {
                System.out.println("Список имен пуст");
                return;
            }

            for (int i = 0; i < names.size(); i++) {
                System.out.println((i + 1) + ". " + names.get(i));
            }

            System.out.println("Всего имен: " + names.size());
        }

        public void run() {

            boolean running = true;

            while (running) {
                showMenu();
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addNewName();
                        break;
                    case "2":
                        displayAllNames();
                        break;
                    case "3":
                        running = false;
                        System.out.println("Выход из программы");
                        break;
                    default:
                        System.out.println("Неверный выбор");
                }
            }

            scanner.close();
        }
    }

}
