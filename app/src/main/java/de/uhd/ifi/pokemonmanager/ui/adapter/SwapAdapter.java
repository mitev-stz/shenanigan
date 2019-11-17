package de.uhd.ifi.pokemonmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.uhd.ifi.pokemonmanager.R;
import de.uhd.ifi.pokemonmanager.data.Pokemon;
import de.uhd.ifi.pokemonmanager.data.Swap;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class SwapAdapter extends Adapter<SwapHolder> {

    private LayoutInflater inflater;
    private Pokemon originalData1;


    public SwapAdapter(Context context,Pokemon swaps){
        this.originalData1 = swaps;
        this.inflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public SwapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = inflater.inflate(R.layout.swaps_listitem, parent, false);
        return new SwapHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SwapHolder holder, int position) {
        holder.setSwap(originalData1.getSwaps().get(position), originalData1);
    }

    @Override
    public int getItemCount() {
        return originalData1.getSwaps().size();
    }

}
class SwapHolder extends RecyclerView.ViewHolder {

    private final TextView swapId;
    private final TextView swapDate;


    public SwapHolder(@NonNull View itemView) {
        super(itemView);
        swapId = itemView.findViewById(R.id.swapID);
        swapDate = itemView.findViewById(R.id.swapDate);
        itemView.setTag(this);
    }
    public void setSwap(Swap swap, Pokemon origin) {
        if (swap != null) {
            long l = Long.parseLong(swap.getId());
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            Date res = new Date(l);
            swapDate.setText("Date: " + swap.getDate().toString());
            if (!swap.getSourcePokemon().equals(origin)) {
                swapId.setText("With: " + swap.getSourcePokemon().getName() + " " + swap.getSourcePokemon().getTrainer().toString());
            } else {
                swapId.setText("With: " + swap.getTargetPokemon().getName() + " " + swap.getTargetPokemon().getTrainer().toString());
            }
        }
    }
}
