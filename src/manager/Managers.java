package manager;

public class Managers {

    public static TaskManager getDefault() {
        //#DEV@BOBA return new InMemoryTaskManager();
        return new FileBackedTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
