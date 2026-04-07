package ru.kuzhleva.codereview.polylines;

import ru.kuzhleva.codereview.errors.Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// public class LenghtPolylineProgram {
public class LengthPolylineProgram {
    //static check check = new check();
    static Check check = new Check();
    static Scanner scanner = new Scanner(System.in);

    public static class Point {
        // fix_me: координаты точки не обязательно должны быть целыми.
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
        public double getY() {
            return yPoint;
        }
        public void setX(int xPoint) {
            this.xPoint = xPoint;
        }
        public void setY(int y) {
            this.yPoint = y;
        }

        public void move(double dxPoint, double dyPoint) {
        //public void move(int dx, int dy) {
            this.xPoint += dxPoint;
            this.yPoint += dyPoint;
        }

        // Метод для вычисления расстояния между двумя точками
        public double distanceTo(Point other) {
//            int dx = this.x - other.x;
//            int dy = this.y - other.y;
            double dxPoint = this.xPoint - other.xPoint;
            double dyPoint = this.yPoint - other.yPoint;
            return Math.sqrt(dxPoint * dxPoint + dyPoint * dyPoint);
        }

        @Override
        public String toString() {
            return "{" + xPoint + ";" + yPoint + "}";
        }

        public static Point fromKeyboard() {
            System.out.println("====================ВВОД ТОЧКИ==========================");
//            int x = check.inputNum("Введите координату X: ");
//            int y = check.inputNum("Введите координату Y: ");
            double xPoint, yPoint;

            System.out.println("Введите координату X: ");
            xPoint = check.checkDouble();

            System.out.println("Введите координату Y: ");
            yPoint = check.checkDouble();

            Point point = new Point(xPoint, yPoint);
            System.out.println("Создана точка: " + point);
            return point;
        }
    }

    public static class Polyline {
        private List<Point> points;

        public Polyline() {
            this.points = new ArrayList<>();
        }

        public Polyline(List<Point> points) {
            this.points = new ArrayList<>(points);
        }

        public Polyline(Point... points) {
            this.points = new ArrayList<>(Arrays.asList(points));
        }

        public List<Point> getPoints() {
            return points;
        }
        public Point getPoint(int index) {
            return points.get(index);
        }
        public int getPointCount() {
            return points.size();
        }

        // Методы добавления точек
        public void addPoint(Point point) {
            points.add(point);
        }

        //public void addPoint(int x, int y) {
        public void addPoint(double xPoint, double yPoint) {
            points.add(new Point(xPoint, yPoint));
        }

        // Новый метод: добавление массива точек
        public void addPoints(Point... newPoints) {
            points.addAll(Arrays.asList(newPoints));
        }

        // Новый метод: добавление списка точек
        public void addPoints(List<Point> newPoints) {
            points.addAll(newPoints);
        }

        //public void move(int dx, int dy) {
        public void move(double dx, double dy) {
            for (Point point : points) {
                point.move(dx, dy);
            }
        }

        public Point getFirstPoint() {
            return points.get(0);
        }

        public Point getLastPoint() {
            return points.get(points.size() - 1);
        }

