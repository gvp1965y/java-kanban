import java.util.ArrayList;

public class Epic extends Task{
    protected ArrayList<Integer> subTaskIds; //#ASK@BOBA private/protected

    public Epic(String name, String description, TaskStatus status) {
        super (name, description, status);
        subTaskIds = new ArrayList<>();
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

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTask(Integer id) {
        for (Integer i : subTaskIds) {
            if (i.equals(id)) {
                subTaskIds.remove(i);
                break;
            }
        }
    }
}
