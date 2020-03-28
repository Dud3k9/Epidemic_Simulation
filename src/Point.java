import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.sun.prism.impl.BufferUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Point {


    GL2 gl = EvantListenser.gl;
    public static ArrayList<Point> points = new ArrayList();
    public static int infectedCount = 0;
    public static int deadCount = 0;
    public static int healedCount = 0;
    private int x, y;
    private float[] color = {0, 0, 1};
    private boolean infected = false;
    private boolean dead = false;
    private boolean healed = false;
    private long infectedTime;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        gl.glPointSize(15);
        gl.glColor3fv(FloatBuffer.wrap(color));
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2f(x, y);
        gl.glEnd();
        points.add(this);
    }

    public void live() {
        setColor(color);
        if (!isDead())
            walk(3);
        checkInfection();
        checklive();
        show();
    }

    private void checklive() {
        if (this.isInfected()) {
            if (System.currentTimeMillis() - this.getInfectedTime() > 30000) {
                double deadP = (Math.random() * 100 + 1);
                if (deadP < 3.5)
                    setDead(true);
                else
                    setHealed(true);
            }
        }
    }

    private void checkInfection() {
        for (Point p2 : points) {
            if (distance(this, p2) <= 1 && p2.isInfected() && !this.isInfected() && !this.isDead() && !isHealed()) {
                this.setInfected(true);
            }
        }
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }

    private void walk(int speed) {
        int tmp = (int) (Math.random() * 3);
        if (tmp == 2)
            x -= speed;
        else
            x += tmp * speed;
        tmp = (int) (Math.random() * 3);
        if (tmp == 2)
            y -= speed;
        else
            y += tmp * speed;

        if (x >= EvantListenser.windowSize)
            x--;
        else if (x <= 0)
            x++;
        if (y >= EvantListenser.windowSize)
            y--;
        else if (y <= 0)
            y++;
    }

    private void show() {
        gl.glPointSize(10);
        gl.glColor3fv(FloatBuffer.wrap(color));
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2f(x, y);
        gl.glEnd();
    }

////////////////geters, seters


    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        setInfected(false);
        setColor(new float[]{0.5f, 0.5f, 0.5f});
        this.dead = dead;
        if(dead)
            deadCount++;
        else
            deadCount--;
    }

    public boolean isHealed() {
        return healed;
    }

    public void setHealed(boolean healed) {
        setInfected(false);
        setColor(new float[]{0, 1, 0});
        this.healed = healed;
        if(healed)
            healedCount++;
        else
            healedCount--;
    }

    public long getInfectedTime() {
        return infectedTime;
    }

    public void setInfectedTime(long infectedTime) {
        this.infectedTime = infectedTime;
    }

    public static int getInfectedCount() {
        return infectedCount;
    }

    public static void setInfectedCount(int infectedCount) {
        Point.infectedCount = infectedCount;
    }

    public boolean isInfected() {
        return infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
        setColor(new float[]{1, 0, 0});
        setInfectedTime(System.currentTimeMillis());
        if (infected)
            infectedCount++;
        else
            infectedCount--;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }
}
