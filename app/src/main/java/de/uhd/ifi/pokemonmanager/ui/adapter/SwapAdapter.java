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
import de.uhd.ifi.pokemonmanager.data.Swap;

public class SwapAdapter extends RecyclerView.Adapter<SwapHolder> {

    private LayoutInflater inflater;
    private List<Swap> originalData1;
    public SwapAdapter(Context context,List<Swap> swaps){
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
        holder.setSwap(originalData1.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
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
    public void setSwap(Swap swap){
        if(swap!=null){
            swapId.setText(swap.getId());
            swapDate.setText(swap.getDate().toString());
        }
    }
}
