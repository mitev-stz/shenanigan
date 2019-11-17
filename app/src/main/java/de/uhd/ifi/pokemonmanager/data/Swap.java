package de.uhd.ifi.pokemonmanager.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.uhd.ifi.pokemonmanager.storage.SerialStorage;
import de.uhd.ifi.pokemonmanager.storage.StorageException;

public class Swap implements Parcelable, Serializable {
    public static final Creator<Swap> CREATOR = new Creator<Swap>() {
        @Override
        public Swap createFromParcel(Parcel parcel) {
            try {
                return new Swap(parcel);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Swap[] newArray(int size) {
            return new Swap[size];
        }
    };
    protected static final SerialStorage STORAGE = SerialStorage.getInstance();

    private static final String TAG = "Swap";
    private String id;
    protected Date date;
    protected int sourcePokemonId;
    protected int targetPokemonId;
    protected int sourceTrainerId;
    protected int targetTrainerId;


    public Swap() {
//        sourcePokemonId = sourcePokemon.getId();
//        targetPokemonId = targetPokemon.getId();
//        sourceTrainerId = sourcePokemon.getTrainer().getId();
//        targetTrainerId = targetPokemon.getTrainer().getId();
//        date = new Date(System.currentTimeMillis());
//        id = sourcePokemon.getName() + targetPokemon + date.toString();
    }

    protected Swap(final Parcel in) throws ParseException {
        sourcePokemonId = in.readInt();
        targetPokemonId = in.readInt();
        sourceTrainerId = in.readInt();
        targetTrainerId = in.readInt();
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd_HHmmss");
        date = time.parse(in.readString());
        id = in.readString();
    }

    public void execute(Pokemon sourcePokemon, Pokemon targetPokemon) {
        if (!sourcePokemon.isSwapAllow() || !targetPokemon.isSwapAllow()) {
            Log.e(TAG, String.format("No swap: Pokemons '%s' and '%s' are NOT both allowed to be swapped!%n", sourcePokemon.getName(), targetPokemon.getName()));
            return;
        }
        Trainer sourceTrainer = sourcePokemon.getTrainer();
        Trainer targetTrainer = targetPokemon.getTrainer();

        if (sourceTrainer == null ) {
            Log.e(TAG, String.format("No swap: source Trainer is null!%n"));
            return;
        }

        if (targetTrainer == null) {
            Log.e(TAG, String.format("No swap: target Trainer is null!%n"));
            return;
        }

        if (sourceTrainer.equals(targetTrainer)) {
            Log.e(TAG, String.format("No swap: Trainers '%s' == '%s' are identical!%n", sourceTrainer, targetTrainer));
            return;
        }

        this.sourcePokemonId = sourcePokemon.getId();
        this.targetPokemonId = targetPokemon.getId();
        this.sourceTrainerId = sourceTrainer.getId();
        this.targetTrainerId = targetTrainer.getId();
        this.date = new Date();
        this.id =  "" + System.currentTimeMillis();
        targetTrainer.addPokemon(sourcePokemon);
        sourceTrainer.addPokemon(targetPokemon);
        sourcePokemon.addSwap(this);
        targetPokemon.addSwap(this);
//        "Swap id: "+sourcePokemon.getName()+ " and " +targetPokemon.getName()+" swapped on "
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public Pokemon getSourcePokemon() {
        return STORAGE.getPokemonById(sourcePokemonId);
    }

    public Pokemon getTargetPokemon() {
        return STORAGE.getPokemonById(targetPokemonId);
    }

    public Trainer getSourceTrainer() {
        return STORAGE.getTrainerById(sourceTrainerId);
    }

    public Trainer getTargetTrainer() {
        return STORAGE.getTrainerById(targetTrainerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sourcePokemonId);
        dest.writeInt(targetPokemonId);
        dest.writeInt(sourceTrainerId);
        dest.writeInt(targetTrainerId);
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        dest.writeString(time);
        dest.writeString(id);
    }
}
