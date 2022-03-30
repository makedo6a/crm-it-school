package kg.itschool.dao.impl;

import kg.itschool.dao.GroupDao;
import kg.itschool.dao.daoutil.Log;
import kg.itschool.model.Group;

import java.sql.*;
import java.time.LocalTime;

public class GroupDaoImpl implements GroupDao {

    public GroupDaoImpl() {
        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_groups(" +
                    "id           BIGSERIAL, " +
                    "name       VARCHAR(50)  NOT NULL, " +
                    "group_time  TIMESTAMP   NOT NULL, " +
                    "date_created TIMESTAMP    NOT NULL DEFAULT NOW(), " +
                    "" +
                    "CONSTRAINT pk_course_id PRIMARY KEY(id))";

            System.out.println("Creating statement...");
            statement = connection.createStatement();
            System.out.println("Executing create table statement...");
            statement.execute(ddlQuery);
            System.out.println(ddlQuery);

        } catch (SQLException e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Group save(Group group) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Group savedGroup = null;

        try {
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Connection established");

            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String createQuery = "INSERT INTO tb_groups(" +
                    "name, group_time, date_created ) " +

                    "VALUES(?, ?, ?)";

            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, String.valueOf(group.getGroupTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(group.getDateCreated()));

            preparedStatement.execute();
            close(preparedStatement);

            String readQuery = "SELECT * FROM tb_courses ORDER BY id DESC LIMIT 1";

            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            savedGroup = new Group();
            savedGroup.setId(resultSet.getLong("id"));
            savedGroup.setName(resultSet.getString("name"));
            savedGroup.setGroupTime(LocalTime.parse(resultSet.getString("group_time")));
            savedGroup.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            Log.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(),e.getMessage());
            e.printStackTrace();

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return savedGroup;
    }

    @Override
    public Group findById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Group group = null;

        try {
            connection = getConnection();

            String readQuery = "SELECT * FROM tb_groups WHERE id = ?";

            preparedStatement = connection.prepareStatement(readQuery);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            group = new Group();
            group.setId(resultSet.getLong("id"));
            group.setName(resultSet.getString("name"));
            group.setGroupTime(LocalTime.parse(resultSet.getString("group_time")));
            group.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return group;
    }

}
