package net.focik.Library.controller;

import net.focik.Library.dao.SeriesDao;
import com.google.gson.Gson;
import net.focik.Library.exceptions.NoEmptyValueException;
import net.focik.Library.model.OperationType;
import net.focik.Library.model.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Series")
public class SeriesServlet extends HttpServlet {
    SeriesDao seriesDao;
    private static Logger logger = LoggerFactory.getLogger(SeriesServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());

        int id = Integer.parseInt(request.getParameter("id"));
        Series series=(createSeries(request, operation));
        seriesDao = new SeriesDao();

        switch (operation) {
            case DELETE:
                seriesDao.delete(id);
                break;
            case UPDATE:
                if(series != null)
                    seriesDao.update(createSeries(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
            case CREATE:
                if(series != null)
                    seriesDao.create(createSeries(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        List<Series> seriesList = createSeriesList(request);

        Gson gson = new Gson();
        String seriesJSON = gson.toJson(seriesList);

        PrintWriter printWriter = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        printWriter.write(seriesJSON);
        printWriter.close();

    }

    private Series createSeries(HttpServletRequest request, OperationType operationType) {
        Series series = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            switch (operationType) {
                case UPDATE:
                    if (id == 0 || title == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
                case CREATE:
                    if (title == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
            }
            series = new Series();
            series.setIdSeries(id);
            series.setTitle(title);
            series.setDescription(description);
        } catch (NoEmptyValueException e) {
            e.printStackTrace();
            logger.error("Can't create series with empty parameters");
        }
        return series;
    }

    private List<Series> createSeriesList(HttpServletRequest request) {

        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());
        int id = Integer.parseInt(request.getParameter("id"));
        seriesDao = new SeriesDao();
        List<Series> seriesList = new ArrayList<>();

        switch (operation) {
            case READ_ONE:
                Series series = seriesDao.readOne(id);
                seriesList.add(series);
                break;
            case READ_ALL:
                seriesList = seriesDao.readAll();
                break;
            case DELETE:
                seriesDao.delete(id);
                break;
        }
        return seriesList;
    }
}
