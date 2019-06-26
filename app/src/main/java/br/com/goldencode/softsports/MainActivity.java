package br.com.goldencode.softsports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //EditTexts
    private AppCompatEditText editTextEmail;
    private AppCompatEditText editTextSenha;

    //Buttons
    private AppCompatButton buttonEntrar;
    private AppCompatButton buttonCadastrar;

    //Outros
    private ProgressDialog progressologin;
    private FirebaseAuth login;
    Softsports db = new Softsports(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.inserirEsportes(new Esporte("Futebol"));
        db.inserirEsportes(new Esporte("Basquete"));
        db.inserirEsportes(new Esporte("Tênis de mesa"));
        db.inserirEsportes(new Esporte("Tênis"));
        db.inserirEsportes(new Esporte("Rugby"));
        db.inserirEsportes(new Esporte("Corrida"));
        db.inserirEsportes(new Esporte("Vôlei"));
        db.inserirEsportes(new Esporte("Surf"));
        db.inserirEsportes(new Esporte("Skate"));

        AppCompatButton buttonEsqueciSenha = (AppCompatButton) findViewById(R.id.btnEsqueciSenha);
        buttonEsqueciSenha.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        editTextEmail = findViewById(R.id.edtEmail);
        editTextSenha = findViewById(R.id.edtSenha);
        buttonEntrar = findViewById(R.id.btnEntrar);

        //Onclick para acessar o app
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inicia a próxima atividade
                String e = editTextEmail.getText().toString();
                String s = editTextSenha.getText().toString();

                if (e == null || TextUtils.isEmpty(e)) {
                    //se o campo email estiver vazio
                    toastError("O campo Email está Vazio");
                    //parar função
                    return;
                }

                if (s == null || TextUtils.isEmpty(s)) {
                    //se o campo senha estiver vazio
                    toastError("O campo Senha está Vazio");
                    //parar função
                    return;
                }
                boolean verificacao = db.verificaemail(e);
                if(verificacao == false){
                    boolean c = db.login(e,s);
                    if(c == true){

                        Intent intent = new Intent(MainActivity.this, TelaInicialActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        toastError("Credenciais Inválidas, Revise seu e-mail e senha");
                    }
                }
                else{
                    toastError("Email não Cadastrado, Registre-se");
                }
            }
        });


    }
    public void toastError(String mensagem){

        Context context = getApplicationContext();
        CharSequence text = mensagem;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);

        View view = toast.getView();

        //Obtém o plano de fundo oval real do Toast e, em seguida, define o filtro de cores
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorError), PorterDuff.Mode.SRC_IN);

        //Obtém o TextView do Toast para que ele possa ser editado
        TextView newText = view.findViewById(android.R.id.message);
        newText.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        newText.setTextColor(getResources().getColor(R.color.colorWhite));

        toast.show();
    }

    //Abre a atividade de cadastro
    public void abrirActivityCadastro(View view){
        Intent intent = new Intent(this, ActivityCadastro.class);
        startActivity(intent);
    }

}