        //вычисление общей длины ломаной
        public double getLength() {
            if (points.size() < 2) {
                return 0.0;
            }

            double totalLength = 0.0;
            for (int i = 0; i < points.size() - 1; i++) {
                Point current = points.get(i);
                Point next = points.get(i + 1);
                totalLength += current.distanceTo(next);
            }
            return totalLength;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Линия [");
            for (int i = 0; i < points.size(); i++) {
                sb.append(points.get(i));
                if (i < points.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        public static Polyline fromKeyboard() {
            System.out.println("===============СОЗДАНИЕ ЛОМАНОЙ ЛИНИИ===================");
            Polyline polyline = new Polyline();

            int pointCount = check.inputNum("Введите количество точек в ломаной: ");
            while (pointCount < 2) {
                System.out.println("Ломаная должна содержать минимум 2 точки!");
                pointCount = check.inputNum("Введите количество точек в ломаной: ");
            }

            for (int i = 0; i < pointCount; i++) {
                System.out.println("Точка " + (i + 1) + " из " + pointCount + ":");
                Point point = Point.fromKeyboard();
                polyline.addPoint(point);
            }

            System.out.println("Создана ломаная: " + polyline);
            return polyline;
        }
    }

    public static class PolylineManager {
        private List<Polyline> polylines;

        public PolylineManager() {
            this.polylines = new ArrayList<>();
        }

        public void showMenu() {
            System.out.println("================МЕНЮ УПРАВЛЕНИЯ ЛОМАНЫМИ================");
            System.out.println("1. Создать новую ломаную");
            System.out.println("2. Показать все ломаные");
            System.out.println("3. Сдвинуть все ломаные");
            System.out.println("4. Добавить точки к ломаной");
            System.out.println("5. Показать длину ломаной");
            System.out.println("6. Выполнить задание");
            System.out.println("7. Выход");
            System.out.print("Выберите действие (1-7): ");
        }

        public void createNewPolyline() {
            Polyline polyline = Polyline.fromKeyboard();
            polylines.add(polyline);
            System.out.println("Ломаная добавлена в список");
        }

        public void displayAllPolylines() {
            System.out.println("=================СПИСОК ВСЕХ ЛОМАНЫХ====================");
            if (polylines.isEmpty()) {
                System.out.println("Список ломаных пуст");
                return;
            }
            for (int i = 0; i < polylines.size(); i++) {
                System.out.println((i + 1) + ". " + polylines.get(i));
            }
        }

        public void moveAllPolylines() {
            if (polylines.isEmpty()) {
                System.out.println("Нет ломаных для сдвига");
                return;
            }
            System.out.println("=================СДВИГ ВСЕХ ЛОМАНЫХ=====================");
//            int dx = check.inputNum("Введите сдвиг по X: ");
//            int dy = check.inputNum("Введите сдвиг по Y: ");
            double dx,dy;
            System.out.println("Введите сдвиг по X: ");
            dx = check.checkDouble();
            System.out.println("Введите сдвиг по Y: ");
            dy = check.checkDouble();
            for (Polyline polyline : polylines) {
                polyline.move(dx, dy);
            }
            System.out.println("Все ломаные сдвинуты на (" + dx + ", " + dy + ")");
        }

        // Добавление точек к существующей ломаной
        public void addPointsToPolyline() {
            if (polylines.isEmpty()) {
                System.out.println("Нет ломаных для добавления точек");
                return;
            }

            System.out.println("Выберите ломаную:");
            for (int i = 0; i < polylines.size(); i++) {
                System.out.println((i + 1) + ". " + polylines.get(i));
            }

            int polylineIndex = check.inputNum("Номер ломаной: ") - 1;
            if (polylineIndex < 0 || polylineIndex >= polylines.size()) {
                System.out.println("Неверный номер ломаной");
                return;
            }

            Polyline polyline = polylines.get(polylineIndex);
            int pointCount = check.inputNum("Сколько точек добавить: ");

            for (int i = 0; i < pointCount; i++) {
                System.out.println("Точка " + (i + 1) + " из " + pointCount + ":");
                Point point = Point.fromKeyboard();
                polyline.addPoint(point);
            }

            System.out.println("Точки добавлены. Новая ломаная: " + polyline);
        }

        // Показ длины ломаной
        public void showPolylineLength() {
            if (polylines.isEmpty()) {
                System.out.println("Нет ломаных для вычисления длины");
                return;
            }

            System.out.println("Выберите ломаную:");
            for (int i = 0; i < polylines.size(); i++) {
                System.out.println((i + 1) + ". " + polylines.get(i));
            }

            int polylineIndex = check.inputNum("Номер ломаной: ") - 1;
            if (polylineIndex < 0 || polylineIndex >= polylines.size()) {
                System.out.println("Неверный номер ломаной");
                return;
            }

            Polyline polyline = polylines.get(polylineIndex);
            double length = polyline.getLength();
            System.out.println("Длина ломаной: " + String.format("%.2f", length));
        }

        public void executeTask() {
            System.out.println("===================ВЫПОЛНЕНИЕ ЗАДАНИЯ====================");

            // 1. Создать Ломаную, проходящую через точки {1;5}, {2;8}, {5;3}
            System.out.println("1. Создание ломаной через точки {1;5}, {2;8}, {5;3}:");
            Polyline polyline = new Polyline(
                    new Point(1, 5),
                    new Point(2, 8),
                    new Point(5, 3)
            );
            polylines.add(polyline);
            System.out.println("Создана ломаная: " + polyline);

            // 2. Вывести на экран её длину
            System.out.println("\n2. Длина ломаной: " + String.format("%.2f", polyline.getLength()));

            // 3. Добавить точки {5;15}, {8;10}
            System.out.println("\n3. Добавление точек {5;15}, {8;10}:");
            polyline.addPoints(
                    new Point(5, 15),
                    new Point(8, 10)
            );
            System.out.println("Обновленная ломаная: " + polyline);

            // 4. Снова вывести на экран длину Ломаной
            System.out.println("\n4. Новая длина ломаной: " + String.format("%.2f", polyline.getLength()));
        }

        public void run() {
            System.out.println("==========ПРОГРАММА ДЛЯ РАБОТЫ С ЛОМАНЫМИ ЛИНИЯМИ==========");

            boolean running = true;
            while (running) {
                showMenu();
                String choice = scanner.nextLine().trim();

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
                        addPointsToPolyline();
                        break;
                    case "5":
                        showPolylineLength();
                        break;
                    case "6":
                        executeTask();
                        break;
                    case "7":
                        running = false;
                        System.out.println("Выход из программы");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 1 до 7");
                }
            }
            scanner.close();
        }
    }
}
