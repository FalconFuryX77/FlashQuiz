import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class FlashQuizApp {
    private JFrame mainFrame;
    private File flashcardFile;
    
    public FlashQuizApp() {
        try {
            flashcardFile = new File("src/Flashcards.txt");
            if(!flashcardFile.exists()) {
                flashcardFile.createNewFile();
            }
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Error creating default file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    initUI();
    }
    
    private void initUI() {
        mainFrame = new JFrame("FlashQuiz");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel welcomeLabel = new JLabel("Welcome to FlashQuiz");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Presented by Praneel Paliwal and Meet Maheshwari");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Dimension buttonSize = new Dimension(250, 60);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        
        JButton[] buttons = {
            createStyledButton("Flashcard Creation Mode", buttonSize, buttonFont),//0
            createStyledButton("Revision Mode", buttonSize, buttonFont),//1
            createStyledButton("Quiz Mode", buttonSize, buttonFont)//2
        };
        
        for (JButton button : buttons) {
            button.setHorizontalAlignment(SwingConstants.CENTER); //by default also center
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(20));
        }
        
        mainPanel.add(Box.createVerticalStrut(50));// height from top of flashquiz to the welcome label
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(20));// height btw welcome label and secondary label
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(80));// height btw button and slabel
        mainPanel.add(buttonPanel);
        
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
        buttons[0].addActionListener(e -> new FlashcardCreationModeFrame(flashcardFile));//AC0
        buttons[1].addActionListener(e -> new RevisionModeFrame(flashcardFile));//AC1
        buttons[2].addActionListener(e -> new QuizModeFrame(flashcardFile));//AC2
        
        mainFrame.setLocationRelativeTo(null);//opens application frame in centre
        mainFrame.setVisible(true);//makes app visible
    }
    
    private JButton createStyledButton(String text, Dimension size, Font font) { //button creation
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlashQuizApp()); //opens flashquiz app
    }
}

class Flashcard {
    private String question;
    private String answer;
    
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}

class FlashcardCreationModeFrame extends JFrame {
    private File flashcardFile;//flashcard file sent to fxn during button click
    private java.util.List<Flashcard> flashcards; //list variable of type flashcards
    private DefaultListModel<String> listModel; //list storing actual questions to be displayed
    private JList<String> flashcardList;//makes the list a visible scrollable list in the ui
    
