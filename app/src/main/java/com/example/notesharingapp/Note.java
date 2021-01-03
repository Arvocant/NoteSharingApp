package com.example.notesharingapp;

import java.util.LinkedList;

public class Note {
    LinkedList<String> Titles = new LinkedList<>();;
    LinkedList<String> Bodies = new LinkedList<>();;
    public Note(LinkedList<String> titles, LinkedList<String> bodies){
        Titles = titles;
        Bodies = bodies;
    }
}
