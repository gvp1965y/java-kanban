//csv_rows_format: \n
//csv_columns_format: id,type,name,status,description,*epic

package manager;

import tasks.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileTaskManager {

    private FileTaskManager() {
        throw new IllegalStateException("Utility class");
    }

    public static Task fromStringCSV(String csvSeparator, String row) {
        ArrayList<String> columns =  new ArrayList<>(Arrays.asList(row.split(csvSeparator)));
        int id = Integer.parseInt(columns.get(0));
        TaskType type = TaskType.valueOf(columns.get(1));
        String taskName = columns.get(2);
        TaskStatus taskStatus = TaskStatus.valueOf(columns.get(3));
        String taskDescription = columns.get(4);
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
                SubTask subTask = new SubTask(taskName, taskDescription, taskStatus, Integer.parseInt(columns.get(5)));
                subTask.setId(id);
                return subTask;
            default:
                return null; //#ASK@BOBA
        }
    }

    public static String toStringCSV(String csvSeparator, Task task) {
        TaskType taskType = task.getTaskType();
        ArrayList<String> columns = new ArrayList<>();

        columns.add(Integer.toString(task.getId()));
        columns.add(taskType.toString());
        columns.add(task.getName());
        columns.add(task.getStatus().toString());
        columns.add(task.getDescription());
        if (task.getTaskType() == TaskType.SUBTASK) {
            columns.add(Integer.toString(((SubTask) task).getEpicId()));
        }

        return String.join(csvSeparator, columns);
    }


}
