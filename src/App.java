import javax.swing.*;
import com.formdev.flatlaf.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Failed to initialize LaF");
        }

        LoginForm loginForm = new LoginForm();
        loginForm.initialize();

    }
    
}
