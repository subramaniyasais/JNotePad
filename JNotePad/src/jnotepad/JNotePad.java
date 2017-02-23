package jnotepad;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 *
 * @author subramaniya.sai
 */
public class JNotePad extends JFrame {
    
    public static void main(String[] args)  {
        try {
            // TODO code application logic here

            JFrame frame = new MainClass();

            Image image = null;
            try {
                File imagefile = new File("Images/Notepad1.png");
                image = ImageIO.read(imagefile);
            } catch (IOException e) {
            }
            frame.setIconImage(image);
            frame.setTitle("JNotepad");
            frame.setVisible(true);
            frame.setSize(920, 580);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setLocationRelativeTo(null);
        } catch (IOException ex) {
            Logger.getLogger(JNotePad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

