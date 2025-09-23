import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    List<Task> getHistory(); //#DEBUG@BOBA

    Task getTask(int id);

    ArrayList<Task> getTasks();

    Integer insTask(Task task);

    boolean updTask(Task task);

    void delTask(int id);

    void delTasks();

    SubTask getSubTask(int id);

    ArrayList<SubTask> getSubTaskByEpic(Epic epic);

    ArrayList<SubTask> getSubTaskByEpic(int epicId);

    ArrayList<SubTask> getSubTasks();

    Integer insSubTask(SubTask subTask);

    boolean updSubTask(SubTask subTask);

    void delSubTask(int id);

    void delSubTasks();

    Epic getEpic(int id);

    ArrayList<Epic> getEpics();

    Integer insEpic(Epic epic);

    boolean updEpic(Epic epic);

    void delEpic(int id);

    void delEpics();

    void updateEpicStatus(int id);
}
