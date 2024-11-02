package ru.mirea.pkmn.sizovaa;

import ru.mirea.pkmn.Card;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CardExport {

    public void saveCardToFile(Card card) {
        String fileName = card.getName() + ".crd";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(card);
            System.out.println("Card saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving card to file: " + e.getMessage());
        }
    }
}

