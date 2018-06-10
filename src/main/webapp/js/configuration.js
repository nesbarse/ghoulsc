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

configuration = {
    isAppInProductionMode: function () {
        return false;
    },
    getAppName: function () {
        var strPath = window.location.pathname;
        return strPath.substr(1, strPath.substr(1, strPath.length).indexOf('/'));
    },
    getAppClientUrl: function () {
        return location.protocol + '//' + location.hostname + ':' + location.port + '/' + this.getAppName();
    },
    getAppUrl: function () {
        return location.protocol + '//' + location.hostname + ':' + location.port + '/' + this.getAppName() + '/json';
    },
    resetCSS: function () {
        $('link[rel="stylesheet"]').each(function () {
            this.remove();
        })
        $('<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="vendor/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>').appendTo("head");        
        //these fonts have been located on our server
        //$('<link href="http://fonts.googleapis.com/css?family=Oswald" rel="stylesheet" type="text/css"/>').appendTo("head");
        //$('<link href="http://fonts.googleapis.com/css?family=Questrial" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="css/main/fonts/fonts.css" rel="stylesheet" type="text/css"/>').appendTo("head");        
        $('<link href="css/main/main.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="css/main/menu.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="css/main/standard.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="css/main/login.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        $('<link href="css/main/forms.css" rel="stylesheet" type="text/css"/>').appendTo("head");
        
    }
}

