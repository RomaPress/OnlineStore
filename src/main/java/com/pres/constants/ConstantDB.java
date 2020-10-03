package com.pres.constants;

import com.pres.model.Order;
import com.pres.model.User;

public final class ConstantDB {
    //Product



    public static final String SQL_FIND_ALL_PRODUCT = "SELECT p.id as id, p.name as name, price, amount, description, t.name as type_name, t.id as type_id FROM product p, type t " +
            "WHERE type_id = t.id GROUP BY p.id";

    public static final String SQL_FIND_PRODUCT_BY_ID = "SELECT p.name as name, price, amount, description, t.name as type_name, t.id as type_id FROM product p, type t " +
            "WHERE  type_id = t.id AND p.id = ?;";



    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT u.id, r.name , first_name, last_name, login, password, phone_number FROM user u, role r  WHERE login = ? AND u.role_id = r.id;";

    public static final String SQL_IF_USER_AUTHORIZATION = "SELECT count(*) as count FROM role r, user u WHERE  u.role_id = r.id AND login = ? AND password = ? GROUP BY u.id;";

    public static final String SQL_INSERT_USER = "INSERT INTO user (role_id, first_name, last_name, login, password, phone_number) " +
            "VALUES ((SELECT role.id FROM role WHERE name = '"+ User.Role.USER.value() +"'),?,?,?,?,? );";

    public static final String SQL_INSERT_ORDER = "INSERT INTO `order` (user_id, status_id) " +
            "VALUES (?,(SELECT s.id FROM status s WHERE name = '"+ Order.Status.REGISTERED.value() +"'));";

    public static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO order_product (order_id, product_id, amount) " +
            "VALUES (?,?,?)";

    public static final String SQL_FIND_ALL_ORDER = "SELECT  o.id as id, s.name as status, o.date_time, " +
            "u.first_name, u.last_name, u.phone_number," +
            "p.name as product, op.amount,p.price, o.total " +
            "FROM order_product op " +
            "LEFT JOIN `order` o on o.id = op.order_id " +
            "LEFT JOIN status s on s.id = o.status_id " +
            "LEFT JOIN product p on p.id = op.product_id " +
            "LEFT JOIN user u on u.id = o.user_id ORDER BY o.id ";

    public static final String SQL_FIND_ORDER_BY_ID = "SELECT  o.id as id, s.name as status, o.date_time, " +
            "    u.first_name, u.last_name, u.phone_number,\n" +
            "    p.name as product, op.amount,p.price, o.total\n" +
            "    FROM order_product op\n" +
            "    LEFT JOIN `order` o on o.id = op.order_id\n" +
            "    LEFT JOIN status s on s.id = o.status_id\n" +
            "    LEFT JOIN product p on p.id = op.product_id\n" +
            "    LEFT JOIN user u on u.id = o.user_id\n" +
            "    WHERE o.id = ?;";

    // This table names product
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";

    // This table names type
    public static final String TYPE_ID = "type_id";
    public static final String TYPE_NAME = "type_name";


    // This table names user
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    // This table names order_product
    public static final String ORDER_ID = "order_id";
    public static final String PRODUCT_ID = "product_id";


    //This table names order_product
    public static final String DATE_TIME = "date_time";
    public static final String TOTAL = "total";
    public static final String STATUS = "status";
    public static final String PRODUCT = "product";

    public static final String COUNT = "count";

}
