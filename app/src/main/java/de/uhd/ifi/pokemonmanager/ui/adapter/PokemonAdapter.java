package de.uhd.ifi.pokemonmanager.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.uhd.ifi.pokemonmanager.R;
import de.uhd.ifi.pokemonmanager.data.Pokemon;
import de.uhd.ifi.pokemonmanager.data.Type;
import de.uhd.ifi.pokemonmanager.ui.PokemonDetailsActivity;

import static java.util.stream.Collectors.toList;

public class PokemonAdapter extends Adapter<PokemonHolder> {

    private LayoutInflater inflater;
    private List<Pokemon> originalData;
    private List<Pokemon> filteredData;
    private Context context;
    private ForItemOptionsListener listener;


    public PokemonAdapter(final Context context, final List<Pokemon> originalData, ForItemOptionsListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.originalData = originalData;
        this.filteredData = originalData.stream().filter(Objects::nonNull).collect(toList());
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = inflater.inflate(R.layout.listitem_pokemon, parent, false);
        return new PokemonHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonHolder holder, int position) {
        holder.setPokemon(filteredData.get(position));

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context, holder.buttonViewOption);
                menu.inflate(R.menu.pokemon_options);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.create_pokemon:
                                holder.onCreatePokemonClick();
                                refresh();
                                break;
                            case R.id.delete_pokemon:
                                holder.onDeleteClick(holder.getAdapterPosition());
                                refresh();
                                break;
                            case R.id.show_pokemon_deteils:
                                holder.onShowPokemonDetailClick(holder.getAdapterPosition());
                                refresh();
                                break;
                        }
                        return false;
                    }

                });

                menu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public void refresh() {
        this.filteredData = originalData.stream().filter(Objects::nonNull).collect(toList());
        notifyDataSetChanged();
    }






}

class PokemonHolder extends ViewHolder implements ForItemOptionsListener{

    private final TextView pokemonName;
    private final TextView pokemonType;
    private final TextView pokemonId;
    private final TextView trainerText;
    private final TextView pokemonSwaps;
    private final TextView pokemonCompetitions;
    public TextView buttonViewOption;
    private ForItemOptionsListener listener;


    PokemonHolder(@NonNull View itemView, ForItemOptionsListener listener) {
        super(itemView);
        this.listener = listener;
        buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);

        pokemonName = itemView.findViewById(R.id.pokemonName);
        pokemonType = itemView.findViewById(R.id.pokemonType);
        pokemonId = itemView.findViewById(R.id.pokemonId);
        trainerText = itemView.findViewById(R.id.trainerText);
        pokemonSwaps = itemView.findViewById(R.id.pokemonSwaps);
        pokemonCompetitions = itemView.findViewById(R.id.pokemonCompetitions);
        itemView.setTag(this);
    }

    void setPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            this.pokemonName.setText(pokemon.getName());
            this.pokemonType.setText(pokemon.getType().toString());
            this.pokemonId.setText(String.format(Locale.getDefault(), "# %d", pokemon.getId()));
            this.trainerText.setText(pokemon.getTrainer().toString());
            this.pokemonSwaps.setText(String.format(Locale.getDefault(), "Swaps: %d", pokemon.getSwaps().size()));
            this.pokemonCompetitions.setText(String.format(Locale.getDefault(), "Competitions: %d", pokemon.getCompetitions().size()));

        }
    }




    @Override
    public void onCreatePokemonClick() {
        listener.onCreatePokemonClick();
    }

    @Override
    public void onShowPokemonDetailClick(int pos) {
        listener.onShowPokemonDetailClick(getAdapterPosition());
    }

    @Override
    public void onDeleteClick(int pos) {
        listener.onDeleteClick(getAdapterPosition());
    }
};
