package br.com.goldencode.softsports;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Softsports extends SQLiteOpenHelper {

    private static final int  VERSAO_BANCO = 4;

    //Nome da base de dados
    private static final String NAME = "bdd_softsports";

    //Tabela softplayer
    private static final String TABELA_SOFTPLAYER = "softplayer";

    //Colunas
    private final static String COD_SOFTPLAYER = "cod_softplayer";
    private final static String NOME = "nome";
    private final static String SOBRENOME = "sobrenome";
    private final static String EMAIL = "email";
    private final static String SENHA = "senha";
    private final static String FT_PERFIL = "ft_perfil";

    //Tabela esporte
    private static final String TABELA_ESPORTE = "esporte";

    //Colunas
    private final static String COD_ESPORTE = "cod_esporte";
    private final static String NOME_ESPORTE = "nome_esporte";

    //Tabela evento
    private static final String TABELA_EVENTO = "evento";

    //Colunas
    private final static String COD_EVENTO = "cod_evento";
    private final static String NOME_EVENTO= "nome_evento";
    private final static String DT_CRIACAO= "dt_criacao";
    private final static String DT_EVENTO= "dt_evento";
    private final static String OBSERVACOES= "observacoes";
    private final static String LOCAL= "local";
    private final static String HR_INICIO= "hr_inicio";
    private final static String HR_TERMINO= "hr_termino";
    private final static String NR_PARTICIPANTES= "nr_participantes";
    private static final String FK_ESPORTE = "cod_esporte" ;
    private static final String FK_USUARIO = "cod_usuario" ;

    //Métodos
    public Softsports(Context context) {
        super(context, NAME, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //FOREIGN KEY(NOME_QUE_EU_QUERO_DAR_PRA_FK) REFERENCES nome_da_tabela(nome_da_pk)

        //Query esporte
        String QUERY_TABELA_ESPORTE = "CREATE TABLE " + TABELA_ESPORTE + "("
                + COD_ESPORTE + " INTEGER PRIMARY KEY, " + NOME_ESPORTE + " TEXT)";

        db.execSQL(QUERY_TABELA_ESPORTE);

        //Query softplayer
        String QUERY_TABELA_SOFTPLAYER = "CREATE TABLE " + TABELA_SOFTPLAYER + "("
                + COD_SOFTPLAYER + " INTEGER PRIMARY KEY, " + NOME + " TEXT, "
                + SOBRENOME + " TEXT, " + EMAIL + " TEXT, " + SENHA + " TEXT, "
                + FT_PERFIL + " BLOB, " + FK_ESPORTE + " INTEGER, " + " FOREIGN KEY (" + FK_ESPORTE + " ) " + " REFERENCES "
                + TABELA_ESPORTE + "(" + COD_ESPORTE + "))";

        db.execSQL(QUERY_TABELA_SOFTPLAYER);

        //Query evento
        String QUERY_TABELA_EVENTO = "CREATE TABLE " + TABELA_EVENTO + "("
                + COD_EVENTO + " INTEGER PRIMARY KEY, " + NOME_EVENTO + " TEXT, "
                + DT_CRIACAO + " TEXT, " + DT_EVENTO + " TEXT, " + OBSERVACOES + " TEXT, "
                + LOCAL + " TEXT, " + HR_INICIO + " TEXT, " + HR_TERMINO + " TEXT, "
                + NR_PARTICIPANTES + " INTEGER, " + " FOREIGN KEY (" + FK_ESPORTE + " ) " + " REFERENCES "
                + TABELA_ESPORTE + "(" + COD_ESPORTE + " ), " + "FOREIGN KEY (" + FK_USUARIO + " ) " + " REFERENCES " + TABELA_SOFTPLAYER + "(" + COD_SOFTPLAYER + "))";

        db.execSQL(QUERY_TABELA_EVENTO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void cadastrarSoftplayer(Usuario softplayer){

        //Escrever na base de dados
        SQLiteDatabase bdd_softsports = this.getWritableDatabase();

        //Valores
        ContentValues players = new ContentValues();

        players.put(NOME, softplayer.getNome());
        players.put(SOBRENOME, softplayer.getSobrenome());
        players.put(EMAIL, softplayer.getEmail());
        players.put(SENHA, softplayer.getSenha());
        players.put(FT_PERFIL, softplayer.getFoto());
        players.put(FK_ESPORTE, softplayer.getCodEsporte());

        bdd_softsports.insert(TABELA_SOFTPLAYER, null, players);
    }

    void inserirEsportes(Esporte esporte){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOME_ESPORTE, esporte.getNome_esporte());

        db.insert(TABELA_ESPORTE, null , values);
    }

    boolean novoEvento(Evento evento){

        //Escrever na base de dados
        SQLiteDatabase bdd_softsports = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOME_EVENTO, evento.getNomeEvento());
        values.put(FK_ESPORTE, evento.getFk_esporte());
        values.put(DT_CRIACAO, evento.getDataCriacao());
        values.put(DT_EVENTO, evento.getNomeEvento());
        values.put(OBSERVACOES, evento.getObservacoes());
        values.put(LOCAL, evento.getLocal());
        values.put(HR_INICIO, evento.getHr_inicio());
        values.put(HR_TERMINO, evento.getHr_termino());
        values.put(NR_PARTICIPANTES, evento.getNr_participantes());

        bdd_softsports.insert(TABELA_EVENTO, null, values);

        Cursor cur = bdd_softsports.rawQuery("SELECT COUNT(*) FROM evento", null);
        if (cur.getCount() > 0){

            return true;
        }
        else
            return false;

    }

}