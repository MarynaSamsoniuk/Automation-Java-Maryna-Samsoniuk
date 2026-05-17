package ua.edu.ukma.samsoniuk.lab1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "surnameServlet", value = "/surname")
public class SurnameServlet extends HttpServlet {
    private String surname;

    public void init() {
        surname = "Samsoniuk";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("surname", surname);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/surname.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {

    }
}


