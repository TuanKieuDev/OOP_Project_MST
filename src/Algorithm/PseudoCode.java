package Algorithm;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.FadeTransition;

public class PseudoCode {
	private Text line1 = new Text();
	private Text line2 = new Text();
	private Text line3 = new Text();
	private Text line4 = new Text();
	private Text line5 = new Text();
	private Text line6 = new Text();
	private Text line7 = new Text();

	public PseudoCode() {
		super();
		line1.setFont(new Font("Arial", 16));
		line2.setFont(new Font("Arial", 16));
		line3.setFont(new Font("Arial", 16));
		line4.setFont(new Font("Arial", 16));
		line5.setFont(new Font("Arial", 16));
		line6.setFont(new Font("Arial", 16));
		line7.setFont(new Font("Arial", 16));
	}
	public Text getLine1() {
		return line1;
	}
	public void setLine1(Text line1) {
		this.line1 = line1;
	}
	public Text getLine2() {
		return line2;
	}
	public void setLine2(Text line2) {
		this.line2 = line2;
	}
	public Text getLine3() {
		return line3;
	}
	public void setLine3(Text line3) {
		this.line3 = line3;
	}
	public Text getLine4() {
		return line4;
	}
	public void setLine4(Text line4) {
		this.line4 = line4;
	}
	public Text getLine5() {
		return line5;
	}
	public void setLine5(Text line5) {
		this.line5 = line5;
	}
	public Text getLine6() {
		return line6;
	}
	public void setLine6(Text line6) {
		this.line6 = line6;
	}
	public Text getLine7() {
		return line7;
	}
	public void setLine7(Text line7) {
		this.line7 = line7;
	};
	
}
