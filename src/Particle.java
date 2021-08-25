import java.awt.*;
import java.io.OutputStream;
import java.util.ArrayList;

public class Particle {
    Vector2D position = new Vector2D();
    Vector2D velocity = new Vector2D();
    double phi;
    double alpha;
    double beta;
    float v;
    int n_t;
    int l_t;
    int r_t;
    int width;
    int height;
    int perceptionRadius;
    Color color = new Color(23, 151, 3, 255);
    ArrayList<Particle> perceivedParticles = new ArrayList<>();
    public static ArrayList<Particle> allParticles = new ArrayList<>();

    Particle(int width, int height, int perceptionRadius,float v,double beta,double alpha){
        this.width = width;
        this.height = height;
        this.perceptionRadius = perceptionRadius;
        this.v = v;
        this.beta = beta;
        this.alpha = alpha;
        double x = Math.random()*width-width/2;
        double y = Math.random()*height-height/2;
        phi = Math.random()*360-180;
        position.set((float) x,(float) y);
        velocity.set((float) Math.cos(Math.toRadians(phi)), (float) Math.toRadians(phi));
        velocity = velocity.multiply(v);
    }

    Particle(int width, int height, int perceptionRadius,float v,double beta,double alpha, float x, float y){
        this.width = width;
        this.height = height;
        this.perceptionRadius = perceptionRadius;
        this.v = v;
        this.beta = beta;
        this.alpha = alpha;
        phi = Math.random()*360-180;
        position.set( x, y);
        velocity.set((float) Math.cos(Math.toRadians(phi)), (float) Math.toRadians(phi));
        velocity = velocity.multiply(v);
    }

    private int perceive(){
        double distance;
        int count = 0;
        for(Particle p : allParticles) {
            distance = p.position.Distance(this.position);
            if (distance <= perceptionRadius && distance > 0) {
                count++;
                perceivedParticles.add(p);
            }
        }
        return count;
    }

    private int perceive(float radius){
        double distance;
        int count = 0;
        for(Particle p : allParticles) {
            distance = p.position.Distance(this.position);
            if (distance <= (double) radius && distance > 0) {
                count++;
            }
        }
        return count;
    }

    public void update(){
        double delta_phi;
        detect();
        delta_phi = alpha + beta*n_t*Math.signum(r_t - l_t);
        phi += delta_phi;
        velocity.set(v * (float) Math.cos(Math.toRadians(phi)), v * (float) Math.sin(Math.toRadians(phi)));
        position = position.add(velocity);
        perceivedParticles.clear();
    }

    public void detect(){
        double angle;
        Vector2D difference;
        n_t = 0;
        n_t = perceive();
        r_t = 0;
        l_t = 0;
        for(Particle other : perceivedParticles){
            difference = other.position.subtract(this.position);
            angle = Math.toDegrees(Vector2D.findAngleBetween(difference,velocity))
                    * Math.signum(Vector2D.Cross(difference,velocity));
            if(angle >=  0 && angle <= 180)
                l_t++;
            else r_t++;
        }
    }
    private void density(){
        if(n_t>15 && n_t <= 35)
            color = Color.BLUE;
        else if(n_t> 35)
            color = Color.yellow;
        else if(n_t>13 && n_t <=15)
            color = new Color(147, 46, 11);
        else if(perceive(20f) > 15)
            color = Color.MAGENTA;
        else
            color = Color.GREEN;
    }

    public void drawParticle(Graphics g){
        int radius = 8;
        int x = (int) position.getX() + width/2;
        int y  = height/2 - (int) position.getY();
        density();
        g.setColor(color);
        g.fillOval(x - radius/2, y - radius/2,radius,radius);
    }
}
