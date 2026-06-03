package ua.edu.ukma.samsoniuk;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private List<Task> tasks;
    private int nextId;

    public TaskService() {
        tasks = new ArrayList<>();
        nextId = 1;
    }

    public void addTask(String name, String description) {
        Task task = new Task(nextId++, name, description);
        tasks.add(task);
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
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.get(index).setCompleted(true);
        return true;
    }

    public boolean changeName(int index, String name) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.get(index).setName(name);
        return true;
    }

    public boolean changeDescription(int index, String description) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.get(index).setDescription(description);
        return true;
    }

    public boolean deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.remove(index);
        return true;
    }

    public int getTaskCount() {
        return tasks.size();
    }
}