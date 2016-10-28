package com.tikape.keskustelupalsta.dao;

import java.sql.*;

public interface Collector<T> {

    T collect(ResultSet rs) throws SQLException;
}
