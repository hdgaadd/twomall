package com.codeman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hdgaadd on 2021/12/06/18:25
 */
public class MapperMultiQueryGenerator {

    // 项目包名
    static String Package = "com.codeman";

    static String day;

    static String time;

    static String template;

    public static void main(String[] args) {
        String TimeNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS").format(Calendar.getInstance().getTime());
        String[] str = TimeNow.split(" ");
        day = str[0];
        time = str[1].substring(0, 8);
        template ="/**\n" +
                " * @author hdgaadd\n" +
                " * Created on " + day +"\n" +  "*/\n";

        MapperMultiQueryGenerator auto = new MapperMultiQueryGenerator();
        //获取所有数据表
        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("please prefix your file name ");
        String fileName = sc.nextLine();
        list.add(fileName);
        for (String table : list) {
            createStart(tables(table));
        }

    }

    /**
     * 把输入字符串的首字母改成大写
     */
    private static String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 把输入字符串的首字母改成小写
     */
    private static String initlow(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    //首字母转换和下划线转换
    private static String tables(String table) {
        String[] tables = table.split("_");
        table = "";
        for (String s : tables) {
            table += initcap(s);
        }
        return table;
    }

    /**
     * 创建Dao
     */
    private static String createDao(String tableName) {
        String service = "package "+Package+".mapper;\n\n" +
                template +
                "public interface "+tableName+"Mapper {\n" +

                "}";
        return service;
    }

    /**
     * 创建Service
     */
    private static String createService(String tableName) {
        String service = "package "+Package+".service;\n" +
                "\n" +
                template +
                "public interface " + "I" + tableName + "Service {\n" +
                "}";
        return service;
    }

    /**
     * 创建ServiceImpl
     */
    private static String createServiceImpl(String tableName) {
        String serviceImpl = "package "+Package+".service.impl;\n" +
                "\n" +
                "import "+Package+".service." + "I" + tableName + "Service;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "\n" + template +
                "@Service\n" +
                "public class " + tableName + "ServiceImpl implements " + "I" + tableName + "Service {\n" +
                "}\n";
        return serviceImpl;
    }

    /**
     * 创建Controller
     */
    private static String createController(String tableName) {
        String controller ="package "+Package+".controller;\n\n" +"import io.swagger.annotations.Api;\n" +
                "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                "import org.springframework.web.bind.annotation.RestController;\n\n"+
                template +
                "@Api(tags = \"" + "\")\n" +
                "@RestController\n" +
                "@RequestMapping(\"/" + "\")\n" +
                "public class " + tableName + "Controller {\n" +
                "}\n";
        return controller;
    }

    /**
     * create XML
     */
    private static String createXml(String tableName) {
        String controller =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                        "<mapper namespace=\"com.codeman.mapper." + tableName +"Mapper\">\n" +
                        "</mapper>";
        return controller;
    }

    /**
     * 开始创建
     */
    static void createStart(String tableName) {
        //获取当前项目的路径
        String baseUrl = System.getProperty("user.dir");
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your modele name");
        String moduleName = sc.nextLine();
        String url = baseUrl +  "\\" + moduleName + "\\src\\main\\java\\com\\codeman\\";
        //创建Dao
        createFile(new File(url + "mapper\\" + tableName + "Mapper.java"), createDao(tableName));
        //创建Service
        createFile(new File(url + "service\\" + "I" + tableName + "Service.java"), createService(tableName));
        //创建ServiceImpl
        createFile(new File(url + "service\\impl\\" + tableName + "ServiceImpl.java"), createServiceImpl(tableName));
        //创建Controller
        createFile(new File(url + "controller\\" + tableName + "Controller.java"), createController(tableName));
        // 创建Mapper.xml
        createFile(new File(baseUrl + "\\" + moduleName + "\\src\\main\\resources\\" + "mapper\\" + tableName + "Mapper.xml"), createXml(tableName));
    }

    /**
     * @param file    创建的文件
     * @param context 文件里面的内容
     */
    static boolean createFile(File file, String context) {
        //获取文件
        File parent = file.getParentFile();
        //如果是目录
        if (parent != null) {
            //创建目录
            parent.mkdirs();
        }
        try {
            //创建文件
            file.createNewFile();
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
                fileWriter.write(context);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                return false;
            }
        } catch (IOException e) {
            System.out.println("创建文件失败:" + e.getMessage());
        }
        return true;
    }
    static class SqlHelper {
        private String url;
        private String username;
        private String password;

        private Connection connection;

        private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

        public SqlHelper(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<HashMap<String, Object>> Get(String sql) {
            List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
            Statement statement = null;
            try {
                statement = getStatement();
                ResultSet set = statement.executeQuery(sql);
                ResultSetMetaData meta = set.getMetaData();
                int columnCount = meta.getColumnCount();
                System.out.println(columnCount);

                while (set.next()) {
                    HashMap<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String column = meta.getColumnName(i);
                        row.put(column, set.getObject(column));
                    }
                    result.add(row);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeStatement(statement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @SuppressWarnings("unchecked")
        public <T> List<T> Get(String sql, String columnName) {
            List<T> result = new ArrayList<T>();
            Statement statement = null;
            try {
                statement = getStatement();
                ResultSet set = statement.executeQuery(sql);
                while (set.next()) {
                    result.add((T) set.getObject(columnName));
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeStatement(statement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        public Statement getStatement() throws ClassNotFoundException, SQLException{
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(url, username, password);
            Statement statement = con.createStatement();
            return statement;
        }

        public Connection getConnection() throws ClassNotFoundException, SQLException {
            if (connection == null) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(url, username, password);
            }

            return connection;
        }

        public void closeConnection(Connection conn) throws ClassNotFoundException, SQLException {
            if (connection != null) {
                connection.close();
            }

            if (conn != null) {
                conn.close();
            }

            System.out.println("-----------Connection closed now-----------");
        }

        public void closeStatement(Statement statement) throws SQLException {
            if (statement != null) {
                Connection con = statement.getConnection();
                statement.close();
                if (con != null) {
                    con.close();
                }
            }
        }
    }
}