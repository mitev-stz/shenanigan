package de.uhd.ifi.pokemonmanager.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;


import java.util.List;

import de.uhd.ifi.pokemonmanager.R;
import de.uhd.ifi.pokemonmanager.data.Pokemon;
import de.uhd.ifi.pokemonmanager.data.Trainer;
import de.uhd.ifi.pokemonmanager.data.Type;
import de.uhd.ifi.pokemonmanager.storage.SerialStorage;
import de.uhd.ifi.pokemonmanager.ui.adapter.ForItemOptionsListener;
import de.uhd.ifi.pokemonmanager.ui.adapter.PokemonAdapter;
import de.uhd.ifi.pokemonmanager.ui.util.RecyclerViewUtil;

public class MainActivity extends AppCompatActivity implements ForItemOptionsListener {
    public static final String DETAIL_POKEMON = "detail_pokemon";
    private static final SerialStorage STORAGE = SerialStorage.getInstance();
    private static boolean wasWiped = false;
    private List<Pokemon> data;
    private static final int SHOW_POKEMON_DETAIL = 20;

    private RecyclerView pokemonList;
    private PokemonAdapter pokemonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokemonList = findViewById(R.id.pokemonList);

        setupList();
    }



    private void setupList() {
        data = STORAGE.getAllPokemon();
        pokemonAdapter = new PokemonAdapter(this, data,this);

        final RecyclerView.LayoutManager manager = RecyclerViewUtil.createLayoutManager(this);

        pokemonList.setLayoutManager(manager);
        pokemonList.setAdapter(pokemonAdapter);
    }


    private void createSampleDataIfNecessary() {
        if (STORAGE.getAllPokemon().isEmpty()) {
            STORAGE.clear(this);

            Trainer t1 = new Trainer("Alisa", "Traurig");
            Trainer t2 = new Trainer("Petra", "Lustig");
            Pokemon p1 = new Pokemon("Shiggy", Type.WATER);
            Pokemon p2 = new Pokemon("Rettan", Type.POISON);
            Pokemon p3 = new Pokemon("Glurak", Type.FIRE);

            t1.addPokemon(p1);
            t1.addPokemon(p2);
            t2.addPokemon(p3);

            STORAGE.update(p1);
            STORAGE.update(p2);
            STORAGE.update(p3);
            STORAGE.update(t1);
            STORAGE.update(t2);
            STORAGE.saveAll(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // wipe storage initially
        if(!wasWiped) {
            STORAGE.clear(this);
            wasWiped = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();



        STORAGE.loadAll(this);
        createSampleDataIfNecessary();
        pokemonAdapter.refresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        STORAGE.saveAll(this);
    }


    @Override
    public void onCreatePokemonClick() {
        Pokemon pok = new Pokemon("Pokemon", Type.FIRE);
        STORAGE.update(pok);
        STORAGE.saveAll(this);
    }

    @Override
    public void onShowPokemonDetailClick(int pos) {
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("Pokemon", (Parcelable) data.get(pos));
        startActivityForResult(intent, SHOW_POKEMON_DETAIL);
    }

    @Override
    public void onDeleteClick(int pos) {
        STORAGE.remove(STORAGE.getAllPokemon().get(pos));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 20) {
            if (resultCode == 1) {
                Pokemon pok = STORAGE.getPokemonById(data.getExtras().getInt("ID"));
                if(!data.getExtras().getString("NewName").equals("")){
                    pok.setName(data.getExtras().getString("NewName"));
                }
                String type = data.getExtras().getString("NewType");
                if (type.equals(Type.FIRE.toString()))
                    pok.setType(Type.FIRE);
                if (type.equals(Type.WATER.toString()))
                    pok.setType(Type.WATER);
                if (type.equals(Type.POISON.toString()))
                    pok.setType(Type.POISON);
                STORAGE.update(pok);
                STORAGE.saveAll(this);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
