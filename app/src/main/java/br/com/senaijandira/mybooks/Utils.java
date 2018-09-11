package br.com.senaijandira.mybooks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static final String DATABASE_NAME = "mybooks.db";

    public static byte[] toByteArray( Bitmap bitmap){

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , blob);

        return blob.toByteArray();
    }

    public static byte[] toByteArray(Resources res,  int imgResource){
        Bitmap bitmap = BitmapFactory.decodeResource(res, imgResource);

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , blob);

        return blob.toByteArray();
    }


    public static Bitmap toBitmap(byte[] imagem){
        return BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
    }

}
