package ru.andrey.spring.dao;

import org.springframework.stereotype.Component;
import ru.andrey.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
private static final String URL = "jdbc:mysql://localhost:3306/first";
private static final String USERNAME = "root";
private static final String PASS = "741480qweasdzxcQWEASDZXC";

private static Connection connection;
static {
    try {
        Driver driver = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(driver);
    }catch (SQLException e){
        System.out.println("o");
        e.printStackTrace();
    }
    try {
        connection = DriverManager.getConnection(URL,USERNAME,PASS);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    public List<Person> index() {
      List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";

            ResultSet resultSet=statement.executeQuery(SQL);
            while (resultSet.next()){
                Person person =new Person();
                person.setId(resultSet.getInt("id"));
                person.setName((resultSet.getString("name")));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException e) {

        }
        return people;
    }

    public Person show(int id) {
    Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Person WHERE id = ?");

            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            person= new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person VALUES (1, ? , ? , ?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());
preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 

    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("UPDATE Person SET name = ?, age = ?, email = ? WHERE id = ?");
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2,updatedPerson.getAge());
            preparedStatement.setString(3,updatedPerson.getEmail());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM Person WHERE id = ?");
            preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
