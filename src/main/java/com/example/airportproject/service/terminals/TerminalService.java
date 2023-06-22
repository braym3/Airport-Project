package com.example.airportproject.service.terminals;

import com.example.airportproject.model.Terminal;

import java.util.List;
import java.util.UUID;

/**
 * Defines the contract for managing terminals.
 * It provides operations to fetch terminals, add a new terminals, remove terminals, and update a terminal.
 */
public interface TerminalService {

    /**
     * Creates a new record in the database with the data from a Terminal object
     * @param terminal The Terminal to persist
     * @return The created Terminal
     */
    Terminal createTerminal(Terminal terminal);

    /**
     * Returns the Terminal with the given id
     * @param id The id of the terminal to find
     * @return Terminal object with the matching id
     */
    Terminal get(UUID id);

    /**
     * Returns a List of all Terminals in the database
     * @return List of all Terminal objects found
     */
    List<Terminal> getAll();

    /**
     * Removes the Terminal with the corresponding id from the database
     * @param id The id used to identify the Terminal to delete
     * @return The deleted Terminal object
     */
    Terminal remove(UUID id);

    /**
     * Updates the number of the terminal in the database that corresponds to the provided id
     * @param id The id used to identify the Terminal to update
     * @param number The new number of the terminal
     * @return The updated Terminal object
     */
    Terminal updateNumber(UUID id, int number);
}