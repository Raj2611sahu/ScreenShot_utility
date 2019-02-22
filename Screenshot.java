import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Screenshot implements ActionListener {

    private static final String MESSAGE_DISPLAY = "Please enter file name";
    private static final String SAVE = "Save";
    private static final String CAPTURE = "Capture";
    private static String fileName;
    private BufferedImage Entire_Screen;
    private JFrame frame = new JFrame("Take Screenshot");
    private JTextField text;
    private JButton start, save;

    private Screenshot() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 80);
        frame.getContentPane().add(BorderLayout.NORTH, createMainPanel());
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {

            public void run() {
                new Screenshot();
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();

        start = new JButton(CAPTURE);
        start.addActionListener(this);
        start.setSize(20, 20);
        panel.add(start);

        text = new JTextField(MESSAGE_DISPLAY);
        text.setPreferredSize(new Dimension(150, 30));
        text.addActionListener(this);
        panel.add(text);

        save = new JButton(SAVE);
        start.setSize(20, 20);
        save.addActionListener(this);
        panel.add(save);

        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getSource().toString();

        if (s.contains("JTextField")) {
            if (text.getText() != null && !text.getText().equals("")) {
                fileName = text.getText();
            }
            return;
        }

        if (e.getActionCommand().equals("Capture")) {
            try {
                Robot awt_robot = new Robot();
                Entire_Screen = awt_robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            } catch (AWTException e1) {
                e1.printStackTrace();
            }

        }

        if (fileName == null) {
            text.setText(MESSAGE_DISPLAY);
            return;
        }

        if (Entire_Screen != null && e.getActionCommand().equals("Save") && !fileName.equals(MESSAGE_DISPLAY)) {
            try {
                ImageIO.write(Entire_Screen, "PNG", new File(System.getProperty("user.dir")+"\\Screen_shots\\"+fileName + ".png"));
                text.setText("");
                fileName = null;
                Entire_Screen.flush();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
