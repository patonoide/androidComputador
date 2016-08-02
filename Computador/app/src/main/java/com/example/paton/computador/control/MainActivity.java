package com.example.paton.computador.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.res.Configuration;
import android.database.Cursor;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.paton.computador.R;
import com.example.paton.computador.adapter.ListAdapter;
import com.example.paton.computador.dao.ComputadorDAO;
import com.example.paton.computador.dao.Contract;
import com.example.paton.computador.model.Computador;



    public class MainActivity extends Activity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, android.app.ActionBar.TabListener {
    private ComputadorDAO ctoDAO;
    private Computador cto;
    private EditText eTCPU, eTRAM, eTMarca, eTHD;
    private ListView listagem;
    android.app.ActionBar.Tab tab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ctoDAO = ComputadorDAO.getInstance(MainActivity.this);
        ctoDAO.open();

        cto = new Computador();

        if (isTablet(MainActivity.this)) {
            setContentView(R.layout.tablet);

            eTMarca = (EditText) findViewById(R.id.marca);
            eTCPU = (EditText) findViewById(R.id.CPU);
            eTRAM = (EditText) findViewById(R.id.RAM);
            eTHD = (EditText) findViewById(R.id.hd);
            listagem = (ListView) findViewById(R.id.listViewT);


            android.app.ActionBar actionBar = getActionBar();
            actionBar.setTitle("Computador");


            controlarEdicao(false);
            carregarListView(ctoDAO.getLista());
            listagem.setOnItemClickListener(MainActivity.this);

        } else {
            android.app.ActionBar actionBar = getActionBar();
            actionBar.setTitle("Computador");

            actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);

            android.app.ActionBar.Tab tab1 = actionBar.newTab().setText("Lista de Computadores");
            tab1.setTabListener(MainActivity.this);
            actionBar.addTab(tab1);


            tab2 = actionBar.newTab().setText("Computador");
            tab2.setTabListener(MainActivity.this);
            actionBar.addTab(tab2);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ctoDAO.close();
    }

    private static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    @Override
    public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
    {
            switch (tab.getPosition()){
                case 0:{
                    setContentView(R.layout.list_smart);


                    listagem = (ListView) findViewById(R.id.listViewT);
                    listagem.setOnItemClickListener(MainActivity.this);

                    carregarListView(ctoDAO.getLista());


                    break;
                }
                case 1:{
                    setContentView(R.layout.cadastrar_smart);


                    eTCPU = (EditText) findViewById(R.id.CPU);
                    eTRAM = (EditText) findViewById(R.id.RAM);
                    eTHD = (EditText) findViewById(R.id.hd);
                    eTMarca = (EditText) findViewById(R.id.marca);


                    controlarEdicao(true);

                    break;
                }
            }
        }

    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView mySearchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        mySearchView.setQueryHint("digite a marca");
        mySearchView.setOnQueryTextListener(MainActivity.this);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuitem_novo:{
                novo();
                return true;
            }
            case R.id.menuitem_salvar:{
                salvar();
                return true;
            }
            case R.id.menuitem_excluir:{
                excluir();
                return true;
            }
            case R.id.menuitem_cancelar:{
                cancelar();
                return true;
            }
        }
        return false;
    }
    @Override
    public void onItemClick(AdapterView<?> adpListView, View view, int position,
                            long id) {
        Cursor cursor = (Cursor) adpListView.getItemAtPosition(position);

        if(!isTablet(MainActivity.this)) {
            tab2.select();
            onTabSelected(tab2, null);
        }

        cto.setId_computador(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Computador.COLUNA_ID)));
        eTCPU.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Computador.COLUNA_CPU)));
        eTMarca.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Computador.COLUNA_MARCA)));
        eTRAM.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Computador.COLUNA_RAM)));
        eTHD.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Computador.COLUNA_HD)));
        controlarEdicao(true);

    }


    public void controlarEdicao(Boolean enabled){
        eTHD.setEnabled(enabled);
        eTRAM.setEnabled(enabled);
        eTMarca.setEnabled(enabled);
        eTCPU.setEnabled(enabled);
    }


    public void limparFormulario(){
        eTMarca.setText(null);
        eTCPU.setText(null);
        eTHD.setText(null);
        eTRAM.setText(null);

    }



    public void carregarListView(Cursor cursor){

        final String[] DE = {Contract.Computador.COLUNA_RAM, Contract.Computador.COLUNA_HD, Contract.Computador.COLUNA_CPU, Contract.Computador.COLUNA_MARCA};
        final int[] PARA = {R.id.tv_item_ram, R.id.tv_item_hd, R.id.tv_item_cpu, R.id.tv_item_marca};

        ListAdapter dadosAdapter = new ListAdapter(
                MainActivity.this,
                R.layout.adapter_layout,
                cursor,
                DE,
                PARA,
                0);

        listagem.setAdapter(dadosAdapter);
    }





    public void caixaDeDialogoSimNao(){

        AlertDialog.Builder cxDialogo = new AlertDialog.Builder(this);

        cxDialogo.setTitle("quer mesmo excluir esse computador?");

        cxDialogo.setMessage("esste computador será apagado pra sempre.");

        cxDialogo.setPositiveButton("Já era", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(ctoDAO.excluir(cto) != 0){
                    carregarListView(ctoDAO.getLista());
                }
                limparFormulario();
                controlarEdicao(false);
                Toast.makeText(MainActivity.this, "computador excluído.", Toast.LENGTH_LONG).show();
            }
        });

        cxDialogo.setNegativeButton("Calma ae", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                limparFormulario();
                controlarEdicao(false);
                Toast.makeText(MainActivity.this, "Não foi excluído.", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = cxDialogo.create();
        alert.show();
    }

    public void novo(){
        cto = new Computador();
        controlarEdicao(true);
        eTCPU.requestFocus();

    }


    public void salvar(){

        if(eTMarca.getText().toString().isEmpty() ||
                eTRAM.getText().toString().isEmpty() ||
                eTHD.getText().toString().isEmpty() || eTCPU.getText().toString().isEmpty() ){
            Toast.makeText(MainActivity.this, "preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }else{
            cto.setCpu(eTCPU.getText().toString());
            cto.setRam(eTRAM.getText().toString());
            cto.setHd(eTHD.getText().toString());
            cto.setMarca(eTMarca.getText().toString());


            if(ctoDAO.salvar(cto)){
                Toast.makeText(this, "Computador  salvo.", Toast.LENGTH_LONG).show();
                limparFormulario();
                controlarEdicao(false);
                carregarListView(ctoDAO.getLista());
            }

        }
    }

    public void cancelar(){
        limparFormulario();
        controlarEdicao(false);
    }


    public void excluir(){
        if(eTRAM.getText().toString().isEmpty() &&
                eTHD.getText().toString().isEmpty() &&
                eTCPU.getText().toString().isEmpty() && eTMarca.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            caixaDeDialogoSimNao();
        }
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String marca) {
        if(marca.isEmpty()){
            carregarListView(ctoDAO.getLista());
        }else{
            carregarListView(ctoDAO.getListaByMarca(marca));
        }
        return false;
    }

}

