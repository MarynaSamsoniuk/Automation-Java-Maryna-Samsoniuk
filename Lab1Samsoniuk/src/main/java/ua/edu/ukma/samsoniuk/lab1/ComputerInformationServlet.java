package ua.edu.ukma.samsoniuk.lab1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;

@WebServlet(name = "ComputerInformationServlet", value = "/computer-info")
public class ComputerInformationServlet extends HttpServlet {
    private HashMap<String, String> computerInformation;
    private Runtime runtime;

    public void init() {
        computerInformation = new HashMap<>();
        runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        computerInformation.put("architecture", osBean.getArch());
        computerInformation.put("osName", osBean.getName());
        computerInformation.put("processors", String.valueOf(osBean.getAvailableProcessors()));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        computerInformation.put("maxMemory",  MemoryFormatter.formatMemory(runtime.maxMemory()));
        computerInformation.put("totalMemory",  MemoryFormatter.formatMemory(runtime.totalMemory()));
        computerInformation.put("freeMemory",  MemoryFormatter.formatMemory(runtime.freeMemory()));

        request.setAttribute("computerInformation", computerInformation);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/computer-info.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {

    }
}
