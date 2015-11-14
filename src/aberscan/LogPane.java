package aberscan;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class LogPane extends JPanel implements Runnable {
	static final long serialVersionUID = 3;
    JTextArea log;
    Thread runner;
    String text;
    JFrame frame;
    
    public LogPane(JFrame frame) {
        log = new JTextArea(15,88);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        this.frame = frame;
        JScrollPane logScrollPane = new JScrollPane(log);
        //Add the scroll pane to this panel.
        add(logScrollPane);
        
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }
    
    public void println(String text) {
    	text = text + "\n";
		log.append(text);
	}
    
    public void print(String text) {
		log.append(text);
	}
    
    public void run() {
        try {
	        while (true) {
	        	log.append(text);
	        	repaint();
	        	frame.repaint();
                Thread.sleep(1000);
	        }
        }
        catch (InterruptedException e) { }
    }
}

