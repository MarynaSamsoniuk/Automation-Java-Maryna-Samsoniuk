package ua.edu.ukma.samsoniuk;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМенеджер завдань");
            System.out.println("1. Додати завдання");
            System.out.println("2. Показати всі завдання");
            System.out.println("3. Позначити виконаним");
            System.out.println("4. Змінити назву");
            System.out.println("5. Змінити опис");
            System.out.println("6. Видалити завдання");
            System.out.println("7. Вийти");
            System.out.print("Оберіть дію: ");

            int choice;
            try {
                choice = readInt(scanner);
            } catch (NumberFormatException e) {
                System.out.println("Введіть число (від 1 до 7)");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Назва завдання: ");
                    String name = scanner.nextLine();
                    System.out.print("Опис: ");
                    String description = scanner.nextLine();
                    boolean added = taskService.addTask(name, description);
                    if (added) {
                        System.out.println("Завдання додано!");
                    } else {
                        System.out.println("Помилка: назва завдання має бути від 1 до 50 символів, опис – не довше 200 символів.");
                    }
                    break;
                case 2:
                    taskService.displayTasks();
                    break;
                case 3:
                    taskService.displayTasks();
                    if (taskService.getTaskCount() > 0) {
                        System.out.print("Введіть номер завдання для позначення виконаним: ");
                        int index = readInt(scanner) - 1;
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
                        System.out.print("Введіть номер завдання для зміни назви: ");
                        int index = readInt(scanner) - 1;
                        System.out.print("Введіть нову назву: ");
                        String newName = scanner.nextLine();
                        boolean nameChanged = taskService.changeName(index, newName);
                        if (nameChanged) {
                            System.out.println("Назву змінено");
                        } else {
                            System.out.println("Не вдалося змінити назву (можливо, неправильний номер або нова назва некоректна).");
                        }
                    }
                    break;
                case 5:
                    taskService.displayTasks();
                    if (taskService.getTaskCount() > 0) {
                        System.out.print("Введіть номер завдання для зміни опису: ");
                        int index = readInt(scanner) - 1;
                        System.out.print("Введіть новий опис: ");
                        String newDescription = scanner.nextLine();
                        boolean descChanged = taskService.changeDescription(index, newDescription);
                        if (descChanged) {
                            System.out.println("Опис змінено");
                        } else {
                            System.out.println("Не вдалося змінити опис (можливо, неправильний номер або новий опис некоректний).");
                        }
                    }
                    break;
                case 6:
                    taskService.displayTasks();
                    if (taskService.getTaskCount() > 0) {
                        System.out.print("Введіть номер завдання для видалення: ");
                        int index = readInt(scanner) - 1;
                        if (taskService.deleteTask(index)) {
                            System.out.println("Завдання видалено");
                        } else {
                            System.out.println("Неправильний номер завдання");
                        }
                    }
                    break;
                case 7:
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неправильний вибір. Спробуйте ще раз");
            }
        }
    }

    private static int readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}