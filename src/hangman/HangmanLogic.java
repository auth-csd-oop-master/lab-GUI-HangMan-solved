package hangman;

import java.util.Set;
import java.util.TreeSet;

/**
 * Αυτή η κλάση αναπαριστά τη λογική του παιχνιδιού.
 */
public class HangmanLogic {

    private int startingLives;
    private int currentLives;
    private Set<Character> charsGiven;
    private String secretWord;
    private char[] currentWord;
    private boolean wordFound;

    public HangmanLogic(int lives) {
        startingLives = lives;
        currentLives = lives;
        charsGiven = new TreeSet<>();
        secretWord = null;
        wordFound = false;        
    }

    public int getStartingLives() {
        return startingLives;
    }

    /**
     * Αυτή η μέθοδος ορίζει την μυστική λέξη.
     * @param word Η μυστική λέξη.
     */
    public void setSecretWord(String word) {
        secretWord = word;
        currentWord = new char[secretWord.length()];
        for (int i=0; i<currentWord.length; i++) {
            currentWord[i] = '_';
        }
    }

    /**
     * Αυτή η μέθοδος ελέγχει αν ο χαρακτήρας έχει ήδη δωθεί.
     * @param c ένας χαρακτήρας
     * @return True αν ο χαρακτήρας έχει δωθεί. False, σε όλες τις άλλες περιπτώσεις.
     */
    public boolean alreadyGiven(char c) {
        if (charsGiven.contains(c)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Προσθήκη χαρακτήρα στην λέξη. Αυτή η μέθοδος θα πρέπει να ελέγχει αν ο χαρακτήρας υπάρχει στη λέξη και να
     * ανανεώνει τις αντίστοιχες μεταβλητές.
     * @param c Ένας χαρακτήρας
     */
    public void addLetter(char c) {
        charsGiven.add(c);
        boolean found = false;
        int remainingLetters = 0;
        for (int i = 0; i < secretWord.length(); i++) {
            if (c == secretWord.charAt(i)) {
                currentWord[i] = c;
                found = true;
            }
            if (currentWord[i] == '_') {
                remainingLetters++;
            }
        }
        if (!found) {
            currentLives--;
        }
        if (remainingLetters == 0) {
            wordFound = true;
        }
    }        

    public int getCurrentLives() {
        return currentLives;
    }
    
    public boolean wordFound() {
        return wordFound;
    }
    
    public Set<Character> getCharsGiven() {
        return charsGiven;
    }
    
    public char[] getCurrentWord() {
        return currentWord;
    }
    
    public String getSecretWord() {
        return secretWord;
    }
}
