package com.example.android.retrofit_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView superListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superListView = findViewById(R.id.superListView);

        getSuperHeroes();
    }

    private void getSuperHeroes() {
        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getSuperHeroes();
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(@NonNull Call<List<Results>> call, @NonNull Response<List<Results>> response) {
                List<Results> myHeroList = response.body();
                assert myHeroList != null;
                String[] oneHeroes = new String[myHeroList.size()];

                for (int i = 0; i < myHeroList.size(); i++) {
                    oneHeroes[i] = myHeroList.get(i).getName();
                }
                superListView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, oneHeroes));
            }

            @Override
            public void onFailure(@NonNull Call<List<Results>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}