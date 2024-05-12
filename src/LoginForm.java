import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {

    final private Font mainFont=new Font("Segeo print", Font.BOLD, 18);

    //JTextField 
    JTextField tfEmail;
    //JPasswordField
    JPasswordField pfPassword;

    public void initialize(){

        /* =============== FORM PAINEL ============ */
        JLabel lbLoginForm=new JLabel("Login Form",SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

        JLabel lbEmail=new JLabel("Email");
        lbEmail.setFont(mainFont);

        //JTextField
        tfEmail=new JTextField();
        tfEmail.setFont(mainFont);

        // Password
        JLabel lbPassword=new JLabel("Password");
        lbPassword.setFont(mainFont);
        //JPasswordField
        pfPassword=new JPasswordField();
        pfPassword.setFont(mainFont);


        // Panel
        JPanel formPanel=new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbLoginForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        
        /* =============== BUTTONS PAINEL ============ */
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                User user = getAuthenticatedUser(email,password);

                if(user != null){
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,"Email or Password Invalid", "Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2,10,0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);

        
        /* =============== Initialize the Frame ============ */
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,500);
        setMinimumSize(new Dimension(350, 450));
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private User getAuthenticatedUser(String email, String password){
        User user = null;

        final String DB_URL = "jbdc:mysql://localhost/java_mystore?serverTimezone=UTC";//java_mystore
        final String USERNAME = "root";
        final String PASSWORD = "root";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database sucessFully......

            String sql = "SELECT * FROM users_mystore WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Database connextion Failed!");
        }

        return user;
    }

    /* =============== RUN JAVA JFRAME ============ */
        public static void main(String[] args) {
            LoginForm loginForm = new LoginForm();
            loginForm.initialize();
        }
}
