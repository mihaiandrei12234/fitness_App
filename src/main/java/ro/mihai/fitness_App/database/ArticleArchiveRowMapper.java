package ro.mihai.fitness_App.database;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleArchiveRowMapper implements RowMapper<ArticleArchive> {
    @Override
    public ArticleArchive mapRow(ResultSet resultSet, int i) throws SQLException {
        ArticleArchive articleArchive = new ArticleArchive();
        articleArchive.setId(resultSet.getInt("id"));
        articleArchive.setTitle(resultSet.getString("title"));
        articleArchive.setDescription(resultSet.getString("description"));
        articleArchive.setContent(resultSet.getString("content"));
        articleArchive.setPhotoFile(resultSet.getString("photoFile"));
        articleArchive.setUser_id(resultSet.getInt("user_id"));
        articleArchive.setCategory_id(resultSet.getInt("category_id"));
        return articleArchive;
    }
}
