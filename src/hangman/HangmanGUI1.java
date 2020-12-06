package hangman;

import hangman.images.Resource;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;

/**
 * Η κλάση για το GUI της κρεμάλας.
 */
public class HangmanGUI1 {

    private JFrame frame;
    private JButton newGame;
    private JPanel topPanel;
    private HangmanLogic logic;
    private JLabel hangman;
    private JLabel givenCharacters;
    private JLabel currentWordLabel;
    private JLabel letterLabel;
    private JTextField letterTextField;
    private JLabel result;
    private JPanel letterPanel;

    /**
     * Στον κατασκευαστή θα πρέπει να ορίζονται όλα τα γραφικά στοιχεία του κεντρικού παραθύρου της εφαρμογής.
     */
    public HangmanGUI1() {

        // Κεντρικό frame & panel
        frame = new JFrame("Κρεμάλα");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 270);

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        topPanel.setBackground(Color.BLACK);
        frame.add(topPanel, BorderLayout.PAGE_START);

        // Κουμπί για την έναρξη του παιχνιδιού.
        newGame = new JButton("Νέο παιχνίδι");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        topPanel.add(newGame);

        // Η κρεμάλα
        hangman = new JLabel();
        hangman.setIcon(new ImageIcon(Resource.getURL("1.gif")));
        frame.add(hangman, BorderLayout.LINE_START);

        // Το κεντρικό panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1));
        centerPanel.setBackground(Color.WHITE);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Label για την τρέχουσα λέξη
        currentWordLabel = new JLabel("Τρέχουσα λέξη: ");
        currentWordLabel.setVisible(false);
        centerPanel.add(currentWordLabel);

        // Label για τους χαρακτήρες που έχουν δωθεί
        givenCharacters = new JLabel("Γράμματα που έχουν δοθεί: ");
        givenCharacters.setVisible(false);
        centerPanel.add(givenCharacters);

        // Panel για το label εισόδου
        letterPanel = new JPanel();
        letterPanel.setOpaque(false);
        letterPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        letterPanel.setVisible(false);
        centerPanel.add(letterPanel);

        // Το label εισόδου
        letterLabel = new JLabel("Δώσε γράμμα:  ");
        letterPanel.add(letterLabel);
        letterTextField = new JTextField(1);
        letterTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                letterGiven();
            }
        });
        letterPanel.add(letterTextField);

        // Label για το αποτέλεσμα
        result = new JLabel();
        centerPanel.add(result);
        
        frame.setLocationRelativeTo(null);
    }

    public void start() {
        frame.setVisible(true);
    }

    /**
     * Μέθοδος που καλείται όταν δίνεται είσοδος από τον χρήστη. Θα πρέπει να ελέγχει αν έχει δωθεί ήδη ο χαρακτήρας
     * και να εμφανίζει το κατάλληλο μήνυμα. Επίσης, θα πρέπει να ελέγχει αν έχει ολοκληρωθεί το παιχνίδι και αν κέρδισε
     * η έχασε ο παίχτης.
     */
    private void letterGiven() {
        char c = letterTextField.getText().charAt(0);
        if (logic.alreadyGiven(c)) {
            JOptionPane.showMessageDialog(frame, "Έχεις ξαναδώσει το γράμμα " + c + ". ", "Προσοχή!", JOptionPane.ERROR_MESSAGE);
            letterTextField.setText("");
        } else {
            logic.addLetter(c);
        }
        letterTextField.setText("");
        updateScreen();
        
        if (logic.getCurrentLives() == 0) {            
            result.setText("Δυστυχώς έχασες. Η κρυμμένη λέξη ήταν \"" + logic.getSecretWord() + "\".");
            letterPanel.setVisible(false);
        } 
        if (logic.wordFound()) {
            result.setText("Συγχαρητήρια, βρήκες την κρυμμένη λέξη");
            letterPanel.setVisible(false);
        }        
    }

    /**
     * Μέθοδος που θα ανανεώνει το παράθυρο μετά την είσοδο του χρήστη.
     */
    private void updateScreen() {
        StringBuilder sb = new StringBuilder("Χαρακτήρες που έχουν δοθεί: ");
        Set<Character> charsGiven = logic.getCharsGiven();
        Iterator<Character> it = charsGiven.iterator();
        int size = charsGiven.size();
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                sb.append(it.next()).append(", ");
            }
            sb.append(it.next());
        }
        givenCharacters.setText(sb.toString());

        sb = new StringBuilder("Τρέχουσα λέξη: ");
        char[] currentWord = logic.getCurrentWord();
        for (char c : currentWord) {
            sb.append(c).append(" ");
        }
        currentWordLabel.setText(sb.toString());
        
        String imgName = (7-logic.getCurrentLives()) + ".gif";
        ImageIcon icon = new ImageIcon(Resource.getURL(imgName));
        hangman.setIcon(icon);
    }

    /**
     * Μέθοδος για την έναρξη του παιχνίδιου. Θα πρέπει να εμφανίζεται ένα παράθυρο διαλόγου στο οποίο θα ορίζουμε την
     * νέα λέξη.
     */
    private void newGame() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        Object[] obj = {"Δώσε την κρυφή λέξη:\n\n", passwordField};
        Object stringArray[] = {"OK", "Άκυρο"};
        if (JOptionPane.showOptionDialog(frame, obj, "Κρυφή λέξη", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.OK_OPTION) {            
            logic = new HangmanLogic(6);
            logic.setSecretWord(new String(passwordField.getPassword()));            
            updateScreen();
            letterPanel.setVisible(true);
            currentWordLabel.setVisible(true);
            givenCharacters.setVisible(true);
            result.setText("");
        }
    }
}
