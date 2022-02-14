package com.TodoList.Springtodo.todo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.Optional;

@DataJpaTest
public class TodoTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TestEntityManager entityManager;

    String title = "테스트 하기";
    String content = "TDD 테스트하기";
    private Todo addTodo() {
        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .build();

        return entityManager.persist(todo);
    }

    @Test
    public void getTodoTest() {
        // GIVEN
        Todo todo = addTodo();
        System.out.println(todo.getId());
        System.out.println(todo.getTitle());
        System.out.println(todo.getContent());
        System.out.println(todo.getProgress());

        Long id = todo.getId();

        // WHEN
        Optional<Todo> savedTodo = todoRepository.findById(id);

        // THEN
        Assertions.assertThat(savedTodo.isPresent()).isEqualTo(true);

        // Optional이기 때문에 isPresent() 사용
        if (savedTodo.isPresent()) {
            Assertions.assertThat(savedTodo.get().getTitle()).isEqualTo(title);
            Assertions.assertThat(savedTodo.get().getContent()).isEqualTo(content);
        }
    }

    @Test
    public void deleteTodoTest() {
        // GIVEN
        Todo todo = addTodo();
        Long id = todo.getId();

        // WHEN
        todoRepository.deleteById(id);

        // THEN
        Assertions.assertThat(entityManager.find(Todo.class, id)).isNull();
    }
}