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
$.fn.serializeObject = function ()
{
    // http://jsfiddle.net/davidhong/gP9bh/
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = encodeURIComponent(this.value) || '';
        }
    });
    return o;
};
init = {
    ajaxCall: function (url, type, data) {
        return $.ajax({
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            method: type,
            url: url,
            data: data,
            timeout: 30000
        });
    },
    loadModal: function (modalName, keyb) {
        $(modalName).modal({
            keyboard: keyb
        })
    },
    getSessionPromise: function () {
        return this.ajaxCall(configuration.getAppUrl() + '?ob=usuario&op=getsessionstatus', 'GET', '');
    },
    getLoginPromise: function (username, password) {
        return this.ajaxCall(configuration.getAppUrl() + '?ob=usuario&op=login&login=' + username + '&password=' + password, 'GET', '');
    },
    getLogoutPromise: function () {
        return this.ajaxCall(configuration.getAppUrl() + '?ob=usuario&op=logout', 'GET', '');
    },
    updateConnectedState: function (loginName) {
        $('#broth_username_id').text(loginName);
        $('.broth_show_when_logged_in').show();
        $('.broth_show_when_logged_out').hide();
        //$("#broth_username_menu_id").css('display', 'block', 'important');
        init.unloadLoginForm();
        //configuration.loadRoutes();
        //Path.listen();
    },
    updateDisconnectedState: function () {
        $('#broth_username_id').text("Login");
        $('.broth_show_when_logged_in').hide();
        $('.broth_show_when_logged_out').show();
        init.loadLoginForm();
    },
    checkAndUpdateUserConnectionState: function () {
        init.getSessionPromise().done(function (data) {
            if (data['status'] == 200) {
                init.updateConnectedState(data.message);
            } else {
                init.updateDisconnectedState();
            }
            ;
        });
    },
    loadLoginForm: function () {
        $("#broth_login_modal_footer").html("");
        $('#brothModalLogin').append(init.loginForm());
        init.loadModal('#broth_modal_login', true);
        $("#broth_button_login").unbind('click');
        $("#broth_button_login").click(function () {
            username = $("#broth_input_login").val();
            password = $("#broth_input_password").val();
            $("#broth_login_modal_footer").html("Please, wait while contacting server for authentication...");
            init.getLoginPromise(username, password).done(function (response) {
                if (response.status == 200) {
                    $("#broth_login_modal_footer").html("Welcome, you're allowed to enter the site!");
                    init.unloadLoginForm();
                    init.updateConnectedState(response.message)
                } else {
                    $("#broth_login_modal_footer").html("Login, password or both are incorrect. Please try it again.");
                    init.updateDisconnectedState();
                }
            })
            return false;
        });
    },
    unloadLoginForm: function () {
        $('#broth_modal_login').modal('hide');
    },
    login: function (username, password) {
        var that = this;
        init.getLoginPromise(username, password).done(function (response) {
            $("#infoPanel").html(response.message);
            that.session_updateConnectedState(response.message);
        });
    },
    logout: function () {
        init.getLogoutPromise().done(function (response) {
            $("#infoPanel").html(response.message);
            init.updateDisconnectedState();
        });
    },
    getUrlObjectFromUrlString: function (url) {
        var a;
        if (typeof url == 'undefined') {
            return {};
        } else {
            if (url == "") {
                return {};
            } else {
                a = url.split('&');
            }
        }
        var b = {};
        for (var i = 0; i < a.length; ++i)
        {
            var p = a[i].split('=');
            if (p.length != 2)
                p = ['id', p[0]]; //id parameter by default
            b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
        }
        return b;
    },
    loginForm: function () {
        return (
                dom.div('id="broth_modal_login" class="modal fade bs-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"',
                        dom.div('class="modal-dialog modal-sm"',
                                dom.div('class="modal-content"',
                                        dom.div('class="modal-header"',
                                                dom.div('class="panel-title text-center"', 'Introduce login y password')
                                                ) +
                                        dom.div('class="modal-body"',
                                                dom.div('class="row"',
                                                        dom.div('class="col-md-12 col-lg-12"',
                                                                dom.form('role="form"',
                                                                        dom.fieldset('',
                                                                                dom.div('class="form-group"',
                                                                                        dom.input('class="form-control" id="broth_input_login" placeholder="Login" name="email" type="email" value="Usuario" autofocus', '')
                                                                                        ) +
                                                                                dom.div('class="form-group"',
                                                                                        dom.input('class="form-control" id="broth_input_password" placeholder="Password" name="password" type="password" value="Contraseña"', '')
                                                                                        ) +
                                                                                dom.a('href="index.html" id="broth_button_login" class="btn btn-lg btn-success btn-block"', 'Login')
                                                                                )

                                                                        )
                                                                )
                                                        )
                                                ) +
                                        dom.div('class="modal-footer" id="broth_login_modal_footer"', '')
                                        )
                                )
                        )
                )
    },
    getPageHeader: function (icon, title, subtitle) {
        return(
                dom.div('class="row"',
                        dom.div('class="col-xs-9 text-left"',
                                dom.h1('id="broth_title"', title) +
                                dom.h1('', dom.small('id="broth_subtitle"', subtitle))
                                ) +
                        dom.div('class="col-xs-3 text-right" id="broth_operationicon"', icon)
                        )
                );
    }
}