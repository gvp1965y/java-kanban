import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int seqId;
    private final HashMap<Integer, Task> tasks; //#ASK@BOBA final ?
    private final HashMap<Integer, SubTask> subtasks; //#ASK@BOBA final ?
    private final HashMap<Integer, Epic> epics; //#ASK@BOBA final ?
    private final HistoryManager taskHistory; //#ASK@BOBA final ?

    public InMemoryTaskManager() {
        seqId = 0;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        taskHistory = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory.getHistory();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        taskHistory.add(task);
        return task;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Integer insTask(Task task) {
        seqId++;
        task.setId(seqId);
        tasks.put(seqId, task);
        return seqId;
    }

    @Override
    public boolean updTask(Task task) {
        int taskId = task.getId();
        if (!tasks.containsKey(taskId)) {
            return false;
        }
        tasks.put(taskId, task);
        return true;
    }

    @Override
    public void delTask(int id) { tasks.remove(id); }

    @Override
    public void delTasks() { tasks.clear(); }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subtasks.get(id);
        taskHistory.add(subTask);
        return subTask;
    }

    @Override
    public ArrayList<SubTask> getSubTaskByEpic(Epic epic) {
        if (epic == null) {
            return null;
        }
        ArrayList<SubTask> result = new ArrayList<>();
        for (Integer i : epic.getSubTaskIds()) {
            result.add(subtasks.get(i));
        }
        return result;
    }

    @Override
    public ArrayList<SubTask> getSubTaskByEpic(int epicId) {
        if (!epics.containsKey(epicId)) {
            return null;
        }
        return getSubTaskByEpic(epics.get(epicId));
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
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

    @Override
    public boolean updSubTask(SubTask subTask) {
        int subTaskId = subTask.getId();
        int epicId = subTask.getEpicId();
        if (!subtasks.containsKey(subTaskId)) {
            return false;
        }
        if (epicId != subtasks.get(subTaskId).getEpicId()) {
            return false;
        }
        subtasks.put(subTaskId, subTask);
        updateEpicStatus(epicId);
        return true;
    }

    @Override
    public void delSubTask(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
            return;
        }
        subtasks.remove(id);
        getEpic(subTask.getEpicId()).removeSubTask(id);
        updateEpicStatus(subTask.getEpicId());
    }

    @Override
    public void delSubTasks() {
        for (Epic epic : getEpics()) {
            epic.removeAllSubTask();
            updateEpicStatus(epic);
        }
        subtasks.clear();
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        taskHistory.add(epic);
        return epic;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Integer insEpic(Epic epic) {
        seqId++;
        epic.setId(seqId);
        epics.put(seqId, epic);
        return seqId;
    }

    @Override
    public boolean updEpic(Epic epic) {
        int epicId = epic.getId();
        if (!epics.containsKey(epicId)) {
            return false;
        }
        epics.get(epicId).setName(epic.getName());
        epics.get(epicId).setDescription(epic.getDescription());
        updateEpicStatus(epicId);
        return true;
    }

    @Override
    public void delEpic(int id) {
        Epic epic = getEpic(id);
        if (epic == null) {
            return;
        }
        for (Integer i : epic.getSubTaskIds()) {
            subtasks.remove(i);
        }
        epics.remove(id);
    }

    @Override
    public void delEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void updateEpicStatus(int id) {
        if (!epics.containsKey(id)) {
            return;
        }
        updateEpicStatus(getEpic(id));
    }

    /*default*/ void updateEpicStatus(Epic epic) {
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
}
