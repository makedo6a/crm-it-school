package kg.itschool.dao.impl;

import kg.itschool.dao.CourseDao;
import kg.itschool.dao.daoutil.Log;
import kg.itschool.model.Course;
import kg.itschool.model.CourseFormat;

import java.sql.*;

public class CourseDaoImpl implements CourseDao {

    public CourseDaoImpl() {
        Connection connection = null;
        Statement statement = null;

        try {
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Connection established");

            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_courses(" +
                    "id           BIGSERIAL, " +
                    "name         VARCHAR(50)  NOT NULL, " +
                    "price        MONEY        NOT NULL, " +
                    "date_created TIMESTAMP    NOT NULL DEFAULT NOW(), " +
                    "course_format_id BIGINT   NOT NULL, "+
                    "" +
                    "CONSTRAINT pk_course_id PRIMARY KEY(id), " +
                    "CONSTRAINT fk_course_format_id FOREIGN KEY(course_format_id) " +
                    "REFERENCES tb_courses_format(id) " +
                    ")";

            System.out.println("Creating statement...");
            statement = connection.createStatement();
            System.out.println("Executing create table statement...");
            statement.execute(ddlQuery);
            System.out.println(ddlQuery);

        } catch (SQLException e) {
            Log.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(),e.getMessage());
            e.printStackTrace();

        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Course save(Course course) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Course savedCourse = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String createQuery = "INSERT INTO tb_courses(" +
                    "name, price, date_created, course_format_id ) " +

                    "VALUES(?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, String.valueOf(course.getPrice()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(course.getDateCreated()));
            preparedStatement.setLong(4, course.getId());

            preparedStatement.execute();
            close(preparedStatement);

            String readQuery = "SELECT c.id, c.name, c.price, c.date_created,f.* "+
                    "FROM tb_course AS c " +
                    "JOIN tb_course_format AS f " +
                    "ON c.course_format_id = f.id " +
                    "ORDER BY c.id DESC " +
                    "LIMIT 1";

            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            CourseFormat courseFormat = new CourseFormat();
            courseFormat.setId(resultSet.getLong("f.id"));
            courseFormat.setFormat(resultSet.getString("Course_format"));
            courseFormat.setOnline(resultSet.getBoolean("is_online"));
            courseFormat.setLessonDuration(resultSet.getTime("lesson_duration").toLocalTime());
            courseFormat.setCourseDurationWeeks(resultSet.getInt("course_duration_weeks"));
            courseFormat.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            courseFormat.setLessonsPerWeek(resultSet.getInt("Lessons_per_week"));

            savedCourse = new Course();
            savedCourse.setId(resultSet.getLong("id"));
            savedCourse.setName(resultSet.getString("name"));
            savedCourse.setPrice(Double.parseDouble(resultSet.getString("price")));
            savedCourse.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            savedCourse.setCourseFormat(courseFormat);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return savedCourse;
    }

    @Override
    public Course findById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Course course = null;

        try {
            connection = getConnection();

            String readQuery = "SELECT c.id, c.name, c.price, c.date_created,f.* "+
                    "FROM tb_course AS c " +
                    "JOIN tb_course_format AS f " +
                    "ON c.course_format_id = f.id  " +
                    "WHERE id = ?;";

            preparedStatement = connection.prepareStatement(readQuery);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            course = new Course();
            course.setId(resultSet.getLong("id"));
            course.setName(resultSet.getString("name"));
            course.setPrice(Double.parseDouble(resultSet.getString("price")));
            course.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return course;
    }

}

