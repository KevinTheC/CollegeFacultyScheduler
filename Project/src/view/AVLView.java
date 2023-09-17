package view;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Availability;
import model.Day;
import model.Time;
public class AVLView extends VBox {
	private final static int vStart = 1;
	private final static int hStart = 1;
	public AVLView() {
		this.setId("AvailabilityViewer");
		HBox[] arr = new HBox[7];
		for (int i=0;i<arr.length;i++) {
			arr[i]= new HBox();
			getChildren().add(arr[i]);
		}
		setTopLabels(arr[0]);
		setLabels();
		addRectangles();
	}
	private void setTopLabels(HBox hbox) {
		Pane pane = new Pane();
		pane.setPrefSize(70, 10);
		hbox.getChildren().add(pane);
		for (int i=0;i<7;i++) {
			pane = new Pane();
			pane.setPrefSize(21, 10);
			pane.getChildren().add(new Text(Day.values()[i].name()));
			hbox.getChildren().add(pane);
		}
	}
	private void addRectangles() {
		for (int i=vStart;i<vStart+6;i++) {
			for (int j=0;j<7;j++) {
				Rectangle r = new Rectangle(20,20*Time.values()[i-vStart].getLength(),Color.PINK);
				((Pane)getChildren().get(i)).getChildren().add(r);
			}
		}
	}
	private void setLabels() {
		HBox[] panes = new HBox[7];
		Text[] texts = new Text[7];
		for (int i=0;i<7;i++) {
			panes[i] = new HBox();
			panes[i].setPrefWidth(70);
			panes[i].setMaxWidth(70);
			if (i<6)
				texts[i] = new Text(Time.values()[i].toString().split("[-]")[0]);
			else
				texts[6] = new Text(Time.values()[5].toString().split("[-]")[1]);
			panes[i].setAlignment(Pos.TOP_RIGHT);
			panes[i].getChildren().add(texts[i]);
			if (i<6)
				((Pane) getChildren().get(i+vStart)).getChildren().add(panes[i]);
		}
		getChildren().add(panes[6]);
	}
	public void refresh(Availability a) {
		for (int i=vStart;i<6+vStart;i++) {
			for (int j=hStart;j<7+hStart;j++) {
				Rectangle r = (Rectangle)((Pane)getChildren().get(i)).getChildren().get(j);
				if (a.available(Time.values()[i-vStart], Day.values()[j-hStart])) {
					if (r.getFill()!=Color.LIMEGREEN)
						r.setFill(Color.LIMEGREEN);
				} else {
					if (r.getFill()!=Color.PINK)
						r.setFill(Color.PINK);
				}
			}
		}
	}
	//TODO move to controller
	public void clear() {
		for (int i=vStart;i<6+vStart;i++) {
			for (int j=hStart;j<7+hStart;j++) {
				Rectangle r = (Rectangle)((Pane)getChildren().get(i)).getChildren().get(j);
				if (r.getFill()!=Color.PINK)
					r.setFill(Color.PINK);
			}
		}
	}
	
}
