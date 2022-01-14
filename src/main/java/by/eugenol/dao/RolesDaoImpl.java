package by.eugenol.dao;

import by.eugenol.DataSourceFactory;
import by.eugenol.data.SessionFactoryHolder;
import by.eugenol.interfaces.RolesDao;
import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.*;

public class RolesDaoImpl implements RolesDao <Roles, Integer> {

    private final SessionFactory sessionFactory;

    public RolesDaoImpl() {
        sessionFactory = SessionFactoryHolder.getSessionFactory();
    }


    @Override
    public Roles getRolesById(Integer id) throws SQLException {
        Session session = sessionFactory.openSession();
        Roles roles = session.get(Roles.class, id);
        session.close();
        return roles;
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
