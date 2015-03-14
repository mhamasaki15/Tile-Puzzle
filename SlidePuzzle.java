import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.border.LineBorder;

public class SlidePuzzle extends JFrame implements ActionListener{
  private BufferedImage master;
  private Tile[][] tiles;
  JPanel panel;
  JButton mixup;
  JLabel count;
  int sidelength;
  int blankindex;
  Tile nulltile;
  int movecount = 0;
  
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
    
      panel = new JPanel();
      getContentPane().add(panel);    
      panel.setLayout(null);
      
      createPuzzleLayout(master, d);//add a for loop inside to change the bounds.
      
      JLabel directions = new JLabel("Click on a tile to slide it into the blank space next to it.");
      directions.setBounds((d * 101 + 200)/2 - 185,40,500,30);
      panel.add(directions);
      
      
      count = new JLabel("Move count: " + movecount);
      count.setBounds((d * 101 + 200)/2 - 43, (d * 101) + 100, 200, 30);
      panel.add(count);
      
      JButton mixup = new JButton("Click to mix up the puzzle!");
      mixup.setBounds ((d * 101 + 200)/2 - 100, 65, 200, 30);
      panel.add(mixup);
      
      setTitle("Sliding Tile Puzzle");
      setSize(d * 101 + 200, d * 101 + 200);
      setLocationRelativeTo(null);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
  }
  
  public void createPuzzleLayout(Image picture, int s){
    
    
    sidelength = s;
    blankindex = s * s;
    tiles = new Tile[sidelength][sidelength];
    
    int index = 1;
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        tiles[row][col] = new Tile(master.getSubimage(col*100, row*100, 100, 100), index);
        index++;   
        tiles[row][col].setBounds((col+1)*101, (row+1)*101, 100, 100);
        tiles[row][col].setBorder(LineBorder.createBlackLineBorder());
        tiles[row][col].addActionListener(this);
       panel.add(tiles[row][col]);
      }      
    }
    tiles[sidelength-1][sidelength-1].setNewIcon(new ImageIcon("/Images/blankicon.jpg"));
    
    nulltile = new Tile(master, 0);
    
  }
  
  public boolean checkAdjacent(Tile center){
    
    return true;
  }
  
  public void actionPerformed(ActionEvent e){ //add sound
    
    if (e.getSource() == mixup){
      
    }

    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        if (e.getSource() == tiles[row][col]){ //change this whole thing to a method later, to implement for the start up
          Tile[] updownleftright = new Tile[4];
          if (row - 1 >= 0) updownleftright[0] = tiles[row - 1][col];
          else updownleftright[0] = nulltile;
            
          if (row + 1 < sidelength) updownleftright[1] = tiles[row + 1][col];
          else updownleftright[1] = nulltile;
          
          if (col - 1 >= 0) updownleftright[2] = tiles[row][col - 1];
          else updownleftright[2] = nulltile;
          
          if (col + 1 < sidelength) updownleftright[3] = tiles[row][col + 1];
          else updownleftright[3] = nulltile;
          
          for (int i = 0; i < 4; i ++)
          {
            if (updownleftright[i].getCurPic() == blankindex){
              tiles[row][col].swap(updownleftright[i]);
              movecount++;
              count.setText("Move count: " + movecount);
               return;
              
            }
          }
        }
      }
    }
  }
}
