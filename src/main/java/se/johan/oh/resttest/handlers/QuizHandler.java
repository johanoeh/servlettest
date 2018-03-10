/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.johan.oh.resttest.handlers;

import javax.servlet.http.HttpServletRequest;
import se.johan.oh.dataaccess.DataAccessInterface;
import static se.johan.oh.resttest.SubjectServlet.SUBJECT_ID;

/**
 *
 * @author johan
 */
public class QuizHandler implements Handler {

    @Override
    public Object handle(HttpServletRequest request, DataAccessInterface dao) {
        Object requestedObject = null;
        String id = request.getParameter(SUBJECT_ID);
        if (id == null) {
            return requestedObject;
        }
        return dao.readAllChapters(Integer.parseInt(id));
    } 
}
