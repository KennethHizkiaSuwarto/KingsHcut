
import java.time.LocalDate;

import component.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;
import model.ReservationDetail;
import model.ReservationHeader;
import model.Service;
import model.ServiceType;
import model.User;
import util.Connect;

public class ReserveService {

	private Connect connect = Connect.getInstance();
	ObservableList<Service> serviceList = FXCollections.observableArrayList();
	ObservableList<String> reservationList = FXCollections.observableArrayList();
	ObservableList<ServiceType> serviceTypeList = FXCollections.observableArrayList();
	ObservableList<ReservationHeader> headerList = FXCollections.observableArrayList();
	private User user;
	private Component component = new Component();

	BorderPane bp = new BorderPane();
	VBox topvBox = new VBox();
	VBox vBoxLeft = new VBox(8);
	VBox vBoxRight = new VBox(8);
	VBox dateContainer = new VBox();
	VBox startContainer = new VBox();
	HBox hBox = new HBox(10);
	HBox checkBoxContainer = new HBox(4);

	ListView<String> reserveListView = new ListView<String>();

	DatePicker datepick = new DatePicker();
	TextField startField = new TextField();

	CheckBox hairCutCheckBox = new CheckBox("Hair Cut");
	CheckBox treatmentCheckBox = new CheckBox("Hair Treatment");
	CheckBox permingCheckBox = new CheckBox("Hair Perming");
	CheckBox coloringCheckBox = new CheckBox("Hair Coloring");
	CheckBox tattooCheckBox = new CheckBox("Hair Tattoo");

	TableView<Service> serviceTable = new TableView<>();
	TableColumn<Service, String> serviceIDColumn = new TableColumn<>("ID");
	TableColumn<Service, String> serviceNameColumn = new TableColumn<>("Name");
	TableColumn<Service, Integer> servicePriceColumn = new TableColumn<>("Price");
	TableColumn<Service, String> serviceDurationColumn = new TableColumn<>("Duration");

	Label reserveServiceLabel = new Label("Reserve Service");
	Label userLabel = new Label();
	Label reserveListLabel = new Label("Reserve List");
	Label dateLabel = new Label("Reservation Date");
	Label startLabel = new Label("Reservation Time");

	Button reserveButton = new Button("Reserve");
	Button addButton = new Button("Add");
	Button cancelButton = new Button("Cancel");

//	Pop-up window
	Stage popUpStage = new Stage();
	BorderPane bpPopUp = new BorderPane();
	Scene popUpScene = new Scene(bpPopUp, 400, 300);
	Window confirmPopup = new Window("Reserve confirmation");
	Label confirmLabel = new Label("Are you sure you want to reserve?");
	Button yButton = new Button("Yes");
	Button nButton = new Button("No");
	VBox popUpContainer = new VBox(15);
	HBox buttonContainer = new HBox(8);

	MenuBar menuBar = component.getUserMenu();

	public void addComponent() {

		serviceList = connect.getService();
		serviceTypeList = connect.getServiceType();
		headerList = connect.getReservationHeader();

		serviceIDColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceID"));
		serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
		servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("ServicePrice"));
		serviceDurationColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceDuration"));

		serviceTable.getColumns().addAll(serviceIDColumn, serviceNameColumn, servicePriceColumn, serviceDurationColumn);
		serviceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		serviceTable.setItems(serviceList);
		reserveListView.setItems(reservationList);

		checkBoxContainer.getChildren().addAll(hairCutCheckBox, treatmentCheckBox, permingCheckBox, coloringCheckBox,
				tattooCheckBox);
		dateContainer.getChildren().addAll(dateLabel, datepick);
		startContainer.getChildren().addAll(startLabel, startField);

		topvBox.getChildren().addAll(reserveServiceLabel, userLabel);
		vBoxLeft.getChildren().addAll(topvBox, checkBoxContainer, serviceTable);
		vBoxRight.getChildren().addAll(reserveListLabel, reserveListView, dateContainer, startContainer, addButton,
				cancelButton, reserveButton);
		hBox.getChildren().addAll(vBoxLeft, vBoxRight);

		bp.setTop(menuBar);
		bp.setCenter(hBox);

