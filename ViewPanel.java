import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static javax.swing.SwingConstants.CENTER;

// taken from https://github.com/javiergs/NielsenSkeleton

public class ViewPanel extends JPanel implements PropertyChangeListener {
	
	private JTextArea textArea;
	private JLabel urlLabel;
	private JLabel topicLabel;
	private JLabel messageLabel;
	private JTextField urlInput;
	private JTextField topicInput;

	private JLabel label;
	private Color labelColor;

	private JLabel label2;
	private Color label2Color;

	private JLabel label3;
	private Color label3Color;

	private JLabel label4;
	private Color label4Color;

	private JLabel label5;
	private Color label5Color;

	private JLabel label6;
	private Color label6Color;

	private JLabel label7;
	private Color label7Color;

	private JLabel label8;
	private Color label8Color;

	private JPanel left;
	private JPanel center;
	private JPanel right;

	
	public ViewPanel() {
		setLayout(new GridLayout(1, 3));

		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));

		urlInput = new JTextField();
		urlInput.setMaximumSize(new Dimension(1000, 20));
		urlLabel = new JLabel("Set Broker URL:");

		topicInput = new JTextField();
		topicInput.setMaximumSize(new Dimension(1000, 20));
		topicLabel = new JLabel("Set Topic Name:");

		messageLabel = new JLabel("Message Log:");
		textArea = new JTextArea();
		textArea.setEditable(false);
		left.add(urlLabel);
		left.add(urlInput);
		left.add(topicLabel);
		left.add(topicInput);
		left.add(messageLabel);
		left.add(textArea);

		right = new JPanel();
		center = new JPanel();
		right.setLayout(new GridLayout(2, 2));
		center.setLayout(new GridLayout(2, 2));
		label = new JLabel("---", CENTER);
		label2 = new JLabel("+--", CENTER);
		label3 = new JLabel("-+-", CENTER);
		label4 = new JLabel("--+", CENTER);
		label5 = new JLabel("-++", CENTER);
		label6 = new JLabel("+-+", CENTER);
		label7 = new JLabel("++-", CENTER);
		label8 = new JLabel("+++", CENTER);
		center.add(label);
		center.add(label2);
		center.add(label3);
		center.add(label4);
		right.add(label5);
		right.add(label6);
		right.add(label7);
		right.add(label8);


		add(left);
		add(center);
		add(right);

		label.setOpaque(true);
		label2.setOpaque(true);
		label3.setOpaque(true);
		label4.setOpaque(true);
		label5.setOpaque(true);
		label6.setOpaque(true);
		label7.setOpaque(true);
		label8.setOpaque(true);

		labelColor = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label2Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label3Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label4Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label5Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label6Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label7Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
		label8Color = new Color(
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				(int) (Math.random() * 256),
				255);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String newVal = evt.getNewValue().toString();
		textArea.append(newVal + "\n");

		// PROCESS newVal IN ORDER TO GET INDIVIDUAL PAD VALUES
		// REPLACE CONDITION WITH CHECK FOR CORRECT BOX
		// need to figure out why transparency causes unwanted text to be drawn behind label...
		if (labelColor.getAlpha() <= 0){
			labelColor = new Color(
					(int) (Math.random() * 256),
					(int) (Math.random() * 256),
					(int) (Math.random() * 256),
					255);
//			labelColor = new Color(
//					(int) ([0] * 256),
//					(int) ([1] * 256),
//					(int) ([2] * 256),
//					255);
		}
		else{
			float newAlpha = ((labelColor.getAlpha() - 16) / 255.0F);
			if (newAlpha <= 0){
				newAlpha = 0;
			}
			labelColor = new Color(labelColor.getColorSpace(), labelColor.getRGBColorComponents(null), newAlpha);
		}

		// SET ALL LABEL BACKGROUNDS
		label.setBackground(labelColor);
		label2.setBackground(label2Color);
		label3.setBackground(label3Color);
		label4.setBackground(label4Color);
		label5.setBackground(label5Color);
		label6.setBackground(label6Color);
		label7.setBackground(label7Color);
		label8.setBackground(label8Color);
	}
}



