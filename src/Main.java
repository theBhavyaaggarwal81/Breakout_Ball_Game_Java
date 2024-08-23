import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Main extends JPanel implements ActionListener, KeyListener {
    int ballX = 150;
    int ballY = 150;
    int ballXSpeed = 2;
    int ballYSpeed = 2;

    int paddleX = 500;
    int paddleY = 720;
    int paddleWidth = 100;
    int paddleHeight = 20;

    int brickRowCount = 7;
    int brickColumnCount = 10;
    int brickWidth = 100;
    int brickHeight = 40;
    int brickPadding = 10;
    int brickOffsetTop = 30;
    int brickOffsetLeft = 30;

    boolean[] bricks;
    JLabel score;
    Timer timer;

    public Main() {
        bricks = new boolean[brickRowCount * brickColumnCount];

        setPreferredSize(new Dimension(1150, 800));
        setBackground(Color.BLACK);

        timer = new Timer(25, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        score = new JLabel("0");
        score.setBounds(1100,50,25,25);
        Font boldFont = new Font(score.getFont().getName(), Font.BOLD, score.getFont().getSize()+30);
        score.setFont(boldFont);

        initializeBricks();
        add(score);
    }

    private void initializeBricks() {
        for (int i = 0; i < brickRowCount * brickColumnCount; i++) {
            bricks[i] = true;
        }
    }

    private void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballX <= 0 || ballX >= getWidth() - 20) {
            ballXSpeed = -ballXSpeed;
        }

        if (ballY <= 0) {
            ballYSpeed = -ballYSpeed;
        }

        if (ballY >= getHeight() - 20) {
            ballXSpeed = 0;
            ballYSpeed = 0;
        }

        if (ballY + 20 >= paddleY && ballX + 20 >= paddleX && ballX <= paddleX + paddleWidth) {
            ballYSpeed = -ballYSpeed;
        }
    }

    private void movePaddle(int direction) {
        paddleX += direction;

        if (paddleX <= 0) {
            paddleX = 0;
        }

        if (paddleX >= getWidth() - paddleWidth) {
            paddleX = getWidth() - paddleWidth;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBall();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            movePaddle(-5);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            movePaddle(5);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, 20, 20);

        g.setColor(Color.BLUE);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);

        for (int row = 0; row < brickRowCount; row++) {
            for (int col = 0; col < brickColumnCount; col++) {
                if (bricks[row * brickColumnCount + col]) {
                    int brickX = col * (brickWidth + brickPadding) + brickOffsetLeft;
                    int brickY = row * (brickHeight + brickPadding) + brickOffsetTop;
                    double p=Math.random();
                    if(p<=0.333)
                        g.setColor(Color.RED);
                    else if(p<=0.66 && p>0.33)
                        g.setColor(Color.BLUE);
                    else
                        g.setColor(Color.GREEN);
                    g.fillRect(brickX, brickY, brickWidth, brickHeight);
                }
            }
        }
    }

    public static void main(String[] args) {
            JFrame frame = new JFrame("Ball Brick Breaker Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Main());
            frame.pack();
            frame.setVisible(true);
    }
}
