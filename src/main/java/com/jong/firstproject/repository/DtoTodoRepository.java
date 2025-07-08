package com.jong.firstproject.repository;

import com.jong.firstproject.model.DtoTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DtoTodoRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<DtoTodo> todoRowMapper = (resultSet, rowNum) -> {
        DtoTodo todo = DtoTodo.builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .completed(resultSet.getBoolean("completed"))
                .userId(resultSet.getInt("user_id"))
                .build();

        return todo;
    };

    public List<DtoTodo> findAllByUserId(int userId) {
        String sql = "SELECT * FROM dto_todo WHERE user_id = ? ORDER BY id";

        return jdbcTemplate.query(sql, todoRowMapper, userId);
    }

    public int save(DtoTodo dtoTodo) {
        String sql = "INSERT INTO dto_todo (user_id, title, completed) VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql, dtoTodo.getUserId(), dtoTodo.getTitle(), dtoTodo.isCompleted());
    }

    public DtoTodo findByIdAndUserId(int id, int userId) {
        String sql = "SELECT * FROM dto_todo WHERE id = ? AND user_id = ?";

        return jdbcTemplate.queryForObject(sql, todoRowMapper, id , userId);
    }

    public int update(DtoTodo dtoTodo) {
        String sql = "UPDATE dto_todo SET title = ?, completed = ? WHERE id = ? AND user_id = ?";

        return jdbcTemplate.update(sql, dtoTodo.getTitle(), dtoTodo.isCompleted(), dtoTodo.getId(), dtoTodo.getUserId());
    }

    public int deleteByIdAndUserId(int id, int userId) {
        String sql = "DELETE FROM dto_todo WHERE id = ? AND user_id = ?";

        return jdbcTemplate.update(sql, id, userId);
    }
}
