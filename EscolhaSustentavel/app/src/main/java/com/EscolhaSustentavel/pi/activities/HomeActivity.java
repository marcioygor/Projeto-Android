package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.fragments.DrawerFragment;
import com.EscolhaSustentavel.pi.fragments.FragHome;


public class HomeActivity extends AppCompatActivity implements DrawerFragment.OnDrawerLeftActionListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private TextView tvName, tvEmail;
    String nome, urlfoto;
    ImageView iv_profile_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_home_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.s_tab_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        tvName = (TextView) findViewById(R.id.tv_name_drawer);
        tvEmail = (TextView) findViewById(R.id.tv_email_drawer);
        iv_profile_drawer=(ImageView) findViewById(R.id.iv_profile_drawer);


        mDrawerLayout.addDrawerListener(actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.s_tab_home,
                R.string.s_tab_home
        ));

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getSupportFragmentManager().popBackStack();
            fragmentTransaction.replace(R.id.fl_fragment_container, FragHome.newInstane());
            fragmentTransaction.commit();
        }



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        nome = bundle.getString("nome");
        String email = bundle.getString("email");

        //Exibindo o nome do usuário logado;
        tvName.setText(nome);
        tvEmail.setText(email);



        enableDrawer();

    }

    //Menu onde fica a opção alterar senha e encerrra sessão
    @Override
    public void onDrawerUpdatePassword() {
        startActivity(new Intent(HomeActivity.this, AltereSenha.class));
        closeDrawer();
    }

    public void onDrawerAlterarNome() {
        startActivity(new Intent(HomeActivity.this, AlterarConta.class));

    }

    public void onDrawerExcluirConta() {
        startActivity(new Intent(HomeActivity.this, ExcluirConta.class));

    }


    @Override
    public void onDrawerLogout() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    private void enableDrawer() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
        actionBarDrawerToggle.syncState();
    }

    private void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }






}


