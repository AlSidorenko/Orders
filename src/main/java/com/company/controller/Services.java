package com.company.controller;

import com.company.model.DbProperties;
import com.company.model.MenuTracker;
import com.company.view.TextConstants;
import com.company.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created on 16.01.2020 22:16.
 *
 * @author Aleks Sidorenko (e-mail: alek.sidorenko@gmail.com).
 * @version Id$.
 * @since 0.1.
 */
public class Services {

    private MenuTracker menuTracker = new MenuTracker();
    private DbProperties props = new DbProperties();
    private View view = new View();
    private Properties prop;
    private PreparedStatement ps;

    private Connection conn;

    public void initApp() throws SQLException {
        Scanner sc = new Scanner(System.in);

        InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");

        prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        // create connection
        try {
            conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
            do {
                menuTracker.showMenu();
                String s = sc.nextLine();

                switch (s) {
                    case "1":
                        addOrder(sc);
                        break;
                    case "2":
                        deleteOrder(sc);
                        break;
                    case "3":
                        changeOrder(sc);
                        break;
                    case "4":
                        viewOrders();
                        break;
                    default:
                        return;
                }
            } while (true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }

    public void addOrder(Scanner sc) throws SQLException {
        view.print(TextConstants.CUSTOMER_NAME);
        String nameCustom = sc.nextLine();
        view.print(TextConstants.NUMBER_ORDER);
        String sOrderNumb = sc.nextLine();
        int orderNumb = Integer.parseInt(sOrderNumb);
        view.print(TextConstants.PRODUCT_NAME);
        String nameProduct = sc.nextLine();

        ps = conn.prepareStatement(prop.getProperty("db.insert_customers"));
        try {
            ps.setString(1, nameCustom);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(prop.getProperty("db.insert_orders"));
        try {
            ps.setInt(1, orderNumb);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(prop.getProperty("db.insert_products"));
        try {
            ps.setString(1, nameProduct);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    public void deleteOrder(Scanner sc) throws SQLException {
        view.print(TextConstants.ID);
        String sId = sc.nextLine();
        int id = Integer.parseInt(sId);

        ps = conn.prepareStatement(prop.getProperty("db.delete_customers"));
        try {
            ps.setInt(1, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(prop.getProperty("db.delete_orders"));
        try {
            ps.setInt(1, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(prop.getProperty("db.delete_products"));
        try {
            ps.setInt(1, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    public void changeOrder(Scanner sc) throws SQLException {
        view.print(TextConstants.ID);
        String sId = sc.nextLine();
        int id = Integer.parseInt(sId);
        view.print(TextConstants.CUSTOMER_NAME);
        String nameCustom = sc.nextLine();
        view.print(TextConstants.NUMBER_ORDER);
        String sOrderNumber = sc.nextLine();
        int order_number = Integer.parseInt(sOrderNumber);
        view.print(TextConstants.PRODUCT_NAME);
        String nameProduct = sc.nextLine();

        ps = conn.prepareStatement(prop.getProperty("db.update_customers"));
        try {
            ps.setString(1, nameCustom);
            ps.setInt(2, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(prop.getProperty("db.update_orders"));
        try {
            ps.setInt(1, order_number);
            ps.setInt(2, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }

        ps = conn.prepareStatement(prop.getProperty("db.update_products"));
        try {
            ps.setString(1, nameProduct);
            ps.setInt(2, id);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    public void viewOrders() {
        List<String> res = new ArrayList<>();
        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(prop.getProperty("db.select_customers"))) {
                    cycleForOutput(res, rs);
                }
            }
            ;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(prop.getProperty("db.select_orders"))) {
                    cycleForOutput(res, rs);
                }
            }
            ;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(prop.getProperty("db.select_products"))) {
                    cycleForOutput(res, rs);
                }
            }
            System.out.println(res.toString());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void cycleForOutput(List<String> res, ResultSet rs) throws SQLException {
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            sb.append(rs.getInt(1));
            sb.append(rs.getString(2));
            res.add(String.valueOf(sb));
        }
    }
}
