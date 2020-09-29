package com.shumei.pi.enums;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/2/26 4:58 下午
 */
public class Enumeration {

    public static final String IS_ADMIN = "admin";
    public static final String IS_EDITOR = "editor";


    public static final String SUCCESS = "SUCCESS";


    public static final String ARTICLE_CREATE_BY = "createBy";
    public static final String ARTICLE_TITLE = "title";
    public static final String USER_USERNAME = "userName";
    public static final String ORDER_BY_DESC = " DESC";
    public static final String ORDER_BY_ASC = " ASC";


    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_HOST = "mail.host";
    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";


    public static final String ROLES = "roles";
    public static final String NAME = "name";
    public static final String AVATAR = "avatar";
    public static final String INTRODUCTION = "introduction";
    public static final String TOKEN = "token";


    public static final String PATH_LOGIN = "/user/login";
    public static final String PATH_REGISTER = "/user/register";
    public static final String GET_CODE = "/message/getCode";


    public static final String EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
    public static final String PHONE = "^1[34578]\\d{9}$";
    public static final String CODE = "\\d{6}$";
}
