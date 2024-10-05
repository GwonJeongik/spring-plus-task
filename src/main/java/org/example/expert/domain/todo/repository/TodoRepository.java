package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    /* weather, 수정일 기준 검색 JPQL*/
    @Query("""
            select t from Todo t
            LEFT JOIN FETCH  t.user u
            where :weather is null or t.weather = :weather
            and :start is null or (t.modifiedAt >= :start)
            and :end is null or (t.modifiedAt <= :end)
            ORDER BY t.modifiedAt DESC
            """)
    Page<Todo> findAllByOrderByModifiedAtDesc(
            @Param(value = "weather") String weather,
            @Param(value = "start") LocalDateTime start,
            @Param(value = "end") LocalDateTime end,
            Pageable pageable
    );

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

}
