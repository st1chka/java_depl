package by.eugenol.dao;

import by.eugenol.DataSourceFactory;
import by.eugenol.interfaces.RolesDao;
import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;

import java.sql.*;
import java.util.*;

public class RolesDaoImpl implements RolesDao <Roles, Integer> {

    @Override
    public Roles find(Integer id) throws SQLException {
        String sql = "SELECT * FROM public4.roles WHERE id = (?)";
        Integer role_id = 0;
        String login = "";

        Connection connection = DataSourceFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            login = resultSet.getString("role_name");
        }
        return new Roles(id, login);
    }

    @Override
    public List<Roles> findAll() throws SQLException {
        List<Roles> roleList = new ArrayList<>();
        String sql = "SELECT * from public4.roles";
        int role_id;
        String name;

        Connection connection = DataSourceFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            role_id = resultSet.getInt("id");
            name = resultSet.getString("role_name");
            Roles roles = new Roles(role_id, name);
            roleList.add(roles);
        }
        return roleList;
    }

    @Override
    public boolean save(Roles roles) throws SQLException {
        String sql = "INSERT into public4.roles (name) VALUES (?)";
        boolean rowInserted;
        Connection connection = DataSourceFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, roles.getName());
        rowInserted = statement.executeUpdate() > 0;
        return rowInserted;
    }

    @Override
    public boolean update(Roles roles) throws SQLException {
        String sql = "UPDATE public4.roles (login) VALUES (?)";
        boolean rowInserted;
        Connection connection = DataSourceFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, roles.getName());
        rowInserted = statement.executeUpdate() > 0;
        return rowInserted;
    }

    @Override
    public boolean delete(Roles roles) throws SQLException {

        String sql = "DELETE FROM public4.roles where id = ?";
        boolean rowDeleted;

        Connection connection = DataSourceFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, roles.getId());
        rowDeleted = statement.executeUpdate() > 0;
        return rowDeleted;
    }
}
