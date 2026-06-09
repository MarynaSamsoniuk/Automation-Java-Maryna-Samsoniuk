package ua.edu.ukma.samsoniuk;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks;
    private int nextId;
    private final TaskValidator validator = new TaskValidator();

    public TaskService() {
        tasks = new ArrayList<>();
        nextId = 1;
    }

    public boolean addTask(String name, String description) {
        Task task = new Task(nextId, name, description);
        try {
            validator.validate(task);
            tasks.add(task);
            nextId++;
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
        String oldName = task.getName();
        task.setName(name);
        try {
            validator.validate(task);
            return true;
        } catch (IllegalArgumentException e) {
            task.setName(oldName);
            return false;
        }
    }

    public boolean changeDescription(int index, String description) {
        if (index < 0 || index >= tasks.size()) return false;
        Task task = tasks.get(index);
        String oldDesc = task.getDescription();
        task.setDescription(description);
        try {
            validator.validate(task);
            return true;
        } catch (IllegalArgumentException e) {
            task.setDescription(oldDesc);
            return false;
        }
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