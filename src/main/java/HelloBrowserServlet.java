import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet")
public class HelloBrowserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String browser = req.getHeader("User-Agent");

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        if (browser.indexOf("OPR") != -1)
            out.println("<h2>Hello user of Opera");
        else if (browser.indexOf("Edg") != -1)
            out.println("<h2>Hello user of Microsoft Edge</h2>");
        else if (browser.indexOf("Chrome") != -1)
            out.println("<h2>Hello user of Google Chrome</h2>");
        else if (browser.indexOf("Firefox") != -1)
            out.println("<h2>Hello user of Mozilla Firefox</h2>");
        else if (browser.indexOf("Safari") != -1)
            out.println("<h2>Hello user of Safari");
        else
            out.println("<h2>Hello user of any other browser</h2>");
    }
}
