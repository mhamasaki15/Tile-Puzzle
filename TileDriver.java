import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;


public class TileDriver{
  
  public static void main(String[] args){
    
    GameMenu game = new GameMenu();
    while (game.gridlength() == 0){
      try {
        Thread.sleep(200);
      }
      catch(InterruptedException e){
      }
    }
    int diff = game.gridlength();
    game.dispose(); //Destroys the Menu JFrame window.

    SlidePuzzle spuzzle = new SlidePuzzle(diff);
    
   
  }
  
}
