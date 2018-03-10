/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.johan.oh.resttest.handlers;

import javax.servlet.http.HttpServletRequest;
import se.johan.oh.dataaccess.DataAccessInterface;
import static se.johan.oh.resttest.SubjectServlet.QUIZ_ID;

/**
 *
 * @author johan
 */
public class QuestionHandler implements Handler {

    @Override
    public Object handle(HttpServletRequest request, DataAccessInterface dao) {
        Object requestedObject = null;
        String id = request.getParameter(QUIZ_ID);
        if (id == null) {
            return requestedObject;
        }
        return dao.readQuestions(Integer.parseInt(id));
    }
    
}
