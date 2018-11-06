package com.example.alejandro.afinal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText guadar, descripcion, fecha, hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guadar = (EditText)findViewById(R.id.txtNombre);
        descripcion = (EditText)findViewById(R.id.txtDescripcion);
        fecha = (EditText)findViewById(R.id.txtFecha);
        hora = (EditText)findViewById(R.id.txtHora);
    }
        //Metodo para agregar notas
    public void Agregar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Notas",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombre = guadar.getText().toString();
        String descri = descripcion.getText().toString();
        String Fe = fecha.getText().toString();
        String Ho = hora.getText().toString();

        if (!nombre.isEmpty() && !descri.isEmpty() && !Fe.isEmpty() && !Ho.isEmpty()){
            ContentValues registrar = new ContentValues();
            registrar.put("Nombre",nombre);
            registrar.put("Descripcion", descri);
            registrar.put("Fecha",Fe);
            registrar.put("Hora",Ho);

            BaseDeDatos.insert("Notas", null, registrar);
            BaseDeDatos.close();
            guadar.setText("");
            descripcion.setText("");
            fecha.setText("");
            hora.setText("");
            Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Todos los campos tienen que estar llenos",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para mostrar
    public void Mostrar(View view){
        AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(this,"Notas",null, 1 );
        SQLiteDatabase BaseDeDatos = Admin.getWritableDatabase();
        String codigo = guadar.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, fecha, hora from datos where nombre = "+ codigo, null);

            if (fila.moveToFirst()){
                guadar.setText(fila.getString(0));
                descripcion.setText(fila.getString(1));
                fecha.setText(fila.getString(2));
                hora.setText(fila.getString(3));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this,"El articulo no existe",Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }

        }else{
            Toast.makeText(this,"Debes ingresar el nombre de la nota",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para eliminar las notas
    public void Eliminar (View view){
        AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(this,"Notas",null, 1 );
        SQLiteDatabase BaseDeDatos = Admin.getWritableDatabase();

        String nombre = guadar.getText().toString();
        String descri = descripcion.getText().toString();
        String Fe = fecha.getText().toString();
        String Ho = hora.getText().toString();
        if(!nombre.isEmpty()){
            int cantidad = BaseDeDatos.delete("datos","nombre=" + nombre, null);
            BaseDeDatos.close();
            guadar.setText("");
            descripcion.setText("");
            fecha.setText("");
            hora.setText("");

            if (cantidad == 1){
                Toast.makeText(this,"Se elimino correctamente",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"No se elimino",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Debes ingresar el nombre de la nota",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo de modificar
    public  void Modificar (View view){
        AdminSQLiteOpenHelper Admin = new AdminSQLiteOpenHelper(this,"Notas",null, 1 );
        SQLiteDatabase BaseDeDatos = Admin.getWritableDatabase();

        String nombre = guadar.getText().toString();
        String descri = descripcion.getText().toString();
        String Fe = fecha.getText().toString();
        String Ho = hora.getText().toString();
    if (!nombre.isEmpty() && !descri.isEmpty() && !Fe.isEmpty() && !Ho.isEmpty()){

        ContentValues registrar = new ContentValues();
        registrar.put("Nombre",nombre);
        registrar.put("Descripcion", descri);
        registrar.put("Fecha",Fe);
        registrar.put("Hora",Ho);

        int cantidad = BaseDeDatos.update("datos",registrar,"nombre="+nombre, null);
        BaseDeDatos.close();

        if(cantidad == 1){
            Toast.makeText(this,"La nota se modifico correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"La nota no se modifico",Toast.LENGTH_SHORT).show();
        }

    }else{
        Toast.makeText(this,"Tiene que llenar todos los datos",Toast.LENGTH_SHORT).show();
        }

    }
}
