package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipDbRepository implements Repository<Friendship> {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructor with parameters
     * @param url url of database
     * @param username username of database
     * @param password password of database
     */
    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Get Friendship by id
     * @param id id of entity
     * @return Optional of found Friendship
     */
    @Override
    public Optional<Friendship> findOne(long id) {
        String sql = "SELECT * FROM friendships WHERE id_friendship = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                long idUser1 = resultSet.getLong("id_user1");
                long idUser2 = resultSet.getLong("id_user2");
                String status = resultSet.getString("status");
                Date date = resultSet.getDate("date");
                Friendship friendship = new Friendship(idUser1, idUser2, status, date);

                connection.close();
                return Optional.of(friendship);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Get all Friendships
     * @return Iterable of all Friendship
     */
    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id_friendship");
                long idUser1 = resultSet.getLong("id_user1");
                long idUser2 = resultSet.getLong("id_user2");
                String status = resultSet.getString("status");
                Date date = resultSet.getDate("date");

                Friendship friendship = new Friendship(idUser1, idUser2, status, date);
                friendship.setId(id);
                friendships.add(friendship);
            }

            connection.close();
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    /**
     * Add Friendship
     * @param entity entity to be added
     */
    @Override
    public void save(Friendship entity) {
        String sql = "INSERT INTO friendships (id_user1, id_user2, status, date) values (?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity.getFirstUserId());
            ps.setLong(2, entity.getSecondUserId());
            ps.setString(3, entity.getStatus());
            ps.setDate(4, new Date(entity.getDate().getTime()));

            ps.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete Friendship
     * @param id id of entity
     * @return Optional of deleted Friendship
     */
    @Override
    public Optional<Friendship> delete(long id) {
        String sql = "DELETE FROM friendships WHERE id_friendship = ?";
        Friendship friendship = null;
        try {
            Optional<Friendship> opFriendship = ActiveRepoFriendship.getInstance().getRepository().findOne(id);
            if (opFriendship.isPresent())
                friendship = opFriendship.get();

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);

            ps.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            return Optional.ofNullable(friendship);
        }
        return Optional.empty();
    }

    /**
     * Update Friendship
     * @param entity entity to be updated
     */
    @Override
    public void update(Friendship entity) {
        String sql1 = "UPDATE friendships SET id_user1 = ? WHERE id_friendship = ?";
        String sql2 = "UPDATE friendships SET id_user2 = ? WHERE id_friendship = ?";
        String sql3 = "UPDATE friendships SET status = ? WHERE id_friendship = ?";
        String sql4 = "UPDATE friendships SET date = ? WHERE id_friendship = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setLong(1, entity.getFirstUserId());
            ps1.setLong(2, entity.getId());

            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setLong(1, entity.getSecondUserId());
            ps2.setLong(2, entity.getId());

            PreparedStatement ps3 = connection.prepareStatement(sql3);
            ps3.setString(1, entity.getStatus());
            ps3.setLong(2, entity.getId());

            PreparedStatement ps4 = connection.prepareStatement(sql4);
            ps4.setDate(1, new Date(entity.getDate().getTime()));
            ps4.setLong(2, entity.getId());

            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
            ps4.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
