package br.com.goldencode.softsports;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    Drawable drawable;
    TextView toolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView nomeusuario = (TextView) findViewById(R.id.tvNomeCompleto);
        TextView email = findViewById(R.id.tvEmail);

        nomeusuario.setText(SingletonUsuario.getInstance().getUsuario().getNome());
        email.setText(SingletonUsuario.getInstance().getUsuario().getEmail());
        toolbarTextView = findViewById(R.id.toolbarText);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawable = (Drawable) ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back, getTheme());


        toolbarTextView.setText("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("PERFIL");
        getSupportActionBar().setHomeAsUpIndicator(drawable);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, TelaInicialActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

}
