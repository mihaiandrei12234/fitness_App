package ro.mihai.fitness_App.database;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet resultSet, int i) throws SQLException {
        Article article = new Article();
        article.setId(resultSet.getInt("id"));
        article.setTitle(resultSet.getString("title"));
        article.setDescription(resultSet.getString("description"));
        article.setContent(resultSet.getString("content"));
        article.setPhotoFile(resultSet.getString("photoFile"));
        article.setCategory_id(resultSet.getInt("category_id"));
        article.setChosen_option(resultSet.getInt("chosen_option"));
        return article;
    }
}
