package com.company;

import com.company.controller.Services;

import java.sql.SQLException;

/**
 * Created on 16.01.2020 22:15.
 *
 * @author Aleks Sidorenko (e-mail: alek.sidorenko@gmail.com).
 * @version Id$.
 * @since 0.1.
 */
public class App {

    public static void main(String[] args) throws SQLException {
        new Services().initApp();
    }
}
