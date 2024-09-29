package component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Component {

	MenuBar userMenuBar;
	MenuBar adminMenuBar;
	Menu userMenu;
	Menu adminMenu;
	MenuItem userLogOutMenu;
	MenuItem adminLogOutMenu;
	MenuItem ReserveMenu;
	MenuItem CustomerMenu;

	MenuItem ServiceManagement;
	MenuItem ReservationManagement;

	public MenuItem getServiceManagement() {
		return ServiceManagement;
	}

	public void setServiceManagement(MenuItem serviceManagement) {
		ServiceManagement = serviceManagement;
	}

	public MenuItem getReservationManagement() {
		return ReservationManagement;
	}

	public void setReservationManagement(MenuItem reservationManagement) {
		ReservationManagement = reservationManagement;
	}

	public MenuItem getReserveMenu() {
		return ReserveMenu;
	}

	public MenuItem getUserLogOutMenu() {
		return userLogOutMenu;
	}

	public void setUserLogOutMenu(MenuItem userLogOutMenu) {
		this.userLogOutMenu = userLogOutMenu;
	}

	public MenuItem getAdminLogOutMenu() {
		return adminLogOutMenu;
	}

	public void setAdminLogOutMenu(MenuItem adminLogOutMenu) {
		this.adminLogOutMenu = adminLogOutMenu;
	}

	public void setReserveMenu(MenuItem reserveMenu) {
		ReserveMenu = reserveMenu;
	}

	public MenuItem getCustomerMenu() {
		return CustomerMenu;
	}

	public void setCustomerMenu(MenuItem customerMenu) {
		CustomerMenu = customerMenu;
	}

	public MenuBar getUserMenu() {

		return userMenuBar;
	}

	public void addUserComponent() {
		userMenuBar = new MenuBar();
		userMenu = new Menu("Menu");
		userLogOutMenu = new MenuItem("Log Out");
		ReserveMenu = new MenuItem("Reserve Service");
		CustomerMenu = new MenuItem("Customer Reservation");

		userMenu.getItems().addAll(ReserveMenu, CustomerMenu, userLogOutMenu);
		userMenuBar.getMenus().add(userMenu);
	}

	public void addAdminComponent() {
		adminMenuBar = new MenuBar();
		adminMenu = new Menu("Menu");
		adminLogOutMenu = new MenuItem("Log Out");
		ServiceManagement = new MenuItem("Service Management");
		ReservationManagement = new MenuItem("Reservation Management");

		adminMenu.getItems().addAll(ServiceManagement, ReservationManagement, adminLogOutMenu);
		adminMenuBar.getMenus().add(adminMenu);
	}

	public MenuBar getAdminMenu() {
		return adminMenuBar;
	}

	public Component() {
		addUserComponent();
		addAdminComponent();
	}
}
