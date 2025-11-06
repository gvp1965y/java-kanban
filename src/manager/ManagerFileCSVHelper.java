/* #ASIS@TOR: format file
id,type,name,status,description,epic
1,TASK,Task1,NEW,Description task1,
2,EPIC,Epic2,DONE,Description epic2,
3,SUBTASK,Sub Task2,DONE,Description sub task3,2
*/

package manager;

import tasks.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ManagerFileCSVHelper {

    public static final String FILENAME = "MainTasks.csv";
    public static final String HEADER = "id,type,name,status,description,epic\n";
    public static final String FIELD_SEPARATOR = ",";
    public static final String ROW_SEPARATOR = "\n";

    private ManagerFileCSVHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDefaultFile() {
        return FILENAME;
    }

    public static void deleteDefaultFile() {
        try {
            Files.deleteIfExists(Paths.get(FILENAME));
        } catch (IOException e) {
            throw new ManagerFileException("Error delete file: " + FILENAME);
        }

    }

    public static Task fromString(String row) {
        ArrayList<String> fields =  new ArrayList<>(Arrays.asList(row.split(FIELD_SEPARATOR)));
        int id = Integer.parseInt(fields.get(0));
        TaskType type = TaskType.valueOf(fields.get(1));
        String taskName = fields.get(2);
        TaskStatus taskStatus = TaskStatus.valueOf(fields.get(3));
        String taskDescription = fields.get(4);
        switch (type) {
            case TASK:
                Task task = new Task(taskName, taskDescription, taskStatus);
                task.setId(id);
                return task;
            case EPIC:
                Epic epic = new Epic(taskName, taskDescription, taskStatus);
                epic.setId(id);
                return epic;
            case SUBTASK:
                SubTask subTask = new SubTask(taskName, taskDescription, taskStatus, Integer.parseInt(fields.get(5)));
                subTask.setId(id);
                return subTask;
            default:
                return null; //#ASK@BOBA
        }
    }

    public static String toString(Task task) {
        TaskType taskType = task.getTaskType();
        ArrayList<String> fields = new ArrayList<>();

        fields.add(Integer.toString(task.getId()));
        fields.add(taskType.toString());
        fields.add(task.getName());
        fields.add(task.getStatus().toString());
        fields.add(task.getDescription());
        if (task.getTaskType() == TaskType.SUBTASK) {
            fields.add(Integer.toString(((SubTask) task).getEpicId()));
        } else {
            fields.add("");
        }

        return String.join(FIELD_SEPARATOR, fields) + ROW_SEPARATOR;
    }


}
