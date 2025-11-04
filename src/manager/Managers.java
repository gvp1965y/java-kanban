package manager;

public class Managers {

    public static TaskManager getDefault() {
        return InFileTaskManager.LoadFromFile(ManagerFileCSVHelper.FILENAME);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
