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
import de.uhd.ifi.pokemonmanager.data.Swap;

public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionHolder> {
    private LayoutInflater inflater;
    private List<Competition> originalData1;
    public CompetitionAdapter(Context context, List<Competition> swaps){
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
        holder.setCompetition(originalData1.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
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
    public void setCompetition(Competition compet){
        if(compet!=null){
            swapId.setText(compet.getId());
            swapDate.setText(compet.getDate().toString());
        }
    }

}
