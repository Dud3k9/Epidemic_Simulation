import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;


public class EvantListenser implements GLEventListener {
    public static GL2 gl = null;
    public static int windowSize=1000;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);

        for (int i = 0; i < 999; i++)
            new Point((int) (Math.random()*windowSize+1), (int) ((Math.random()*windowSize)+1));
        new Point(windowSize/2,windowSize/2).setInfected(true);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        for (Point p : Point.points) {
            p.live();
        }

        gl.glColor3f(0,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(0,0);
        gl.glVertex2f(300,0);
        gl.glVertex2f(300,120);
        gl.glVertex2f(0,120);
        gl.glEnd();


        TextRenderer textRenderer=new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
        textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
        gl.glColor3f(1,0,0);
        textRenderer.draw("infected: "+Point.infectedCount+"/"+Point.points.size(),0,0);
        textRenderer.endRendering();

        textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
        gl.glColor3f(0,1,0);
        textRenderer.draw("healed: "+Point.healedCount+"/"+Point.points.size(),0,20);
        textRenderer.endRendering();

        textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
        gl.glColor3f(0.5f,0.5f,0.5f);
        textRenderer.draw("dead: "+Point.deadCount+"/"+Point.points.size(),0,40);
        textRenderer.endRendering();

        textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
        gl.glColor3f(0,0,1);
        textRenderer.draw("undaunted: "+(Point.points.size()-(Point.deadCount+Point.healedCount+Point.infectedCount))+"/"+Point.points.size(),0,60);
        textRenderer.endRendering();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GLMatrixFunc.GL_MATRIX_MODE);
        gl.glLoadIdentity();
        gl.glOrtho(0, windowSize, 0, windowSize, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
