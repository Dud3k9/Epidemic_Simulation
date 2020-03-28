import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

public class Renderer {

    private static GLWindow window=null;

    public static void init(){
        GLProfile.initSingleton();
        GLProfile profile=GLProfile.get(GLProfile.GL2);
        GLCapabilities cops=new GLCapabilities(profile);


        window=GLWindow.create(cops);
        window.setSize(640,640);
        window.setTitle("Epidemic simulation");
        window.setResizable(true);
        window.setVisible(true);
        window.addGLEventListener(new EvantListenser());

        FPSAnimator fpsAnimator=new FPSAnimator(window, 45);
        fpsAnimator.start();
    }

    public static void main(String[] args) {
        init();
    }
}
