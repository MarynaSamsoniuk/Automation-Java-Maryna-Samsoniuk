package ua.edu.ukma.samsoniuk.plugin;

import java.util.ArrayList;
import java.util.Date;

public class TaskService {
    private ArrayList<Task> tasks;
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