		// Pop-up Window
		buttonContainer.getChildren().addAll(yButton, nButton);
		popUpContainer.getChildren().addAll(confirmLabel, buttonContainer);
		confirmPopup.getContentPane().getChildren().add(popUpContainer);
		bpPopUp.setCenter(confirmPopup);

	}

	public void style() {
		bp.setPrefSize(800, 500);

		startField.setPromptText("hh:mm");
		datepick.setPromptText("dd/mm/yyyy");

		reserveListView.setPrefSize(300, 120);
		serviceTable.setMaxSize(500, 340);

		hBox.setMargin(vBoxRight, new Insets(90, 0, 0, 10));

		vBoxLeft.setAlignment(Pos.TOP_CENTER);
		vBoxLeft.setMargin(reserveServiceLabel, new Insets(10, 0, 0, 20));
		vBoxLeft.setMargin(checkBoxContainer, new Insets(10, 0, 0, 12));
		vBoxLeft.setMargin(userLabel, new Insets(10, 0, 0, 20));
		vBoxLeft.setMargin(serviceTable, new Insets(0, 0, 0, 10));

		datepick.setPrefWidth(300);
		addButton.setPrefSize(300, 30);
		cancelButton.setPrefSize(300, 30);
		reserveButton.setPrefSize(300, 30);

		vBoxLeft.setMaxWidth(500);
		reserveServiceLabel.setFont(Font.font(null, FontWeight.BOLD, 25));
		userLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 15));
		userLabel.setText("User: " + user.getUsername());
		reserveListLabel.setFont(Font.font(null, FontWeight.BOLD, 15));

