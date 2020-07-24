package net.focik.Library.controller;

import net.focik.Library.dao.CategoryEntityDao;
import com.google.gson.Gson;
import net.focik.Library.exceptions.NoEmptyValueException;
import net.focik.Library.model.CategoryEntity;
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

@WebServlet("/Category")
public class CategoryServlet extends HttpServlet {
    CategoryEntityDao categoryDao;
    private static Logger logger = LoggerFactory.getLogger(CategoryServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());

        int id = Integer.parseInt(request.getParameter("id"));
        CategoryEntity category=(createCategory(request, operation));
        categoryDao = new CategoryEntityDao();

        switch (operation) {
            case DELETE:
                categoryDao.deleteById(id);
                break;
            case UPDATE:
                if(category != null)
                    categoryDao.update(createCategory(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
            case CREATE:
                if(category != null)
                    categoryDao.create(createCategory(request, operation));
                else
                    request.getRequestDispatcher("error.jsp").forward(request,response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        List<CategoryEntity> categoryList = createCategoryList(request);

        Gson gson = new Gson();
        String authorsJSON = gson.toJson(categoryList);

        PrintWriter printWriter = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        printWriter.write(authorsJSON);
        printWriter.close();

    }

    private CategoryEntity createCategory(HttpServletRequest request, OperationType operationType) {
        CategoryEntity category = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String Name = request.getParameter("name");
            switch (operationType) {
                case UPDATE:
                    if (id == 0 || Name == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
                case CREATE:
                    if (Name == "") {
                        throw new NoEmptyValueException();
                    }
                    break;
                case DELETE:
                    categoryDao.deleteById(id);
                    break;
            }
            category = new CategoryEntity();
            category.setIdCategory(id);
            category.setName(Name);
        } catch (NoEmptyValueException e) {
            e.printStackTrace();
            logger.error("Can't create category with empty parameters");
        }
        return category;
    }

    private List<CategoryEntity> createCategoryList(HttpServletRequest request) {

        OperationType operation = OperationType.valueOf(request.getParameter("operation").toUpperCase());
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDao = new CategoryEntityDao();
        List<CategoryEntity> categories = new ArrayList<>();

        switch (operation) {
            case READ_ONE:
                CategoryEntity category = categoryDao.findById(id);
                categories.add(category);
                break;
            case READ_ALL:
                categories = categoryDao.getAll();
                break;
        }
        return categories;
    }
}
