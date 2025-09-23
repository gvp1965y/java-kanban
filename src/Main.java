//#DEMO@BOBA: task, taskId; subTask, subTaskId; epic, epicId: изменяемые элементы
//#DEMO@BOBA: taskUpdated; subTaskUpdated; epicUpdated : успешное обновление элемента

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");

        //TaskManager tm = new InMemoryTaskManager();
        TaskManager tm = Managers.getDefault();
        printAll(tm, "После создания --------------");

        Task task = new Task("task01", null, TaskStatus.NEW);
        int taskId = tm.insTask(task);
        tm.insTask(new Task("task02", null, TaskStatus.IN_PROGRESS));
        Epic epic = new Epic("epic04", null, TaskStatus.NEW);
        int epicId = tm.insEpic(epic);
        SubTask subTask = new SubTask("subTask0401", null, TaskStatus.NEW, epicId);
        int subTaskId = tm.insSubTask(subTask);
        tm.insSubTask(new SubTask("subTask0402", null, TaskStatus.DONE, epicId));
        tm.insSubTask(new SubTask("subTask0501", null, TaskStatus.DONE, tm.insEpic(new Epic("epic05", null, TaskStatus.NEW))));
        printAll(tm, "После вставки --------------");

        task.setStatus(TaskStatus.IN_PROGRESS);
        boolean taskUpdated = tm.updTask(task);
        if (!taskUpdated) { printNotUpdated(task); }
        subTask.setStatus(TaskStatus.IN_PROGRESS);
        boolean subTaskUpdated = tm.updSubTask(subTask);
        if (!subTaskUpdated) { printNotUpdated(subTask); }
        epic.setStatus(TaskStatus.NEW);
        boolean epicUpdated = tm.updEpic(epic);
        if (!epicUpdated) { printNotUpdated(epic); }
        printAll(tm, "После изменения Статусов ------------");

        tm.delTask(taskId);
        tm.delSubTask(subTaskId);
        tm.delEpic(epicId);
        printAll(tm, "После удаления ------------");

        tm.delTasks();
        tm.delEpics();
        tm.delSubTasks();
        printAll(tm, "После удаления всех ------------");

        System.out.println("Приехали!");
    }

    static void printAll(TaskManager tm, String caption) {
        System.out.println(caption);
        if (tm.getTasks().isEmpty()) {
            System.out.println("  Tasks " + tm.getTasks());
        } else {
            for (Task task : tm.getTasks()) {
                System.out.println("  "+task);
            }
        }
        if (tm.getSubTasks().isEmpty()) {
            System.out.println("  SubTasks " + tm.getSubTasks());
        } else {
            for (SubTask subTask : tm.getSubTasks()) {
                System.out.println("  "+subTask);
            }
        }
        if (tm.getEpics().isEmpty()) {
            System.out.println("  Epics " + tm.getEpics());
        } else {
            for (Epic epic : tm.getEpics()) {
                System.out.println("  "+epic);
            }
        }
        System.out.println("  History " + tm.getHistory()); //#DEBUG@BOBA
    }

    static void printNotUpdated(Task task) {
        System.out.println("Ошибка обновления " + task);
    }
}
