package com.company.view;

/**
 * Created on 16.01.2020 22:40.
 *
 * @author Aleks Sidorenko (e-mail: alek.sidorenko@gmail.com).
 * @version Id$.
 * @since 0.1.
 */
public class View {

    public void print(String message) {
        System.out.print(message);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printMessageResult(String message, String txt) {
        System.out.println(message + " " + txt);
    }
}
