package ro.mihai.fitness_App.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleArchiveDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ArticleArchive> findAll() {
        return jdbcTemplate.query("select * from articleArchive", new ArticleArchiveRowMapper());
    }

    public ArticleArchive findById(Integer id) {
        return jdbcTemplate.query("select * from articleArchive where id = " + id, new ArticleArchiveRowMapper()).get(0);
    }

    public List<ArticleArchive> findAllByTitle(String title) {
        return jdbcTemplate.query("select * from articleArchive where title='" + title + "'", new ArticleArchiveRowMapper());
    }

    public List<ArticleArchive> findAllByUserIdAndCategoryId(Integer user_id, Integer category_id) {
        return jdbcTemplate.query("select * from articleArchive where category_id = " + category_id + " AND user_id = " + user_id, new ArticleArchiveRowMapper());
    }

    public void save(String title, String description, String content, String photoFile, int user_id, int category_id) {
        jdbcTemplate.update("insert into articleArchive values(null, ?, ?, ?, ?, ?, ?)", title, description, content, photoFile, user_id, category_id);
    }
}
