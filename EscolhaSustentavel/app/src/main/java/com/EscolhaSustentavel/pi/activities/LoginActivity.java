package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.helpers.ValidarEntrada;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutNickname;
    private TextInputLayout textInputLayoutSenha;

    private TextInputEditText textInputEditTextNickname;
    private TextInputEditText textInputEditTextSenha;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkCadastro;
    private AppCompatTextView altereSenha;
    private ValidarEntrada validarEntrada;
    private DatabaseHelper databaseHelper;
    private SignInButton singin;
    private TextView Nome, Email;
    private ImageView foto;
    private GoogleApiClient googleApiClient;
    private static final int REQ_COD = 9001;
    private LinearLayout prof_section;
    String nome, Emailg;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IniciarViews();
        IniciarListeners();
        IniciarObjetos();


    }

    private void IniciarViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNickname = (TextInputLayout) findViewById(R.id.textInputLayoutNickname);
        textInputLayoutSenha = (TextInputLayout) findViewById(R.id.textInputLayoutSenha);


        textInputEditTextNickname = (TextInputEditText) findViewById(R.id.textInputEditTextNickname);
        textInputEditTextSenha = (TextInputEditText) findViewById(R.id.textInputEditTextSenha);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        textViewLinkCadastro = (AppCompatTextView) findViewById(R.id.textViewLinkCadastro);
        textViewLinkCadastro = (AppCompatTextView) findViewById(R.id.textViewLinkCadastro);
        altereSenha = (AppCompatTextView) findViewById(R.id.altereSenha);

    }


    private void IniciarListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkCadastro.setOnClickListener(this);
        altereSenha.setOnClickListener(this);


    }

    private void IniciarObjetos() {
        databaseHelper = new DatabaseHelper(activity);
        validarEntrada = new ValidarEntrada(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                VerificarLogin();
                break;
            case R.id.textViewLinkCadastro:
                Intent intentRegister = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.altereSenha:
                Intent intentAltere = new Intent(getApplicationContext(), AltereSenha.class);
                startActivity(intentAltere);
                break;

        }
    }


    private void VerificarLogin() {

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextSenha, textInputLayoutSenha, "Digite a Senha")) {
            return;
        }

        if (!validarEntrada.isInputEditTextFilled(textInputEditTextNickname, textInputLayoutNickname, "Digite o Nickname")) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextNickname.getText().toString().trim()
                , textInputEditTextSenha.getText().toString().trim())) {

            Bundle bundle = new Bundle();
            bundle.putString("nome", nome);
            bundle.putString("email", Emailg);
            Intent accountsIntent = new Intent(activity, HomeActivity.class);

            accountsIntent.putExtras(bundle);
            startActivity(accountsIntent);


        } else {
            Snackbar.make(nestedScrollView, "Nickname ou senha incorretos", Snackbar.LENGTH_LONG).show();
            LimparCampos();
        }
    }


    //Esse método é para limpar os campos
    private void LimparCampos() {
        textInputEditTextNickname.setText(null);
        textInputEditTextSenha.setText(null);
    }


}

