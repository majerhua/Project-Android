package com.example.pruebacodqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;
import fr.ganfra.materialspinner.MaterialSpinner;
import fr.ganfra.materialspinner.MaterialSpinner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity  implements AlCuadrado.View {

    private Button btnScanner;
    private TextView tvBarCode;
    private TextView tvAlCuadrado;
    private TextView mJsonTxtView;
    private EditText edAlCuadrado;
    private AlCuadrado.Presenter presenter;
    private Spinner spinner;
    String[] ITEMS = {};
    Context a = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDisciplinas();

        tvAlCuadrado = findViewById(R.id.tvAlCuadrado);
        edAlCuadrado = findViewById(R.id.edAlCuadrado);
        presenter = new AlCuadradoPresenter(this);
    }

    public void getDisciplinas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://appweb.ipd.gob.pe/academia/web/servicios/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Disciplinas>> call = jsonPlaceHolderApi.getDisciplinas();
        call.enqueue(new Callback<List<Disciplinas>>() {
            @Override
            public void onResponse(Call<List<Disciplinas>> call, Response<List<Disciplinas>> response) {
                if(!response.isSuccessful()){
                    mJsonTxtView.setText("Code:"+response.code());
                }

                List<Disciplinas> disList = response.body();


                int size = disList.size();
                System.out.println("Tamaño Lista =>"+size);
                int cont=0;
                ITEMS = new String[size];

                for(Disciplinas dis: disList){
                    System.out.println(dis.getDisciplinaNombre());
                    ITEMS[cont]=dis.getDisciplinaNombre();
                    cont++;
                }


                System.out.println("Array=>");
                System.out.println(ITEMS[0]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(a, android.R.layout.simple_spinner_item, ITEMS);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner = (MaterialSpinner) findViewById(R.id.spinnerl);
                spinner.setAdapter(adapter);
                /*int size = disList.size();
                int cont=0;
                ITEMS = new String[size];
                String content = "";

                for(Disciplinas dis: disList){
                    ITEMS[cont]=dis.getDisciplinaNombre();
                    content+=dis.getDisciplinaNombre();

                }
                mJsonTxtView.setText(content);*/

            }

            @Override
            public void onFailure(Call<List<Disciplinas>> call, Throwable t) {
                mJsonTxtView.setText(t.getMessage());
            }
        });
    }

    public void calcular(View view){
        presenter.alCuadrado(edAlCuadrado.getText().toString());
    }

    @Override
    public void showResult(String result) {
        tvAlCuadrado.setText(result);
    }
}



/*
package com.example.pruebacodqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity   {

   private Button btnScanner;
    private TextView tvBarCode;
    private Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScanner = findViewById(R.id.btnScanner);
        tvBarCode = findViewById(R.id.tvBarCode);
        btnScanner.setOnClickListener(mOnClickListener);
        mySpinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.planets_array));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                tvBarCode.setText("El Código de barras es\n"+result.getContents());
            }else{
                tvBarCode.setText("Error al Scanear Código Barras o QR");
            }
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.btnScanner:
                    new IntentIntegrator(MainActivity.this).initiateScan();
                    break;
            }
        }
    };
}
*/