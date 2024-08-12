package legacyCode;

import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Drag and drop coordinates
    private int xx, xy;
    
	public BaseFrame() throws HeadlessException {

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xx = e.getX();
                xy = e.getY();
            }
        });

        this.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                BaseFrame.this.setLocation(x - xx, y - xy);
            }
        });
	}
    public static void main(String[] args) {
        new BaseFrame();
    }
}
