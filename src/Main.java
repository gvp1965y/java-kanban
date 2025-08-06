//#SAMPLE@BOBA: int task01Id = tm.insTask(task01); etc. subTask, epic
//#SAMPLE@BOBA: boolean updated = tm.updTask(task01)); etc. subTask, epic

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager tm = new TaskManager();
        printAll(tm, "После создания --------------");

        Task task01 = new Task("task01", null, TaskStatus.NEW);
        tm.insTask(task01);
        Task task02 = new Task("task02", null, TaskStatus.IN_PROGRESS);
        tm.insTask(task02);
        Epic epic04 = new Epic("epic04", null, TaskStatus.NEW);
        tm.insEpic(epic04);
        SubTask subTask0401 = new SubTask("subTask0401", null, TaskStatus.NEW, epic04.getId());
        tm.insSubTask(subTask0401);
        SubTask subTask0402 = new SubTask("subTask0402", null, TaskStatus.IN_PROGRESS, epic04.getId());
        tm.insSubTask(subTask0402);
        Epic epic05 = new Epic("epic05", null, TaskStatus.NEW);
        tm.insEpic(epic05);
        SubTask subTask0501 = new SubTask("subTask0501", null, TaskStatus.DONE, epic05.getId());
        tm.insSubTask(subTask0501);
        printAll(tm, "После вставки --------------");

        task01.setStatus(TaskStatus.IN_PROGRESS);
        tm.updTask(task01);
        subTask0401.setStatus(TaskStatus.IN_PROGRESS);
        tm.updSubTask(subTask0401);
        epic04.setStatus(TaskStatus.NEW);
        tm.updEpic(epic04);
        printAll(tm, "После изменения ------------");

        tm.delTask(task01.getId());
        tm.delEpic(epic04.getId());
        printAll(tm, "После удаления ------------");

        System.out.println("Приехали!");
    }

    static void printAll(TaskManager tm, String caption) {
        System.out.println(caption);
        for (Task task : tm.getTasks()) {
            System.out.println(task);
        }
        for (SubTask subTask : tm.getSubTasks()) {
            System.out.println(subTask);
        }
        for (Epic epic : tm.getEpics()) {
            System.out.println(epic);
        }
    }
}
