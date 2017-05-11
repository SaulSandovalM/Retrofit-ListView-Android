package com.retrofit.fixtergeek.retrofit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.retrofit.fixtergeek.retrofit.modelos.Catalogo;
import com.retrofit.fixtergeek.retrofit.modelos.Course;
import com.retrofit.fixtergeek.retrofit.modelos.Instructor;

import java.util.List;

import retrofit.RestAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Funciona: ";
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);

        RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("https://www.udacity.com/public-api/v0/").build();
        Service service = restadapter.create(Service.class);
        service.getClass(new Callback<List<Adapter>()){
            @Override
            public void success(List<Adapter> );
            Adapter adapter = new Adapter(MainActivity.this,);
            lista.setAdapter(adapter);
        }

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<Catalogo> requesCatalogo = service.listaCatalogo();

        requesCatalogo.enqueue(new Callback<Catalogo>() {
            @Override
            public void onResponse(Call<Catalogo> call, Response<Catalogo> response) {

                if (!response.isSuccessful()){
                    Log.i("TAG", "Error" + response.code());
                }else {
                    Catalogo catalogo = response.body();
                    for (Course c : catalogo.courses){
                        Log.i(TAG, String.format("%s: %s", c.title, c.subtitle));

                        //Listview
                        //ListView lv = (ListView) getView().findViewById(android.R.id.lista);

                        for (Instructor i : c.instructors){
                            Log.i(TAG, i.name);
                        }
                        Log.i(TAG, "-------------");
                    }
                }
            }

            @Override
            public void onFailure(Call<Catalogo> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
    private class Adapter extends ArrayAdapter<com.retrofit.fixtergeek.retrofit.Adapter>{
        private List<com.retrofit.fixtergeek.retrofit.Adapter> lista;

        public Adapter(Context context, List<com.retrofit.fixtergeek.retrofit.Adapter> adapter){
            super(context, R.layout.item, adapter);
            lista = adapter;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.item, null);

            TextView title = (TextView) item.findViewById(R.id.title);
            title.setText(lista.get(position).getTitle());

            TextView subtitle = (TextView)item.findViewById(R.id.subtitle);
            subtitle.setText(lista.get(position).getSubtitle());

            TextView name = (TextView)item.findViewById(R.id.name);
            name.setText(lista.get(position).getName());

            return item;
        }
    }
}
