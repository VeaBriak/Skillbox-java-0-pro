package main;

import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController
{
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks/")
    public List<Task> list() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @PostMapping("/tasks/")
    public int add(Task task) {
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        Optional<Task> optionalTaskDelete = taskRepository.findById(id);
        if (optionalTaskDelete.isPresent()) {
            taskRepository.delete(optionalTaskDelete.get());
            return ResponseEntity.status(HttpStatus.OK).body("Removed!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable int id) {
        Optional<Task> optionalTaskUpdate = taskRepository.findById(id);
        if (optionalTaskUpdate.isPresent()) {
            taskRepository.save(optionalTaskUpdate.get());
            return ResponseEntity.status(HttpStatus.OK).body("Update!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }
}
