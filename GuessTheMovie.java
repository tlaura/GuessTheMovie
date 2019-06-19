/* This time you'll be completing a game where the player gets to guess the movie name given the number of letters in it 
(pretty much like hangman but with movies)!

The rules are simple, the computer randomly picks a movie title, and shows you how many letters it's made up of. 
Your goal is to try to figure out the movie by guessing one letter at a time.

If a letter is indeed in the title the computer will reveal its correct position in the word, if not, you lose a point. 
If you lose 10 points, game over!
*/


import java.util.*;
import java.io.File;

public class GuessTheMovie {
    public String randomValue;
    public String wordToGuess;
    public StringBuilder result;
    public ArrayList<Character> letterArray = new ArrayList<>();
    public ArrayList<Character> wrongLetterArray = new ArrayList<>();
    public int wrongGuesses = 0;
    public int numWrongGuessesLeft = 11;
    

    // choose a random movie from the list:
    public void chooseMovie() throws Exception{
        File file = new File("movies.txt");
        Scanner scanner = new Scanner(file);
        
        // make list of movies from the txt file:
        List<String> titles = new ArrayList<String>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            titles.add(line);  
        }
        
        // choose a random movie(string) from the list:
        Random randomGenerator = new Random();
        this.randomValue = titles.get(randomGenerator.nextInt(titles.size()));

        this.wordToGuess = randomValue.replaceAll("[a-zA-Z]", "_");
        System.out.println(wordToGuess);

        System.out.println();
        scanner.close();

    }

    // start reading the user's input and search for it in the title
    public void searchLetter() throws Exception { 

        // keep guessing until all _ are replaced for letters 
        while (!randomValue.equals(wordToGuess) && this.numWrongGuessesLeft > 1){

            System.out.println("Guess a letter: ");
            System.out.println("(10 wrong guesses allowed)");
            System.out.println();


            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                char letter = scanner.next().charAt(0);
                int index = randomValue.indexOf(letter);


                // check if letter is in movie title         
                if (index >= 0) {   
                    
                    this.result = new StringBuilder(wordToGuess);

                    if (letterArray.contains(letter)) {
                        wrongLetterArray.add(letter);
                        System.out.println();
                        System.out.println("You already guessed this letter");
                        System.out.println();
                    } else {
                        //add all the user input to an ArrayList
                        letterArray.add(letter);
                    }

                    for (int i = 0; i < randomValue.length(); i++) {
                        //if letter in the film 
                        if (randomValue.charAt(i) == letter) {
                            //set the input to the particular index
                            result.setCharAt(i, letter);
                            wordToGuess = result.toString();
                        }
                    }
                    System.out.println(wordToGuess);
                    System.out.println();  


                    if (hasWon(wordToGuess)) {
                        System.out.println();
                        System.out.println("You win!"); 
                        System.out.println("You have guessed " + randomValue + " correctly.");
                        
                    } else if (numWrongGuessesLeft == 1) {
                        System.out.println();
                        System.out.println("You lost, the movie was: " + randomValue);
                    } else {
                        System.out.println(wordToGuess);
                    }
                      
                } else {
                    // guess is wrong 
                    wrongGuesses++;
                    this.numWrongGuessesLeft--;
                    
                    wrongLetterArray.add(letter);

                    StringBuilder builder = new StringBuilder(wrongLetterArray.size());
                    for(Character wrongChar: wrongLetterArray) {
                        builder.append(wrongChar + " ");
                    }
                    String wrongLetters = builder.toString();
                    

                    System.out.println();
                    System.out.println("Incorrect letter");
                    System.out.println("You have (" + wrongGuesses +  ") wrong guesses so far: " + wrongLetters);
                    System.out.println();

                }
            }
            
            System.out.println();
            scanner.close();
        }
        
    }

    // to win string doesn't contain _
    public boolean hasWon(String completeString){
        return !completeString.contains("_");
    }



    public static void main (String[] args) throws Exception{
        GuessTheMovie movie =  new GuessTheMovie();
        // accessed in a static way (not through object):
        movie.chooseMovie();
        movie.searchLetter();
    }
}