package com.gridPuzzle.wordsearchapi.controlles;

import com.gridPuzzle.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;


    @GetMapping("/wordGrid")
    @CrossOrigin(origins = "http://localhost:1234")
    public String CreateWordGrid(@RequestParam int gridSize, @RequestParam String[] wordList){
        List<String> words = Arrays.asList( wordList);
        //words = wordGridService.toUperCase(words);
        char[][] grid =  wordGridService.generateGrid(gridSize,words);
        String gridToString = "";

        int gridSze = grid[0].length;
        for(int i =0; i< gridSze; i++){
            for(int j =0; j< gridSze; j++){
                gridToString+= grid[i][j] + " ";

            }
            gridToString+= "\r\n";
        }
        System.out.println(gridToString);
        return gridToString;
    }
}
