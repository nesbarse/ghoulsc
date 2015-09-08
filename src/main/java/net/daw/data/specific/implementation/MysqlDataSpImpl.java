/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.data.specific.implementation;

import net.daw.data.publicinterface.DataInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.SqlBuilder;

public class MysqlDataSpImpl implements DataInterface {

    Connection connection = null;

    public MysqlDataSpImpl(Connection pooledConnection) {
        connection = pooledConnection;
    }

    @Override
    public int insertOne(String strTabla) throws Exception {

        ResultSet oResultSet = null;
        java.sql.PreparedStatement oPreparedStatement = null;
        int id = 0;
        try {
            String strSQL = "INSERT INTO " + strTabla + " (id) VALUES (null) ";
            oPreparedStatement = connection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            int returnLastInsertId = oPreparedStatement.executeUpdate();
            if (returnLastInsertId != -1) {
                oResultSet = oPreparedStatement.getGeneratedKeys();
                oResultSet.next();
                id = oResultSet.getInt(1);
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":insertOne ERROR inserting register"));
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":insertOne ERROR inserting register: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return id;
    }

    @Override
    public int setNull(int intId, String strTabla, String strCampo) throws Exception {
        PreparedStatement oPreparedStatement = null;
        int intResult = 0;
        try {
            String strSQL = "UPDATE " + strTabla + " SET " + strCampo + " = null WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":setNull ERROR updating register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public int updateOne(int intId, String strTabla, String strCampo, String strValor) throws Exception {
        int intResult = 0;
        PreparedStatement oPreparedStatement = null;
        try {
            String strSQL = "UPDATE " + strTabla + " SET " + strCampo + " = '" + strValor + "' WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":updateOne ERROR updating register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public String getId(String strTabla, String strCampo, String strValor) throws Exception {
        String strResult = null;
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT id FROM " + strTabla + " WHERE " + strCampo + "='" + strValor + "'";
            oResultSet = oStatement.executeQuery(strSQL);
            if (oResultSet.next()) {
                strResult = oResultSet.getString("id");
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getId ERROR: ID not exists"));
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getId ERROR: Can't process query: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return strResult;
    }

    public String getIdByTwoValues(String strTabla, String strCampo1, String strValor1, String strCampo2, String strValor2) throws Exception {
        String strResult = null;
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT id FROM " + strTabla + " WHERE " + strCampo1 + "='" + strValor1 + "' AND " + strCampo2 + "='" + strValor2 + "'";
            oResultSet = oStatement.executeQuery(strSQL);
            if (oResultSet.next()) {
                strResult = oResultSet.getString("id");
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getId ERROR: ID not exists"));
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getId ERROR: Can't process query: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return strResult;
    }

//    @Override
//    public String getOne(String strTabla, String strCampo, int id) throws Exception {
//        String strResult = null;
//        PreparedStatement oPreparedStatement = null;
//        ResultSet oResultSet = null;
//        String strSQL = "";
//
//        if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//            try {
//                strSQL = strTabla;
//                strSQL += " AND id=" + id;
//                oPreparedStatement = connection.prepareStatement(strSQL);
//                //oPreparedStatement.setInt(1, id);
//                oResultSet = oPreparedStatement.executeQuery();
//                if (oResultSet.next()) {
//                    strResult = oResultSet.getString(strCampo);
//                } else {
//                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: ID not exists: " + id));
//                }
//            } catch (Exception ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oResultSet != null) {
//                    oResultSet.close();
//                }
//                if (oPreparedStatement != null) {
//                    oPreparedStatement.close();
//                }
//            }
//        } else {
//            try {
//                //pte cambiar
//                strSQL = "SELECT " + strCampo + " FROM " + strTabla + " WHERE id=?";
//                oPreparedStatement = connection.prepareStatement(strSQL);
//                oPreparedStatement.setInt(1, id);
//                oResultSet = oPreparedStatement.executeQuery();
//                if (oResultSet.next()) {
//                    strResult = oResultSet.getString(strCampo);
//                } else {
//                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: ID not exists: " + id));
//                }
//            } catch (Exception ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oPreparedStatement != null) {
//                    oPreparedStatement.close();
//                }
//            }
//
//        }
//        return strResult;
//    }
//    @Override
//    public Boolean existsOne(String strTabla, int id) throws Exception {
//        Boolean strOrigenTabla = true;
//        if (strTabla.length() >= 6) {
//            if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//                strOrigenTabla = false;
//            }
//        }
//        ResultSet oResultSet = null;
//        if (strOrigenTabla) {
//            int result = 0;
//            Statement oStatement = null;
//            try {
//                oStatement = (Statement) connection.createStatement();
//                String strSQL = "SELECT COUNT(*) FROM " + strTabla + " WHERE 1=1";
//                oResultSet = oStatement.executeQuery(strSQL);
//                if (oResultSet.next()) {
//                    result = oResultSet.getInt("COUNT(*)");
//                } else {
//                    return false;
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":existsOne ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oResultSet != null) {
//                    oResultSet.close();
//                }
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//
//            }
//            return (result > 0);
//        } else {
//            int intResult = 0;
//            Statement oStatement = null;
//            try {
//                oStatement = (Statement) connection.createStatement();
//                String strSQL = "SELECT COUNT(*) " + strTabla.substring(strTabla.indexOf("FROM"), strTabla.length());
//                oResultSet = oStatement.executeQuery(strSQL);
//                while (oResultSet.next()) {
//                    intResult = oResultSet.getInt("COUNT(*)");
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCountSQL ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oResultSet != null) {
//                    oResultSet.close();
//                }
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return intResult > 0;
//        }
//    }
    @Override
    public void removeSomeId(String strTabla, ArrayList<Integer> Ids) throws SQLException {
        Statement oStatement = null;
        try {
            Iterator<Integer> iterador = Ids.listIterator();
            while (iterador.hasNext()) {
                oStatement = (Statement) connection.createStatement();
                String strSQL = "DELETE FROM " + strTabla + " WHERE id = " + iterador.next();
                oStatement.executeUpdate(strSQL);
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":removeSomeId ERROR: En la consulta: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
    }

    @Override
    public void removeSomeCondition(String strTabla, String campo, String valor) throws Exception {
        Statement oStatement = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "DELETE FROM " + strTabla + " WHERE " + campo + " like " + valor;
            oStatement.executeUpdate(strSQL);
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":deleteOne ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oStatement != null) {
                oStatement.close();
            }
        }
    }

//    @Override
//    public ArrayList<String> getColumnsName(String strTabla) throws Exception {
//        Boolean strOrigenTabla = true;
//        if (strTabla.length() >= 6) {
//            if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//                strOrigenTabla = false;
//            }
//        }
//        ArrayList<String> vector = null;
//        Statement oStatement = null;
//        if (strOrigenTabla) {
//            try {
//                vector = new ArrayList<>();
//                oStatement = connection.createStatement();
//                String strSQL = "SHOW FULL COLUMNS FROM " + strTabla;
//                ResultSet oResultSet = oStatement.executeQuery(strSQL);
//                while (oResultSet.next()) {
//                    if (oResultSet.getString("Field").length() >= 4) {
//                        if (oResultSet.getString("Field").substring(0, 3).equalsIgnoreCase("id_")) {
//                            vector.add("obj_" + oResultSet.getString("Field").substring(3));
//                        } else {
//                            vector.add(oResultSet.getString("Field"));
//                        }
//                    } else {
//                        vector.add(oResultSet.getString("Field"));
//                    }
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getColumnsName ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//
//            }
//            return vector;
//        } else {
//            vector = new ArrayList<>();
//            String strResult = null;
//            PreparedStatement oPreparedStatement = null;
//            ResultSet oResultSet;
//            try {
//                oPreparedStatement = connection.prepareStatement(strTabla);
//                oResultSet = oPreparedStatement.executeQuery();
//                //oResultSet = oStatement.executeQuery(strTabla);
//                ResultSetMetaData rsmd = oResultSet.getMetaData();
//                int numberOfColumns = rsmd.getColumnCount();
//                for (int contador = 1; contador <= numberOfColumns; contador++) {
//                    if (rsmd.getColumnName(contador).length() >= 5) { //los nombres de las tablas como minimo han de tener dos caracteres + el id_ o el set = 5 caracteres
//                        if (rsmd.getColumnName(contador).substring(0, 3).equalsIgnoreCase("id_")) {
//                            vector.add("obj_" + rsmd.getColumnName(contador).substring(3));
//                        } else {
//                            vector.add(rsmd.getColumnName(contador));
//                        }
//
//                    } else {
//                        vector.add(rsmd.getColumnName(contador));
//                    }
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getColumnsName ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//
//            }
//            return vector;
//
//        }
//    }
//
//    @Override
//    public ArrayList<String> getPrettyColumns(String strTabla) throws Exception {
//        Boolean strOrigenTabla = true;
//        if (strTabla.length() >= 6) {
//            if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//                strOrigenTabla = false;
//            }
//        }
//        ArrayList<String> vector = null;
//        Statement oStatement = null;
//        if (strOrigenTabla) {
//            try {
//                vector = new ArrayList<>();
//                oStatement = connection.createStatement();
//                //String strSQL = "SELECT id FROM " + strTabla + " WHERE 1=1 ";
//                String strSQL = "SHOW FULL COLUMNS FROM " + strTabla;
//                ResultSet oResultSet = oStatement.executeQuery(strSQL);
//                while (oResultSet.next()) {
//                    vector.add(oResultSet.getString("Comment")); //COLUMNS.Comment COLUMN_COMMENT
////                if (desc) {
////                    if (oResultSet.getString("COLUMN_NAME").length() >= 4) {
////                        if (oResultSet.getString("COLUMN_NAME").substring(0, 3).equalsIgnoreCase("id_")) {
////                            vector.add("desc_" + oResultSet.getString("COLUMN_NAME").substring(3));
////                        }
////                    }
////                }
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPrettyColumns ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//
//            }
//            return vector;
//        } else {
//            vector = new ArrayList<>();
//            String strResult = null;
//            PreparedStatement oPreparedStatement = null;
//            ResultSet oResultSet;
//            try {
//
//                oPreparedStatement = connection.prepareStatement(strTabla);
//                oResultSet = oPreparedStatement.executeQuery();
//                //oResultSet = oStatement.executeQuery(strTabla);
//                ResultSetMetaData rsmd = oResultSet.getMetaData();
//                int numberOfColumns = rsmd.getColumnCount();
//                for (int contador = 1; contador <= numberOfColumns; contador++) {
//                    if (rsmd.getColumnName(contador).length() >= 5) { //los nombres de las tablas como minimo han de tener dos caracteres + el id_ o el set = 5 caracteres
//                        if (rsmd.getColumnName(contador).substring(0, 3).equalsIgnoreCase("id_")) {
//                            vector.add(rsmd.getColumnName(contador).substring(3));
//                        } else {
//                            vector.add(rsmd.getColumnName(contador));
//                        }
//
//                    } else {
//                        vector.add(rsmd.getColumnName(contador));
//                    }
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getColumnsName ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//
//            }
//            return vector;
//        }
//    }
//    @Override
//    public ArrayList<String> getColumnsName(String strTabla) throws Exception {
//        Connection conexion = ConnectionSource.getDataSource().getConnection();
//        Statement oStatement = null;
//        try {
//            ArrayList<String> vector = new ArrayList<>();
//
//           
//            oStatement = conexion.createStatement();
//            //String strSQL = "SELECT id FROM " + strTabla + " WHERE 1=1 ";
//            String strSQL = "SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` ";
//            strSQL += "WHERE `TABLE_SCHEMA`='" + ConnectionClass.getDatabaseName() + "' ";
//            strSQL += "AND `TABLE_NAME`='" + strTabla + "' ";
//            ResultSet oResultSet = oStatement.executeQuery(strSQL);
//            while (oResultSet.next()) {
//                if (oResultSet.getString("COLUMN_NAME").length() >= 4) {
//                    if (oResultSet.getString("COLUMN_NAME").substring(0, 3).equalsIgnoreCase("id_")) {
//                        vector.add("obj_" + oResultSet.getString("COLUMN_NAME").substring(3));
//                    } else {
//                        vector.add(oResultSet.getString("COLUMN_NAME"));
//                    }
//                } else {
//                    vector.add(oResultSet.getString("COLUMN_NAME"));
//                }
//            }
//            return vector;
//        } catch (SQLException e) {
//            throw new Exception("mysql.getColumnsName: Error en la consulta: " + e.getMessage());
//        } finally {
//            oStatement.close();
//            conexion.close();
//        }
//    }
//    public ArrayList<Integer> getPageFromSQL(String strSql) throws Exception {
////pte:currando..
//        Statement oStatement = null;
//        try {
//            ArrayList<Integer> vector = new ArrayList<>();
//            int intOffset;
//
//            oStatement = (Statement) connection.createStatement();
//
//            ResultSet oResultSet = oStatement.executeQuery(strSql);
//
//            ResultSetMetaData metaData = resultSet.getMetaData();
//
//            int count = metaData.getColumnCount();
//            for (int i = 1; i <= count; i++) {
//                if (metaData.getColumnName(i).equals(desiredColumnName)) {
//                    // Whatever you want to do here.
//                }
//            }
//
//            while (oResultSet.next()) {
//                vector.add(oResultSet.getInt("id"));
//            }
//            return vector;
//        } catch (SQLException e) {
//            throw new Exception("mysql.getPageFromSQL: Error en la consulta: " + e.getMessage());
//        } finally {
//            if (oStatement != null) {
//                oStatement.close();
//            }
//
//        }
//    }    
    //    @Override
//    public String getOneFromSql(String strSQL) throws Exception {
//        Statement oStatement = null;
//        ResultSet oResultSet;
//        try {
//            oResultSet = oStatement.executeQuery(strSQL);
//            if (oResultSet.next()) {
//                return oResultSet.getString("strvalue");
//            } else {
//                return null;
//            }
//        } catch (SQLException ex) {
//            throw new Exception("mysql.getOne: No se ha podido realizar la consulta: " + ex.getMessage());
//        } finally {
//            if (oStatement != null) {
//                oStatement.close();
//            }
//
//        }
//    }
//    @Override
//    public ArrayList<Integer> getPage(String strTabla, int intRegsPerPage, int intPagina, ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder) throws Exception {
//        Boolean strOrigenTabla = true;
//        if (strTabla.length() >= 6) {
//            if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//                strOrigenTabla = false;
//            }
//        }
//        if (strOrigenTabla) {
//
//            ArrayList<Integer> vector = null;
//            Statement oStatement = null;
//            try {
//                vector = new ArrayList<>();
//                int intOffset;
//                oStatement = (Statement) connection.createStatement();
//                String strSQL = "SELECT id FROM " + strTabla + " WHERE 1=1 ";
//                String strSQLcount = "SELECT COUNT(*) FROM " + strTabla + " WHERE 1=1 ";
//                if (alFilter != null) {
//                    String strSQLFilter = "";
//                    Iterator iterator = alFilter.iterator();
//                    while (iterator.hasNext()) {
//                        FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
//                        switch (oFilterBean.getFilterOperator()) {
//                            case "like":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'";
//                                break;
//                            case "notlike":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'";
//                                break;
//                            case "equals":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'";
//                                break;
//                            case "notequalto":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'";
//                                break;
//                            case "less":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "lessorequal":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "greater":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "greaterorequal":
//                                strSQLFilter += " AND " + oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue() + "";
//                                break;
//                        }
//                    }
//                    strSQL += strSQLFilter;
//                    strSQLcount += strSQLFilter;
//                }
//                //when limit of pages exceed, show last page
//                ResultSet oResultSet = oStatement.executeQuery(strSQLcount);
//                int intCuenta = 0;
//                if (oResultSet.next()) {
//                    intCuenta = oResultSet.getInt("COUNT(*)");
//                }
//                int maxPaginas = new Double(intCuenta / intRegsPerPage).intValue();
//                intPagina = Math.min(intPagina - 1, maxPaginas) + 1;
//                intOffset = Math.max(((intPagina - 1) * intRegsPerPage), 0);
//                //--                        
//                if (hmOrder != null) {
//                    strSQL += " ORDER BY";
//                    for (Map.Entry oPar : hmOrder.entrySet()) {
//                        strSQL += " " + oPar.getKey() + " " + oPar.getValue() + ",";
//                    }
//                    strSQL = strSQL.substring(0, strSQL.length() - 1);
//                }
//                strSQL += " LIMIT " + intOffset + " , " + intRegsPerPage;
//                oResultSet = oStatement.executeQuery(strSQL);
//                while (oResultSet.next()) {
//                    vector.add(oResultSet.getInt("id"));
//                }
//
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPage ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return vector;
//        } else {
//            ArrayList<Integer> vector = null;
//            Statement oStatement = null;
//            try {
//                vector = new ArrayList<>();
//                int intOffset;
//                oStatement = (Statement) connection.createStatement();
//                String strSQLcount = "SELECT COUNT(*) " + strTabla.substring(strTabla.indexOf("FROM"), strTabla.length());
//                //when limit of pages exceed, show last page
//                ResultSet oResultSet = oStatement.executeQuery(strSQLcount);
//                int intCuenta = 0;
//                if (oResultSet.next()) {
//                    intCuenta = oResultSet.getInt("COUNT(*)");
//                }
//                int maxPaginas = new Double(intCuenta / intRegsPerPage).intValue();
//                intPagina = Math.min(intPagina - 1, maxPaginas) + 1;
//                intOffset = Math.max(((intPagina - 1) * intRegsPerPage), 0);
//                //--                        
//                strTabla += " LIMIT " + intOffset + " , " + intRegsPerPage;
//                oResultSet = oStatement.executeQuery(strTabla);
//                while (oResultSet.next()) {
//                    vector.add(oResultSet.getInt("id"));
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPageSQL ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return vector;
//        }
//    }
//    @Override
//    public int getPages(String strTabla, int intRegsPerPage, ArrayList<FilterBeanHelper> alFilter) throws Exception {
//        Boolean strOrigenTabla = true;
//        if (strTabla.length() >= 6) {
//            if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//                strOrigenTabla = false;
//            }
//        }
//        if (strOrigenTabla) {
//            int intResult = 0;
//            Statement oStatement = null;
//            try {
//                oStatement = (Statement) connection.createStatement();
//                String strSQL = "SELECT count(*) FROM " + strTabla + " WHERE 1=1";
//                if (alFilter != null) {
//                    Iterator iterator = alFilter.iterator();
//                    while (iterator.hasNext()) {
//                        FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
//                        switch (oFilterBean.getFilterOperator()) {
//                            case "like":
//                                strSQL += " AND " + oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'";
//                                break;
//                            case "notlike":
//                                strSQL += " AND " + oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'";
//                                break;
//                            case "equals":
//                                strSQL += " AND " + oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'";
//                                break;
//                            case "notequalto":
//                                strSQL += " AND " + oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'";
//                                break;
//                            case "less":
//                                strSQL += " AND " + oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "lessorequal":
//                                strSQL += " AND " + oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "greater":
//                                strSQL += " AND " + oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "greaterorequal":
//                                strSQL += " AND " + oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue() + "";
//                                break;
//                        }
//
//                    }
//                }
//                ResultSet oResultSet = oStatement.executeQuery(strSQL);
//                while (oResultSet.next()) {
//                    intResult = oResultSet.getInt("COUNT(*)") / intRegsPerPage;
//                    if ((oResultSet.getInt("COUNT(*)") % intRegsPerPage) > 0) {
//                        intResult++;
//                    }
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPages ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return intResult;
//        } else {
//
//            int intResult = 0;
//            Statement oStatement = null;
//            try {
//                oStatement = (Statement) connection.createStatement();
//                strTabla = "SELECT COUNT(*) " + strTabla.substring(strTabla.indexOf("FROM"), strTabla.length());
//
//                ResultSet oResultSet = oStatement.executeQuery(strTabla);
//                while (oResultSet.next()) {
//                    intResult = oResultSet.getInt("COUNT(*)") / intRegsPerPage;
//                    if ((oResultSet.getInt("COUNT(*)") % intRegsPerPage) > 0) {
//                        intResult++;
//                    }
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPagesSQL ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return intResult;
//        }
//    }
//
//  
//    @Override
//    public int getCount(String strTabla, ArrayList<FilterBeanHelper> alFilter) throws Exception {
//        Boolean strOrigenTabla = true;
//        if (strTabla.length() >= 6) {
//            if (strTabla.substring(0, 6).equalsIgnoreCase("SELECT")) {
//                strOrigenTabla = false;
//            }
//        }
//        if (strOrigenTabla) {
//            int intResult = 0;
//            Statement oStatement = null;
//            try {
//                oStatement = (Statement) connection.createStatement();
//                String strSQL = "SELECT count(*) FROM " + strTabla + " WHERE 1=1";
//                if (alFilter != null) {
//                    Iterator iterator = alFilter.iterator();
//                    while (iterator.hasNext()) {
//                        FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
//                        switch (oFilterBean.getFilterOperator()) {
//                            case "like":
//                                strSQL += " AND " + oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'";
//                                break;
//                            case "notlike":
//                                strSQL += " AND " + oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'";
//                                break;
//                            case "equals":
//                                strSQL += " AND " + oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'";
//                                break;
//                            case "notequalto":
//                                strSQL += " AND " + oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'";
//                                break;
//                            case "less":
//                                strSQL += " AND " + oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "lessorequal":
//                                strSQL += " AND " + oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "greater":
//                                strSQL += " AND " + oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue() + "";
//                                break;
//                            case "greaterorequal":
//                                strSQL += " AND " + oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue() + "";
//                                break;
//                        }
//                    }
//                }
//                ResultSet oResultSet = oStatement.executeQuery(strSQL);
//                while (oResultSet.next()) {
//                    intResult = oResultSet.getInt("COUNT(*)");
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCount ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return intResult;
//        } else {
//            int intResult = 0;
//            Statement oStatement = null;
//            try {
//                oStatement = (Statement) connection.createStatement();
//                strTabla = "SELECT COUNT(*) " + strTabla.substring(strTabla.indexOf("FROM"), strTabla.length());
//                ResultSet oResultSet = oStatement.executeQuery(strTabla);
//                while (oResultSet.next()) {
//                    intResult = oResultSet.getInt("COUNT(*)");
//                }
//            } catch (SQLException ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCountSQL ERROR:  Can't process query: " + ex.getMessage()));
//            } finally {
//                if (oStatement != null) {
//                    oStatement.close();
//                }
//            }
//            return intResult;
//        }
//    }
    @Override
    public int getCount(String strSqlSelectDataOrigin) throws Exception {
        int intResult = 0;
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strNewSqlDataSource = "SELECT COUNT(*) " + strSqlSelectDataOrigin.substring(strSqlSelectDataOrigin.indexOf("from"), strSqlSelectDataOrigin.length());
            oStatement = (Statement) connection.createStatement();
            oResultSet = oStatement.executeQuery(strNewSqlDataSource);
            while (oResultSet.next()) {
                intResult = oResultSet.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCountSQL ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return intResult;
    }

    public int getPages(String strSqlSelectDataOrigin, int intRegsPerPage) throws Exception {
        int intResult = 0;
        int intCount = 0;
        Statement oStatement = null;
        try {
            intCount = Math.max(this.getCount(strSqlSelectDataOrigin), 1);
            intResult = (intCount - 1) / intRegsPerPage;
            intResult++;
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPagesSQL ERROR:  Can't process query: " + ex.getMessage()));
        } finally {

            if (oStatement != null) {
                oStatement.close();
            }
        }
        return intResult;
    }

    public ArrayList<Integer> getAllId(String strSqlSelectDataOrigin) throws Exception {
        ArrayList<Integer> vector = null;
        Statement oStatement = null;
        ResultSet oResultSet = null;
        vector = new ArrayList<>();
        try {
            oStatement = (Statement) connection.createStatement();
            oResultSet = oStatement.executeQuery(strSqlSelectDataOrigin);
            while (oResultSet.next()) {
                vector.add(oResultSet.getInt("id"));
            }
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAllSQL ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return vector;
    }

    @Override
    public ResultSet getAllSql(String strSqlSelectDataOrigin) throws Exception {
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            oStatement = (Statement) connection.createStatement();
            oResultSet = oStatement.executeQuery(strSqlSelectDataOrigin);
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAllSQL ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
        }
        return oResultSet;
    }

    @Override
    public ResultSet getPage(String strSqlSelectDataOrigin, int intRegsPerPage, int intPagina) throws Exception {
        Statement oStatement = null;
        ResultSet oResultSet = null;
        try {
            int intCount = this.getCount(strSqlSelectDataOrigin);
            strSqlSelectDataOrigin += SqlBuilder.buildSqlLimit(intCount, intRegsPerPage, intPagina);
            oStatement = (Statement) connection.createStatement();
            oResultSet = oStatement.executeQuery(strSqlSelectDataOrigin);
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAllSQL ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
        }
        return oResultSet;
    }

//    @Override
//    public ArrayList<Integer> getPage(String strSqlSelectDataOrigin, int intRegsPerPage, int intPagina) throws Exception {
//        ArrayList<Integer> vector = null;
//        Statement oStatement = null;
//        ResultSet oResultSet = null;
//        vector = new ArrayList<>();
//        try {
//            int intCount = this.getCount(strSqlSelectDataOrigin);
//            strSqlSelectDataOrigin += SqlBuilder.buildSqlLimit(intCount, intRegsPerPage, intPagina);
//            oStatement = (Statement) connection.createStatement();
//            oResultSet = oStatement.executeQuery(strSqlSelectDataOrigin);
//            while (oResultSet.next()) {
//                vector.add(oResultSet.getInt("id"));
//            }
//        } catch (Exception ex) {
//            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPageSQL ERROR:  Can't process query: " + ex.getMessage()));
//        } finally {
//            if (oResultSet != null) {
//                oResultSet.close();
//            }
//            if (oStatement != null) {
//                oStatement.close();
//            }
//        }
//        return vector;
//    }
    @Override
    public int removeOne(int intId, String strTabla) throws Exception {
        PreparedStatement oPreparedStatement = null;
        int intResult = 0;
        try {
            String strSQL = "DELETE FROM " + strTabla + " WHERE id = ?";
            oPreparedStatement = (PreparedStatement) connection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, intId);
            intResult = oPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":removeOne ERROR removing register: " + ex.getMessage()));
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return intResult;
    }

    @Override
    public Boolean existsOne(String strSqlSelectDataOrigin, int id) throws Exception {
        ResultSet oResultSet = null;
        int intResult = 0;
        Statement oStatement = null;
        try {
            oStatement = (Statement) connection.createStatement();
            String strSQL = "SELECT COUNT(*) " + strSqlSelectDataOrigin.substring(strSqlSelectDataOrigin.indexOf("from"), strSqlSelectDataOrigin.length());
            oResultSet = oStatement.executeQuery(strSQL);
            while (oResultSet.next()) {
                intResult = oResultSet.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCountSQL ERROR:  Can't process query: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oStatement != null) {
                oStatement.close();
            }
        }
        return intResult > 0;

    }

    @Override
    public String getOne(String strSqlSelectDataOrigin, String strCampo, int id) throws Exception {
        String strResult = null;
        PreparedStatement oPreparedStatement = null;
        ResultSet oResultSet = null;
        String strSQL = "";

        try {
            strSQL = strSqlSelectDataOrigin.substring(0, strSqlSelectDataOrigin.indexOf("1=1") + 3) + " AND id=" + id + " " + strSqlSelectDataOrigin.substring(strSqlSelectDataOrigin.indexOf("1=1") + 3, strSqlSelectDataOrigin.length());
            oPreparedStatement = connection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                strResult = oResultSet.getString(strCampo);
            } else {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: ID not exists: " + id));
            }
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getOne ERROR: Can't process query: " + ex.getMessage()));
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return strResult;
    }

}
