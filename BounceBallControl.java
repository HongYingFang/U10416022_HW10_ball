import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.paint.Paint;

public class BounceBallControl extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		BallPane ballPane = new BallPane();
		ballPane.setStyle("-fx-border-color:black");

		Button btAdd = new Button("+");
		Button btSubtract = new Button("-");
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(btAdd, btSubtract);
		hBox.setAlignment(Pos.CENTER);

		// Add or remove a ball
		btAdd.setOnAction(e -> ballPane.add());
		btSubtract.setOnAction(e -> ballPane.subtract());

		// Pause and resume animation
		ballPane.setOnMousePressed(e -> ballPane.pause());
		ballPane.setOnMouseReleased(e -> ballPane.play());

		// Use a scroll bar to control animation speed
		ScrollBar sbSpeed = new ScrollBar();
		sbSpeed.setMax(20);
		sbSpeed.setValue(10);
		ballPane.rateProperty().bind(sbSpeed.valueProperty());

		BorderPane pane = new BorderPane();
		pane.setCenter(ballPane);
		pane.setTop(sbSpeed);
		pane.setBottom(hBox);

		// Create a scene and place the pane in the stage
		Scene scene = new Scene(pane, 450, 300);
		primaryStage.setTitle("BounceBall"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	private class BallPane extends Pane {
		private Timeline animation;

		public BallPane() {
			// Create an animation for moving the ball
			animation = new Timeline(new KeyFrame(Duration.millis(50), e -> moveBall()));
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.play(); // Start animation
		}

		public void add() {
			// Use array to set the ball color at random
			Random random = new Random();
			Paint[] color = { Color.TOMATO, Color.SKYBLUE, Color.YELLOW, Color.PURPLE, Color.SPRINGGREEN };
			int ballcolor = random.nextInt(5);
			int ballcolor2 = random.nextInt(5);
			// Use array to set the ball size at random
			double size1[] = { 10, 15, 20, 30, 45 };
			int ballsize = random.nextInt(5);
			int ballsize2 = random.nextInt(5);
			getChildren().add(new Ball(50, 200, size1[ballsize], (Paint) color[ballcolor]));
			getChildren().add(new Ball(250, 150, size1[ballsize2], (Paint) color[ballcolor2]));
			

		}

		public void subtract() {
			if (getChildren().size() > 0) {
				getChildren().remove(getChildren().size() - 1);
			}
		}

		public void play() {
			animation.play();
		}

		public void pause() {
			animation.pause();
		}

		public void increaseSpeed() {
			animation.setRate(animation.getRate() + 0.1);
		}

		public void decreaseSpeed() {
			animation.setRate(animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
		}

		public DoubleProperty rateProperty() {
			return animation.rateProperty();
		}

		protected void moveBall() {
			for (Node node : this.getChildren()) {
				Ball ball = (Ball) node;
				Ball ball2 = (Ball) node;
				// Check boundaries
				if (ball.getCenterX() < ball.getRadius() || ball.getCenterX() > getWidth() - ball.getRadius()) {
					ball.dx *= -1; // Change ball move direction
				}
				if (ball.getCenterY() < ball.getRadius() || ball.getCenterY() > getHeight() - ball.getRadius()) {
					ball.dy *= -1; // Change ball move direction
				}
				
				if (ball2.getCenterX() < ball2.getRadius() || ball2.getCenterX() > getWidth() - ball2.getRadius()) {
					ball.dx2 *= -1; // Change ball move direction
				}
				if (ball2.getCenterY() < ball2.getRadius() || ball2.getCenterY() > getHeight() - ball2.getRadius()) {
					ball.dy2 *= -1; // Change ball move direction
				}

				// Adjust ball position
				ball.setCenterX(ball.dx + ball.getCenterX());
				ball.setCenterY(ball.dy + ball.getCenterY());
				ball2.setCenterX(ball2.dx2+ ball2.getCenterX());
				ball2.setCenterY(ball2.dy2 + ball2.getCenterY());
			}
		}
	}

	public class Ball extends Circle {
		private double dx = 1, dy = 1;
		private double dx2 = 1, dy2 = 1;
		public Ball(double centerX, double centerY, double radius, Paint fill) {
			super(centerX, centerY, radius, fill);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
