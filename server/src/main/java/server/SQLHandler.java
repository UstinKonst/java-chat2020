package server;

import java.sql.*;

public class SQLHandler {
    private static Connection connection;
    private static PreparedStatement psGetNickName;

    public static boolean connect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            prepareAllStatement();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void prepareAllStatement() throws SQLException {
        psGetNickName = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");

    }

    public static String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            psGetNickName.setString(1, login);
            psGetNickName.setString(2, password);
            ResultSet rs = psGetNickName.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }


}

