package com.ffzx.cas.support;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2016/8/13.
 */
@Component
public class DatabaseManagerImpl implements DatabaseManager {


    @Resource
    private DataSource dataSource;

    @Override
    public List<Map> excute(String sql) {
        Connection connection = null;
        List<Map> datas = new ArrayList<>();
        Map<String, Object> row = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            Map<Integer, String> header = new HashMap<>();
            for (int i = 1; i <= columns; i++) {
                header.put(i, metaData.getColumnName(i));
            }
            while (resultSet.next()) {
                row = new HashMap<>();
                datas.add(row);
                for (int i = 1; i <= columns; i++) {
                    row.put(header.get(i), resultSet.getObject(i));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return datas;
    }

    @Override
    public int count(String sql) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
        return 0;
    }

    /**
     * @param connection
     */
    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                ;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
