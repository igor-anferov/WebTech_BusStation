package bus_station.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Igor on 21.04.2017.
 */

@WebServlet("/")
public class Home extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        // Do required initialization
        message = "test Servlet";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", "Igor");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}