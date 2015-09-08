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
package net.daw.data.publicinterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DataInterface {

    public Boolean existsOne(String strSqlSelectDataOrigin, int id) throws Exception;

    public String getId(String strTabla, String strCampo, String strValor) throws Exception;

    public String getOne(String strSqlSelectDataOrigin, String strCampo, int id) throws Exception;

    public int setNull(int intId, String strTabla, String strCampo) throws Exception;

    public int insertOne(String strTabla) throws Exception;

    public int updateOne(int intId, String strTabla, String strCampo, String strValor) throws Exception;

    public int removeOne(int intId, String strTabla) throws Exception;

    public void removeSomeId(String strTabla, ArrayList<Integer> Ids) throws SQLException;

    public void removeSomeCondition(String strTabla, String campo, String valor) throws Exception;

    public ResultSet getPage(String strSqlSelectDataOrigin, int intRegsPerPage, int intPagina) throws Exception;

    public int getPages(String strSqlSelectDataOrigin, int intRegsPerPage) throws Exception;

    public int getCount(String strSqlSelectDataOrigin) throws Exception;

    public ResultSet getAllSql(String strSqlSelectDataOrigin) throws Exception;
}
