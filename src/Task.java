import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected TaskStatus status;

    public Task(String name, String description, TaskStatus status) {
        this.name = name;
        setDescription(description);
        this.status = status;
    }

    public Task(Task task) {     //#ASK@BOBA:  clone
        this.id = task.id;
        this.name = task.name;
        this.description = task.description;
        this.status = task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public String toString() {
        return "Task {"
                + "id= " + id
                + ", status= " + status
                + ", name= " + name
                + ", description= '" + description + "'"
                + "}";
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            this.description = "Description " + this.name;
        } else {
            this.description = description;
        }
    }

    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) { this.status = status; }
}
