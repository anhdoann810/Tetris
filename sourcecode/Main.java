import javax.swing.SwingUtilities;
import view.MainFrame;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}