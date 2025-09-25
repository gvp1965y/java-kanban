import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    int TASK_HISTORY_MAX = 10;            //#ASK@BOBA public static final
    private final List<Task> taskHistory; //#ASK@BOBA final

    public InMemoryHistoryManager() {
        taskHistory = new LinkedList<>(); //#ASK@BOBA LinkedList vs ArrayList
    }

    @Override
    public void add(Task task) {
        if (taskHistory.size() == TASK_HISTORY_MAX) {
            taskHistory.removeFirst();
        }
        taskHistory.add(new Task(task));    //#ASK@BOBA:  history by clone
    }

    @Override
    public List<Task> getHistory() { return taskHistory; }
}
