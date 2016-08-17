package co.edu.udea.compumovil.grx.Lab1UI.Object;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by jaime on 14/08/2016.
 */
public class Persona {

    private String nombre;
    private String apellido;
    private String sexo;
    private Calendar fechaNacimiento;
    private String pais;
    private String telefono;
    private String direccion;
    private String email;
    private ArrayList<String> hobbies;
    private ArrayList<String> favs;
    private boolean contactoFavorito;

    public Persona(String nombre, String apellido, String sexo, Calendar fechaNacimiento, String pais, String direccion, String telefono, String email, ArrayList<String> hobbies, ArrayList<String> favs, boolean contactoFavorito) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.hobbies = hobbies;
        this.favs = favs;
        this.contactoFavorito = contactoFavorito;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public ArrayList<String> getFavs() {
        return favs;
    }

    public boolean isContactoFavorito() {
        return contactoFavorito;
    }

}
