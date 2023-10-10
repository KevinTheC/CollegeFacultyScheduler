package control;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import chrono.TimeRange;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Availability;
import model.Course;
import model.Day;

public class AvailabilityController {
	@FXML
	private Rectangle monday;
	@FXML
	private Rectangle tuesday;
	@FXML
	private Rectangle wednesday;
	@FXML
	private Rectangle thursday;
	@FXML
	private Rectangle friday;
	@FXML
	private Rectangle saturday;
	@FXML
	private Rectangle sunday;
	private Rectangle[] rects;
	private Color[] colors;
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void setInstance(Parent root, Stage stage, Scene scene) {
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		rects = new Rectangle[] {monday,tuesday,wednesday,thursday,friday,saturday,sunday};
		colors = new Color[] {Color.RED,Color.BLUE,Color.BLACK,Color.GREEN};
	}
	public void refresh(Availability avl) {
		int min = 420;
		int max = 900;
		int col = 0;
		HashMap<Course,Color> legend = new HashMap<>();
		for (Day day : Day.values()) {
			List<TimeRange<Course>> list = avl.get(day).toRanges();
			List<Stop> stops = new LinkedList<>();
			for (int i=0;i<list.size();i++) {
				if (!legend.containsKey(list.get(i).getType()))
					legend.put(list.get(i).getType(), colors[col++]);
				stops.add(new Stop((list.get(i).getBegin().toMin()-min)/(1.0*max),legend.get(list.get(i).getType())));
				stops.add(new Stop((list.get(i).getEnd().toMin()-min)/(1.0*max),legend.get(list.get(i).getType())));
			}
			rects[day.ordinal()].setFill(new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE,stops));
		}
	}
}
