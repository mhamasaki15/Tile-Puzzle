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
  private JPanel panel;
  private JButton mixup;
  private JLabel directions;
  //private JButton showreference;
  //Private JButton show numbers.
  private JLabel win;
  private JLabel count;
  private int sidelength;
  private int blankindex;
  private Tile nulltile;
  private int movecount = 0;
  
  
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
    
    
    directions = new JLabel("Click on a tile to slide it into the blank space next to it.");
    directions.setBounds((d * 101 + 200)/2 - 185,40,500,15);
    panel.add(directions);
    
    count = new JLabel("Move count: " + movecount);
    count.setBounds((d * 101 + 200)/2 - 43, (d * 101) + 100, 200, 30);
    panel.add(count);
    
    mixup = new JButton("Click to mix up the puzzle!");//this button hates me
    mixup.setBounds ((sidelength * 101 + 200)/2 - 100, 65, 200, 20);
    mixup.addActionListener(this);
    panel.add(mixup);
    
    
    
    win = new JLabel();
    win.setBounds ((d * 101 + 200)/2 - 185, 50, 400, 30);
    
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
        tiles[row][col].addActionListener(this); //later, put this in a different method so the player cant mix up the tiles before.
        panel.add(tiles[row][col]);
      }      
    }
    tiles[sidelength-1][sidelength-1].setNewIcon(new ImageIcon("/Images/blankicon.jpg"));    
    nulltile = new Tile(master, 0);
    
  }
  
  public boolean checkSwitch(int row, int col){
    boolean output = false;
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
        return true;
      }
    }
    return false;
  }
  
  
  public boolean checkWin(){
    boolean output = true;
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        if (tiles[row][col].getCurPic() != tiles[row][col].getCorrect()) output = false;
      }
    }
    return output;
  }
  
  
  public void actionPerformed(ActionEvent e){ //add sound, a way to show the numbers of the tiles, and a way to see the main picture.   
   
    if (e.getSource() == mixup){
      System.out.println("sammie");
    }
    
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        if (e.getSource() == tiles[row][col] && tiles[row][col].getCurPic() != blankindex){ //Ensures that clicking on the blank tile at the start of the game does not count as "winning."
          
          if (checkSwitch(row, col) == true){
            movecount++;
            count.setText("Move count: " + movecount);
          }
          if (checkWin() == true){             
            System.out.println("SDFSDF");
            panel.remove(mixup);
            panel.remove(directions);
            panel.invalidate();
            panel.repaint();
            win.setText("Congratulations! You solved the puzzle! You took " + movecount + " moves!");
            panel.add(win);
          }
          return;
        }
      }
    }
  }
}

