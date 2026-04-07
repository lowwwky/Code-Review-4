package ru.kuzhleva.codereview.polylines;

import ru.kuzhleva.codereview.errors.Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PolylineProgram {
    //static check check = new check();
    static Check check = new Check();
    static Scanner scanner = new Scanner(System.in);

    // точка на плоскости с координатами (x, y)
    public static class Point {
        // fix_me: координаты точки не обязательно должны быть целыми.
        // fix_me: названия должны быть осмысленными
//        private int x;
//        private int y;
        private double xPoint;
        private double yPoint;

        public Point(double xPoint, double yPoint) {
            // public Point(int x, int y) {
            this.xPoint = xPoint;
            this.yPoint = yPoint;
        }

        //public int getX() {
        public double getxPoint() {
            return xPoint;
        }

        //public int getY() {
        public double getyPoint() {
            return yPoint;
        }
        public void setxPoint(int xPoint) {
            this.xPoint = xPoint;
        }
        public void setyPoint(int yPoint) {
            this.yPoint = yPoint;
        }

        public void move(double dxPoint, double dyPoint) {
            //public void move(int dx, int dy) {
            this.xPoint += dxPoint;
            this.yPoint += dyPoint;
        }

        // Преобразование точки в строку формата "{x;y}"
        @Override
        public String toString() {
            return "{" + xPoint + ";" + yPoint + "}";
        }

        // Статический метод для создания точки через ввод с клавиатуры
        public static Point fromKeyboard() {
            System.out.println("====================ВВОД ТОЧКИ==========================");
//            int x = check.inputNum("Введите координату X: ");
//            int y = check.inputNum("Введите координату Y: ");
            double xPoint, yPoint;

            System.out.println("Введите координату X: ");
            xPoint = check.checkDouble();

            System.out.println("Введите координату Y: ");
            yPoint = check.checkDouble();

            PolylineProgram.Point point = new PolylineProgram.Point(xPoint, yPoint);
            System.out.println("Создана точка: " + point);
            return point;
        }
    }

    //Ломаная - представляет ломаную линию как последовательность точек
    public static class Polyline {
        private List<Point> points;  // Список точек, через которые проходит ломаная

        // Конструкторы
        public Polyline() {
            this.points = new ArrayList<>();  // Создание пустой ломаной
        }

        public Polyline(List<Point> points) {
            this.points = new ArrayList<>(points);  // Создание ломаной из списка точек
        }

        public Polyline(Point... points) {
            this.points = new ArrayList<>(Arrays.asList(points));  // Создание ломаной из массива точек
        }

        // Геттеры для работы с точками ломаной
        public List<Point> getPoints() { return points; }
        public Point getPoint(int index) { return points.get(index); }
        public int getPointCount() { return points.size(); }

        // Методы добавления точек в ломаную
        public void addPoint(Point point) {
            points.add(point);  // Добавление готовой точки
        }

        //public void addPoint(int x, int y) {
        public void addPoint(double xPoint, double yPoint) {
            points.add(new Point(xPoint, yPoint));  // Создание и добавление точки по координатам
        }

        // Метод для сдвига всей ломаной на заданные расстояния
        //public void move(int dx, int dy) {
        public void move(double dxPoint, double dyPoint) {
            for (Point point : points) {
                point.move(dxPoint, dyPoint);  // Сдвиг каждой точки ломаной
            }
        }

        // Получение первой точки ломаной
        public Point getFirstPoint() {
            return points.get(0);
        }

        // Получение последней точки ломаной
        public Point getLastPoint() {
            return points.get(points.size() - 1);
        }

        // Преобразование ломаной
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Линия [");
            for (int i = 0; i < points.size(); i++) {
                sb.append(points.get(i));  // Добавление точки в строку
                if (i < points.size() - 1) {
                    sb.append(", ");  // Добавление разделителя между точками
                }
            }
            sb.append("]");
            return sb.toString();
        }

        // Статический метод для создания ломаной через ввод с клавиатуры
        public static Polyline fromKeyboard() {
            System.out.println("===============СОЗДАНИЕ ЛОМАНОЙ ЛИНИИ===================");
            Polyline polyline = new Polyline();  // Создание новой ломаной

            // Ввод количества точек с проверкой (минимум 2 точки)
            int pointCount = check.inputNum("Введите количество точек в ломаной: ");
            while (pointCount < 2) {
                System.out.println("Ломаная должна содержать минимум 2 точки!");
                pointCount = check.inputNum("Введите количество точек в ломаной: ");
            }

            // Последовательный ввод всех точек ломаной
            for (int i = 0; i < pointCount; i++) {
                System.out.println("Точка " + (i + 1) + " из " + pointCount + ":");
                Point point = Point.fromKeyboard();  // Создание точки через ввод
                polyline.addPoint(point);  // Добавление точки в ломаную
            }

            System.out.println("Создана ломаная: " + polyline);
            return polyline;
        }
    }

    // Класс для управления ломаными линиями
    public static class PolylineManager {
        private List<Polyline> polylines;  // Список всех ломаных

        // Конструктор менеджера
        public PolylineManager() {
            this.polylines = new ArrayList<>();
        }

        public void showMenu() {
            System.out.println("================МЕНЮ УПРАВЛЕНИЯ ЛОМАНЫМИ================");
            System.out.println("1. Создать новую ломаную");
            System.out.println("2. Показать все ломаные");
            System.out.println("3. Сдвинуть все ломаные");
            System.out.println("4. Выполнить задание");
            System.out.println("5. Выход");
            System.out.print("Выберите действие (1-5): ");
        }

        // Метод для создания новой ломаной через ввод с клавиатуры
        public void createNewPolyline() {
            Polyline polyline = Polyline.fromKeyboard();  // Создание ломаной через ввод
            polylines.add(polyline);  // Добавление ломаной в список
            System.out.println("Ломаная добавлена в список");
        }

        // Метод для отображения всех ломаных
        public void displayAllPolylines() {
            System.out.println("=================СПИСОК ВСЕХ ЛОМАНЫХ====================");

            if (polylines.isEmpty()) {
                System.out.println("Список ломаных пуст");
                return;
            }

            // Вывод всех ломаных с их номерами
            for (int i = 0; i < polylines.size(); i++) {
                System.out.println((i + 1) + ". " + polylines.get(i));
            }
        }

        // Метод для сдвига всех ломаных на заданное расстояние
        public void moveAllPolylines() {
            if (polylines.isEmpty()) {
                System.out.println("Нет ломаных для сдвига");
                return;
            }

            // Ввод расстояний сдвига
            System.out.println("=================СДВИГ ВСЕХ ЛОМАНЫХ=====================");
            //            int dx = check.inputNum("Введите сдвиг по X: ");
//            int dy = check.inputNum("Введите сдвиг по Y: ");
            double dx,dy;
            System.out.println("Введите сдвиг по X: ");
            dx = check.checkDouble();
            System.out.println("Введите сдвиг по Y: ");
            dy = check.checkDouble();
            // Применение сдвига ко всем ломаным
            for (Polyline polyline : polylines) {
                polyline.move(dx, dy);
            }

            System.out.println("Все ломаные сдвинуты на (" + dx + ", " + dy + ")");
        }

        // Метод для автоматического выполнения задания
        public void executeTask() {
            System.out.println("===================ВЫПОЛНЕНИЕ ЗАДАНИЯ====================");

            // 1. Создание первой ломаной через точки {1;5}, {2;8}, {5;3}
            System.out.println("1. Создание первой ломаной:");
            Polyline polyline1 = new Polyline(
                    new Point(1, 5),
                    new Point(2, 8),
                    new Point(5, 3)
            );
            polylines.add(polyline1);
            System.out.println("Первая ломаная: " + polyline1);

            // 2. Создание второй ломаной с совпадающими крайними точками
            System.out.println("Создание второй ломаной:");
            Polyline polyline2 = new Polyline();
            // Первая точка совпадает с первой точкой первой ломаной
            polyline2.addPoint(new Point(polyline1.getFirstPoint().getxPoint(), polyline1.getFirstPoint().getyPoint()));
            polyline2.addPoint(new Point(2, -5));   // Средняя точка 1
            polyline2.addPoint(new Point(4, -8));   // Средняя точка 2
            // Последняя точка совпадает с последней точкой первой ломаной
            polyline2.addPoint(new Point(polyline1.getLastPoint().getxPoint(), polyline1.getLastPoint().getyPoint()));
            polylines.add(polyline2);
            System.out.println("Вторая ломаная: " + polyline2);

            // 3. Сдвиг всех ломаных
            System.out.println("\n3. Сдвиг всех ломаных на (2, 3):");
            for (Polyline polyline : polylines) {
                polyline.move(2, 3);
            }

            // Вывод результатов
            System.out.println("Результаты после сдвига:");
            for (int i = 0; i < polylines.size(); i++) {
                System.out.println((i + 1) + ". " + polylines.get(i));
            }

            // Проверка совпадения крайних точек
            System.out.println("Проверка совпадения крайних точек:");
            Point first1 = polyline1.getFirstPoint();
            Point first2 = polyline2.getFirstPoint();
            Point last1 = polyline1.getLastPoint();
            Point last2 = polyline2.getLastPoint();

            System.out.println("Первые точки совпадают: " +
                    (first1.getxPoint() == first2.getxPoint() && first1.getyPoint() == first2.getyPoint()));
            System.out.println("Последние точки совпадают: " +
                    (last1.getxPoint() == last2.getxPoint() && last1.getyPoint() == last2.getyPoint()));
        }

        // Главный метод работы программы
        public void run() {
            System.out.println("==========ПРОГРАММА ДЛЯ РАБОТЫ С ЛОМАНЫМИ ЛИНИЯМИ==========");

            boolean running = true;

            // Основной цикл программы
            while (running) {
                showMenu();
                String choice = scanner.nextLine().trim();

                // Обработка выбора пользователя
                switch (choice) {
                    case "1":
                        createNewPolyline();
                        break;
                    case "2":
                        displayAllPolylines();
                        break;
                    case "3":
                        moveAllPolylines();
                        break;
                    case "4":
                        executeTask();
                        break;
                    case "5":
                        running = false;
                        System.out.println("Выход из программы");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 1 до 5");
                }
            }

            scanner.close();
        }
    }

}
