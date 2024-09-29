
import component.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.ReservationHeader;
import model.User;
import util.Connect;

public class CustomerReservation {

	private User user;
	private Component component = new Component();
	private Connect connect = Connect.getInstance();
	ObservableList<ReservationHeader> reservationList = FXCollections.observableArrayList();

	BorderPane bp = new BorderPane();
	HBox hBox = new HBox(10);
	VBox leftVbox = new VBox(8);
	VBox rightVbox = new VBox(8);

	TableView<ReservationHeader> reservationTable = new TableView<ReservationHeader>();
	TableColumn<ReservationHeader, String> reservationIdColumn = new TableColumn<ReservationHeader, String>("ID");
	TableColumn<ReservationHeader, String> reservationDateColumn = new TableColumn<ReservationHeader, String>("Date");
	TableColumn<ReservationHeader, String> reservationStartColumn = new TableColumn<ReservationHeader, String>(
			"Start Time");
	TableColumn<ReservationHeader, String> reservationEndColumn = new TableColumn<ReservationHeader, String>(
			"End Time");
	TableColumn<ReservationHeader, String> reservationStatusColumn = new TableColumn<ReservationHeader, String>(
			"Status");

	Label customerReservationLabel = new Label("User Reservation");
	Label reservationListLabel = new Label("Reservation History");
	Label reservationIdLabel = new Label("ID: ");
	Label reservationDateLabel = new Label("Date: ");
	Label reservationStartLabel = new Label("Start Time: ");
	Label reservationEndLabel = new Label("End Time: ");
	Label reservationStatusLabel = new Label("Status: ");

	Button cancelButton = new Button("Cancel");

	MenuBar menuBar = component.getUserMenu();

	public BorderPane getBp() {
		return bp;
	}

	public void setBp(BorderPane bp) {
		this.bp = bp;
	}

	private void style() {
		customerReservationLabel.setText(user.getUsername() + "'s Reservation");

		customerReservationLabel.setFont(Font.font(null, FontWeight.BOLD, 25));
		reservationListLabel.setFont(Font.font(null, FontWeight.BOLD, 15));
		reservationIdLabel.setFont(Font.font(null, FontWeight.BOLD, 15));
		reservationDateLabel.setFont(Font.font(null, FontWeight.BOLD, 15));
		reservationStartLabel.setFont(Font.font(null, FontWeight.BOLD, 15));
		reservationEndLabel.setFont(Font.font(null, FontWeight.BOLD, 15));
		reservationStatusLabel.setFont(Font.font(null, FontWeight.BOLD, 15));

		cancelButton.setPrefSize(300, 30);

		reservationTable.setMaxSize(500, 340);

		hBox.setMargin(leftVbox, new Insets(10, 0, 0, 20));
		hBox.setMargin(rightVbox, new Insets(130, 0, 0, 10));

		hBox.setAlignment(Pos.CENTER_LEFT);
	}

	private ObservableList<ReservationHeader> getReservationHeaderByID() {
		ObservableList<ReservationHeader> tempoList = connect.getReservationHeader();
		ObservableList<ReservationHeader> tempoList2 = FXCollections.observableArrayList();

		for (int i = 0; i < tempoList.size(); i++) {
			if (tempoList.get(i).getUserID().equals(user.getUserID())) {
				tempoList2.add(tempoList.get(i));
			}
		}

		return tempoList2;
	}

	private void updateReservationList(ReservationHeader reservationHeader) {
		for (int i = 0; i < reservationList.size(); i++) {
			if (reservationList.get(i).getReservationID().equals(reservationHeader.getReservationID())) {
				reservationList.get(i).setReservationStatus("Cancelled");
				break;
			}
		}

	}

	private void addComponent() {
		reservationList = getReservationHeaderByID();

		reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));
		reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationDate"));
		reservationStartColumn.setCellValueFactory(new PropertyValueFactory<>("StartReservationTime"));
		reservationEndColumn.setCellValueFactory(new PropertyValueFactory<>("EndReservationTime"));
		reservationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationStatus"));
		reservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		reservationTable.getColumns().addAll(reservationIdColumn, reservationDateColumn, reservationStartColumn,
				reservationEndColumn, reservationStatusColumn);

		leftVbox.getChildren().addAll(customerReservationLabel, reservationListLabel, reservationTable);
		rightVbox.getChildren().addAll(reservationIdLabel, reservationDateLabel, reservationStartLabel,
				reservationEndLabel, reservationStatusLabel, cancelButton);

		hBox.getChildren().addAll(leftVbox, rightVbox);

		bp.setTop(component.getUserMenu());
		bp.setCenter(hBox);

		reservationTable.setItems(reservationList);

	}

	private void event() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Error Cancellation");

		reservationTable.getSelectionModel().selectedItemProperty().addListener((e, oldValue, newValue) -> {
			if (newValue != null) {
				reservationIdLabel
						.setText("ID: " + reservationTable.getSelectionModel().getSelectedItem().getReservationID());
				reservationDateLabel.setText(
						"Date: " + reservationTable.getSelectionModel().getSelectedItem().getReservationDate());
				reservationStartLabel.setText("Start Time: "
						+ reservationTable.getSelectionModel().getSelectedItem().getStartReservationTime());
				reservationEndLabel.setText(
						"End Time: " + reservationTable.getSelectionModel().getSelectedItem().getEndReservationTime());
				reservationStatusLabel.setText(
						"Status: " + reservationTable.getSelectionModel().getSelectedItem().getReservationStatus());
			}
		});

		cancelButton.setOnAction(e -> {
			if (reservationTable.getSelectionModel().getSelectedItem() == null) {
				alert.setContentText("No reservation selected");
				alert.show();
			} else if (reservationTable.getSelectionModel().getSelectedItem().getReservationStatus()
					.equals("Finished")) {
				alert.setContentText("Cannot cancel finished reservation");
				alert.show();
			} else if (reservationTable.getSelectionModel().getSelectedItem().getReservationStatus()
					.equals("Cancelled")) {
				alert.setContentText("Reservation is already cancelled");
				alert.show();
			} else {
				updateReservationList(reservationTable.getSelectionModel().getSelectedItem());
				connect.cancelReservation(reservationTable.getSelectionModel().getSelectedItem());
				reservationTable.refresh();
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Cancellation Success");
				alert.setContentText("Your reservation is successfully cancelled");
				alert.show();
			}
		});

	}

	public void menuEvent() {
		component.getUserLogOutMenu().setOnAction(e -> {
			Main.getScene().setRoot(new SignIn().getvBox());
		});
		component.getReserveMenu().setOnAction(e -> {
			Main.getScene().setRoot(new ReserveService(user).getBp());
		});
		component.getCustomerMenu().setDisable(true);

	}

	public CustomerReservation(User user) {
		this.user = user;
		addComponent();
		style();
		event();
		menuEvent();

	}

}
