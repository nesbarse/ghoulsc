/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

/**
 *
 * @author rafa
 */
public class UsuarioBean extends GenericBeanImplementation implements GenericBeanInterface {

    private String login = "";
    private String password = "";
    private Enum tipoUsuario = null;

    /**
     * @return the tipoUsuario
     */
    public Enum getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(Enum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}