    public FlashcardCreationModeFrame(File file) {
        super("Flashcard Creation Mode");
        this.flashcardFile = file;
        flashcards = new ArrayList<>();
        loadFlashcards();
        
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Flashcard Creation Mode");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));//title padding and panel
        titlePanel.add(titleLabel);
        
        listModel = new DefaultListModel<>(); // listing the flashcards
        for(Flashcard fc : flashcards) {
            listModel.addElement(fc.getQuestion()); //using getter method to get questions from flashcard file
        }
        
        flashcardList = new JList<>(listModel);//flashcard display part (next 3 lines)
        flashcardList.setFont(new Font("Arial", Font.PLAIN, 16));
        flashcardList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(flashcardList); //making it a scrollable list
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        JButton newCardButton = createStyledButton("New Flashcard", buttonSize, buttonFont);//button creation
        JButton deleteCardButton = createStyledButton("Delete Flashcard", buttonSize, buttonFont);
        JButton viewEditButton = createStyledButton("View/Edit Answer", buttonSize, buttonFont);
        JButton importButton = createStyledButton("Import File", buttonSize, buttonFont);
        //creation of button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        //basically a horizontal layout that adjusts components-buttons according height and width of the whole frame
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));//basic border btw buttons
        buttonPanel.add(newCardButton); //adding buttons to the buttonpanel
        buttonPanel.add(deleteCardButton);
        buttonPanel.add(viewEditButton);
        buttonPanel.add(importButton);
        //3 panels given north cnter and south resp ie, top-title, middle-scrollable list, bottom- buttons panel
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        //action listners for buttons calling various functions
        newCardButton.addActionListener(e -> createNewFlashcard());
        deleteCardButton.addActionListener(e -> deleteSelectedFlashcard());
        viewEditButton.addActionListener(e -> viewOrEditFlashcard());
        importButton.addActionListener(e -> importFlashcardFile());
        
        setSize(800, 500);//size of creation mode frame
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createStyledButton(String text, Dimension size, Font font) { //simple styled button fxn same as before
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(font);
        return button;
    }
    
    private void importFlashcardFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a flashcard file to import");
        
        // Add a file filter for .txt files (optional enhancement)
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
            }
            
            @Override
            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // checks if the import file has a .txt extension
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                JOptionPane.showMessageDialog(this,
                    "Only .txt files are supported for import.",
                    "Import Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int originalCount = flashcards.size();
            try(BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                Flashcard current = null;
                while((line = br.readLine()) != null) {
                    if(line.startsWith("Q:")) {
                        current = new Flashcard();
                        current.setQuestion(line.substring(2).trim());//start input from 2nd index to avoid tag
                    } else if(line.startsWith("A:") && current != null) {
                        current.setAnswer(line.substring(2).trim());
                        flashcards.add(current);
                        listModel.addElement(current.getQuestion());
                    }
                }
                saveFlashcards();
                int newCount = flashcards.size() - originalCount;
                JOptionPane.showMessageDialog(this,
                    newCount + " flashcards imported and added to your collection.",
                    "Import Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch(IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error reading file: " + ex.getMessage(),
                    "Import Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private void loadFlashcards() {//br writes onto file using basic filehandling
        try(BufferedReader br = new BufferedReader(new FileReader(flashcardFile))) {
            String line;
            Flashcard current = null;
            while((line = br.readLine()) != null) {
                if(line.startsWith("Q:")) {//br keeps taking input letter by letter till it reaches A:
                    current = new Flashcard();
                    current.setQuestion(line.substring(2).trim());
                } else if(line.startsWith("A:") && current != null) {
                    current.setAnswer(line.substring(2).trim());
                    flashcards.add(current);
                }
            }
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading flashcards: " + ex.getMessage(),
                "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveFlashcards() {
        try(PrintWriter pw = new PrintWriter(new FileWriter(flashcardFile))) {
            for(Flashcard fc : flashcards) {
                pw.println("Q: " + fc.getQuestion());
                pw.println("A: " + fc.getAnswer());
                pw.println(); // blank line between flashcards
            }
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving flashcards: " + ex.getMessage(),
                "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createNewFlashcard() {
        String question = JOptionPane.showInputDialog(this, "Enter question:");
        if(question == null || question.trim().isEmpty()) return;
        
        String[] options = {"MCQ", "Subjective"};//select type
        int type = JOptionPane.showOptionDialog(this, "Select flashcard answer type", "Flashcard Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        String answer = "";
        if(type == 0) { // MCQ type stored using <option> tag
            try {
                int numOptions = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of choices:"));
                StringBuilder optionsBuilder = new StringBuilder();
                for(int i = 0; i < numOptions; i++){
                    String choice = JOptionPane.showInputDialog(this, "Enter choice " + (i+1) + ":");
                    optionsBuilder.append("<option>").append(choice).append("</option>");
                }
                String correct = JOptionPane.showInputDialog(this, "Enter the correct answer:");
                answer = optionsBuilder.toString() + "<correct>" + correct + "</correct>";
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.");
                return;
            }
        } else { // Subjective type
            answer = JOptionPane.showInputDialog(this, "Enter answer:");
        }
        
        Flashcard newCard = new Flashcard();
        newCard.setQuestion(question);
        newCard.setAnswer(answer);
        flashcards.add(newCard);
        listModel.addElement(newCard.getQuestion());
        saveFlashcards();
    }
    
    private void deleteSelectedFlashcard() {//delete flashcards as per selected flashcard index
        int index = flashcardList.getSelectedIndex();
        if(index >= 0) {
            flashcards.remove(index);
            listModel.remove(index);
            saveFlashcards();
        }
    }
    
    private void viewOrEditFlashcard() {//edit answer option
        int index = flashcardList.getSelectedIndex();
        if(index >= 0) {
            Flashcard fc = flashcards.get(index);
            String newAnswer = JOptionPane.showInputDialog(this, "Edit answer:", fc.getAnswer());
            if(newAnswer != null) {
                fc.setAnswer(newAnswer);
                saveFlashcards();
            }
        }
    }
}

class RevisionModeFrame extends JFrame {
    private File flashcardFile;
    private java.util.List<Flashcard> flashcards;
    private int currentIndex = 0;
    
    private JLabel questionLabel;
    private JTextArea answerArea;
    private JButton showAnswerButton;
    
    public RevisionModeFrame(File file) {
        super("Revision Mode");
        this.flashcardFile = file;
        flashcards = new ArrayList<>();
        loadFlashcards();
        
        setLayout(new BorderLayout());
        //Frontend
        JLabel titleLabel = new JLabel("Revision Mode", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.add(titleLabel);
        
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createTitledBorder("Question"));
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BorderLayout());
        answerPanel.setBorder(BorderFactory.createTitledBorder("Answer"));
        
        answerArea = new JTextArea(5, 20);
        answerArea.setFont(new Font("Arial", Font.PLAIN, 16));
        answerArea.setEditable(false);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);
        answerArea.setVisible(false);
        
        JScrollPane answerScrollPane = new JScrollPane(answerArea);
        answerPanel.add(answerScrollPane, BorderLayout.CENTER);
        
        cardPanel.add(questionPanel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(answerPanel);
        
        Dimension buttonSize = new Dimension(130, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        JButton previousButton = createStyledButton("Previous", buttonSize, buttonFont);
        showAnswerButton = createStyledButton("Show Answer", buttonSize, buttonFont);
        JButton nextButton = createStyledButton("Next", buttonSize, buttonFont);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        buttonPanel.add(previousButton);
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(nextButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        //action listners
        showAnswerButton.addActionListener(e -> toggleAnswer());//answer visible or not
        nextButton.addActionListener(e -> showNextCard());
        previousButton.addActionListener(e -> showPreviousCard());
        
        if (!flashcards.isEmpty()) {
            displayCurrentCard();
        } else {
            questionLabel.setText("No flashcards available");
            showAnswerButton.setEnabled(false);
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
        }
        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createStyledButton(String text, Dimension size, Font font) {//same as before
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(font);
        return button;
    }
    
    private void loadFlashcards() {
        try(BufferedReader br = new BufferedReader(new FileReader(flashcardFile))) {
            String line;
            Flashcard current = null;
            while((line = br.readLine()) != null) {
                if(line.startsWith("Q:")) {
                    current = new Flashcard();
                    current.setQuestion(line.substring(2).trim());
                } else if(line.startsWith("A:") && current != null) {
                    current.setAnswer(line.substring(2).trim());
                    flashcards.add(current);
                }
            }
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading flashcards: " + ex.getMessage(),
                "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayCurrentCard() {//parse and display as per tags
        if (currentIndex >= 0 && currentIndex < flashcards.size()) {
            Flashcard fc = flashcards.get(currentIndex);
            questionLabel.setText(fc.getQuestion());
            
            String answerText = fc.getAnswer();
            if (answerText.contains("<option>") && answerText.contains("<correct>")) {
                String correctAnswer = parseCorrectAnswer(answerText);
                answerArea.setText("Correct answer: " + correctAnswer);
            } else {
                answerArea.setText(answerText);
            }
            
            answerArea.setVisible(false);
            showAnswerButton.setText("Show Answer");
        }
    }
    
    private void toggleAnswer() {//changing visibility
        if (answerArea.isVisible()) {
            answerArea.setVisible(false);
            showAnswerButton.setText("Show Answer");
        } else {
            answerArea.setVisible(true);
            showAnswerButton.setText("Hide Answer");
        }
    }
    
    private void showNextCard() { //moving along flashcard list
        if (currentIndex < flashcards.size() - 1) {
            currentIndex++;
            displayCurrentCard();
        }
    }
    
    private void showPreviousCard() {
        if (currentIndex > 0) {
            currentIndex--;
            displayCurrentCard();
        }
    }
    
    private String parseCorrectAnswer(String answerText) {
        int start = answerText.indexOf("<correct>");
        int end = answerText.indexOf("</correct>");
        if(start != -1 && end != -1) {
            return answerText.substring(start + "<correct>".length(), end);
        }
        return "";
    }
}

class QuizModeFrame extends JFrame {
    private File flashcardFile;
    private java.util.List<Flashcard> mcqFlashcards;//list of mcq flashcards
    private int currentIndex = 0;
    private int correctCount = 0;
    private int wrongCount = 0;
    
    private JLabel questionLabel;
    private JPanel optionsPanel;
    
    public QuizModeFrame(File file) {
        super("Quiz Mode");
        this.flashcardFile = file;
        mcqFlashcards = new ArrayList<>();
        loadMCQFlashcards();
        
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Quiz Mode", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.add(titleLabel);
        
        questionLabel = new JLabel("Question will appear here", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(30));
        questionPanel.add(optionsPanel);
        
        Dimension buttonSize = new Dimension(120, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        JButton nextButton = createStyledButton("Next", buttonSize, buttonFont);
        JButton skipAllButton = createStyledButton("Skip All", buttonSize, buttonFont);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        buttonPanel.add(nextButton);
        buttonPanel.add(skipAllButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(e -> checkAnswerAndProceed());
        skipAllButton.addActionListener(e -> showResult());
        
        displayCurrentQuestion();
        
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(font);
        return button;
    }
    
    private void loadMCQFlashcards() { //filter for mcq only
        try(BufferedReader br = new BufferedReader(new FileReader(flashcardFile))) {
            String line;
            Flashcard current = null;
            while((line = br.readLine()) != null) {
                if(line.startsWith("Q:")) {
                    current = new Flashcard();
                    current.setQuestion(line.substring(2).trim());
                } else if(line.startsWith("A:") && current != null) {
                    String ans = line.substring(2).trim();
                    if(ans.contains("<option>") && ans.contains("<correct>")) {
                        current.setAnswer(ans);
                        mcqFlashcards.add(current);//add to list only if mcq
                    }
                }
            }
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading flashcards: " + ex.getMessage(),
                "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayCurrentQuestion() {
        if(currentIndex < mcqFlashcards.size()) {
            Flashcard fc = mcqFlashcards.get(currentIndex);
            questionLabel.setText(fc.getQuestion());
            optionsPanel.removeAll();
            
            java.util.List<String> options = parseOptions(fc.getAnswer());
            ButtonGroup group = new ButtonGroup();
            
            for(String opt : options) {//using radio button to display options
                JRadioButton rb = new JRadioButton(opt);
                rb.setFont(new Font("Arial", Font.PLAIN, 16));
                rb.setAlignmentX(Component.LEFT_ALIGNMENT);
                rb.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                group.add(rb);
                optionsPanel.add(rb);
                optionsPanel.add(Box.createVerticalStrut(10));
            }
            
            optionsPanel.revalidate();
            optionsPanel.repaint();
        } else {
            showResult();
        }
    }
    
    private java.util.List<String> parseOptions(String answerText) {
        java.util.List<String> options = new ArrayList<>();
        int index = 0;
        while((index = answerText.indexOf("<option>", index)) != -1) {
            int start = index + "<option>".length();
            int end = answerText.indexOf("</option>", start);
            if(end == -1) break;
            options.add(answerText.substring(start, end));
            index = end + "</option>".length();
        }
        return options;
    }
    
    private String parseCorrectAnswer(String answerText) {
        int start = answerText.indexOf("<correct>");
        int end = answerText.indexOf("</correct>");
        if(start != -1 && end != -1) {
            return answerText.substring(start + "<correct>".length(), end);
        }
        return "";
    }
    
    private void checkAnswerAndProceed() {
        String selected = null;
        for(Component comp : optionsPanel.getComponents()) {
            if(comp instanceof JRadioButton) {
                JRadioButton rb = (JRadioButton) comp;
                if(rb.isSelected()){
                    selected = rb.getText();
                    break;
                }
            }
        }
        
        if(selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an option.");
            return;
        }
        
        Flashcard fc = mcqFlashcards.get(currentIndex);
        String correct = parseCorrectAnswer(fc.getAnswer());
        
        if(selected.equals(correct)) {
            correctCount++;
        } else {
            wrongCount++;
        }
        
        currentIndex++;
        displayCurrentQuestion();
    }
    
    private void showResult() {
        JOptionPane.showMessageDialog(this, 
            "Quiz Over!\nTotal: " + mcqFlashcards.size() + 
            "\nCorrect: " + correctCount + 
            "\nWrong: " + wrongCount);
        dispose();
    }
}