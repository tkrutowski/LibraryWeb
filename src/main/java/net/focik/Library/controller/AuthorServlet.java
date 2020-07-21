package net.focik.Library.controller;

import net.focik.Library.dao.AuthorDao;
import com.google.gson.Gson;
import net.focik.Library.exceptions.NoEmptyValueException;
import net.focik.Library.model.Author;
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

@WebServlet("/Author")
public class AuthorServlet extends HttpServlet {

    AuthorDao authorDao;
    private static Logger logger = LoggerFactory.getLogger(AuthorServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());

        int idAuthor = Integer.parseInt(request.getParameter("id"));
        Author author=(createAuthor(request, operation));
        authorDao = new AuthorDao();

        switch (operation) {
            case DELETE:
                authorDao.delete(idAuthor);
                break;
            case UPDATE:
                if(author != null)
                    authorDao.update(createAuthor(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
            case CREATE:
                if(author != null)
                    authorDao.create(createAuthor(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        List<Author> authorList = createAuthorList(request);

        Gson gson = new Gson();
        String authorsJSON = gson.toJson(authorList);

        PrintWriter printWriter = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        printWriter.write(authorsJSON);
        printWriter.close();

    }

    private Author createAuthor(HttpServletRequest request, OperationType operationType) {
        Author author = null;
        try {
            int idAuthor = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            switch (operationType) {
                case UPDATE:
                    if (idAuthor == 0 || firstName == "" || lastName == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
                case CREATE:
                    if (firstName == "" || lastName == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
            }
            author = new Author();
            author.setId(idAuthor);
            author.setFirstName(firstName);
            author.setLastName(lastName);
        } catch (NoEmptyValueException e) {
            e.printStackTrace();
            logger.error("Can't create author with empty parameters");
        }
        return author;
    }

    private List<Author> createAuthorList(HttpServletRequest request) {

        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());
        int idAuthor = Integer.parseInt(request.getParameter("id"));
        authorDao = new AuthorDao();
        List<Author> authorList = new ArrayList<>();

        switch (operation) {
            case READ_ONE:
                Author author = authorDao.readOne(idAuthor);
                authorList.add(author);
                break;
            case READ_ALL:
                authorList = authorDao.readAll();
                break;
            case DELETE:
                authorDao.delete(idAuthor);
                break;
        }
        return authorList;
    }
}
