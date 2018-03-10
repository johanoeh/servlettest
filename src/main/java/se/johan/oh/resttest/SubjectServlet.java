/*
 * Toy servlet used as backend for a
 */
package se.johan.oh.resttest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.johan.oh.connection.ConnectionHandler;
import se.johan.oh.containers.Subject;
import se.johan.oh.dataaccess.DataAccessFacade;
import se.johan.oh.dataaccess.DataAccessInterface;
import se.johan.oh.resttest.handlers.ChapterHandler;
import se.johan.oh.resttest.handlers.Handler;
import se.johan.oh.resttest.handlers.QuestionHandler;
import se.johan.oh.resttest.handlers.QuizHandler;

/**
 * @author johan
 */
@WebServlet(name = "SubjectServlet", urlPatterns = {"/SubjectServlet", "/subject", "/chapter", "/quiz"})
public class SubjectServlet extends HttpServlet {

    private DataAccessInterface dao;
    private static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/admin;create=true;user=admin;password=1234";
    public static final String TYPE = "type";
    public static final String JSON = "json";
    public static final String XML = "xml";
    public static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    public static final String SUBJECT = "subject";
    public static final String CHAPTER = "chapter";
    public static final String QUIZ = "quiz";
    public static final String SUBJECT_ID = "subjectID";
    public static final String QUIZ_ID = "quizID";
    public static final String QUESTION = "question";

    @Override
    public void init() {
        dao = new DataAccessFacade(new ConnectionHandler(CONNECTION_STRING));
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SubjectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubjectServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter(TYPE);
        
        if (type == null) {
            return;
        }
        response.setContentType(CONTENT_TYPE);
        
        String resource = getResourceName(request.getRequestURI());
        
        Object requestedObject = null;

        switch (resource) {
            case SUBJECT:
                requestedObject = dao.readAllSubjects();
                break;
            case CHAPTER:
                Handler chapterHandler = new ChapterHandler();
                requestedObject = chapterHandler.handle(request, dao);
                break;
            case QUIZ:
                Handler quizHandler = new QuizHandler();
                requestedObject = quizHandler.handle(request, dao);
                break;
            case QUESTION:
                Handler questionHandler = new QuestionHandler();
                requestedObject = questionHandler.handle(request, dao);
                break;
            default:
                return;
        }

      

        if (requestedObject != null) {
            switch (type) {
                case JSON:
                    displaySubjectJSON(response.getWriter(), requestedObject);
                    break;
                case XML:
                    displaySubjectXML(response.getWriter());
                    break;
                default:
                    processRequest(request, response);
                    break;
            }
        }
    }

    private String getResourceName(String URI) {
        String[] split = URI.split("/");
        String resource = split[split.length - 1];
        return resource;
    }

    private void displaySubjectJSON(PrintWriter out, Object object) throws JsonProcessingException {
        if (object != null) {
            ObjectMapper mapper = new ObjectMapper();
            out.print(mapper.writeValueAsString(object));
        }
    }

    private void displaySubjectXML(PrintWriter out) {
        List<Subject> subjects = dao.readAllSubjects();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {

    }

}
