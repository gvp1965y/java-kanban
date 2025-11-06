package manager;

public class Managers {

    public static TaskManager getDefault() {
        return InFileTaskManager.loadFromFile(ManagerFileCSVHelper.FILENAME);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
