import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GameMenu extends JFrame implements ActionListener{
  
  private JButton easy;
  private JButton medium;
  private JButton hard;
  private int sidelength = 0;
  
  public GameMenu(){
    {      
      JPanel panel = new JPanel();
      add(panel);    
      panel.setLayout(null);
      
      JLabel welcome = new JLabel("Let's solve a puzzle! Please choose a difficulty level.");
      welcome.setBounds(90,30,500,30);
      panel.add(welcome);
      
      easy = new JButton("Easy: 3 x 3");
      try {
        Image udon = ImageIO.read(getClass().getResource("Images/udonicon.jpg"));
        easy.setIcon(new ImageIcon(udon));
      } catch (IOException ex) {
      }
      easy.setVerticalTextPosition(SwingConstants.BOTTOM); 
      easy.setHorizontalTextPosition(SwingConstants.CENTER);
      easy.setBounds(80, 90, 110, 150);            
      
      medium = new JButton("Medium: 4 x 4"); 
      try {
        Image sammie = ImageIO.read(getClass().getResource("Images/sammieicon.jpg"));
        medium.setIcon(new ImageIcon(sammie));
      } catch (IOException ex) {
      }
      medium.setVerticalTextPosition(SwingConstants.BOTTOM); 
      medium.setHorizontalTextPosition(SwingConstants.CENTER);
      medium.setBounds(200, 90, 110, 150);
      
      hard = new JButton("Hard: 5 x 5");
      try {
        Image flower = ImageIO.read(getClass().getResource("Images/flowericon.jpg"));
        hard.setIcon(new ImageIcon(flower));
      } catch (IOException ex) {
      }
      hard.setVerticalTextPosition(SwingConstants.BOTTOM); 
      hard.setHorizontalTextPosition(SwingConstants.CENTER);
      hard.setBounds(320, 90, 110, 150);
      
      easy.addActionListener(this);
      medium.addActionListener(this);
      hard.addActionListener(this);
      
      panel.add(easy);
      panel.add(medium);
      panel.add(hard);               
      
      setTitle("Sliding Tile Puzzle");
      setSize(500, 310);
      setLocationRelativeTo(null);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
  }
  
  public void actionPerformed(ActionEvent e){
    if (e.getSource() == easy) sidelength = 3;
    if (e.getSource() == medium) sidelength = 4;
    if (e.getSource() == hard) sidelength = 5;
    
    dispose();
    SlidePuzzle s = new SlidePuzzle (sidelength);
  }
  
  public static void main(String[] args){
    GameMenu game = new GameMenu();
  }  
}