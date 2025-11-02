package manager;

import tasks.Task;
import tasks.Epic;
import tasks.SubTask;

public class FileBackedTaskManager extends InMemoryTaskManager {

    void save() {
        //#TODO@BOBA
    }

    @Override
    public Integer insTask(Task task) {
        Integer seqId = super.insTask(task);
        save();
        return seqId;
    }

    @Override
    public Integer insSubTask(SubTask subTask) {
        Integer seqId = super.insSubTask(subTask);
        save();
        return seqId;
    }

    @Override
    public Integer insEpic(Epic epic) {
        Integer seqId = super.insEpic(epic);
        save();
        return seqId;
    }
}
