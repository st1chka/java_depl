package by.eugenol.dao;

import by.eugenol.DataSourceFactory;
import by.eugenol.data.SessionFactoryHolder;
import by.eugenol.interfaces.UsersDao;
import by.eugenol.pojos.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.*;

public class UsersDaoImpl implements UsersDao<Users, Integer>  {

    private final SessionFactory sessionFactory;

    public UsersDaoImpl() {
        sessionFactory = SessionFactoryHolder.getSessionFactory();
    }

    @Override
    public Users getUsersById(int id) {
        Session session = sessionFactory.openSession();
        Users users = session.get(Users.class, id);
        session.close();
        return users;
    }


    @Override
    public List<Users> findAll() throws SQLException {
        List<Users> listUser = new ArrayList<>();
        String sql = "SELECT * FROM public4.users";

        Connection conn = DataSourceFactory.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String login = resultSet.getString("login");
            Users user = new Users(login);
            listUser.add(user);
        }
        return listUser;
    }


    @Override
    public boolean save(Users users) throws SQLException {
        String sql = "INSERT into public4.users (login) VALUES (?)";
        boolean rowInserted;
        Connection connection = DataSourceFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, users.getLogin());
        rowInserted = statement.executeUpdate() > 0;
        return rowInserted;
    }


    @Override
    public boolean update(Users users) throws SQLException {
        String sql = "UPDATE  public4.users (login) VALUES (?)";
        boolean rowInserted;
        Connection connection = DataSourceFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, users.getLogin());
        rowInserted = statement.executeUpdate() > 0;
        return rowInserted;
    }

    @Override
    public boolean delete(Users o) throws SQLException {
        String sql = "DELETE FROM public4.users where id=?";
        boolean rowDelete;
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setLong(1, o.getId());
        rowDelete = statement.executeUpdate() > 0;
        return rowDelete;
    }

    public boolean addUserRole(Integer userID, Integer roleId) {
        List<Integer> rolesId = new ArrayList<>();
        Statement statement;
        boolean rowInserted = false;

        String sql1 = "SELECT * FROM public4.roles";
        String sql2 = "INSERT INTO public4.user_roles VALUES (?,?)";
        try (Connection connection = DataSourceFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql1);
            while (rs.next()) {
                Integer i = rs.getInt("id");
                if (roleId != null) {
                    rolesId.add(i);
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            UsersDaoImpl usersDao = new UsersDaoImpl();
            Users users = usersDao.getUsersById(userID);
            for (Integer rl_id : rolesId) {
                if (Objects.equals(rl_id, roleId)) {
                    preparedStatement.setInt(1, users.getId());
                    preparedStatement.setInt(2, rl_id);
                    rowInserted = preparedStatement.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }
}
