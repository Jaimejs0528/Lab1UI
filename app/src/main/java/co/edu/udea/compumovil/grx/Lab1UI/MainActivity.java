package co.edu.udea.compumovil.grx.Lab1UI;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.edu.udea.compumovil.grx.Lab1UI.Object.Persona;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private ArrayList<CheckBox> favoritos;
    private ArrayList<String> hobbiesF;
    private String[] paT;
    private int base;
    private Persona nueva;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paT = getResources().getStringArray(R.array.paises);
        String[] hoT = getResources().getStringArray(R.array.hobbies);

        ArrayAdapter<String> pais = new ArrayAdapter<>(this, R.layout.spinner_for_hobbies, paT);
        ArrayAdapter<String> hobbie = new ArrayAdapter<>(this, R.layout.spinner_for_hobbies, hoT);
        favoritos = new ArrayList<>();
        hobbiesF = new ArrayList<>();

        DatePicker date = (DatePicker) findViewById(R.id.dpFecha);
        date.setMaxDate(Calendar.getInstance().getTimeInMillis());

        AutoCompleteTextView autoCountries = (AutoCompleteTextView) findViewById(R.id.autoCountries);
        autoCountries.setAdapter(pais);
        autoCountries.setOnFocusChangeListener(this);
        autoCountries.setThreshold(1);

        EditText email = (EditText) findViewById(R.id.txtcorreo);
        base = email.getCurrentHintTextColor();
        email.setOnFocusChangeListener(this);

        Spinner hobbies = (Spinner) findViewById(R.id.spHobbies);
        hobbies.setAdapter(hobbie);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void OnClick(View e) {

        switch (e.getId()) {

            case R.id.btnAdd:
                Spinner temp = (Spinner) findViewById(R.id.spHobbies);
                LinearLayout container = (LinearLayout) findViewById(R.id.llContainer);
                if (!temp.getSelectedItem().equals(temp.getItemAtPosition(0))) {
                    String selected = temp.getSelectedItem().toString();
                    for (String s : hobbiesF) {
                        if (selected.equals(s)) {
                            Toast.makeText(this, getString(R.string.mensajeSp2), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    CheckBox check = new CheckBox(this);
                    check.setText(temp.getSelectedItem().toString());
                    check.setTextSize(18f);
                    check.setTextColor(getResources().getColor(R.color.text));
                    check.setBottom(R.dimen.button_margin_vertical);
                    container.addView(check);
                    TextView tempTxt = (TextView) findViewById(R.id.txtfavs);
                    tempTxt.setVisibility(View.VISIBLE);
                    favoritos.add(check);
                    hobbiesF.add(selected);
                    temp.setSelection(0);
                    Toast.makeText(this, getString(R.string.mensajeSp3) + " " + selected, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, getString(R.string.mensajeSp1), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnShow:

                if (validarTexto((EditText) findViewById(R.id.txtNombre)) & validarTexto((AutoCompleteTextView) findViewById(R.id.autoCountries)) & validarTexto((EditText) findViewById(R.id.txtTelefono)) & validarTexto((EditText) findViewById(R.id.txtcorreo))) {
                    TextView tempEmail = (TextView) findViewById(R.id.txtcorreo);
                    if (validarEmail(tempEmail.getText())) {
                        String nombre = (((EditText) findViewById(R.id.txtNombre))).getText().toString();
                        String apellido = (((EditText) findViewById(R.id.txtApellido))).getText().toString();
                        String genero;
                        int generoSelect = ((RadioGroup) findViewById(R.id.rgGenero)).getCheckedRadioButtonId();
                        RadioButton genS = (RadioButton) findViewById(generoSelect);
                        genero = genS.getText().toString();
                        DatePicker dt = (DatePicker) findViewById(R.id.dpFecha);
                        int dia = dt.getDayOfMonth();
                        int mes = dt.getMonth();
                        int año = dt.getYear();
                        Calendar feNaci = Calendar.getInstance();
                        feNaci.set(año, mes, dia);
                        String pais = (((AutoCompleteTextView) findViewById(R.id.autoCountries))).getText().toString();
                        String telefono = (((EditText) findViewById(R.id.txtTelefono))).getText().toString();
                        String direccion = (((EditText) findViewById(R.id.txtDireccion))).getText().toString();
                        String correo = (((EditText) findViewById(R.id.txtcorreo))).getText().toString();
                        ArrayList<String> favHob = new ArrayList<>();
                        for (CheckBox favorito : favoritos) {
                            if (favorito.isChecked()) {
                                //Toast.makeText(this,favorito.getText(),Toast.LENGTH_LONG).show();
                                favHob.add(favorito.getText().toString());
                            }
                        }
                        CheckBox contacto = (CheckBox) findViewById(R.id.contactoFav);
                        boolean contactoFav = contacto.isChecked();
                        nueva = new Persona(nombre, apellido, genero, feNaci, pais, direccion, telefono, correo, hobbiesF, favHob, contactoFav);
                        TextView salidatxt = (TextView) findViewById(R.id.salida);
                        salidatxt.setVisibility(View.VISIBLE);
                        salidatxt.setText(crearSalida());
                        Toast.makeText(this, getString(R.string.mensajeMostrar), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.mensajeCampos), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public String crearSalida() {
        SimpleDateFormat fecha;
        fecha = new SimpleDateFormat(getString(R.string.dateFormat));

        String salida = getString(R.string.salida) + " \n";
        if (nueva.isContactoFavorito()) {
            salida = salida + "\n" + getString(R.string.mensajeContactoFav);
        }
        salida = salida + agregarLineas(nueva.getNombre(), getString(R.string.nombre));
        salida = salida + agregarLineas(nueva.getApellido(), getString(R.string.apellido));
        salida = salida + agregarLineas(nueva.getSexo(), getString(R.string.genero));
        salida = salida + "\n" + getString(R.string.feNaci) + ": " + fecha.format(nueva.getFechaNacimiento().getTime());
        salida = salida + agregarLineas(nueva.getPais(), getString(R.string.pais2));
        salida = salida + agregarLineas(nueva.getTelefono(), getString(R.string.telefono));
        salida = salida + agregarLineas(nueva.getDireccion(), getString(R.string.direccion));
        salida = salida + agregarLineas(nueva.getEmail(), getString(R.string.correo));
        salida = salida + agregarLineasArray(nueva.getHobbies(), getString(R.string.hobbies));
        salida = salida + agregarLineasArray(nueva.getFavs(), getString(R.string.favoritos));
        return salida;


    }

    public String agregarLineas(String datos, String campo) {
        if (!datos.equals("")) {
            return "\n" + campo + ": " + datos;
        } else {
            return "";
        }
    }

    public String agregarLineasArray(ArrayList<String> datos, String campo) {
        if (!datos.isEmpty()) {
            String finalDatos = "";
            for (String dato : datos) {
                if (finalDatos.equals("")) {
                    finalDatos = "\n - " + dato;
                } else {
                    finalDatos = finalDatos + "\n - " + dato;
                }
            }
            return "\n" + campo + ": " + finalDatos;
        } else {
            return "";
        }
    }

    public boolean validarTexto(EditText viewTemp) {
        if (viewTemp.getText().toString().equals("")) {
            viewTemp.setHintTextColor(getResources().getColor(R.color.error));
            return false;
        } else {
            viewTemp.setHintTextColor(ColorStateList.valueOf(base));
            return true;
        }
    }

    public boolean validarTexto(AutoCompleteTextView viewTemp) {
        if (viewTemp.getText().toString().equals("")) {

            viewTemp.setHintTextColor(getResources().getColor(R.color.error));
            return false;
        } else {
            viewTemp.setHintTextColor(ColorStateList.valueOf(base));
            return true;
        }
    }

    public boolean validarEmail(CharSequence email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.mensajeEmail), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.autoCountries:
                if (!hasFocus) {
                    AutoCompleteTextView temp = (AutoCompleteTextView) findViewById(R.id.autoCountries);
                    for (String aPaT : paT) {
                        if (temp != null) {
                            if ((temp.getText() + "").equalsIgnoreCase(aPaT)) {
                                return;
                            }
                        }
                    }
                    Toast.makeText(this, getString(R.string.mensajeP), Toast.LENGTH_SHORT).show();
                    if (temp != null) {
                        temp.setText("");
                    }
                }
                break;

            case R.id.txtcorreo:
                if (!hasFocus) {
                    TextView temp = (TextView) findViewById(R.id.txtcorreo);
                    validarEmail(temp.getText());
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://co.edu.udea.compumovil.grx.lab1ui/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://co.edu.udea.compumovil.grx.lab1ui/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /*public void onSaveInstanceState(Bundle outState){
        outState.pu
    }*/
}
