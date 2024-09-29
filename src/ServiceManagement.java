import component.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Service;
import model.ServiceType;
import model.User;
import util.Connect;

public class ServiceManagement {
	private User user;
	private Component component = new Component();
	private Connect connect = Connect.getInstance();
	ObservableList<Service> serviceList = FXCollections.observableArrayList();
	ObservableList<ServiceType> serviceTypeList = FXCollections.observableArrayList();

	BorderPane bp = new BorderPane();
	HBox hBox = new HBox(10);
	VBox leftVbox = new VBox(8);
	VBox rightVbox = new VBox(8);
	VBox idContainer = new VBox();
	VBox typeContainer = new VBox();
	VBox nameContainer = new VBox();
	VBox priceContainer = new VBox();
	VBox durationContainer = new VBox();
	VBox buttonContainer = new VBox(8);

	TableView<Service> serviceTable = new TableView<Service>();
	TableColumn<Service, String> serviceIdColumn = new TableColumn<Service, String>("Service ID");
	TableColumn<Service, String> serviceTypeIdColumn = new TableColumn<Service, String>("Service Type ID");
	TableColumn<Service, String> serviceNameColumn = new TableColumn<Service, String>("Service Name");
	TableColumn<Service, Integer> servicePriceColumn = new TableColumn<Service, Integer>("Service Price");
	TableColumn<Service, Integer> serviceDurationColumn = new TableColumn<Service, Integer>("Service Duration");

	ComboBox<String> serviceTypeBox = new ComboBox<>();

	TextField nameField = new TextField();
	TextField priceField = new TextField();
	TextField durationField = new TextField();

	Label serviceManagementLabel = new Label("Service Management");
	Label serviceListLabel = new Label("Service List");
	Label TypeIDLabel = new Label("Service Type");
	Label NameLabel = new Label("Service Name");
	Label PriceLabel = new Label("Service Price");
	Label DurationLabel = new Label("Service Duration");

	Button addButton = new Button("Add");
	Button updateButton = new Button("Update");
	Button deleteButton = new Button("Delete");

	public BorderPane getBp() {
		return bp;
	}

	public void setBp(BorderPane bp) {
		this.bp = bp;
	}

	private ObservableList<String> fillServiceType() {
		ObservableList<ServiceType> tempoList = connect.getServiceType();
		ObservableList<String> tempoList2 = FXCollections.observableArrayList();

		for (ServiceType serviceType : tempoList) {
			tempoList2.add(serviceType.getServiceTypeName());
		}
		return tempoList2;
	}

	public void addComponent() {
		serviceList = connect.getService();
		serviceTypeList = connect.getServiceType();

		serviceTable.setItems(serviceList);
		serviceTypeBox.setItems(fillServiceType());

		serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceID"));
		serviceTypeIdColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceTypeID"));
		serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
		servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("ServicePrice"));
		serviceDurationColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceDuration"));

		serviceTable.getColumns().addAll(serviceIdColumn, serviceTypeIdColumn, serviceNameColumn, servicePriceColumn,
				serviceDurationColumn);

		nameContainer.getChildren().addAll(NameLabel, nameField);
		typeContainer.getChildren().addAll(TypeIDLabel, serviceTypeBox);
		priceContainer.getChildren().addAll(PriceLabel, priceField);
		durationContainer.getChildren().addAll(DurationLabel, durationField);
		buttonContainer.getChildren().addAll(addButton, updateButton, deleteButton);

		leftVbox.getChildren().addAll(serviceManagementLabel, serviceListLabel, serviceTable);
		rightVbox.getChildren().addAll(nameContainer, typeContainer, priceContainer, durationContainer,
				buttonContainer);

		hBox.getChildren().addAll(leftVbox, rightVbox);

		bp.setTop(component.getAdminMenu());
		bp.setCenter(hBox);

