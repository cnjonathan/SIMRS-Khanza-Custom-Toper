package simrskhanza;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class DlgViewHasilRadiologi extends JDialog {
    private WebEngine engine;
    private final JFXPanel jfxPanel = new JFXPanel();
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel lblStatus = new JLabel();
    private final JTextField txtURL = new JTextField();
    private JPanel jPanel1;
    private javax.swing.JInternalFrame internalFrame;

    public DlgViewHasilRadiologi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initComponents2();
        
        this.setLocation(10, 2);
        setSize(628, 674);
    }
    
    private void initComponents2() {
        txtURL.addActionListener((ActionEvent e) -> loadURL(txtURL.getText()));
  
        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);
        
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jfxPanel, BorderLayout.CENTER);
        jPanel1.add(progressBar, BorderLayout.SOUTH);
        jPanel1.add(lblStatus, BorderLayout.NORTH);
        
        internalFrame.add(jPanel1, BorderLayout.CENTER);
        createScene();  // Create the JavaFX scene
    }

    private void initComponents() {
        internalFrame = new javax.swing.JInternalFrame();
        jPanel1 = new JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());

        getContentPane().add(internalFrame, BorderLayout.CENTER);
        pack();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  
        loadURL("http://10.77.41.100:19898/intiwid/viewer.html?studyUID=1.2.40.0.13.1.611964.202405110045.0549202405046045");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgViewHasilRadiologi dialog = new DlgViewHasilRadiologi(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private void createScene() {
        Platform.runLater(() -> {
            WebView view = new WebView();
            engine = view.getEngine();
            engine.setJavaScriptEnabled(true);

            engine.setOnStatusChanged((WebEvent<String> event) -> SwingUtilities.invokeLater(() -> lblStatus.setText(event.getData())));

            engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> SwingUtilities.invokeLater(() -> progressBar.setValue(newValue.intValue())));

            engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, Throwable value) -> {
                if (engine.getLoadWorker().getState() == Worker.State.FAILED) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(internalFrame, (value != null) ? engine.getLocation() + "\n" + value.getMessage() : engine.getLocation() + "\nUnexpected error.", "Loading error...", JOptionPane.ERROR_MESSAGE));
                }
            });

            engine.locationProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> SwingUtilities.invokeLater(() -> txtURL.setText(newValue)));

            engine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    try {
                        System.out.println("URL : " + engine.getLocation());
                    } catch (Exception ex) {
                        System.out.println("Notification : " + ex);
                    }
                }
            });

            jfxPanel.setScene(new Scene(view));
        });
    }

    public void loadURL(String url) {
        Platform.runLater(() -> {
            try {
                engine.load(url);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(internalFrame, "Failed to load URL: " + url, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void CloseScene() {
        Platform.setImplicitExit(false);
    }
}
