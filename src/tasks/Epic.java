package tasks;

import java.util.ArrayList;

public class Epic extends Task{
    protected final ArrayList<Integer> subTaskIds;

    public Epic(String name, String description, TaskStatus status) {
        super (name, description, status);
        subTaskIds = new ArrayList<>();
    }

    public Epic(Epic epic) {
        super (epic);
        this.subTaskIds = epic.subTaskIds;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Epic epic = (Epic) object;
        return id == epic.id;
    }

    @Override
    public String toString() {
        return "Epic { "
                + "id= " + id
                + ", status= " + status
                + ", name= " + name
                + ", description= '" + description + "'"
                + ", subTaskIds= " + subTaskIds
                + "}";
    }

    public ArrayList<Integer> getSubTaskIds() { return subTaskIds; }

    public void insSubTask(int id) {
        if (id != this.id) {
            subTaskIds.add(id);
        }
    }

    public void removeSubTask(Integer id) { subTaskIds.remove(id); }

    public void removeAllSubTask() {
        subTaskIds.clear();
    }
}
