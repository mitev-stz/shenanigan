package de.uhd.ifi.pokemonmanager.ui.adapter;

import java.security.cert.PKIXRevocationChecker;

public interface ForItemOptionsListener {
    void onCreatePokemonClick();
    void onShowPokemonDetailClick(int pos);
    void onDeleteClick(int pos);
}
