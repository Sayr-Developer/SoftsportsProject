package br.com.goldencode.softsports;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaMain extends AppCompatActivity {
    private ListaEsportesAdapter madapter;
    public SQLiteDatabase mDatabase;
    private ImageView mimageicon;
    private TextView mtextnome;
    private TextView mtextemail;
    private TextView mtextEsporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_main);

        Softsports dbHelper = new Softsports(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        madapter = new ListaEsportesAdapter(this, pegarallItens());
        recyclerView.setAdapter(madapter);
        mtextemail = findViewById(R.id.textview2);
        mtextnome = findViewById(R.id.textview);
        mtextEsporte = findViewById(R.id.textview3);
        mimageicon = findViewById (R.id.imageView);
        madapter.swapCursor(pegarallItens());

    }

    private Cursor pegarallItens(){
        return mDatabase.query(
                ListaEsporteContract.ListaEsportesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListaEsporteContract.ListaEsportesEntry.COLUMN_4 + " DESC"
        );
    }
}
