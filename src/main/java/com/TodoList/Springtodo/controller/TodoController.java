package com.TodoList.Springtodo.controller;

import com.TodoList.Springtodo.todo.Todo;
import com.TodoList.Springtodo.todo.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/todolist")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTodoList(){
        List<Todo> todoList = todoService.getTodoList();
        return ResponseEntity.ok(todoList);
    }

    // 추가
    @PostMapping
    public ResponseEntity<String> postTodo(@RequestBody Todo todo) throws Exception {
        todoService.setTodo(todo);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable("id") Long id,  @RequestBody Todo todo) throws Exception {
        Optional<Todo> todoOptional = todoService.getTodo(id);
        if (todoOptional.isPresent()) {
            if(todoOptional.get().todoUpdate(id, todo)) {
                return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("BADREQUEST", HttpStatus.BAD_REQUEST);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id){
        if(todoService.getTodo(id).isPresent()) {
            todoService.deleteTodo(id);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("BADREQUEST", HttpStatus.BAD_REQUEST);
        }
    }

}
