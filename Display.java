import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import javax.imageio.*;
import javax.swing.*;

public class Display extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{

  private static Map<String, Image> images = new HashMap<String, Image>();
  
  public static Image getImage(String name)
  {
    try
    {
      Image image = images.get(name);
      if (image == null)
      {
        URL url = Display.class.getResource(name);
        if (url == null)
          throw new RuntimeException("unable to load image:  " + name);
        image = ImageIO.read(url);
        images.put(name, image);
      }
      return image;
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
  private int mouseMoveX;
  private int mouseMoveY;
  private int mouseDragX;
  private int mouseDragY;
  private boolean dragged = false;
  private JFrame frame;
  private int mouseX;
  private int mouseY;
  private int mouseRX;
  private int mouseRY;
  private Generate generate;
  private Queue<KeyEvent> keys;
  
  public Display(final int width, final int height)
  {
    keys = new ConcurrentLinkedQueue<KeyEvent>();
    mouseX = -1;
    mouseY = -1;
    
    try
    {
      SwingUtilities.invokeAndWait(new Runnable() { public void run() {
        generate = new Generate(width, height);
        
        frame = new JFrame();
        frame.setTitle("World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(Display.this);
        addMouseListener(Display.this);
        addMouseMotionListener(Display.this);
        frame.getContentPane().add(Display.this);
        
        frame.pack();
        frame.setVisible(true);
      }});
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  public void paintComponent(Graphics g)
  {
    try
    {
      generate.paintComponent(g);
    }
    catch(Exception e)
    {
      e.printStackTrace();  //show error
      setVisible(false);  //stop drawing so we don't keep getting the same error
    }
  }
  
  public void run()
  {
    while (true)
    {
      frame.setTitle(generate.getTitle());
      generate.stepAll();
      repaint();
      try { Thread.sleep(10); } catch(Exception e) { }
      
      while (!keys.isEmpty())
      {
        KeyEvent event = keys.poll();
        if (event.getID() == KeyEvent.KEY_PRESSED)
          generate.keyPressed(event.getKeyCode());
        else if (event.getID() == KeyEvent.KEY_RELEASED)
          generate.keyReleased(event.getKeyCode());
        else
          throw new RuntimeException("Unexpected event type:  " + event.getID());
      }
      
      if (mouseX != -1)
      {
        generate.mouseClicked(mouseX, mouseY);
        mouseX = -1;
        mouseY = -1;
      }
      if (mouseRX != -1)
      {
        generate.mouseReleased(mouseRX, mouseRY);
        mouseRX = -1;
        mouseRY = -1;
      }
      generate.mouseMoved(mouseMoveX,mouseMoveY);
      if(dragged) {
        generate.mouseDragged(mouseDragX, mouseDragY);
        dragged = false;
      }
    }
  }
  
  public void keyPressed(KeyEvent e)
  {
    keys.add(e);
  }
  
  public void keyReleased(KeyEvent e)
  {
    keys.add(e);
  }
  
  public void keyTyped(KeyEvent e)
  {
    //ignored
  }
  
  public void mousePressed(MouseEvent e)
  {
    mouseX = e.getX();
    mouseY = e.getY();
  }
  
  public void mouseReleased(MouseEvent e)
  {
    mouseRX = e.getX();
    mouseRY = e.getY();
  }
  
  public void mouseClicked(MouseEvent e)
  {

  }
  
  public void mouseEntered(MouseEvent e)
  {
  }
  
  public void mouseExited(MouseEvent e)
  {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    dragged = true;
    mouseDragX = e.getX();
    mouseDragY = e.getY();
  }

  public double getMouseX(){
    return mouseMoveX;
}
  public double getMouseY(){
    return mouseMoveY;
  }
  @Override
  public void mouseMoved(MouseEvent e) {
    mouseMoveX = e.getX();
    mouseMoveY = e.getY();
    //System.out.println("mouse move in display: " + mouseMoveX);


  }
}