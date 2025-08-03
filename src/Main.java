//import ....

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager tm = new TaskManager();
// insert -->>
        Task task01 = new Task("task01", "Description task01", TaskStatus.NEW);
        final int task01Id = tm.insTask(task01); //#ASK@BOBA final
        Task task02 = new Task("task02", "Description task02", TaskStatus.IN_PROGRESS);
        final int task02Id = tm.insTask(task02); //#ASK@BOBA final

        Epic epic04 = new Epic("epic04", "Description epic04", TaskStatus.NEW);
        final int epic04Id = tm.insEpic(epic04);
        SubTask subTask0401 = new SubTask("subTask0401", "Description subTask0401", TaskStatus.NEW, epic04Id);
        final int subTask0401Id = tm.insSubTask(subTask0401);
        SubTask subTask0402 = new SubTask("subTask0402", "Description subTask0402", TaskStatus.IN_PROGRESS, epic04Id);
        final int subTask0402Id = tm.insSubTask(subTask0402);

        Epic epic05 = new Epic("epic05", "Description epic05", TaskStatus.NEW);
        final int epic05Id = tm.insEpic(epic05);
        SubTask subTask0501 = new SubTask("subTask50", "Description subTask50", TaskStatus.DONE, epic05Id);
        final int subTask0501Id = tm.insSubTask(subTask0501);

        for (Task task : tm.getTasks()) {
            System.out.println(task);
        }
        for (SubTask subTask : tm.getSubTasks()) {
            System.out.println(subTask);
        }
        for (Epic epic : tm.getEpics()) {
            System.out.println(epic);
        }
// insert --<<
// update -->>

// update --<<
// delete -->>
        tm.delTask(task01Id);
        tm.delEpic(epic04Id);
        for (Task task : tm.getTasks()) {
            System.out.println(task);
        }
        for (SubTask subTask : tm.getSubTasks()) {
            System.out.println(subTask);
        }
        for (Epic epic : tm.getEpics()) {
            System.out.println(epic);
        }
// delete --<<
        System.out.println("Приехали!");
    }
}
