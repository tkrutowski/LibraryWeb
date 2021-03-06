package net.focik.Library.controller;

import net.focik.Library.dao.BookstoreEntityDao;
import com.google.gson.Gson;
import net.focik.Library.exceptions.NoEmptyValueException;
import net.focik.Library.model.BookstoreEntity;
import net.focik.Library.model.OperationType;
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

@WebServlet("/Bookstore")
public class BookstoreServlet extends HttpServlet {
    BookstoreEntityDao bookstoreDao;
    private static Logger logger = LoggerFactory.getLogger(BookstoreServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());

        int id = Integer.parseInt(request.getParameter("id"));
        BookstoreEntity bookstore=(createBookstore(request, operation));
        bookstoreDao = new BookstoreEntityDao();

        switch (operation) {
            case DELETE:
                bookstoreDao.deleteById(id);
                break;
            case UPDATE:
                if(bookstore != null)
                    bookstoreDao.update(createBookstore(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
            case CREATE:
                if(bookstore != null)
                    bookstoreDao.create(createBookstore(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        List<BookstoreEntity> bookstoreList = createBookstoreList(request);

        Gson gson = new Gson();
        String bookstoreJSON = gson.toJson(bookstoreList);

        PrintWriter printWriter = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        printWriter.write(bookstoreJSON);
        printWriter.close();

    }

    private BookstoreEntity createBookstore(HttpServletRequest request, OperationType operationType) {
        BookstoreEntity bookstore = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String www = request.getParameter("www");
            switch (operationType) {
                case UPDATE:
                    if (id == 0 || name == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
                case CREATE:
                    if (name == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
            }
            bookstore = new BookstoreEntity();
            bookstore.setIdBookstore(id);
            bookstore.setName(name);
            bookstore.setWww(www);
        } catch (NoEmptyValueException e) {
            e.printStackTrace();
            logger.error("Can't create bookstore with empty parameters");
        }
        return bookstore;
    }

    private List<BookstoreEntity> createBookstoreList(HttpServletRequest request) {

        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());
        int id = Integer.parseInt(request.getParameter("id"));
        bookstoreDao = new BookstoreEntityDao();
        List<BookstoreEntity> bookstoreList = new ArrayList<>();

        switch (operation) {
            case READ_ONE:
                BookstoreEntity bookstore = bookstoreDao.findById(id);
                bookstoreList.add(bookstore);
                break;
            case READ_ALL:
                bookstoreList = bookstoreDao.getAll();
                break;
        }
        return bookstoreList;
    }
}