		serviceTypeBox.setValue("Haircut");
	}

	private void style() {
		serviceManagementLabel.setFont(Font.font(null, FontWeight.BOLD, 25));
		serviceListLabel.setFont(Font.font(null, FontWeight.BOLD, 15));

		serviceTable.setPrefWidth(588);

		addButton.setPrefSize(300, 30);
		updateButton.setPrefSize(300, 30);
		deleteButton.setPrefSize(300, 30);

		hBox.setAlignment(Pos.CENTER);
		hBox.setMargin(leftVbox, new Insets(10, 0, 40, 20));
		hBox.setMargin(rightVbox, new Insets(80, 20, 0, 5));

		durationField.setPromptText("Input service duration here");
		priceField.setPromptText("Input service price here");
		nameField.setPromptText("Input service name here");
	}

	private void comboBoxEvent() {
		for (ServiceType serviceType : serviceTypeList) {
			if (serviceTable.getSelectionModel().getSelectedItem().getServiceTypeID()
					.equals(serviceType.getServiceTypeID())) {
				serviceTypeBox.setValue(serviceType.getServiceTypeName());
			}
		}
	}

	private String getTypeID(String serviceTypeName) {
		String ServiceTypeID = "";
		for (ServiceType serviceType : serviceTypeList) {
			if (serviceTypeName.equals(serviceType.getServiceTypeName())) {
				ServiceTypeID = serviceType.getServiceTypeID();
			}
		}
		return ServiceTypeID;
	}

	private String generateServiceID(int idNumber) {
		String ServiceID = "";

		if (idNumber < 10) {
			ServiceID = "SV00" + idNumber;
		} else if (idNumber < 100) {
			ServiceID = "SV0" + idNumber;
		} else if (idNumber < 1000) {
			ServiceID = "SV" + idNumber;
		}

		return ServiceID;

	}

	private boolean validServiceName(String serviceName) {
		for (Service service : serviceList) {
			if (service.getServiceName().equals(serviceName)) {
				return false;
			}
		}
		return true;
	}

	private void event() {

		serviceTable.getSelectionModel().selectedItemProperty().addListener((e, oldValue, newValue) -> {
			if (newValue != null) {
				nameField.setText(newValue.getServiceName());
				priceField.setText("" + newValue.getServicePrice());
				durationField.setText("" + newValue.getServiceDuration());
				comboBoxEvent();
			}
		});

		addButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			if (nameField.getText().equals("")) {
				alert.setContentText("Service name cannot be empty");
				alert.show();
			} else if (!validServiceName(nameField.getText())) {
				alert.setContentText("Service already exist");
				alert.show();
			} else if (serviceTypeBox.getValue().equals("")) {
				alert.setContentText("No service type is selected");
				alert.show();
			} else if (priceField.getText().equals("")) {
				alert.setContentText("Service price cannot be empty");
				alert.show();
			} else if (durationField.getText().equals("")) {
				alert.setContentText("Service duration cannot be empty");
				alert.show();
			} else {
				String currServiceID = serviceList.get(serviceList.size() - 1).getServiceID();
				int serviceNumber = Integer.parseInt(currServiceID.substring(2, 5)) + 1;

				String serviceID = generateServiceID(serviceNumber);
				String serviceTypeID = getTypeID(serviceTypeBox.getValue());
				String serviceName = nameField.getText();
				int servicePrice = Integer.parseInt(priceField.getText());
				int serviceDuration = Integer.parseInt(durationField.getText());

				Service service = new Service(serviceID, serviceTypeID, serviceName, servicePrice, serviceDuration);
				serviceList.add(service);
				serviceTable.refresh();
				connect.insertService(service);

				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Service is successfully added");
				alert.show();
			}

		});

		updateButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");

			if (serviceTable.getSelectionModel().getSelectedItem() == null) {
				alert.setContentText("There is no service selected");
				alert.show();
			} else {
				String serviceID = serviceTable.getSelectionModel().getSelectedItem().getServiceID();
				String serviceTypeID = getTypeID(serviceTypeBox.getValue());
				String serviceName = nameField.getText();
				int servicePrice = Integer.parseInt(priceField.getText());
				int serviceDuration = Integer.parseInt(durationField.getText());

				Service service = new Service(serviceID, serviceTypeID, serviceName, servicePrice, serviceDuration);

				serviceList.set(serviceTable.getSelectionModel().getSelectedIndex(), service);
				serviceTable.refresh();
				connect.updateService(service);

				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Service is successfully updated");
				alert.show();
			}
		});

		deleteButton.setOnAction(e -> {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");

			if (serviceTable.getSelectionModel().getSelectedItem() == null) {
				alert.setContentText("There is no service selected");
				alert.show();
			} else {
				String serviceID = serviceTable.getSelectionModel().getSelectedItem().getServiceID();
				String serviceTypeID = getTypeID(serviceTypeBox.getValue());
				String serviceName = nameField.getText();
				int servicePrice = Integer.parseInt(priceField.getText());
				int serviceDuration = Integer.parseInt(durationField.getText());

				Service service = new Service(serviceID, serviceTypeID, serviceName, servicePrice, serviceDuration);

				serviceList.remove(serviceTable.getSelectionModel().getSelectedIndex());
				serviceTable.refresh();
				connect.deleteService(service);

				alert.setAlertType(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Service is successfully deleted");
				alert.show();
			}
		});

	}

	public void menuEvent() {
		component.getAdminLogOutMenu().setOnAction(e -> {
			Main.getScene().setRoot(new SignIn().getvBox());
		});
		component.getReservationManagement().setOnAction(e -> {
			Main.getScene().setRoot(new ReservationManagement(user).getBp());
		});

		component.getServiceManagement().setDisable(true);
	}

	public ServiceManagement(User user) {
		super();
		this.user = user;
		addComponent();
		style();
		event();
		menuEvent();
	}

}
