import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Snake Souls"); // название
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // возможность закрытия
        setSize(800,825); // размер
        setResizable(false); //запрет на измененние размеров
        setLocation(320,0); // раположение фрейма
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {    MainWindow mainWindow = new MainWindow();   }
}