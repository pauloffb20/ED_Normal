package com.company.Classes;

import com.company.Estruturas.*;
import com.company.Classes.Menu;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    /**
     * Comecar aplicacao
     * @param args
     * @throws EmptyException
     * @throws IOException
     * @throws NoComparableException
     * @throws ParseException
     * @throws InvalidIndexException
     * @throws NotFoundException
     * @throws EmptyCollectionException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws EmptyException, IOException, NoComparableException, ParseException, InvalidIndexException, NotFoundException, EmptyCollectionException, org.json.simple.parser.ParseException {
        Menu menu = new Menu();
        menu.run();
    }
}
