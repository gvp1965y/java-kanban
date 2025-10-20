package manager;

import tasks.Task;

import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager{
    private Node head;
    private Node tail;
    private final Map<Integer, Node> nodeMap;

    public InMemoryHistoryManager() {
        nodeMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (task == null) { return; }
        removeNode(task.getId());
        setTail(task);
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public void removeNodes(Set<Integer> ids) {
        Iterator<Integer> i = ids.iterator();
        while (i.hasNext()) {
            Integer id = i.next();
            i.remove();
            removeNode(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void setTail(Task task) {
        if (nodeMap.isEmpty()) {
            Node node = new Node(null, task, null); //#ASK@BOBA: clone task
            nodeMap.put(task.getId(), node);
            head = node;
            tail = node;
        } else {
            Node node = new Node(tail, task, null); //#ASK@BOBA: clone task
            nodeMap.put(task.getId(), node);
            node.prev.next = node;
            tail = node;
        }
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>(nodeMap.size());
        Node node = head;
        int idx = 0;
        while (node != null) {
            Task task = node.data;
            taskList.add(idx, task);
            node = node.next;
            idx++;
        }
        return taskList;
    }

    private void removeNode(int id) {
        final Node node = nodeMap.remove(id);
        if (node == null) {
            return;
        }
        if (head == tail) {
            head = null;
            tail = null;
        }
        else if (node == head) {
            head = node.next;
            if (node.next != null) {
                node.next.prev = null;
            }
        }
        else if (node == tail) {
            tail = node.prev;
            if (node.prev != null) {
                node.prev.next = null;
            }
        }
        else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}
