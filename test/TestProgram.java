//#ASIS@BOBA:
// Реализованы только те тесты, которые указаны в ТЗ "Финальный проект спринта 5" и "Финальный проект спринта 6" в порядке перечисления.
// "Непонятки" и ошибки в ТЗ трактуются "в пользу" программиста.

import tasks.*;
import manager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestProgram {
    public HistoryManager historyManager;
    public TaskManager taskManager;

    @BeforeEach                           //#ASK@BOBA: before all ????
    void initTaskManagers() {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test //#ASIS@TOR убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    void validManagers() {
        assertNotNull(taskManager, "taskManager has not initialized");
        assertNotNull(historyManager, "historyManager has not initialized");
    }

    @Test //#ASIS@TOR: проверьте, что экземпляры класса Task равны друг другу, если равен их id
    void taskEqualsById() {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int taskId = taskManager.insTask(task);
        final Task taskClone = taskManager.getTask(taskId);
        assertEquals(task, taskClone, "Task class instances with the same ID are not equal");
    }

    @Test //#ASIS@TOR: проверьте, что наследники класса Task равны друг другу, если равен их id
    void extendsTaskEqualsById() {
        final Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        final int epicId = taskManager.insEpic(epic);
        final Epic epicClone = taskManager.getEpic(epicId);
        final SubTask subTask = new SubTask("SubTask0101", null, TaskStatus.NEW, epic.getId());
        final int subTaskId = taskManager.insSubTask(subTask);
        final SubTask subTaskClone = taskManager.getSubTask(subTaskId);
        assertEquals(epic, epicClone, "Epic class instances with the same ID are not equal");
        assertEquals(subTask, subTaskClone, "SubTask class instances with the same ID are not equal");
    }

    @Test //#ASIS@TOR: проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id
    void taskManagerIsWorked() {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int taskId = taskManager.insTask(task);
        final Task taskClone = taskManager.getTask(taskId);
        final Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        final int epicId = taskManager.insEpic(epic);
        final Epic epicClone = taskManager.getEpic(epicId);
        final SubTask subTask = new SubTask("SubTask0101", null, TaskStatus.NEW, epic.getId());
        final int subTaskId = taskManager.insSubTask(subTask);
        final SubTask subTaskClone = taskManager.getSubTask(subTaskId);
        assertEquals(task, taskClone, "taskManager doesn't work with tasks");
        assertEquals(epic, epicClone, "taskManager doesn't work with epics");
        assertEquals(subTask, subTaskClone, "taskManager doesn't work with subtasks");
    }

    @Test //#ASIS@TOR: проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
    void taskManagerInvalidId () {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int taskId = taskManager.insTask(task);                                 // заданное id
        final int taskNewId = taskManager.insTask(task);                              // сгенерированное id
        assertNotEquals(taskManager.getTask(taskId), taskManager.getTask(taskNewId), "ID conflict in the taskManager");
    }

    @Test //#ASIS@TOR: проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи
    void epicNotAddYourselfBySubTask() {
        Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        taskManager.insEpic(epic);
        final int subTaskCount = epic.getSubTaskIds().size();
        epic.insSubTask(epic.getId());
        assertEquals(subTaskCount, epic.getSubTaskIds().size(), "epic added myself to the subtasks");
    }

    @Test //#ASIS@TOR: проверьте, что объект Subtask нельзя сделать своим же эпиком
    void subTaskDoesNotMakeItselfItsOwnEpic() {
        Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        final int epicId = taskManager.insEpic(epic);
        SubTask subTask = new SubTask("SubTask0101", null, TaskStatus.NEW, epic.getId());
        final int subTaskId = taskManager.insSubTask(subTask);
        subTask.setId(epicId);
        assertEquals(subTaskId, subTask.getId(), "subTask make itself its own epic");
    }

    @Test //#ASIS@TOR: создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    void taskManagerAdd() {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int id = taskManager.insTask(task);
        assertNotNull(taskManager.getTask(id), "task is missing");
        assertEquals(task, taskManager.getTask(id), "task it is changed");
    }

    @Test //#ASIS@TOR: убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    void historyManagerAdd() {
        Task task = new Task("Task01", null, TaskStatus.NEW);
        historyManager.add(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        historyManager.add(task);
        assertNotNull(historyManager.getHistory().getLast(), "task is missing");
        assertEquals(task, historyManager.getHistory().getLast(),"task it is changed");
    }

    @Test //#ASIS@TOR: Проверьте, что встроенный связный список версий, а также операции добавления и удаления работают корректно.
    void historyManagerIsWorked() {
        Task task01 = new Task("Task01", null, TaskStatus.NEW);
        task01.setId(1);
        historyManager.add(task01);
        Task task02 = new Task("Task02", null, TaskStatus.NEW);
        task02.setId(2);
        historyManager.add(task02);
        task01.setStatus(TaskStatus.IN_PROGRESS);
        historyManager.add(task01);
        Task task03 = new Task("Task03", null, TaskStatus.NEW);
        task03.setId(3);
        historyManager.add(task03);
        historyManager.remove(task03.getId());
        assertEquals(2, historyManager.getHistory().size(),"historyManager unnecessary entries");
        assertEquals(task02, historyManager.getHistory().getFirst(),"historyManager doesn't delete repetitions");
        assertEquals(task01, historyManager.getHistory().getLast(),"historyManager doesn't do the remove");
    }

    @Test //#ASIS@TOR: Внутри эпиков не должно оставаться неактуальных id подзадач.
    void relevanceEpicSubtasks() {
        Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        taskManager.insEpic(epic);
        SubTask subTask = new SubTask("SubTask0101", null, TaskStatus.NEW, epic.getId());
        taskManager.insSubTask(subTask);
        subTask = new SubTask("SubTask0102", null, TaskStatus.NEW, epic.getId());
        taskManager.insSubTask(subTask);
        taskManager.delSubTasks();
        assertEquals(0, epic.getSubTaskIds().size(), "epic contains deleted subtasks");
    }
}
