package de.uhd.ifi.pokemonmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.uhd.ifi.pokemonmanager.R;
import de.uhd.ifi.pokemonmanager.data.Competition;
import de.uhd.ifi.pokemonmanager.data.Pokemon;
import de.uhd.ifi.pokemonmanager.data.Swap;

public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionHolder> {
    private LayoutInflater inflater;
    private Pokemon originalData1;
    public CompetitionAdapter(Context context, Pokemon swaps){
        this.originalData1 = swaps;
        this.inflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public CompetitionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = inflater.inflate(R.layout.swaps_listitem, parent, false);
        return new CompetitionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionHolder holder, int position) {
        holder.setCompetition(originalData1.getCompetitions().get(position), originalData1);
    }

    @Override
    public int getItemCount() {
        return originalData1.getCompetitions().size();
    }

}
class CompetitionHolder extends RecyclerView.ViewHolder {

    private final TextView swapId;
    private final TextView swapDate;


    public CompetitionHolder(@NonNull View itemView) {
        super(itemView);
        swapId = itemView.findViewById(R.id.swapID);
        swapDate = itemView.findViewById(R.id.swapDate);
        itemView.setTag(this);
    }
    public void setCompetition(Competition compet, Pokemon origin){

        if(compet!=null){
            swapDate.setText(compet.getDate().toString());
            if(!compet.getWinner().equals(origin)){
                swapId.setText("Competition lost against: " + compet.getWinner().getName() + " " + compet.getWinner().getTrainer().toString());
            } else {
                swapId.setText("Competition lost against: " + compet.getLoser().getName() + " " + compet.getLoser().getTrainer().toString());
            }

        }
    }

}
