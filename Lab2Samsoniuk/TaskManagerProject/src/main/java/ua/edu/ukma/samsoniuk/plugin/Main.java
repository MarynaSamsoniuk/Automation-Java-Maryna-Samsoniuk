package ua.edu.ukma.samsoniuk.plugin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМенеджер завдань");
            System.out.println("1.Додати завдання");
            System.out.println("2.Показати всі завдання");
            System.out.println("3.Позначити виконаним");
            System.out.println("4.Видалити завдання");
            System.out.println("5.Вийти");
            System.out.print("Оберіть дію: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введіть число(від 1 до 5)");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Назва завдання: ");
                    String name = scanner.nextLine();
                    System.out.print("Опис: ");
                    String description = scanner.nextLine();
                    taskService.addTask(name, description);
                    System.out.println("Завдання додано!");
                    break;
                case 2:
                    taskService.displayTasks();
                    break;
                case 3:
                    taskService.displayTasks();
                    if (taskService.getTaskCount() > 0) {
                        System.out.print("Введіть номер завдання, яке позначити виконаним: ");
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        if (taskService.markCompleted(index)) {
                            System.out.println("Завдання позначено виконаним!");
                        } else {
                            System.out.println("Неправильний номер завдання");
                        }
                    }
                    break;
                case 4:
                    taskService.displayTasks();
                    if (taskService.getTaskCount() > 0) {
                        System.out.print("Введіть номер завдання для видалення: ");
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        if (taskService.deleteTask(index)) {
                            System.out.println("Завдання видалено");
                        } else {
                            System.out.println("Неправильний номер завдання");
                        }
                    }
                    break;
                case 5:
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неправильний вибір. Спробуйте ще раз");
            }
        }
    }
}