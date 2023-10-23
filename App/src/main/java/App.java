import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.io.File;

public class App {
    private JPanel rootPanel;
    private JTextField textField1;
    private JLabel label;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JTextArea textArea1;
    private JLabel dataLabel;
    private JTextPane textPane1;
    private JLabel radioLabel;
    private JButton submitButton;
    private JTextPane resultPane;
    private final FileHandler file;

    // Throws IOException because of file creation.
    public App() throws IOException {

        // Creating a .txt file to store data results
        File data = new File("data.txt");

        try {
            if (data.createNewFile() || data.exists()) {
                System.out.println("File creation successful: " + data.getName());
            } else {
                System.out.println("File creation failed");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }


        // Create the FileHandler. This class handles all reading and writing to the file.
        this.file = new FileHandler(data);


        // ----------- Action listeners for components -----------

        textField1.addActionListener(e -> {
            String userInput = textField1.getText();

            textArea1.append(userInput + "\n");
            textField1.setText("");

            // Needed to resize the JFrame as data is entered
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(rootPanel);
            frame.setSize(frame.getWidth(), frame.getHeight() + 14);
        });


        // Simple logic for the radio buttons. If one is selected the other is deselected
        yesRadioButton.addActionListener(e -> {
            noRadioButton.setSelected(false);
            textPane1.setText("True");
        });

        noRadioButton.addActionListener(e -> {
            yesRadioButton.setSelected(false);
            textPane1.setText("False");
        });

        // Main logic to submit the data
        submitButton.addActionListener(e -> {

                // Check if the user entered data and checked a radio button
                try {
                    if (textArea1.getText().trim().isEmpty() || textPane1.getText().trim().isEmpty()) {
                        this.adjustResultPane(false);
                        resultPane.setText("Please enter data or choose radio button to submit");
                        return;
                    }

                    file.write("Area Text:\n");
                    file.write(textArea1.getText());
                    file.write("\n");

                    file.write("Pane Text:\n");
                    file.write(textPane1.getText());
                    file.write("\n");

                    file.getWriter().close();

                    System.out.println(file.read());

                    file.getReader().close();

                    // provide feedback to the user through resultPane
                    this.adjustResultPane(true);

                } catch (IOException err) {
                    // Properly handle the exception or log it
                    err.printStackTrace();
                    // provide feedback to the user
                    this.adjustResultPane(false);
                }

        });


        // ----------- Text centering for resultPane -----------

        // Create the StyledDocument for resultPane
        StyledDocument docResultPane = resultPane.getStyledDocument();
        SimpleAttributeSet centerAlignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER);

        // Apply the center alignment style to the entire document of resultPane
        docResultPane.setParagraphAttributes(0, docResultPane.getLength(), centerAlignment, false);

    }

    // main function. Needs to throw an IOException because App throws one
    public static void main(String[] args) throws IOException {
        try {
            JFrame frame = new JFrame("App");
            frame.setContentPane(new App().rootPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        } catch (IOException err) {
            throw new IOException(err);
        }
    }

    // Used to adjust resultPane styling
    public void adjustResultPane(boolean result) {
        resultPane.setForeground(Color.WHITE);

        if (result) {
            resultPane.setText("Data saved to the file.");
            resultPane.setBackground(Color.GREEN);
        } else {
            resultPane.setText("Error saving data to the file.");
            resultPane.setBackground(Color.RED);
        }
    }
}
