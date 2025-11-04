package manager;

import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class InFileTaskManager extends InMemoryTaskManager {
    private final Path path;

    public InFileTaskManager(String pathName) {
        //#TODO@BOBA
        super();
        this.path = Paths.get(pathName);
    }

    public static InFileTaskManager LoadFromFile(String pathName) {
        //#TODO@BOBA
        InFileTaskManager tm = new InFileTaskManager(pathName);
        try {
            tm.loadFromCSV();
            return tm;
        } catch (IOException e) {
            throw new ManagerLoadException("Error load from file: " + pathName);
        }
    }

    private void loadFromCSV() throws IOException {
        //#ASK@BOBA: readAllLines
        //#TODO@BOBA
        if (!Files.exists(path)) {
            Files.createFile(path);
            return;
        }
        try (BufferedReader buff = new BufferedReader(new FileReader(path.toFile()))) {
            buff.readLine(); //#ASK@BOBA: firstRow
            String row;
            while ((row = buff.readLine()) != null) {
                if (row.isEmpty()) {
                    break;
                }
                Task task = fromString(row);
                if (task == null) {
                    break;
                }
                int id = task.getId();
                if (seqId < id) {
                    seqId = id;
                }
                switch (task.getTaskType()) {
                    case TASK:
                        tasks.put(id, task);
                        break;
                    case EPIC:
                        epics.put(id, (Epic) task);
                        break;
                    case SUBTASK:
                        subtasks.put(id, (SubTask) task);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void save() {
        //#ALERT@BOBA: recording sequence is important, - subtasks only after epics
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            BufferedWriter buff = new BufferedWriter(new FileWriter(path.toFile()));
            buff.write(ManagerFileCSVHelper.HEADER);

            Collection<Task> taskValues = tasks.values();
            for (Task task : taskValues) {
                buff.write(toString(task));
            }
            Collection<Epic> epicValues = epics.values();
            for (Task task : epicValues) { //#ASK@BOBA: Epic epic
                buff.write(toString(task));
            }
            Collection<SubTask> subTaskValues = subtasks.values();
            for (Task task : subTaskValues) { //#ASK@BOBA: SubTask subTask
                buff.write(toString(task));
            }
            buff.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Error save to file: " + path.toFile());
        }
    }

    private Task fromString(String value) {
        return ManagerFileCSVHelper.fromString(value);
    }

    private String toString(Task task) {
        return ManagerFileCSVHelper.toString(task);
    }

    @Override
    public Integer insTask(Task task) {
        Integer seqId = super.insTask(task);
        save();
        return seqId;
    }

    @Override
    public void delTask(int id) {
        super.delTask(id);
        save();
    }

    @Override
    public boolean updTask(Task task) {
        boolean result = super.updTask(task);
        save();
        return result;
    }

    @Override
    public void delTasks() {
        super.delTasks();
        save();
    }

    @Override
    public Integer insSubTask(SubTask subTask) {
        Integer seqId = super.insSubTask(subTask);
        save();
        return seqId;
    }

    @Override
    public boolean updSubTask(SubTask subTask) {
        boolean result = super.updSubTask(subTask);
        save();
        return result;
    }

    @Override
    public void delSubTask(int id) {
        super.delSubTask(id);
        save();
    }

    @Override
    public void delSubTasks() {
        super.delSubTasks();
        save();
    }

    @Override
    public Integer insEpic(Epic epic) {
        Integer seqId = super.insEpic(epic);
        save();
        return seqId;
    }

    @Override
    public boolean updEpic(Epic epic) {
        boolean result = super.updEpic(epic);
        save();
        return result;
    }

    @Override
    public void delEpic(int id) {
        super.delEpic(id);
        save();
    }

    @Override
    public void delEpics() {
        super.delEpics();
        save();
    }
}

