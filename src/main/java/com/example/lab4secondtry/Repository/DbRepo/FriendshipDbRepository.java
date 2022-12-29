package com.example.lab4secondtry.Repository.DbRepo;

import com.example.lab4secondtry.Domain.Friendship;
import com.example.lab4secondtry.Repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipDbRepository implements Repository<Friendship> {
    private String url;
    private String username;
    private String password;

    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

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

//            while (resultSet.next()) {
//                long idFriendship = resultSet.getLong("id_friendship");
//                if (idFriendship == id) {
//                    long idUser1 = resultSet.getLong("id_user1");
//                    long idUser2 = resultSet.getLong("id_user2");
//                    String status = resultSet.getString("status");
//                    Friendship friendship = new Friendship(idUser1, idUser2, status);
//                    return Optional.of(friendship);
//                }
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

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

    @Override
    public Optional<Friendship> save(Friendship entity) {
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
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> delete(long id) {
        String sql = "DELETE FROM friendships WHERE id_friendship = ?";
        Friendship friendship = null;
        try {
            Optional<Friendship> opFriendship = ActiveRepoFriendship.getInstance().getRepository().findOne(id);
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

    @Override
    public Optional<Friendship> update(Friendship entity) {
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
        return Optional.empty();
    }
}
