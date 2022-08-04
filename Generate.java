import java.awt.*;

public class Generate {
    private static final int width = 800;
    private static final int height = 500;
    private static int mouseX = 0;
    private static int mouseY = 0;
    private static int mouseDragX = -1;
    private static int mouseDragY = -1;
    private static PhaserDiagram phaserDiagram;
    private static PhaserDiagram posotive;
    private static PhaserDiagram negative;
    private static Phaser zero;
    public Generate(int w, int h) {
        phaserDiagram = new PhaserDiagram(
                new Phaser(1, 0),
                new Phaser(1, Math.PI * 2 / 3),
                new Phaser(1, Math.PI * 4 / 3));
        posotive = new PhaserDiagram(
                new Phaser(1, 0),
                new Phaser(1, Math.PI * 2 / 3),
                new Phaser(1, Math.PI * 4 / 3));
        negative = new PhaserDiagram(
                new Phaser(0, 0),
                new Phaser(0, 0),
                new Phaser(0, 0));
        zero = new Phaser(0, 0);

    }

    public static void main(String[] args) {


        run();
    }

    public static void run() {
        Display display = new Display(width, height);
        display.run();
    }

/*
    [1,1,1 ]
    [1,a2,a]
    [1,a,a2]
*/
    public void stepAll() {
        Phaser I0 = phaserDiagram.getPhaserA();
        Phaser I1 = phaserDiagram.getPhaserB();
        Phaser I2 = phaserDiagram.getPhaserC();
        Phaser a = new Phaser(1,2*Math.PI/3);
        Phaser a2 = new Phaser(1,4*Math.PI/3);

        //positive
        double x1 = I0.getMagnitude()*Math.cos(I0.getTheta());
        double y1 = I0.getMagnitude()*Math.sin(I0.getTheta());
        Phaser I1a = I1.getAddA();
        double x2 = x1 + I1a.getMagnitude()*Math.cos(I1a.getTheta());
        double y2 = y1 + I1a.getMagnitude()*Math.sin(I1a.getTheta());
        Phaser I2a2 = I2.getAddA2();
        double x3 = x2 + I2a2.getMagnitude()*Math.cos(I2a2.getTheta());
        double y3 = y2 + I2a2.getMagnitude()*Math.sin(I2a2.getTheta());

        Phaser posPhaser = new Phaser(Math.sqrt(x3*x3+y3*y3)/3,Math.atan2(y3,x3));
        posotive.setPhaserA(posPhaser);
        posotive.setPhaserB(posPhaser.getAddA());
        posotive.setPhaserC(posPhaser.getAddA2());


        //neg
         x1 = I0.getMagnitude()*Math.cos(I0.getTheta());
         y1 = I0.getMagnitude()*Math.sin(I0.getTheta());
        Phaser I1a2 = I1.getAddA2();
         x2 = x1 + I1a2.getMagnitude()*Math.cos(I1a2.getTheta());
         y2 = y1 + I1a2.getMagnitude()*Math.sin(I1a2.getTheta());
        Phaser I2a = I2.getAddA();
         x3 = x2 + I2a.getMagnitude()*Math.cos(I2a.getTheta());
         y3 = y2 + I2a.getMagnitude()*Math.sin(I2a.getTheta());

        Phaser negPhaser = new Phaser(Math.sqrt(x3*x3+y3*y3)/3,Math.atan2(y3,x3));
        negative.setPhaserA(negPhaser);
        negative.setPhaserB(negPhaser.getAddA());
        negative.setPhaserC(negPhaser.getAddA2());


        //zero
        x1 = I0.getMagnitude()*Math.cos(I0.getTheta());
        y1 = I0.getMagnitude()*Math.sin(I0.getTheta());
        x2 = x1 + I1.getMagnitude()*Math.cos(I1.getTheta());
        y2 = y1 + I1.getMagnitude()*Math.sin(I1.getTheta());
        x3 = x2 + I2.getMagnitude()*Math.cos(I2.getTheta());
        y3 = y2 + I2.getMagnitude()*Math.sin(I2.getTheta());




        zero = new Phaser(Math.sqrt(x3*x3+y3*y3)/3,Math.atan2(y3,x3));



    }


    public Phaser addPhasers(Phaser a, Phaser b)
    {
        double x = a.getMagnitude()*Math.cos(a.getTheta())+b.getMagnitude()*Math.cos(b.getTheta());
        double y = a.getMagnitude()*Math.sin(a.getTheta())+b.getMagnitude()*Math.sin(b.getTheta());

        double mag = Math.sqrt(x*x+y*y);
        double theta = Math.atan(y/x);

        return new Phaser(mag,theta);
    }


    public Phaser multiplyPhasers(Phaser a, Phaser b)
    {
        return new Phaser(a.getMagnitude()*b.getMagnitude(),a.getTheta()+b.getTheta());
    }


    public void mouseClicked(int x, int y) {
    }

    public void mouseReleased(int x, int y) {
        phaserDiagram.setSelected(false);
    }


    public void keyPressed(int key) {


    }


    public void keyReleased(int key) {
        phaserDiagram.getPhaserA().addA();
    }

    public void mouseMoved(int x, int y) {
        mouseX = x;
        mouseY = y;
        mouseDragX = -1;
        mouseDragY = -1;
    }

    public void mouseDragged(int x, int y) {
        mouseDragX = x;
        mouseDragY = y;
    }

    public String getTitle() {
        return "Decomposer";
    }


    public void drawSymmetricComponentVector(Graphics g, Color c, Phaser p, int n, boolean button) {
        double x1 = (width * n) / 5;
        double y1 = height / 2;
        double x2 = p.getX() + x1;
        double y2 = p.getY() + height / 2;

        if (button) {
            g.setColor(Color.lightGray);
            int r = 5;
            if (Math.sqrt(Math.pow(mouseX - x2, 2) + Math.pow(mouseY - y2, 2)) < 10 || p.isSelected()) {
                r = 8;
                if (mouseDragX != -1) {
                    p.setSelected(true);
                    p.setMagnitude(Math.sqrt(Math.pow(mouseDragX - x1, 2) + Math.pow(mouseDragY - y1, 2))/80);
                    p.setThetaRad(Math.atan2(mouseDragY - y1, mouseDragX - x1));
                    x2 = p.getX() + x1;
                    y2 = p.getY() + height / 2;
                }
            }
            g.drawImage(Display.getImage("img.png"), (int) (x2 - r / 2), (int) y2 - r / 2, r, r, null);
        }
        g.setColor(c);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        g.setColor(Color.white);
        Font font  = new Font(p.toString(),Font.PLAIN,9);
        g.setFont(font);
        g.drawString(p.toStringSimple(),(int)x2,(int)y2-10);
    }

    public void drawPhaserDiagram(Graphics g, PhaserDiagram diagram, int n,Boolean button)
    {
        drawSymmetricComponentVector(g, new Color(255, 0, 0), diagram.getPhaserA(), n, button);
        drawSymmetricComponentVector(g, new Color(0, 255, 0), diagram.getPhaserB(), n, button);
        drawSymmetricComponentVector(g, new Color(0, 0, 255), diagram.getPhaserC(), n, button);
    }

    public void paintComponent(Graphics g) {


        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, width, height);

        drawPhaserDiagram(g, phaserDiagram, 1, true);

        drawPhaserDiagram(g, posotive, 2, false);

        drawPhaserDiagram(g, negative, 3, false);

        drawSymmetricComponentVector(g, new Color(255, 0, 0), zero, 4, false);





    }
}