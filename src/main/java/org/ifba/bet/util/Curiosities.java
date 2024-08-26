package org.ifba.bet.util;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Curiosities {

    private static final List<String> CURIOSITIES;

    static {
        List<String> tempList = new ArrayList<>();
        
        tempList.add("As formigas nunca dormem.");
        tempList.add("O coração de um camarão está em sua cabeça.");
        tempList.add("O mel nunca estraga.");
        tempList.add("As bananas são bagas.");
        tempList.add("A lua está se afastando da Terra.");
        tempList.add("Tubarões não têm ossos.");
        tempList.add("Coalas têm impressões digitais.");
        tempList.add("Algumas águas-vivas são imortais.");
        tempList.add("Baleias azuis são os maiores animais.");
        tempList.add("Relâmpagos criam calor mais quente que o sol.");
        tempList.add("Abelhas reconhecem rostos humanos.");
        tempList.add("Lontras seguram mãos ao dormir.");
        tempList.add("Flamingos nascem com penas cinzas.");
        tempList.add("Computadores já pesaram 27 toneladas.");
        tempList.add("Existem mais estrelas do que grãos de areia.");
        tempList.add("Nariz de gato tem impressão digital única.");
        tempList.add("Baratas podem viver sem cabeça.");
        tempList.add("Sangue de caranguejos é azul.");
        tempList.add("Mais galáxias do que árvores na Terra.");
        tempList.add("Olho de avestruz maior que o cérebro.");
        tempList.add("Falcão-peregrino é o animal mais rápido.");
        tempList.add("Tigres siberianos podem medir 3 metros.");
        tempList.add("Som viaja mais rápido na água.");
        tempList.add("Girafas têm 7 vértebras no pescoço.");
        tempList.add("Elefantes têm memória excelente.");

        CURIOSITIES = Collections.unmodifiableList(tempList);
    }

    public static String getCuriosity(int index) {
        if (index < 0 || index >= CURIOSITIES.size()) {
            throw new IndexOutOfBoundsException("Índice fora dos limites: " + index);
        }
        return CURIOSITIES.get(index);
    }
}
