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
        return new ArrayList<>(tasks.values());
    }

    public Integer insTask(Task task) {
        seqId++;
        task.setId(seqId);
        tasks.put(seqId, task);
        return seqId;
    }

    public boolean updTask(Task task) {
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        tasks.put(taskId, task);
        return true;
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
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        } else {
            ArrayList<SubTask> result = new ArrayList<>();
            //
            //#TODO@BOBA
            //
            return result;
        }
        //ArrayList<SubTask> result = new ArrayList<>();
        //for (int id : subtasks.keySet()) {
        //    SubTask subTask = subtasks.get(id);
        //    if (id == subTask.getEpicId()) {
        //        result.add(subTask);
        //    }
        //}
        //return result;
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Integer insSubTask(SubTask subTask) {
        Epic epic = getEpic(subTask.getEpicId());
        if (epic == null) {
            return null;
        }
        seqId++;
        subTask.setId(seqId);
        subtasks.put(seqId, subTask);
        epic.insSubTask(seqId);
        updateEpicStatus(epic);
        return seqId;
    }

    public boolean updSubTask(SubTask subTask) {
        int subTaskId = subTask.getId();
        if (!subtasks.containsKey(subTaskId)) {
            return false;
        }
        //#TODO@BOBA
        subtasks.put(subTaskId, subTask);
        updateEpicStatus(subTask.getEpicId());
        return true;
    }

    public void delSubTask(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
            return;
        }
        subtasks.remove(id);
        getEpic(subTask.getEpicId()).removeSubTask(id);
        updateEpicStatus(subTask.getEpicId());
    }

    public void delSubTasks() {
        for (Epic epic : getEpics()) {
            epic.removeAllSubTask();
            updateEpicStatus(epic);           //#ASK@BOBA
            //epic.setStatus(TaskStatus.NEW); //#ASK@BOBA
        }
        subtasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public Integer insEpic(Epic epic) {
        seqId++;
        epic.setId(seqId);
        epics.put(seqId, epic);
        return seqId;
    }

    public boolean updEpic(Epic epic) {
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) {
            return false;
        }
        //#TODO@BOBA
        updateEpicStatus(epic);
        epics.put(epicId, epic);
        return true;
    }

    public void delEpic(int id) {
        Epic epic = getEpic(id);
        if (epic == null) {
            return;
        }
        ArrayList<Integer> delSubTask = new ArrayList<>();
        //#ASK@BOBA -- error
        //#TODO@BOBA
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

    private void updateEpicStatus(Epic epic) {
        int subtaskCount = epic.getSubTaskIds().size();
        int statusNew = 0;
        int statusDone = 0;
        for (Integer i : epic.getSubTaskIds()) {  //#ASK@BOBA if-switch
            if (getSubTask(i).getStatus() == TaskStatus.NEW) {
                statusNew++;
            } else if (getSubTask(i).getStatus() == TaskStatus.DONE) {
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

    private void updateEpicStatus(int id) {
        Epic epic = getEpic(id);
        updateEpicStatus(epic);
    }
}
