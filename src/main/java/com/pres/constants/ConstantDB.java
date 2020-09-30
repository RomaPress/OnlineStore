package com.pres.constants;

import com.pres.model.Order;
import com.pres.model.User;

public class ConstantDB {

    public static final String SQL_FIND_ALL_PRODUCT = "SELECT id, name, price, amount, description FROM product;";
    public static final String SQL_FIND_PRODUCT_BY_ID = "SELECT name, price, amount, description FROM product WHERE id = ?;";
    public static final String SQL_FIND_ROLE_BY_LOGIN_AND_PASSWORD = "SELECT r.name FROM role r, user u WHERE r.id = u.role_id AND login = ? AND password = ?;";
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT u.id, r.name , first_name, last_name, login, password, phone_number FROM user u, role r  WHERE login = ? AND u.role_id = r.id;";
    public static final String SQL_IF_USER_AUTHORIZATION = "SELECT count(*) as count FROM role r, user u WHERE  u.role_id = r.id AND login = ? AND password = ? GROUP BY u.id;";
    public static final String SQL_INSERT_USER = "INSERT INTO user (role_id, first_name, last_name, login, password, phone_number) " +
            "VALUES ((SELECT role.id FROM role WHERE name = '"+ User.Role.USER.value() +"'),?,?,?,?,? );";
    public static final String SQL_INSERT_ORDER = "INSERT INTO `order` (user_id, status_id) " +
            "VALUES (?,(SELECT s.id FROM status s WHERE name = '"+ Order.Status.REGISTERED.value() +"'));";
    public static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO order_product (order_id, product_id, amount) " +
            "VALUES (?,?,?)";

    // This table names product
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";

    // This table names user
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    // This table names order_product
    public static final String ORDER_ID = "order_id";
    public static final String PRODUCT_ID = "product_id";


    public static final String COUNT = "count";


    private ConstantDB() {
    }
}
