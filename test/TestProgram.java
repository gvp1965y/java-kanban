//#ASIS@BOBA реализованы только те тесты, которые указаны в ТЗ "Финальный проект спринта 5" в порядке перечисления
//           "непонятки" и ошибки в ТЗ трактуются "в пользу" программиста

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestProgram {
    public HistoryManager historyManager;
    public TaskManager taskManager;

    @BeforeEach                           //#ASK@BOBA before all ????
    void initTaskManagers() {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test // убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    void validManagers() {
        assertNotNull(taskManager, "taskManager has not initialized");
        assertNotNull(historyManager, "historyManager has not initialized");
    }

    @Test // проверьте, что экземпляры класса Task равны друг другу, если равен их id
    void taskEqualsById() {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int taskId = taskManager.insTask(task);
        final Task taskClone = taskManager.getTask(taskId);
        assertEquals(task, taskClone, "Task class instances with the same ID are not equal");
    }

    @Test // проверьте, что наследники класса Task равны друг другу, если равен их id
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

    @Test //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id
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

    @Test //проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера
    void taskManagerInvalidId () {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int taskId = taskManager.insTask(task);                                 // заданное id
        final int taskNewId = taskManager.insTask(task);                              // сгенерированное id
        assertNotEquals(taskManager.getTask(taskId), taskManager.getTask(taskNewId), "ID conflict in the taskManager");
    }

    @Test //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи
    void epicNotAddYourselfBySubTask() {
        Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        taskManager.insEpic(epic);
        final int subTaskCount = epic.getSubTaskIds().size();
        epic.insSubTask(epic.getId());
        assertEquals(subTaskCount, epic.getSubTaskIds().size(), "epic added myself to the subtasks");
    }

    @Test //проверьте, что объект Subtask нельзя сделать своим же эпиком
    void subTaskDoesNotMakeItselfItsOwnEpic() {
        Epic epic = new Epic("Epic01", null, TaskStatus.NEW);
        final int epicId = taskManager.insEpic(epic);
        SubTask subTask = new SubTask("SubTask0101", null, TaskStatus.NEW, epic.getId());
        final int subTaskId = taskManager.insSubTask(subTask);
        subTask.setId(epicId);
        assertEquals(subTaskId, subTask.getId(), "subTask make itself its own epic");
    }

    @Test // создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    void taskManagerAdd() {
        final Task task = new Task("Task01", null, TaskStatus.NEW);
        final int id = taskManager.insTask(task);
        assertNotNull(taskManager.getTask(id), "task is missing");
        assertEquals(task, taskManager.getTask(id), "task it is changed");
    }

    @Test // убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    void historyManagerAdd() {
        Task task = new Task("Task01", null, TaskStatus.NEW);
        historyManager.add(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        historyManager.add(task);
        assertNotNull(historyManager.getHistory().getLast(), "task is missing");
        assertEquals(task, historyManager.getHistory().getLast(),"task it is changed");
    }
}