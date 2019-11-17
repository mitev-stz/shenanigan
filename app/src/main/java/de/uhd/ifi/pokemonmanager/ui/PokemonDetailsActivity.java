package de.uhd.ifi.pokemonmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.uhd.ifi.pokemonmanager.R;
import de.uhd.ifi.pokemonmanager.data.Pokemon;
import de.uhd.ifi.pokemonmanager.data.Swap;
import de.uhd.ifi.pokemonmanager.data.Type;
import de.uhd.ifi.pokemonmanager.storage.SerialStorage;
import de.uhd.ifi.pokemonmanager.ui.adapter.CompetitionAdapter;
import de.uhd.ifi.pokemonmanager.ui.adapter.SwapAdapter;
import de.uhd.ifi.pokemonmanager.ui.util.RecyclerViewUtil;

public class PokemonDetailsActivity extends AppCompatActivity {

    private static final SerialStorage STORAGE = SerialStorage.getInstance();
    private RecyclerView swapsList;
    private RecyclerView competitionsList;
    private SwapAdapter swapAdapter;
    private EditText pokemonName;
    private EditText pokemonType;
    private TextView pokemonNumber;
    private TextView pokemonTrainer;
    private CompetitionAdapter competitionAdapter;
    Pokemon pokemon;


    private void disableEditText(EditText editText){
        editText.setEnabled(false);

    }

    private void enableEditText(EditText editText){
        editText.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pokemon_details);
        pokemon = getIntent().getExtras().getParcelable("Pokemon");
        swapsList = findViewById(R.id.swapsList);
        competitionsList = findViewById(R.id.competitionsList);
        pokemonName = findViewById(R.id.pokemonName);
        pokemonType = findViewById(R.id.pokemonType);
        pokemonNumber = findViewById(R.id.pokemonNumber);
        pokemonTrainer = findViewById(R.id.pokemonTrainer);

        swapAdapter = new SwapAdapter(this, pokemon);
        competitionAdapter = new CompetitionAdapter(this, pokemon);
        final RecyclerView.LayoutManager manager = RecyclerViewUtil.createLayoutManager(this);
        final RecyclerView.LayoutManager manager1 = RecyclerViewUtil.createLayoutManager(this);
        swapsList.setLayoutManager(manager);
        swapsList.setAdapter(swapAdapter);

        competitionsList.setLayoutManager(manager1);
        competitionsList.setAdapter(competitionAdapter);

        pokemonName.setText(pokemon.getName());
        pokemonType.setText(pokemon.getType().toString());
        pokemonNumber.setText(""+ pokemon.getId());
        pokemonTrainer.setText(pokemon.getTrainer().toString());
        disableEditText(pokemonName);
        disableEditText(pokemonType);

        Button editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditText(pokemonName);
                enableEditText(pokemonType);
            }
        });

        Button closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(0,intent);
                finish();
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ID",pokemon.getId());
                intent.putExtra("NewName",pokemonName.getText().toString());
                intent.putExtra("NewType",pokemonType.getText().toString());
                setResult(1,intent);
                finish();

            }
        });
    }
}
