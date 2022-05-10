import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 800; // размерность
    private final int DOT_SIZE = 16; //размер одной ячейки
    private final int ALL_DOTS = 640000; //количество ячеек

    private Image dot; //точка
    private Image apple; //яблоко

    private int appleX; //позоция яблока для x
    private int appleY; //позоция яблока для y

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private int dots;
    private int score = 0;

    private Timer timer;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){ //конструктор класса
        setBackground(Color.darkGray);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){ //инициализация игры
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = SIZE/2 - i*DOT_SIZE;
            y[i] = SIZE/2;
        }
        timer = new Timer(125,this);
        timer.start();
        createApple();
    }

    public void createApple(){ //генерирование яблока
        appleX = new Random().nextInt(50)*DOT_SIZE;
        appleY = new Random().nextInt(50)*DOT_SIZE;
    }

    public void loadImages(){ //загрузка картинок
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){                                 //рисовка
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{                                     // gameover
            String str = "YOU DIED!";
            String bezdarnost = "бездарность...";
            String StringScore = "Your score is ";
            StringScore += Integer.toString(score);
            Font f1 = new Font("Times New Roman",Font.CENTER_BASELINE,14);
            Font f2 = new Font("Times New Roman",Font.BOLD,42);
            Font f3 = new Font("Times New Roman",Font.BOLD,21);
            g.setColor(Color.red);

            g.setFont(f2);
            g.drawString(str,SIZE/2 - 110,SIZE/2);

            g.setFont(f3);
            g.drawString(StringScore,SIZE/2-60,SIZE/2+SIZE/4);

            g.setFont(f1);
            g.drawString(bezdarnost,SIZE/2-40,SIZE/2+SIZE/32);

        }

    }


    public void move(){                         // движение

        //для всего туловища
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }


        // для головы
        if(left){
            x[0] -= DOT_SIZE;
        }

        if(right){
            x[0] += DOT_SIZE;
        }

        if(up){
            y[0] -= DOT_SIZE;
        }

        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        //проверка яблока
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            score++;
            createApple();
        }
    }

    public void checkCollisions(){
        //проверка колизий
        for (int i = dots; i >0 ; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
                break;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }

        if(x[0]<0){
            inGame = false;
        }

        if(y[0]>SIZE){
            inGame = false;
        }

        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    //класс проверки нажатий
    class FieldKeyListener extends KeyAdapter{

        //метод проверка нажатий
        @Override
        public void keyPressed(KeyEvent e) {

            super.keyPressed(e);
            int key = e.getKeyCode();
            if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && !right){
                left = true;
                up = false;
                down = false;
            }
            if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && !left){
                right = true;
                up = false;
                down = false;
            }

            if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && !down){
                right = false;
                up = true;
                left = false;
            }
            if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}