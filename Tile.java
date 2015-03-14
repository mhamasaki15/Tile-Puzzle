import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Tile extends JButton{
  private int curpic;
  private int correctindex;
  private ImageIcon icon;
  
  public Tile(BufferedImage img, int current){
    correctindex = current;
    curpic = current;
    icon = new ImageIcon(img);
    setIcon(icon);
    setSize(100, 100);
  }
    
  public int getCurPic(){
    return curpic;
  }
  
  public void setCurPic(int newindex){
    curpic = newindex;
  }
  
  public ImageIcon getIcon(){
    return icon;
  }
  
  public void setNewIcon(ImageIcon newicon){
    icon = newicon;
    setIcon(newicon);
  }
  
  
  public void swap(Tile blank){
    int origblankindex = blank.getCurPic();
    ImageIcon origblankicon = blank.getIcon();
    blank.setCurPic(this.getCurPic());
    blank.setNewIcon(this.getIcon());
    setCurPic(origblankindex);
    setNewIcon(origblankicon);
    
    
  }
  
}