package ua.edu.ukma.samsoniuk;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks;
    private int nextId;

    public TaskService() {
        tasks = new ArrayList<>();
        nextId = 1;
    }

    public boolean addTask(String name, String description) {
        Task task = new Task(nextId, name, description);
        if (name.length() > 50) {
            System.out.println("Name is must not exceed 50 characters");
            return false;
        } else if (name.length() < 5) {
            System.out.println("Name is must by at least 5 characters");
            return false;
        }

        if (description.length() > 100) {
            System.out.println("Description is must not exceed 50 characters");
            return false;
        } else if (description.length() < 5) {
            System.out.println("Description is must by at least 5 characters");
            return false;
        }
        tasks.add(task);
        nextId++;
        return true;
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Завдань немає");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public boolean markCompleted(int index) {
        if (index < 0 || index >= tasks.size()) return false;
        tasks.get(index).setCompleted(true);
        return true;
    }

    public boolean changeName(int index, String name) {
        if (index < 0 || index >= tasks.size()) return false;
        Task task = tasks.get(index);
        if (name.length() > 50) {
            System.out.println("Name is must not exceed 50 characters");
            return false;
        } else if (name.length() < 5) {
            System.out.println("Name is must by at least 5 characters");
            return false;
        }
        task.setName(name);
        return true;
    }

    public boolean changeDescription(int index, String description) {
        if (index < 0 || index >= tasks.size()) return false;
        Task task = tasks.get(index);
        if (description.length() > 100) {
            System.out.println("Description is must not exceed 50 characters");
            return false;
        } else if (description.length() < 5) {
            System.out.println("Description is must by at least 5 characters");
            return false;
        }
        task.setDescription(description);
        return true;
    }

    public boolean deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) return false;
        tasks.remove(index);
        return true;
    }

    public int getTaskCount() {
        return tasks.size();
    }
}