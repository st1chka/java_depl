package by.eugenol.servlet;

import by.eugenol.dao.UsersDaoImpl;
import by.eugenol.interfaces.UsersDao;
import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/")
public class MainServlet extends HttpServlet{
        private static final long serialVersionUID = 1L;
        private UsersDao userDao;

        public void init() {
            userDao = new UsersDaoImpl();
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            doGet(request, response);
        }


        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException{
            String action = request.getServletPath();

            try {
                switch (action) {
                    case "/new":
                        showNewForm(request, response);
                        break;
                    case "/insert":
                        insertUser(request, response);
                        break;
                    case "/delete":
                        deleteUser(request, response);
                        break;
                    case "/edit":
                        showEditForm(request, response);
                        break;
                    case "/update":
                        updateUser(request, response);
                        break;
                    default:
                        listUser(request, response);
                        break;
                }
            } catch (SQLException | IOException ex) {
                throw new ServletException(ex);
            }
        }


        private void listUser(HttpServletRequest request, HttpServletResponse response) //Get List of users for creating view on JSP
                throws SQLException, ServletException, IOException {
            List<Users> listUser = userDao.findAll();
            request.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
            dispatcher.forward(request, response);
        }

        private void showNewForm(HttpServletRequest request, HttpServletResponse response) //Forward to new input form
                throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
        }

        private void showEditForm(HttpServletRequest request, HttpServletResponse response) //Forward to edit form
                throws SQLException, ServletException, IOException {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Users existingUser = userDao.getUsersById(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            request.setAttribute("user", existingUser);
            dispatcher.forward(request, response);

        }

        private void insertUser(HttpServletRequest request, HttpServletResponse response) //Get login and role, and insert user to database
                throws SQLException, IOException {
            String login = request.getParameter("login");
            String role = request.getParameter("role");
            Roles roles = new Roles();
            roles.setName(role);
            Set<Roles> rolesSet = new HashSet<>();
            rolesSet.add(roles);
            Users newUser = new Users(login);
            userDao.saveUserWithRoles(rolesSet, newUser);
            response.sendRedirect("list");
        }

        private void updateUser(HttpServletRequest request, HttpServletResponse response) //Get id and login from JSP and update user in database
                throws SQLException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            String login = request.getParameter("login");
            Users user = new Users(login);
            userDao.update(user);
            response.sendRedirect("list");
        }

        private void deleteUser(HttpServletRequest request, HttpServletResponse response) //Get id from JSP and delete user by id using DAO
                throws SQLException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            userDao.deleteUserById(id);
            response.sendRedirect("list");
        }



    }

