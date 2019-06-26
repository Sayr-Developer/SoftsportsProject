package br.com.goldencode.softsports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class ActivityCadastro extends AppCompatActivity{

    //EditTexts
    private AppCompatEditText editTextNome;
    private AppCompatEditText editTextSobrenome;
    private AppCompatEditText editTextEmail;
    private AppCompatEditText editTextSenha;

    //RadioGroup
    private RadioGroup radioGroupEsporte;
    private RadioButton radioButton;

    //Buttons
    private AppCompatButton buttonCadastrar;
    private AppCompatButton buttonPerfil;

    //Outros
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private ImageView circleFotoPerfil;
    Softsports db = new Softsports(this);
    public int cod_esporte;
    public String nome;
    public String sobrenome;
    public String email;
    public String senha;
    public String esporte;
    public RadioButton selected;
    //Firebase
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (AppCompatEditText) findViewById(R.id.edtNome);
        editTextSobrenome = (AppCompatEditText) findViewById(R.id.edtSobrenome);
        editTextEmail = (AppCompatEditText) findViewById(R.id.edtEmail);
        editTextSenha = (AppCompatEditText) findViewById(R.id.edtSenha);
        buttonCadastrar = (AppCompatButton) findViewById(R.id.btnCadastrar);
        buttonPerfil = (AppCompatButton) findViewById(R.id.btnFoto);
        circleFotoPerfil = (ImageView) findViewById(R.id.iconePerfil);
        radioGroupEsporte = findViewById(R.id.radioGroupCadastro);

        nome = editTextNome.getText().toString().trim();
        sobrenome = editTextSobrenome.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        senha = editTextSenha.getText().toString().trim();

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        progressDialog = new ProgressDialog(this, R.style.customAlertDialogStyle);

        //Seleciona a foto que aparecerá pro usuário quando selecionada
        buttonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarFoto();
            }
        });

        //Cadastra o usuário no BDD
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });

    }

    //Volta para a tela de login
    public void voltarLogin(View view){
        finish();
    }

    public void selecionarFoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //Busca a imagem e insere na circleImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && intent != null && intent.getData() != null) {

            Uri fotoPerfilUri = intent.getData();

                circleFotoPerfil.setImageURI(fotoPerfilUri);
                circleFotoPerfil.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonPerfil.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_w));

        }
    }

        //Toast de sucesso personalizado
        public void toastSuccess(String mensagem){

            Context context = getApplicationContext();
            CharSequence text = mensagem;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);

            View view = toast.getView();

            //Obtém o plano de fundo oval real do Toast e, em seguida, define o filtro de cores
            view.getBackground().setColorFilter(getResources().getColor(R.color.colorSuccess), PorterDuff.Mode.SRC_IN);

            //Obtém o TextView do Toast para que ele possa ser editado
            TextView newText = view.findViewById(android.R.id.message);
            newText.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
            newText.setTextColor(getResources().getColor(R.color.colorWhite));

            toast.show();

        }

        //Toast de insucesso personalizado
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

    //Cadastra um novo usuário no BDD
    private void cadastrarUsuario(){

        RadioGroup radioesporte = (RadioGroup) findViewById(R.id.radioGroupCadastro);
        switch (radioesporte.getCheckedRadioButtonId())
        {
            case R.id.radioFutebol:
                cod_esporte = 1;
                selected = (RadioButton) findViewById(R.id.radioFutebol);
                esporte = selected.getText().toString();
                break;
            case R.id.radioBasquete:
                cod_esporte = 2;
                selected = (RadioButton) findViewById(R.id.radioBasquete);
                esporte = selected.getText().toString();
                break;
            case R.id.radioTenis:
                cod_esporte = 3;
                selected = (RadioButton) findViewById(R.id.radioTenis);
                esporte = selected.getText().toString();
                break;
            case R.id.radioTenisDeMesa:
                cod_esporte = 4;
                selected = (RadioButton) findViewById(R.id.radioTenisDeMesa);
                esporte = selected.getText().toString();
                break;
            case R.id.radioRugby:
                cod_esporte = 5;
                selected = (RadioButton) findViewById(R.id.radioRugby);
                esporte = selected.getText().toString();
                break;
            case R.id.radioVolei:
                cod_esporte = 6;
                selected = (RadioButton) findViewById(R.id.radioVolei);
                esporte = selected.getText().toString();
                break;
            case R.id.radioSurf:
                cod_esporte = 7;
                selected = (RadioButton) findViewById(R.id.radioSurf);
                esporte = selected.getText().toString();
                break;
            case R.id.radioSkate:
                cod_esporte = 8;
                selected = (RadioButton) findViewById(R.id.radioSkate);
                esporte = selected.getText().toString();
                break;
            case R.id.radioCorrida:
                cod_esporte = 9;
                selected = (RadioButton) findViewById(R.id.radioCorrida);
                esporte = selected.getText().toString();
                break;

        }

        nome = editTextNome.getText().toString().trim();
        sobrenome = editTextSobrenome.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        senha = editTextSenha.getText().toString().trim();

        if (nome == null || TextUtils.isEmpty(nome)) {
            //se o campo senha estiver vazio
            Toast.makeText(this, "O campo nome está vazio", Toast.LENGTH_SHORT).show();
            //parar função
            return;
        }

        if (sobrenome == null || TextUtils.isEmpty(sobrenome)) {
            //se o campo senha estiver vazio
            Toast.makeText(this, "O campo sobrenome está vazio", Toast.LENGTH_SHORT).show();
            //parar função
            return;
        }

        if (email == null || TextUtils.isEmpty(email)) {
            //se o campo email estiver vazio
            Toast.makeText(this, "O campo email está vazio", Toast.LENGTH_SHORT).show();
            //parar função
            return;
        }

        if (senha == null || TextUtils.isEmpty(senha)) {
            //se o campo senha estiver vazio
            Toast.makeText(this, "O campo senha está vazio", Toast.LENGTH_SHORT).show();
            //parar função
            return;
        }
        if(cod_esporte == 0 )
        {
            Toast.makeText(this, "Selecione seu esporte preferido", Toast.LENGTH_SHORT).show();
            return;
        }
        if(esporte == null)
        {
            return;
        }
        //se as validações estiverem ok

        //será mostrado o progressdialog

        //Função que controla o progressdialog e cadastra o usuário:

        progressDialog.setMessage("Cadastrando usuário, aguarde.");
        progressDialog.show();
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();

            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressDialog.dismiss();
                boolean verificacao = db.verificaemail(email);
                boolean lista = db.inserirLista(nome,sobrenome,email,esporte,cod_esporte);
                if (verificacao == true && lista == true) {
                    boolean insert = db.cadastrarSoftplayer(new Usuario(nome, sobrenome, email, senha, cod_esporte));

                    if (insert) {
                        progressDialog.dismiss();
                        finish();
                        toastSuccess("Usuário cadastrado com sucesso!");
                        Usuario u = new Usuario(nome+" "+sobrenome,email);
                        u.setNome(nome+" "+sobrenome);
                        u.setEmail(email);
                        SingletonUsuario.getInstance().setUsuario(u);

                    } else {
                        toastError("O usuário não pode ser cadastrado, tente novamente.");
                        progressDialog.dismiss();
                    }
                }
                else{
                    toastError("Usuário Já Cadastrado, Efetue Login");
                }
            }
        });

    }

}
