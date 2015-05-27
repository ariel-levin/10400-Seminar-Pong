package view.utils;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class PopupMessage {

	public static Popup createPopup(final String message, final Stage stage) {
		
		final Popup popup = new Popup();
		
		popup.setAutoFix(true);
		popup.setAutoHide(true);
		popup.setHideOnEscape(true);
		Label label = new Label(message);
		label.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				popup.hide();
			}
		});
		label.getStylesheets().add("resources/css/styles.css");
		label.getStyleClass().add("popup");
		popup.getContent().add(label);

		popup.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
				popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
			}
		});

		return popup;
	}
	
}
