import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int seqId;
    private final HashMap<Integer, Task> tasks; //#ASK@BOBA final ?
    private final HashMap<Integer, SubTask> subtasks; //#ASK@BOBA final ?
    private final HashMap<Integer, Epic> epics; //#ASK@BOBA final ?

    public TaskManager(){
        seqId = 0;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public ArrayList<Task> getTasks() {
        //ArrayList<Task> result = new ArrayList<>();
        //for (int id : tasks.keySet()) {
        //    result.add(tasks.get(id));
        //}
        //return result;
        return new ArrayList<>(tasks.values());
    }
    public int insTask(Task task) {
        seqId++;
        task.setId(seqId);
        tasks.put(seqId, task);
        return seqId;
    }
    public void updTask(Task task) {
        //#TODO
    }
    public void delTask(int id) {
        tasks.remove(id);
    }
    public void delTasks() {
        tasks.clear();
    }

    public SubTask getSubTask(int id) {
        return subtasks.get(id);
    }
    public ArrayList<SubTask> getSubTaskByEpic(int epicId) {
        ArrayList<SubTask> result = new ArrayList<>();
        for (int id : subtasks.keySet()) {
            SubTask subTask = subtasks.get(id);
            if (id == subTask.getEpicId()) {
                result.add(subTask);
            }
        }
        return result;
    }
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }
    public int insSubTask(SubTask subTask) {
        int epicId = subTask.getEpicId();
        if (epicId > 0) {
            seqId++;
            subTask.setId(seqId);
            subtasks.put(seqId, subTask);
            getEpic(epicId).getSubTaskIds().add(seqId);
            updateEpicStatus(epicId);
            return seqId;
        } else {
            return 0;
        }
    }
    public void delSubTask(int id) {
        SubTask subTask = subtasks.get(id);
        subtasks.remove(id);
        getEpic(subTask.getEpicId()).removeSubTask(id);
        updateEpicStatus(subTask.getEpicId());
    }
    public void delSubTasks() {
        for (Epic epic : getEpics()) {
            epic.getSubTaskIds().clear();
            epic.setStatus(TaskStatus.NEW);
        }
        subtasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }
    public int insEpic(Epic epic) {
        seqId++;
        epic.setId(seqId);
        epics.put(seqId, epic);
        return seqId;
    }
    public void updEpic(Epic epic) {
        //#TODO
    }
    public void delEpic(int id) {
        ArrayList<Integer> delSubTask = new ArrayList<>();
        for (int i : subtasks.keySet()) {
            if (id == subtasks.get(i).getEpicId()) {
                delSubTask.add(i);
            }
        }
        for (int i : delSubTask) {
            subtasks.remove(i);
        }
        epics.remove(id);
    }
    public void delEpics() {
        subtasks.clear();
        epics.clear();
    }
    private void updateEpicStatus(int id) {
        Epic epic = getEpic(id);
        int subtaskCount = epic.getSubTaskIds().size();
        int statusNew = 0;
        int statusDone = 0;
        for (Integer i : epic.getSubTaskIds()) {
            if (getSubTask(i).getStatus() == TaskStatus.NEW) {
                statusNew++;
            } else {
                statusDone++;
            }
        }
        if (subtaskCount == statusNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (subtaskCount == statusDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
