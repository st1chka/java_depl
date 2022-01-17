package by.eugenol.interfaces;

import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsersDao<T, ID> {


    Users getUsersById(Integer id) throws SQLException;

    List<T> findAll() throws SQLException;

    Serializable save(T o) throws  SQLException;

    void update(T o) throws SQLException;

    boolean deleteUserById(Serializable id) throws SQLException;

    Serializable saveUserWithRoles(Set<Roles> rolesSet, Users user) throws SQLException;
}
