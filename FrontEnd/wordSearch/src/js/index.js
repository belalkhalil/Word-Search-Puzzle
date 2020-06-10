import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css' // Import precompiled Bootstrap css
import '@fortawesome/fontawesome-free/css/all.css'

import { Grid } from "./Grid";

const generateGridBtn = document.querySelector("#generate-grid");
const submitWordBtn = document.querySelector("#submit-Word");
const playBtn = document.querySelector("#got");
let userListOfWords = [];
let gotListOFWords = [];


// play with pre-defined list
playBtn.addEventListener("click", async () => {
    const grid = new Grid();
    clearBox('words-area');
    // here i found it convinient to create a hard coded list
    gotListOFWords = ["Robb", "Stannis", "Joffrey", "Khal Drogo"
        , "Theon", "Ramsey", "Ygritte",
        "Tormund", "Jon Snow", "Hodor", "Tyrion", "Missandei",
        "Littlefinger", "Daenerys", "Arya", "Olenna", "Cersei"
    ];
    gotListOFWords = await trimUserInput(gotListOFWords);
    // git size from user
    const gridSize = 15;
    // fitching gid to backend
    let result = await fetchGridinfo(gridSize, gotListOFWords);
    grid.words = gotListOFWords;
    grid.renderGrid(gridSize, result);
    printWordList(grid.words);



});

//fill up userListOfWords with user inout words
submitWordBtn.addEventListener('click', async () => {
    let inputWord = document.querySelector("#add-word").value;
    userListOfWords.push(inputWord);
    document.getElementById('add-word').value = '';


});

// subimt by pressing enter on input field
document.getElementById("add-word").addEventListener('keydown', async (e) => {
    if (e.keyCode == 13) {
        let inputWord = document.querySelector("#add-word").value;
        userListOfWords.push(inputWord);
        document.getElementById('add-word').value = '';
    }



});


generateGridBtn.addEventListener("click", async () => {
    // create new grid each click
    const grid = new Grid();
    //clear List of Words html section on each click
    clearBox('words-area');
    // convert user input words into a Uppercase without white spaces strings
    userListOfWords = await trimUserInput(userListOfWords);
    // git size from user
    const gridSize = document.querySelector("#grid-size").value;
    // fitching gid to backend
    let result = await fetchGridinfo(gridSize, userListOfWords);
    // print grid on screen
    grid.renderGrid(gridSize, result);

    //fill up grid.words
    grid.words = userListOfWords;

    //print out List of Words on html section
    printWordList(grid.words);


    //reset user word list on each click
    userListOfWords = [];


});

// fetching endpoint using fetch api
async function fetchGridinfo(gridSize, listOfWords) {

    // ${} allows us to assign string in place
    let response = await fetch(`./wordGrid?gridSize=${gridSize}&wordList=${listOfWords}`);
    let result = await response.text();
    return result.split(" ");

}
// convert user input words into a Uppercase without white spaces strings
async function trimUserInput(listOfWords) {
    for (let i = 0; i < listOfWords.length; i++) {
        listOfWords[i] = listOfWords[i].split(' ').join('').toUpperCase();
    }
    return listOfWords;
}

// print word list on html section with some nice spacing
async function printWordList(wordList) {

    for (let i = 0; i < wordList.length; i++) {
        if (i % 5 == 0 && i > 1) {
            let br = document.createElement("br");
            wordListSection.appendChild(br);
        }
        let word = wordList[i] + ", ";
        let wordListNode = document.createTextNode(word);
        let wordListSection = document.querySelector(".word-list");
        wordListSection.appendChild(wordListNode);
    }

}

//clear word list section
function clearBox(elementID) {
    console.log("called");
    document.getElementById(elementID).innerHTML = "";
}

