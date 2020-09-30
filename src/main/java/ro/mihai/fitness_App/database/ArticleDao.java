package ro.mihai.fitness_App.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Article> findAll() {
        return jdbcTemplate.query("select * from article", new ArticleRowMapper());
    }

    public Article findById(Integer id) {
        return jdbcTemplate.query("select * from article where id = " + id, new ArticleRowMapper()).get(0);

    }

    public List<Article> findAllByCategoryId(Integer category_id) {
        return jdbcTemplate.query("select * from article where category_id = " + category_id, new ArticleRowMapper());

    }

    public List<Article> findAllByCategoryIdAndChosenOption(Integer category_id, Integer chosen_option) {
        return jdbcTemplate.query("select * from article where category_id =" + category_id + " AND chosen_option=" + chosen_option, new ArticleRowMapper());

    }
}
