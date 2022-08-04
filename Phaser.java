import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Phaser {
    private double theta;
    private double magnitude;

    private boolean selected;

    Phaser(double magnitude, double theta)
    {
        this.magnitude = magnitude;
        this.theta = theta;
        selected = false;
    }
    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getTheta() {
        return theta;
    }

    public void setThetaDeg(double theta) {
        this.theta = Math.toRadians(theta);
    }

    public void setThetaRad(double theta) {
        this.theta = theta;
    }

    public void addA()
    {
        theta = theta- 2*Math.PI/3;
    }

    public void addA2()
    {
        theta = theta- 4*Math.PI/3;
    }

    public Phaser getAddA()
    {
        return new Phaser(magnitude, theta- 2*Math.PI/3);
    }

    public Phaser getAddA2()
    {
        return new Phaser(magnitude, theta- 4*Math.PI/3);
    }


    public double getX()
    {
        return cos(theta)*magnitude*80;
    }

    public double getY()
    {
        return sin(theta)*magnitude*80;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String toString()
    {
        return "(" + magnitude + ", \u00B0" + Math.toDegrees(theta)%360 + ")";
    }

    public String toStringSimple()
    {
        double m = ((double)((int)(magnitude*100)))/100;
        double t = ((double)((int)(Math.toDegrees(theta)%360*100)))/100;
        return "(" + m + ", " + t + "\u00B0)";
    }


}
