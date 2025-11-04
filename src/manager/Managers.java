package manager;

public class Managers {

    public static TaskManager getDefault() {
        return InFileTaskManager.LoadFromFile("resources/tasks.csv");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
