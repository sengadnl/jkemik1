/**
 * 
 */
package view;


import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author dalet
 * 
 */
public class JKIcon extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon imageIcon;
	//private Image image;

	private String path_, desc;

	public JKIcon(String path, String desc) {
		this.desc = desc;
		this.path_ = path;
	}
	public void setIcon(String path){
		this.path_ = path;
		//createIcon();
	}

	public JLabel createIcon() {
		URL imgURl = getClass().getResource(this.path_);
		if (imgURl != null) {
			this.imageIcon = new ImageIcon(imgURl, this.desc);
			return new JLabel(this.imageIcon);
		}

		return null;
	}

	public ImageIcon createImageIcone() {
		URL imgURl = getClass().getResource(this.path_);
		if (imgURl != null) {
			return new ImageIcon(imgURl, this.desc);
		} 

		return this.imageIcon;
	}
	

	public BufferedImage createBufferedImageIcone() {
		try {
			if (new URL(this.path_) != null) {
				// return new ImageIcon(imgURl, desc);
				return ImageIO.read(new URL(this.path_));
			} 
		} catch (Exception e) {

		}
		return null;
	}
}
