import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.User;

public class Start
{
	public static void startMainThread(String username, String sessionId) {
		boolean fullscreen = false;
		int width = 854;
		int height = 480;
		Frame frame = new Frame("Minecraft");
		Canvas canvas = new Canvas();
		frame.setLayout(new BorderLayout());
		frame.add(canvas, "Center");
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setLocationRelativeTo(null);
		Minecraft mc = new Minecraft(canvas, width, height, fullscreen);
		if(username != null && sessionId != null) {
			 mc.user = new User(username, sessionId);
		} else {
			 mc.user = new User("Player" + System.currentTimeMillis() % 1000L, "");
		}
		mc.width = width;
		mc.height = height;
		Thread mcThread = new Thread(mc, "Minecraft main thread");
		mcThread.setPriority(10);
		frame.setVisible(true);
		frame.addWindowListener(new GameWindowListener(mc, mcThread));
		mcThread.start();
	}

	public static void main(String[] args) {
		String username = "Player" + System.currentTimeMillis() % 1000L;
		if(args.length > 0) {
			username = args[0];
		}

		String sessionId = "-";
		if(args.length > 1) {
			sessionId = args[1];
		}

		startMainThread(username, sessionId);
	}

}