package tasks;

public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, TaskStatus status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public SubTask(SubTask subTask) {
        super(subTask);
        this.epicId = subTask.epicId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SubTask subTask = (SubTask) object;
        return id == subTask.id;
    }

    @Override
    public String toString() {
        return "SubTask { "
             + "id= " + id
             + ", status= " + status
             + ", name= " + name
             + ", description= '" + description + "'"
             + ", epicId= " + epicId
             + "}";
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public void setId(int id) {
        if (id != this.epicId) {
            this.id = id;
        }
    }

    public int getEpicId() {
        return epicId;
    }
}
