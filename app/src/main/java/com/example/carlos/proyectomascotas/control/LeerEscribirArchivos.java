package com.example.carlos.proyectomascotas.control;

import android.os.Environment;
import android.util.Log;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;

public class LeerEscribirArchivos {
    // obtengo almacenamiento externo sin importar plataforma
    private File file= Environment.getExternalStorageDirectory();
    private String ruta=file.getAbsolutePath()+ File.separator;

    public void escribirArchivo(Configuration configuration, String nombre){
        try{
        FileOutputStream fos = new FileOutputStream(ruta + nombre);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(configuration);
        out.close();

        }catch (FileNotFoundException e){
            Log.e("error archivo", e.toString());
        }catch (IOException e) {
            Log.e("error io: ", e.toString());
        }

    }

    public Configuration leerArchivo(String nombre){
        Configuration p = null;
        try {
            FileInputStream fis = new FileInputStream(ruta+nombre);
            ObjectInputStream in= new ObjectInputStream(fis);
            p=(Configuration) in.readObject();
        } catch (FileNotFoundException e) {
            Log.e("error: ",e.toString());
        } catch (IOException e) {
            Log.e("error io: ",e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("error no es persona: ",e.toString());
        }
        return p;
    }

}


