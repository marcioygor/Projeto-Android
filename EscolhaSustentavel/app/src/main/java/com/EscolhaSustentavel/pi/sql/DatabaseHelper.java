package com.EscolhaSustentavel.pi.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.EscolhaSustentavel.pi.model.Usuario;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "BancoPi.db";

    // Usuário
    private static final String TABELA_Usuario = "usuario";

    private static final String COLUNA_Usuario_ID = "user_id";
    private static final String COLUNA_Usuario_NOME = "user_nome";
    private static final String COLUNA_USuario_NICKNAME = "user_nickname";
    private static final String COLUNA_Usuario_SENHA = "user_senha";
    private static final String COLUNA_Usuario_PalavraS = "user_palavras";



    private String CREATE_USER_TABLE = "CREATE TABLE " + TABELA_Usuario
            + "(" + COLUNA_Usuario_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_Usuario_NOME + " TEXT,"
            + COLUNA_USuario_NICKNAME + " TEXT,"
            + COLUNA_Usuario_SENHA + " TEXT,"
            + COLUNA_Usuario_PalavraS + " TEXT"
            +")";



    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABELA_Usuario;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Apenas para testes - Remover apos ajustes
        db.execSQL(DROP_USER_TABLE);

        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }


    // USUÁRIO
    public void addUser(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_Usuario_NOME, usuario.getNome());
        values.put(COLUNA_USuario_NICKNAME, usuario.getNickname());
        values.put(COLUNA_Usuario_SENHA, usuario.getSenha());
        values.put(COLUNA_Usuario_PalavraS, usuario.getPalavraseg());

        db.insert(TABELA_Usuario, null, values);
        db.close();
    }



    public void deleteUser(String nick, String Palavras) {

        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "DELETE FROM  usuario WHERE user_nickname= " + "'" + nick + "'" +
                "and user_palavras = " + "'" + Palavras + "'";

        db.execSQL(strSQL);
    }



    public boolean checkUser(String email) {
        String[] columns = {
                COLUNA_Usuario_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUNA_USuario_NICKNAME + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String nickname, String senha) {
        String[] columns = {
                COLUNA_Usuario_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUNA_USuario_NICKNAME + " = ?" + " AND " + COLUNA_Usuario_SENHA + " = ?";
        String[] selectionArgs = {nickname, senha};

        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }







  //método que irá checar se a palavra de segurança pertence ao usuario

    public boolean CheckPalavra(String nickname, String palavras){

        String[] columns = {
                COLUNA_Usuario_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUNA_USuario_NICKNAME + " = ?" + " AND " + COLUNA_Usuario_PalavraS + " = ?";

        String[] selectionArgs = {nickname, palavras};


        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;

    }



    //método para alterar a senha do usuario

    public void AlterarSenha(String Nikcname, String PalavraS, String Novasenha) {



        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "UPDATE usuario SET user_senha= " + "'" + Novasenha + "'" +
                " WHERE user_nickname= " + "'" + Nikcname + "'" + " and user_palavras = " + "'" + PalavraS + "'";

        db.execSQL(strSQL);

    }

    public void AlterarNome(String Nikcname, String PalavraS, String Novonome) {



        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "UPDATE usuario SET user_nome= " + "'" + Novonome + "'" +
                " WHERE user_nickname= " + "'" + Nikcname + "'" + " and user_palavras = " + "'" + PalavraS + "'";

        db.execSQL(strSQL);

    }






}
