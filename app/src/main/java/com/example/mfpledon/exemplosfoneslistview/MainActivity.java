package com.example.mfpledon.exemplosfoneslistview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ItemListView {

    private String texto;
    private int iconeRid;

    public ItemListView() {
    }

    public ItemListView(String texto, int iconeRid) {
        this.texto = texto;
        this.iconeRid = iconeRid;
    }

    public int getIconeRid() {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid) {
        this.iconeRid = iconeRid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}

class AdapterListView extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ItemListView> itens;

    public AdapterListView(Context context, ArrayList<ItemListView> itens) {
        this.itens = itens; //Itens que preenchem o listview
        mInflater = LayoutInflater.from(context); //responsavel por pegar o Layout do item.
    }

    public int getCount() {
        return itens.size();
    }

    public ItemListView getItem(int position) {
        try{
            return itens.get(position);
        }
        catch (Exception ex) {
            //Toast.makeText(null, "\nErro em getItem, position: " + position, Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        try {
            ItemListView item = itens.get(position); //Pega o item de acordo com a posção
            view = mInflater.inflate(R.layout.rowlayoutfonestext, null); //infla o layout para podermos preencher os dados
            ((TextView) view.findViewById(R.id.labelfone)).setText(item.getTexto());
            ((ImageView) view.findViewById(R.id.icon)).setImageResource(item.getIconeRid());
            return view;
        }
        catch (Exception ex) {
                //Toast.makeText(null, "\nErro em getView, position: " + position, Toast.LENGTH_LONG).show();
                return null;
        }
    }
}

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;

    final int  PERMISSIONS_CALL_PHONE_ID= 1;
    final int  PERMISSIONS_CAMERA_ID= 5;
    boolean permissions_call_phone_value = false;
    boolean permissions_camera_value = false;

    String vetorcontatos [] = {  // as barras / permitirão pegar apenas o telefone:
            "Campus São Miguel / 2037-5700",
            "Campus Liberdade / 3385-3000",
            "Campus Pinheiros / 3030-4000",
            "Campus Anália Franco / 2672-6200",
            "Campus Virtual / 3385-3019",
            "Campus Virtual / 3385-3039",
            "Processo seletivo / 3003-1189",
            "Processo seletivo / 0800 721 5844",
            "CAA - São Miguel / 2037-5791",
            "CAA - Anália Franco / 2672-6215",
            "CAA - Liberdade / 3385-3008",
            "CAA - Pinheiros / 3030-4000",
            "Suporte redes - Anália Franco / 2672-6202",
            "Suporte redes - Liberdade / 3385-3016",
            "Suporte redes - São Miguel / 2037-5806",
            "Suporte SIAA professores / 2178-1324",
            "",
            ""
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_CALL_PHONE_ID);
        }else{
            permissions_call_phone_value = true;
        }
        listView = (ListView) findViewById(R.id.list_view);
        //Define o "listener" quando alguem clicar no item:
        listView.setOnItemClickListener(this);
        createListView();
    }

    private void createListView() {
        try {
            itens = new ArrayList<ItemListView>();
            //usamos ciclos separados apenas para usar figuras diferentes:
            for (int i = 1; i <= 4; i++) {
                ItemListView item = new ItemListView(vetorcontatos[i - 1], R.drawable.fones);
                itens.add(item);
            }
            for (int i = 5; i <= 8; i++) {
                ItemListView item = new ItemListView(vetorcontatos[i - 1], R.drawable.telescope);
                itens.add(item);
            }
            for (int i = 9; i <= 12; i++) {
                ItemListView item = new ItemListView(vetorcontatos[i - 1], R.drawable.fones_bck);
                itens.add(item);
            }
            for (int i = 13; i <= 16; i++) {
                ItemListView item = new ItemListView(vetorcontatos[i - 1], R.drawable.fones2);
                itens.add(item);
            }
            for (int i = 1; i < 10; i++) {
                ItemListView item = new ItemListView("", R.drawable.semfone);
                itens.add(item);
            }

            adapterListView = new AdapterListView(this, itens);
            listView.setAdapter(adapterListView);
            listView.setCacheColorHint(Color.TRANSPARENT); //cor quando a lista é selecionada para rolagem.
        }
        catch(Exception exx){}
        }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        try {
            ItemListView item = adapterListView.getItem(arg2); //Pega o item que foi selecionado.
            //Toast.makeText(this, "Você clicou em: " + item.getTexto(), Toast.LENGTH_LONG).show();
            if (arg2 < 16) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    String fone = vetorcontatos[arg2].split("/")[1];
                    Uri uri = Uri.parse("tel:" + fone);  //ligar para o número de telefone do contato selecionado
                    Intent it = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(it);
                } else {
                    Toast.makeText(this, "\nAs ligações não foram autorizadas neste aparelho.\n", Toast.LENGTH_LONG).show();
                }
            }
        }
        catch(Exception exx) {}
    }

}
