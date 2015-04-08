import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.border.LineBorder;
import java.lang.*;


public class SlidePuzzle extends JFrame implements ActionListener{
  private BufferedImage master;
  private Tile[][] tiles;
  private Tile[][] resettiles;
  private JPanel panel;
  private JButton mixup;
  private JButton reference;
  private JLabel directions;
  private JButton shownumbers;
  private boolean shownum;
  private JButton reset;
  private JButton reshuffle;
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
    
    shownum = false;
    
    
    panel = new JPanel();
    getContentPane().add(panel);    
    panel.setLayout(null);
    
    createPuzzleLayout(master, d);//add a for loop inside to change the bounds.
    
    //Text and Buttons
    directions = new JLabel("Click on a tile to slide it into the blank space next to it.");
    directions.setBounds((d * 101 + 200)/2 - 192, 28,500,15);
    
    count = new JLabel("Move count: " + movecount);
    count.setBounds((d * 101 + 200)/2 - 63, (d * 101) + 73, 200, 20);
    panel.add(count);
    
    reference = new JButton("Reference Image");
    reference.setBounds((d * 101 + 200)/2 - 234, (d * 101) + 100, 135, 30);
    reference.addActionListener(this);
    panel.add(reference);
    
    shownumbers = new JButton("Tile Numbers");
    shownumbers.setBounds((d * 101 + 200)/2 - 96, (d * 101) + 100, 115, 30);
    shownumbers.addActionListener(this);
    panel.add(shownumbers);
    
    reset = new JButton("Reset");
    reset.setBounds((d * 101 + 200)/2 + 21, (d * 101) + 100, 70, 30);
    reset.addActionListener(this);
    panel.add(reset);
    
    reshuffle = new JButton("Reshuffle");
    reshuffle.setBounds((d * 101 + 200)/2 + 95, (d * 101) + 100, 100, 30);
    reshuffle.addActionListener(this);
    panel.add(reshuffle);
    
    mixup = new JButton("Click to mix up the puzzle!");//this button hates me
    mixup.setBounds ((sidelength * 101 + 200)/2 - 120, 27, 200, 30);
    mixup.addActionListener(this);
    panel.add(mixup);
    
    win = new JLabel();
    win.setBounds ((d * 101 + 200)/2 - 205, 22, 400, 30);
    
    //JFrame settings
    setTitle("Sliding Tile Puzzle");
    setSize(d * 101 + 160, d * 101 + 160);
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
        tiles[row][col].setBounds((col+1)*101 -20, (row+1)*101 - 30, 100, 100);
        tiles[row][col].setBorder(LineBorder.createBlackLineBorder());
        panel.add(tiles[row][col]);
      }      
    }
    tiles[sidelength-1][sidelength-1].setNewIcon(new ImageIcon("/Images/blankicon.jpg"));    
    nulltile = new Tile(master, 0);
    
    resettiles = new Tile[sidelength][sidelength];
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
  
  
  public void removeButtonListeners(){
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        tiles[row][col].removeActionListener(this);
      }
    }
  }
  
  public void checkShowNumbers(){
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        if (shownum && tiles[row][col].getCurPic() != blankindex) tiles[row][col].setText("" + tiles[row][col].getCurPic());
        else tiles[row][col].setText("");                
      }
    }
  }
  
  public void actionPerformed(ActionEvent e){ //add sound, a way to show the numbers of the tiles, and a way to see the main picture.   
    
    if (e.getSource() == mixup){
      
      int mixmoves = 0;
      while (mixmoves <= 400)
      {
        if (checkSwitch((int)(Math.random() * sidelength), (int)(Math.random() * sidelength))) 
        {
          mixmoves++;         
        }
      }
      
      for (int row = 0; row < sidelength; row ++)
      {
        for (int col = 0; col < sidelength; col++)
        {
          tiles[row][col].addActionListener(this); 
          resettiles[row][col] = new Tile(tiles[row][col].getIcon(), tiles[row][col].getCurPic());
        }
      }
      
      panel.remove(mixup);
      panel.invalidate();
      panel.repaint();
      panel.add(directions);
      checkShowNumbers();
    }
    
    
    if (e.getSource() == reference){
      JFrame refimage = new JFrame("Reference Image");
      refimage.setSize(sidelength*100, sidelength*100);
      
      JPanel refpanel = new JPanel();      
      refimage.getContentPane().add(refpanel);    
      refpanel.setLayout(null); 
      
      JLabel refpic = new JLabel();
      refpic.setBounds(0, 0, sidelength*100, sidelength*100);
      ImageIcon reficon = new ImageIcon(master);
      refpic.setIcon(reficon);
      
      refpanel.add(refpic);
      refimage.add(refpanel);
      
      refimage.setVisible(true);
      
    }
    
    if (e.getSource() == shownumbers){
      shownum = !shownum;
      checkShowNumbers();
    }
    //
    if (e.getSource() == reset){
      for (int row = 0; row < sidelength; row ++)
      {
        for (int col = 0; col < sidelength; col++)
        {
          tiles[row][col].setCurPic(resettiles[row][col].getCurPic());
          tiles[row][col].setNewIcon(resettiles[row][col].getIcon());
        }
      }
      movecount = 0;
      count.setText("Move count: " + movecount);
    }
    
    
    if (e.getSource() == reshuffle){
      
      int mixmoves = 0;
      while (mixmoves <= 400)
      {
        if (checkSwitch((int)(Math.random() * sidelength), (int)(Math.random() * sidelength))) 
        {
          mixmoves++;         
        }
      }
      
      for (int row = 0; row < sidelength; row ++)
      {
        for (int col = 0; col < sidelength; col++)
        {
          resettiles[row][col] = new Tile(tiles[row][col].getIcon(), tiles[row][col].getCurPic());
        }
      }
      movecount = 0;
      count.setText("Move count: " + movecount);
    }
    
    
    for (int row = 0; row < sidelength; row ++)
    {
      for (int col = 0; col < sidelength; col++)
      {
        if (e.getSource() == tiles[row][col] && tiles[row][col].getCurPic() != blankindex){ //Ensures that clicking on the blank tile at the start of the game does not count as "winning."
          
          if (checkSwitch(row, col) == true){
            movecount++;
            count.setText("Move count: " + movecount);
            checkShowNumbers();
            
          }
          if (checkWin() == true){             
            panel.remove(directions);
            panel.invalidate();
            panel.repaint();
            win.setText("Congratulations! You solved the puzzle! You took " + movecount + " moves!");
            panel.add(win);
            removeButtonListeners();
          }
          return;
        }
      }
    }
  }
}

