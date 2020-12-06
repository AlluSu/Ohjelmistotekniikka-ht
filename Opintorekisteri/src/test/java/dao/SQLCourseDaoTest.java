/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import opintorekisteri.dao.SqlCourseDao;
import org.junit.Before;

/**
 * Testiluokka kurssien SQL-operaatioille.
 * @author Aleksi Suuronen
 */
public class SQLCourseDaoTest {
    
    private Connection connection;
    private String db = "jdbc:sqlite::memory:"; 
    SqlCourseDao courseDao;

}
