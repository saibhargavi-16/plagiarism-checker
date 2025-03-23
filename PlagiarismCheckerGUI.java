import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class PlagiarismCheckerGUI extends JFrame {

    private JTextField file1PathField;
    private JTextField file2PathField;
    private JButton browseButton1;
    private JButton browseButton2;
    private JButton checkButton;
    private JLabel resultLabel;

    public PlagiarismCheckerGUI() {
        setTitle("Plagiarism Checker");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JPanel file1Panel = new JPanel(new BorderLayout());
        file1PathField = new JTextField();
        browseButton1 = new JButton("Browse");
        browseButton1.addActionListener(e -> selectFile(file1PathField));
        file1Panel.add(file1PathField, BorderLayout.CENTER);
        file1Panel.add(browseButton1, BorderLayout.EAST);

        JPanel file2Panel = new JPanel(new BorderLayout());
        file2PathField = new JTextField();
        browseButton2 = new JButton("Browse");
        browseButton2.addActionListener(e -> selectFile(file2PathField));
        file2Panel.add(file2PathField, BorderLayout.CENTER);
        file2Panel.add(browseButton2, BorderLayout.EAST);

        checkButton = new JButton("Check Plagiarism");
        checkButton.addActionListener(e -> checkPlagiarism());

        resultLabel = new JLabel("Similarity: --%", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(file1Panel);
        add(file2Panel);
        add(checkButton);
        add(resultLabel);

        setVisible(true);
    }

    private void selectFile(JTextField textField) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void checkPlagiarism() {
        String file1Path = file1PathField.getText();
        String file2Path = file2PathField.getText();

        if (file1Path.isEmpty() || file2Path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select both files!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Map<String, Integer> freqMap1 = FileProcessor.getWordFrequency(file1Path);
            Map<String, Integer> freqMap2 = FileProcessor.getWordFrequency(file2Path);

            double similarity = SimilarityChecker.calculateCosineSimilarity(freqMap1, freqMap2);

            resultLabel.setText(String.format("Similarity: %.2f%%", similarity * 100));
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading files: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlagiarismCheckerGUI::new);
    }
}
