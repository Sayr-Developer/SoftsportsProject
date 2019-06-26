package br.com.goldencode.softsports;

import android.provider.BaseColumns;

public class ListaEsporteContract {

    private ListaEsporteContract() {}

    public static final class ListaEsportesEntry implements BaseColumns{
        public static final String TABLE_NAME = "esporteslista";

        public static final String COLUMN_1 = "id";
        public static final String COLUMN_2 = "nome_usuario";
        public static final String COLUMN_3 = "email";
        public static final String COLUMN_4 = "esporte";
        public static final String COLUMN_5 = "cod_esporte";


    }
}
