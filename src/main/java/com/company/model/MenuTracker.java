package com.company.model;

import com.company.view.TextConstants;
import com.company.view.View;

/**
 * Created on 17.01.2020 11:09.
 *
 * @author Aleks Sidorenko (e-mail: alek.sidorenko@gmail.com).
 * @version Id$.
 * @since 0.1.
 */
public class MenuTracker {

    private View view = new View();

    public void showMenu() {
        view.printMessage(TextConstants.ADD_ORDER);
        view.printMessage(TextConstants.DELETE_ORDER);
        view.printMessage(TextConstants.CHANGE_ORDER);
        view.printMessage(TextConstants.VIEW_ORDER);
        view.printMessage(TextConstants.EXIT);
        view.print(TextConstants.CHOICE);
    }
}
