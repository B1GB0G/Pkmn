package ru.mirea.pkmn.sizovaa;

import ru.mirea.pkmn.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardImport {
    public Card Import(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            Card newCard;
            String line;
            PokemonStage pokemonStage = PokemonStage.valueOf(br.readLine().trim());
            String name = br.readLine().trim();
            int hp = Integer.parseInt(br.readLine().trim());
            EnergyType pokemonType = EnergyType.valueOf(br.readLine().trim());
            Card evolvesFrom = null;
            if (!br.readLine().trim().equals("-")) {
                    CardImport ci = new CardImport();
                    evolvesFrom = ci.Import("src/main/resources/evolves_from.txt");
            }
            List<AttackSkill> skills = new ArrayList<>();
            for (String i:br.readLine().split(",")) {
                String[] parts = i.split("/");
                skills.add(new AttackSkill(parts[1], parts[0], Integer.parseInt(parts[2])));
            }
            line = br.readLine().trim();
            EnergyType weaknessType = null;
            if (!line.equals("-")) {
                weaknessType = EnergyType.valueOf(line);
            }
            line = br.readLine().trim();
            EnergyType resistanceType = null;
            if (!line.equals("-")) {
                resistanceType = EnergyType.valueOf(line);
            }
            String retreatCost = br.readLine().trim();
            String gameSet = br.readLine().trim();
            char regulationMark = br.readLine().charAt(0);
            String[] parts = br.readLine().trim().split("/");
            Student pokemonOwner;
            if (parts.length == 4)
                pokemonOwner = new Student(parts[1], parts[0], parts[2], parts[3]);
            else
                pokemonOwner = null;
            String number = br.readLine().trim();
            newCard = new Card(pokemonStage, name, hp, pokemonType, evolvesFrom, skills, weaknessType, resistanceType, retreatCost, gameSet, regulationMark, pokemonOwner, number);
            return newCard;
        } catch (IOException e) {
            System.err.println("Error importing card from file: " + e.getMessage());
            return null;
        }
    }

    public Card loadCardFromFile(String fileName) {
        Card card = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            card = (Card) in.readObject();
            System.out.println("Card loaded from " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading card from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Card class not found: " + e.getMessage());
        }
        return card;
    }

}
