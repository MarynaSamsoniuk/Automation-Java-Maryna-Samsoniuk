package ua.edu.ukma.samsoniuk;

@GenerateValidator
public class Task {
    private final int id;
    @Validate(notNull = true, minLength = 1, maxLength = 50)
    private String name;
    @Validate(minLength = 0, maxLength = 200)
    private String description;
    private boolean isCompleted;

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (isCompleted) {
            return name + "(" + description + ") - виконано!";
        } else {
            return name + "(" + description + ")";
        }
    }
}