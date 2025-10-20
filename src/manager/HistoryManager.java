package manager;

import tasks.*;
import java.util.List;
import java.util.Set;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    void removeNodes(Set<Integer> ids);

    List<Task> getHistory();
}
