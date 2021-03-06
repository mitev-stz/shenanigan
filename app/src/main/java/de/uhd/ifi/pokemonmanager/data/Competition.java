package de.uhd.ifi.pokemonmanager.data;

import android.os.Parcel;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Competition extends Swap {
    public static final Creator<Competition> CREATOR = new Creator<Competition>() {
        @Override
        public Competition createFromParcel(Parcel parcel) {
            try {
                return new Competition(parcel);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Competition[] newArray(int size) {
            return new Competition[size];
        }
    };

    private static final String TAG = "Competition";
    private int winnerId;
    private int loserId;

    public Competition() {
        super();
//        super(sourcePokemon,targetPokemon);
    }

    private Competition(final Parcel in) throws ParseException {
        super(in);
    }
    @Override
    public void execute(Pokemon sourcePokemon, Pokemon targetPokemon) {
        if (sourcePokemon.getTrainer().equals(targetPokemon.getTrainer())) {
            Log.e(TAG, String.format("No competition: Trainers '%s' == '%s' are identical!%n", sourcePokemon.getTrainer(), targetPokemon.getTrainer()));
            return;
        }
        Trainer sourceTrainer = sourcePokemon.getTrainer();
        Trainer targetTrainer = targetPokemon.getTrainer();

        date = new Date();
        this.sourcePokemonId = sourcePokemon.getId();
        this.targetPokemonId = targetPokemon.getId();
        sourceTrainerId = sourceTrainer.getId();
        targetTrainerId = targetTrainer.getId();
        double score1 = (sourcePokemon.getType().ordinal() + 1) * Math.random();
        double score2 = (targetPokemon.getType().ordinal() + 1) * Math.random();
        Log.i(TAG, String.format(Locale.getDefault(),"Pokemon '%s' has score: %f%n", sourcePokemon, score1));
        Log.i(TAG, String.format(Locale.getDefault(),"Pokemon '%s' has score: %f%n", targetPokemon, score2));
        if (score1 > score2) {
            sourcePokemon.getTrainer().addPokemon(targetPokemon);
            setWinner(sourcePokemon);
            setLoser(targetPokemon);
            Log.i(TAG, String.format("Pokemon '%s' wins!%n", sourcePokemon));
        } else if (score1 < score2) {
            targetPokemon.getTrainer().addPokemon(sourcePokemon);
            setWinner(targetPokemon);
            setLoser(sourcePokemon);
            Log.i(TAG, String.format("Pokemon '%s' wins!%n", targetPokemon));
        } else {
            Log.i(TAG, String.format("Pokemon '%s' and '%s' both have the same score, there is no winner or loser!", sourcePokemon, targetPokemon));
        }
        sourcePokemon.addCompetition(this);
        targetPokemon.addCompetition(this);
    }

    public Pokemon getWinner() {
        return STORAGE.getPokemonById(winnerId);
    }

    private void setWinner(Pokemon winner) {
        this.winnerId = winner.getId();
    }

    public Pokemon getLoser() {
        return STORAGE.getPokemonById(loserId);
    }

    private void setLoser(Pokemon loser) {
        this.loserId = loser.getId();
    }

    public void writeToParcel(final Parcel dest, int flags) {
        dest.writeInt(sourcePokemonId);
        dest.writeInt(targetPokemonId);
        dest.writeInt(sourceTrainerId);
        dest.writeInt(targetTrainerId);
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        dest.writeString(time);
    }
}
