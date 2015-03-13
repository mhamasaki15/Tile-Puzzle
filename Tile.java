import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Tile extends JButton{
  private int curpic;
  private int correctindex;
  private Image currentImage;
  
  public Tile(BufferedImage img, int current){
    correctindex = current;
    curpic = current;
    currentImage = img;
    ImageIcon icon = new ImageIcon(img);
    setIcon(icon);
    setSize(100, 100);
  }
    
  
  public Image getImage(){    
    return currentImage;
  }
  
  public void switchTiles(Tile adjacent){
  }
  
}