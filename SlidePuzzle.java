import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class SlidePuzzle extends JFrame implements ActionListener{
  private BufferedImage master;
  private Tile[][] tiles;
  
  public SlidePuzzle(int d){
    if (d == 3)
    {
      try {
        master = ImageIO.read(getClass().getResource("Images/udon.jpg"));
      }
      catch (IOException ex) {
      }
    }
    else if (d == 4)
    {
      try {
        master = ImageIO.read(getClass().getResource("Images/sammie.jpg"));
      }
      catch (IOException ex) {
      }
    }
    else
    {
      try {
        master = ImageIO.read(getClass().getResource("Images/flower.jpg"));
      }
      catch (IOException ex) {
      }
    }
    
    
    
    JPanel panel = new JPanel();
      getContentPane().add(panel);    
      panel.setLayout(null);
      
      createPuzzleLayout(master, d);//add a for loop inside to change the bounds.
      
      JLabel directions = new JLabel("Click on a tile to slide it into the blank space next to it.");
      directions.setBounds(90,40,500,30);
      panel.add(directions);
      
      setTitle("Sliding Tile Puzzle");
      setSize(d * 100 + 200, d * 100 + 200);
      setLocationRelativeTo(null);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
  }
  
  public void createPuzzleLayout(Image picture, int sidelength){
    
    tiles = new Tile[sidelength][sidelength];
    
    int index = 1;
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        tiles[row][col] = new Tile(master.getSubimage(col*100, row*100, 100, 100), index);
        index++;   
      }      
    }
    tiles[sidelength-1][sidelength-1].setIcon(new ImageIcon("/Images/blankicon.jpg"));
    
  }
  
  public void actionPerformed(ActionEvent e){
  }
}