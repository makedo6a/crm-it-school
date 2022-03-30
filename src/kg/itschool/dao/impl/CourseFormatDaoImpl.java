package kg.itschool.dao.impl;

import kg.itschool.dao.CourseFormatDao;
import kg.itschool.dao.daoutil.Log;
import kg.itschool.model.CourseFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseFormatDaoImpl implements CourseFormatDao {

    public CourseFormatDaoImpl() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;




        try{

            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Connection established");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_course_format("+
                    "id BIGSERIAL,"+
                    "course_format VARCHAR(50) NOT NULL, "+
                    "course_duration_weeks INT NOT NULL, "+
                    "lesson_duration TIME NOT NULL, "+
                    "Lesson_per_week INT NOT NULL, "+
                    "is_online BOOLEAN NOT NULL, "+
                    "date_created TIMESTAMP NOT NULL DEFAULT NOW(), "+

                    "CONSTRAINT pk_course_format_id PRIMARY KEY(id), "+
                    "CONSTRAINT course_duration_weeks_negative_or_zero CHECK (course_duration_weeks > 0), "+
                    "CONSTRAINT lesson_per_week_negative_or_zero CHECK (lesson_per_week > 0), "+
            ");";

            Log.info(this.getClass().getSimpleName(), PreparedStatement.class.getSimpleName(),"Creating preparedStatement");
             preparedStatement = connection.prepareStatement(ddlQuery);
             preparedStatement.execute();
        } catch (SQLException e){
            Log.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(),e.getMessage());
            e.printStackTrace();

        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public CourseFormat save(CourseFormat courseFormat) {
        return null;
    }

    @Override
    public CourseFormat findById(Long id) {
        return null;
    }
}
