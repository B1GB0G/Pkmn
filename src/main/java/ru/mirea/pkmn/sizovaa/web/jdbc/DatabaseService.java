package ru.mirea.pkmn.sizovaa.web.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.sql.SQLException;
import java.util.UUID;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName, UUID... id) throws SQLException, JsonProcessingException;

    Student getStudentFromDatabase(String studentName) throws SQLException;

    Student getStudentFromDatabase(UUID id) throws SQLException;

    void saveCardToDatabase(Card card);

    void createPokemonOwner(Student owner);
}
