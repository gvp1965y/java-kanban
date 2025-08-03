//import ....;

public class SubTask extends Task{
    private int epicId;

    public SubTask(int id, String name, String description, TaskStatus status, int epicId) {
        super (id, name, description, status);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, TaskStatus status, int epicId) {
        super (name, description, status);
        this.epicId = epicId;
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

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
