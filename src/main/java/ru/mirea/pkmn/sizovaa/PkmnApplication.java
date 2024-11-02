package ru.mirea.pkmn.sizovaa;

import com.fasterxml.jackson.databind.JsonNode;
import ru.mirea.pkmn.AttackSkill;
import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.sizovaa.web.http.PkmnHttpClient;
import ru.mirea.pkmn.sizovaa.web.jdbc.DatabaseServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PkmnApplication {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        /*CardImport ci = new CardImport();
        Card c = ci.Import("src/main/resources/my_card.txt");
        System.out.println(c.toString());

        CardExport exporter = new CardExport();
        exporter.saveCardToFile(c);

        CardImport importer = new CardImport();
        Card loadedCard = importer.loadCardFromFile("Cloyster.crd");

        if (loadedCard != null) {
            System.out.println("Loaded Card: " + loadedCard);
        } else {
            System.out.println("Failed to load the card.");
        }

        importer = new CardImport();
        loadedCard = importer.loadCardFromFile("Ninetales.crd");

        if (loadedCard != null) {
            System.out.println("Loaded Card: " + loadedCard);
        } else {
            System.out.println("Failed to load the card.");
        }*/

        CardImport ci = new CardImport();
        Card icard = ci.Import("src/main/resources/my_card.txt");

        PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();

        JsonNode card;
        try {
            card = pkmnHttpClient.getPokemonCard(icard.getName(), icard.getNumber());
            //card = pkmnHttpClient.getPokemonCard(icard.getEvolvesFrom().getName(), "29");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(((JsonNode) card).toPrettyString());

        System.out.println(card.findValues("attacks")
                .stream()
                .map(JsonNode::toPrettyString)
                .collect(Collectors.toSet()));
        if (card.has("data") && card.get("data").isArray()) {
            for (JsonNode crd : card.get("data")) {
                // Проверяем массив атак
                if (crd.has("attacks") && crd.get("attacks").isArray()) {
                    for (JsonNode attack : crd.get("attacks")) {
                        // Извлекаем поле "text"
                        String newDescription = attack.get("text").asText();
                        System.out.println(newDescription);
                        String targetName = attack.get("name").asText(); // Название атаки, которую нужно изменить
                        for (AttackSkill skill : icard.getSkills()) {
                            if (skill.getName().equals(targetName)) {
                                skill.setDescription(newDescription); // Изменяем описание
                                break; // Завершаем цикл после первого совпадения
                            }
                        }
                    }
                }
            }
        }
        System.out.println(icard);

        DatabaseServiceImpl dbs = new DatabaseServiceImpl();
        dbs.saveCardToDatabase(icard);
        Card dcard = dbs.getCardFromDatabase(icard.getName());
        System.out.println(dcard.toString());
    }

}

