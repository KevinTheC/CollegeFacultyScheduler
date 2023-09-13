package Q1;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class AVLview extends VBox {
	public AVLview() {
		HBox[] arr = new HBox[6];
		for (int i=0;i<arr.length;i++) {
			arr[i]= new HBox();
			getChildren().add(arr[i]);
		}
		Pane[] panes = new Pane[6];
		Text[] texts = new Text[6];
		for (int i=0;i<texts.length;i++) {
			panes[i] = new Pane();
			panes[i].setPrefWidth(70);
			texts[i] = new Text(Time.values()[i].toString());
			panes[i].getChildren().add(texts[i]);
			((Pane) getChildren().get(i))
			.getChildren().add(panes[i]);
		}
		for (int i=0;i<arr.length;i++) {
			for (int j=0;j<7;j++) {
				Rectangle r = new Rectangle(20,20*Time.values()[i].getLength(),Color.RED);
				r.getStyleClass().add("rect");
				r.setFill(Color.RED);
				arr[i].getChildren().add(r);
			}
		}
	}
	public void refresh(Availability a) {
		
	}
	public void clear() {
		
	}
	
}