//		Pop-up window
		popUpStage.setScene(popUpScene);
		popUpStage.setTitle("Reserve Confirmation Pop-Up");
		confirmPopup.setMovable(false);
		confirmPopup.setPrefSize(900, 600);
		popUpContainer.setMinSize(confirmPopup.getWidth(), confirmPopup.getHeight());
		popUpContainer.setAlignment(Pos.CENTER);
		buttonContainer.setAlignment(Pos.CENTER);
		confirmLabel.setFont(Font.font("null", FontWeight.BOLD, 20));
	}

	public BorderPane getBp() {
		return bp;
	}

	public void setBp(BorderPane bp) {
		this.bp = bp;
	}

	boolean hairCut = false;
	boolean hairTreatment = false;
	boolean hairPerming = false;
	boolean hairColoring = false;
	boolean hairTattoo = false;

	public void filter(boolean hairCut, boolean hairTreatment, boolean hairPerming, boolean hairColoring,
			boolean hairTattoo) {
		ObservableList<Service> tempoList = FXCollections.observableArrayList();

		if (hairCut) {
			for (Service service : serviceList) {
				if (service.getServiceTypeID().equals("ST001")) {
					tempoList.add(service);
				}
			}
		}
		if (hairTreatment) {
			for (Service service : serviceList) {
				if (service.getServiceTypeID().equals("ST002")) {
					tempoList.add(service);
				}
			}
		}
		if (hairPerming) {
			for (Service service : serviceList) {
				if (service.getServiceTypeID().equals("ST003")) {
					tempoList.add(service);
				}
			}
		}
		if (hairColoring) {
			for (Service service : serviceList) {
				if (service.getServiceTypeID().equals("ST004")) {
					tempoList.add(service);
				}
			}
		}
		if (hairTattoo) {
			for (Service service : serviceList) {
				if (service.getServiceTypeID().equals("ST005")) {
					tempoList.add(service);
				}
			}
		}
		if (!hairCut && !hairTreatment && !hairPerming && !hairColoring && !hairTattoo) {
			serviceTable.setItems(serviceList);
		} else {
			serviceTable.setItems(tempoList);
		}

	}

	private boolean validTimeFormat(String time) {
		String[] timeArray = time.split(":");

		for (int i = 0; i < time.length(); i++) {
			if (Character.isAlphabetic(time.charAt(i))) {
				return false;
			}
		}

		if (time.length() != 5) {
			return false;
		} else if (!time.contains(":")) {
			return false;
		} else if (Integer.parseInt(timeArray[0]) > 21 || Integer.parseInt(timeArray[0]) < 9) {
			return false;
		} else if (Integer.parseInt(timeArray[1]) > 59 || Integer.parseInt(timeArray[1]) < 0) {
			return false;
		}
		return true;
	}

	public boolean checkReservationList(String serviceName) {
		boolean validReservation = true;
		for (String reservation : reservationList) {
			if (serviceName.equals(reservation)) {
				validReservation = false;
			}
		}

		return validReservation;
	}

	private String generateReservationID(int idNumber) {
		String ReservationID = "";

		if (idNumber < 10) {
			ReservationID = "RS00" + idNumber;
		} else if (idNumber < 100) {
			ReservationID = "RS0" + idNumber;
		} else if (idNumber < 1000) {
			ReservationID = "RS" + idNumber;
		}

		return ReservationID;

	}

	private String getFinalEndReservationTime(String startReservationTime) {
		int minuteDurations = 0;
		int hoursDurations = 0;
		int minutes = 0;
		String EndReservationTime = "";
		String[] timeArray = startReservationTime.split(":");

		if (!reservationList.isEmpty()) {
			for (String string : reservationList) {
				for (Service service : serviceList) {
					if (string.equals(service.getServiceName())) {
						minuteDurations += service.getServiceDuration();
					}
				}
			}
		}

		minutes = Integer.parseInt(timeArray[1]) + minuteDurations;
		if (minutes > 59) {
			hoursDurations = (int) Math.floor(minuteDurations / 60);
			minutes = minutes % 60;

			if (minutes > 59) {
				hoursDurations++;
				minutes = minutes % 60;
			}
		} else {
			minutes = minutes + (minuteDurations % 60);
		}

		EndReservationTime = (Integer.parseInt(timeArray[0]) + hoursDurations) + ":" + minutes;

		return EndReservationTime;
	}

	private String getEndReservationTime(String startReservationTime) {
		int minuteDurations = 0;
		int hoursDurations = 0;
		int minutes = 0;
		String EndReservationTime = "";
		String[] timeArray = startReservationTime.split(":");

		if (!reservationList.isEmpty()) {
			for (String string : reservationList) {
				for (Service service : serviceList) {
					if (string.equals(service.getServiceName())) {
						minuteDurations += service.getServiceDuration();
					}
				}
			}

		}

		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).getServiceName()
					.equals(serviceTable.getSelectionModel().getSelectedItem().getServiceName())) {
				minuteDurations = minuteDurations + serviceList.get(i).getServiceDuration();
			}
		}

		minutes = Integer.parseInt(timeArray[1]) + minuteDurations;
		if (minutes > 59) {
			hoursDurations = (int) Math.floor(minuteDurations / 60);
			minutes = minutes % 60;

			if (minutes > 59) {
				hoursDurations++;
				minutes = minutes % 60;
			}
		} else {
			minutes = minutes + (minuteDurations % 60);
		}

		EndReservationTime = (Integer.parseInt(timeArray[0]) + hoursDurations) + ":" + minutes;

		return EndReservationTime;
	}

	private boolean validEndReservationTime(String EndReservationTime) {

		if (EndReservationTime.equals("21:00")) {
			return true;
		}
		String[] timeArray = EndReservationTime.split(":");

		if (Integer.parseInt(timeArray[0]) >= 21) {
			return false;
		}

		return true;
	}

	String StartReservationTime;

	public void event() {

		addButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error Reservation");

			if (serviceTable.getSelectionModel().getSelectedItem() == null) {
				alert.setContentText("There is no service selected");
				alert.show();
			} else if (datepick.getValue() == null) {
				alert.setContentText("Reservation date cannot be empty");
				alert.show();
			} else if (datepick.getValue().isBefore(LocalDate.now())) {
				alert.setContentText("Reservation date invalid");
				alert.show();
			} else if (startField.getText().equals("")) {
				alert.setContentText("Reservation time cannot be empty");
				alert.show();
			} else if (!validTimeFormat(startField.getText())) {
				alert.setContentText("Invalid time format");
				alert.show();
			} else if (!checkReservationList(serviceTable.getSelectionModel().getSelectedItem().getServiceName())) {
				alert.setContentText("Service already added");
				alert.show();
			} else if (!connect.checkValidReservationTime(("" + datepick.getValue()), startField.getText())) {
				alert.setContentText("Reservation time is already reserved by someone else");
				alert.show();
			} else if (!validEndReservationTime(getEndReservationTime(startField.getText()))) {
				alert.setContentText("Reservation time is over the Barber's open hours");
				alert.show();
			} else {
				reservationList.add(serviceTable.getSelectionModel().getSelectedItem().getServiceName());
				startField.setDisable(true);
				datepick.setDisable(true);
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Service added");
				alert.setContentText("Service successfully added");
				alert.show();
			}
		});

		cancelButton.setOnAction(e -> {
			if (reservationList.size() == 1) {
				startField.setDisable(false);
				datepick.setDisable(false);
			}

			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error Cancellation");

			if (reserveListView.getSelectionModel().getSelectedItem() == null) {
				alert.setContentText("No service selected");
				alert.show();
			} else {
				reservationList.remove(reserveListView.getSelectionModel().getSelectedIndex());
				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Cancellation Success");
				alert.setContentText("Service successfully cancelled");
				alert.show();
			}
		});

		reserveButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if (reservationList.isEmpty()) {
				alert.setHeaderText("Error Reservation");
				alert.setContentText("You haven't pick any service");
				alert.show();
			} else {
				popUpStage.show();
				yButton.setOnAction(e2 -> {
					String ReservationID = "";
					String ReservationDate = "" + datepick.getValue();
					StartReservationTime = startField.getText();
					String EndReservationTime = getFinalEndReservationTime(StartReservationTime);

					String currReservationID = headerList.get(headerList.size() - 1).getReservationID();
					int reservationNumber = Integer.parseInt(currReservationID.substring(2, 5)) + 1;
					ReservationID = generateReservationID(reservationNumber);

					ReservationHeader header = new ReservationHeader(ReservationID, user.getUserID(), ReservationDate,
							StartReservationTime, EndReservationTime, "In Progress");
					connect.insertReservationHeader(header);

					for (String string : reservationList) {
						String ServiceID = null;
						for (int i = 0; i < serviceList.size(); i++) {
							if (string.equals(serviceList.get(i).getServiceName())) {
								ServiceID = serviceList.get(i).getServiceID();
							}
						}
						ReservationDetail reservationDetail = new ReservationDetail(ReservationID, ServiceID);
						connect.insertReservationDetail(reservationDetail);
					}
					alert.setAlertType(AlertType.INFORMATION);
					alert.setHeaderText("Reservation Success");
					alert.setContentText("Reservation successfully reserved");
					alert.show();

					popUpStage.close();
					startField.setText("");
					startField.setDisable(false);
					datepick.setValue(null);
					datepick.setDisable(false);
					reservationList.clear();
				});
				nButton.setOnAction(e2 -> {
					popUpStage.close();
					e2.consume();
				});
			}

		});

		hairCutCheckBox.setOnAction(e -> {
			if (hairCutCheckBox.isSelected()) {
				hairCut = true;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			} else {
				hairCut = false;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			}
		});
		treatmentCheckBox.setOnAction(e -> {
			if (treatmentCheckBox.isSelected()) {
				hairTreatment = true;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			} else {
				hairTreatment = false;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			}
		});
		permingCheckBox.setOnAction(e -> {
			if (permingCheckBox.isSelected()) {
				hairPerming = true;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			} else {
				hairPerming = false;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			}
		});
		coloringCheckBox.setOnAction(e -> {
			if (coloringCheckBox.isSelected()) {
				hairColoring = true;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			} else {
				hairColoring = false;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			}
		});
		tattooCheckBox.setOnAction(e -> {
			if (tattooCheckBox.isSelected()) {
				hairTattoo = true;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			} else {
				hairTattoo = false;
				filter(hairCut, hairTreatment, hairPerming, hairColoring, hairTattoo);
			}
		});
	}

	public void menuEvent() {
		component.getUserLogOutMenu().setOnAction(e -> {
			Main.getScene().setRoot(new SignIn().getvBox());
		});
		component.getCustomerMenu().setOnAction(e -> {
			Main.getScene().setRoot(new CustomerReservation(user).getBp());
		});
		component.getReserveMenu().setDisable(true);

	}

	public ReserveService(User user) {
		super();
		this.user = user;
		addComponent();
		style();
		event();
		menuEvent();
	}

}
