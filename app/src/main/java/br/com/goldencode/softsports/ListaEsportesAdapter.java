package br.com.goldencode.softsports;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaEsportesAdapter extends RecyclerView.Adapter <ListaEsportesAdapter.ListaEsportesHolder> {

    private Context mContext;
    private Cursor mCursor;

    public ListaEsportesAdapter(Context context, Cursor cursor)
    {
    mContext = context;
    mCursor = cursor;
    }
    public class ListaEsportesHolder extends RecyclerView.ViewHolder{
        public ImageView mimageView;
        public TextView mtextnome;
        public TextView mtextemail;
        public TextView mtextEsporte;
        public ListaEsportesHolder(@NonNull View itemView) {
            super(itemView);
            mimageView =  itemView.findViewById(R.id.imageView);
            mtextemail = itemView.findViewById(R.id.textview2);
            mtextnome = itemView.findViewById(R.id.textview);
            mtextEsporte = itemView.findViewById(R.id.textview3);

        }
    }

    @NonNull
    @Override
    public ListaEsportesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.usuario_item, viewGroup, false);
        return new ListaEsportesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaEsportesHolder listaEsportesHolder, int i) {
    if (!mCursor.moveToPosition(i)){
        return;
    }
    String nome = mCursor.getString(mCursor.getColumnIndex(ListaEsporteContract.ListaEsportesEntry.COLUMN_2));
    String email = mCursor.getString(mCursor.getColumnIndex(ListaEsporteContract.ListaEsportesEntry.COLUMN_3));
    String esporte = mCursor.getString(mCursor.getColumnIndex(ListaEsporteContract.ListaEsportesEntry.COLUMN_4));

    listaEsportesHolder.mimageView.setImageResource(R.drawable.icon_usuario);
    listaEsportesHolder.mtextnome.setText(nome);
    listaEsportesHolder.mtextemail.setText(email);
    listaEsportesHolder.mtextEsporte.setText(esporte);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor c)
    {
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = c;
        if(c != null){
            notifyDataSetChanged();
        }
    }
}
