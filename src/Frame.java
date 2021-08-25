import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class Frame extends JFrame implements KeyListener{
    private Image raster;
    private Graphics rGraphics;
    private final int height;
    private final int width;
    private  boolean keyPressed;


    Frame(int height, int width){
        this.height = height;
        this.width = width;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width,height);
        setVisible(true);
        setup();
        keyPressed = true;
        addKeyListener(this);
    }

    public void setup(){
        raster = this.createImage(width,height);
        rGraphics = raster.getGraphics();

        for(int i = 0; i<2000; i++) {
            Particle p = new Particle(width, height,65,0.67f,17,180);
            Particle.allParticles.add(p);
        }

    }


    public void draw(){
        int t = 0;
        while(keyPressed)
        {
            drawBG();  //draws background
            particles();
            getGraphics().drawImage(raster,0,0,getWidth(),getHeight(),null);
            try{Thread.sleep(1);}catch(Exception e){}
            t++;
            System.out.println(t);
        }
    }


    private void particles(){
        for(Particle p : Particle.allParticles){
            p.update();
            p.drawParticle(rGraphics);
        }
    }
    private void drawBG(){
        rGraphics.setColor(Color.black);
        rGraphics.fillRect(0,0,width,height);
    }

    public void keyTyped(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            keyPressed = false;
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            keyPressed = false;
    }

    public void keyReleased(KeyEvent e){

    }

}
