/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.johan.oh.resttest.handlers;

import javax.servlet.http.HttpServletRequest;
import se.johan.oh.dataaccess.DataAccessInterface;

/**
 *
 * @author johan
 */
public interface Handler {
    public Object handle(HttpServletRequest request, DataAccessInterface dao);
}
