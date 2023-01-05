package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.User;
import com.example.lab4secondtry.Repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserDbRepository implements Repository<User> {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructor with parameters
     * @param url url of database
     * @param username username of database
     * @param password password of database
     */
    public UserDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Get User by id
     * @param id id of entity
     * @return Optional of found User
     */
    @Override
    public Optional<User> findOne(long id) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long idUser = resultSet.getLong("id_user");
                if (idUser == id) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    User user = new User(firstName, lastName);
                    return Optional.of(user);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Get all Users
     * @return Iterable of all Users
     */
    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id_user");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User user = new User(firstName, lastName);
                user.setId(id);
                users.add(user);
            }
            connection.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Add User
     * @param entity entity to be added
     */
    @Override
    public void save(User entity) {
        String sql = "insert into users (first_name, last_name) values (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());

            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete User
     * @param id id of entity
     * @return Optional of deleted User
     */
    @Override
    public Optional<User> delete(long id) {
        String sql = "DELETE FROM users WHERE id_user = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);

            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Update User
     * @param entity entity to be updated
     */
    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET first_name = ? WHERE id_user = ?\n" +
                "UPDATE users SET last_name = ? WHERE id_user = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getFirstName());
            ps.setLong(2, entity.getId());
            ps.setString(3, entity.getLastName());
            ps.setLong(4, entity.getId());

            ps.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
