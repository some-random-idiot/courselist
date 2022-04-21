package courselist.persistence;

import courselist.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseDao {
    // Provide connection to the database.
    private Connection connection;

    /**
     * Initialize a new CourseDao with JDBC connection to the database.
     * @param connection
     */
    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieve a course from the database via its course id.
     * @param id
     */
    public Course get(int id) {
        Course course = null;

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM courses WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            // Create a Course object from the first row of resultSet.
            if (resultSet.next()) {
                createCourse(course, resultSet, id);
            }
        }
        catch (SQLException e) {
            // Throw an exception with a descriptive message.
            throw new RuntimeException("Error retrieving course with id of " + id, e);
        }
        finally {
            // TODO: Close the statement.
        }

        return course;
    }

    private void createCourse(Course course, ResultSet resultSet, int id) throws SQLException {
        String courseNumber = resultSet.getString("course_number");
        String title = resultSet.getString("title");
        int credits = resultSet.getInt("credits");
        double difficulty = resultSet.getDouble("difficulty");

        course = new Course(courseNumber, title, credits);
        course.setDifficulty(difficulty);
        course.setId(id);
    }
}
