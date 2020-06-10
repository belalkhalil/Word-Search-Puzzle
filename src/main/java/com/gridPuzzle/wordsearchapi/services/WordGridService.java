package com.gridPuzzle.wordsearchapi.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// each request needs to get a separate instance
@Service
public class WordGridService {

        private enum Direction{
            HORIZONTAL,
            VERTICAL,
            DIAGONAL,
            HORIZONTAL_Inverse,
            VERTICAL_Inverse,
            DIAGONAL_Inverse
        }
        // inner class to save all possible coordinates
        private class Coordinate {
            int x;
            int y;
            Coordinate(int x, int y){
                this.x =x;
                this.y=y;
            }
        }



        public void display_grid(char[][] content){
            int gridSze = content[0].length;
            for(int i =0; i< gridSze; i++){
                for(int j =0; j< gridSze; j++){
                    System.out.print(content[i][j] + " ");

                }
                System.out.println();
            }
        }


        public char[][] generateGrid(int gridSize, List<String> words){

             List<Coordinate> coordinates = new ArrayList<Coordinate>();
            char [][] content= new char[gridSize][gridSize];

            for(int i =0; i< gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    content[i][j] = '_';
                    if(j ==0){continue;}
                    coordinates.add(new Coordinate(i,j));

                }
            }




            for(String word: words){
                //shuffle coordinates list
                Collections.shuffle(coordinates);

                for (Coordinate coordinate: coordinates) {
                    int x = coordinate.x;
                    int y = coordinate.y;

                    // get random direct
                    Direction selectedDirection = getDirection(content,word, coordinate);
                    if(selectedDirection != null){
                        switch (selectedDirection) {

                            case VERTICAL:
                                for (char c: word.toCharArray()){
                                    content[x++][y] = c;
                                }
                                break;
                            case HORIZONTAL:
                                for (char c: word.toCharArray()){
                                    content[x][y++] = c;
                                }
                                break;
                            case DIAGONAL:
                                for (char c: word.toCharArray()){
                                    content[x++][y++] = c;
                                }
                                break;

                            case VERTICAL_Inverse:
                                for (char c: word.toCharArray()){
                                    content[x--][y] = c;
                                }
                                break;
                            case HORIZONTAL_Inverse:
                                for (char c: word.toCharArray()){
                                    content[x][y--] = c;
                                }
                                break;
                            case DIAGONAL_Inverse:
                                for (char c: word.toCharArray()){
                                    content[x--][y--] = c;
                                }
                                break;
                        }
                        break;
                    }
                }

            }
            randomFillGrid(content);
            return content;
        }



        // returns a random direction that word fits in or null
        private Direction getDirection(char[][] content, String word, Coordinate coordinate){
            List<Direction> directions = Arrays.asList(Direction.values());
            // shuffle list with random directions
            Collections.shuffle(directions);
            for (Direction direction: directions
            ) {
                if (doesFit(content,word, coordinate, direction)){
                    return direction;
                }
            }
            return null;
        }

        // checks if word fits at specific direction and coordinate
        private boolean doesFit(char[][] content, String word, Coordinate coordinate, Direction direction){
            int gridSze = content[0].length;
            switch (direction) {

                case HORIZONTAL:
                    if ( coordinate.y+ word.length() > gridSze) return  false;
                    for (int i =0; i< word.length(); i++){
                        char letter = content[coordinate.x][coordinate.y +i];
                        if(letter != '_' && letter != word.charAt(i)) return false;
                    }
                    break;
                case VERTICAL:
                    if ( coordinate.x+ word.length() > gridSze) return  false;
                    for (int i =0; i< word.length(); i++){
                        char letter = content[coordinate.x +i][coordinate.y];
                        if(letter != '_' && letter != word.charAt(i)) return false;
                    }
                    break;

                case DIAGONAL:
                    if ( coordinate.x+ word.length() > gridSze || coordinate.y+ word.length() > gridSze) return false;
                    for (int i =0; i< word.length(); i++){
                        char letter = content[coordinate.x +i][coordinate.y +i];
                        if(letter != '_' && letter != word.charAt(i)) return false;
                    }
                    break;

                case HORIZONTAL_Inverse:
                    if ( coordinate.y< word.length()) return  false;
                    for (int i =0; i< word.length(); i++){
                        char letter = content[coordinate.x][coordinate.y -i];
                        if(letter != '_' && letter != word.charAt(i)) return false;
                    }
                    break;
                case VERTICAL_Inverse:
                    if ( coordinate.x< word.length()) return  false;
                    for (int i =0; i< word.length(); i++){
                        char letter =content[coordinate.x -i][coordinate.y];
                        if(letter != '_' && letter != word.charAt(i)) return false;
                    }
                    break;

                case DIAGONAL_Inverse:
                    if ( coordinate.x< word.length() || coordinate.y< word.length() ) return false;
                    for (int i =0; i< word.length(); i++){
                        char letter =content[coordinate.x -i][coordinate.y -i];
                        if(letter != '_' && letter != word.charAt(i)) return false;
                    }
                    break;

            }

            return true;
        }

        private void randomFillGrid(char[][] content){
            String allCapLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            for (int i =0; i< content.length;i++){
                for (int j =0; j< content.length;j++){
                    if(content[i][j] == '_'){
                        int randomIndex = ThreadLocalRandom.current().nextInt(0,allCapLetters.length());
                        content[i][j]= allCapLetters.charAt(randomIndex);
                    }

                }
            }
        }




}
