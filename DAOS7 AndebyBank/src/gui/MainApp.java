package gui;

import java.beans.EventHandler;
import java.math.BigInteger;
import java.util.Random;

import storage.Storage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Pos;

public class MainApp extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() {
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Andeby bank");
		GridPane pane = new GridPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();

	}

	// -----------------------------------------------
	private TextField txfCprNr, txfNavn, txfAdresse, txfPostNr, txfBynavn, txfRegNr, txfKontonummer, txfKontotekst,
			txfSaldo, txfLåneret;
	private ToggleGroup tggKontotype;
	private RadioButton rbnLøn, rbnPrioritet;
	private Button btnOpretKunde, btnOpretKonto, btnSletKunde, btnTransaktioner, btnKonti, btnAfdelinger;
	private Label lblErrorKunde, lblErrorKonto;
	private ListView<String> lvwData;

	private void initContent(GridPane pane) {
		pane.setPadding(new Insets(10));
		pane.setHgap(5);
		pane.setVgap(5);

		// CPR NUMMER
		Label lblCprNr = new Label("CPR Nummer");
		pane.add(lblCprNr, 0, 0);

		txfCprNr = new TextField();
		txfCprNr.setPromptText("DDMMYYXXXX");
		pane.add(txfCprNr, 1, 0);

		// NAVN
		txfNavn = new TextField();
		txfNavn.setPromptText("Navn");
		pane.add(txfNavn, 0, 1, 2, 1);

		// ADRESSE
		txfAdresse = new TextField();
		txfAdresse.setPromptText("Adresse");
		pane.add(txfAdresse, 0, 2, 2, 1);

		// POSTNUMMER OG BYNAVN
		txfPostNr = new TextField();
		txfPostNr.setPromptText("PostNr");
		txfPostNr.setPrefWidth(10);
		txfPostNr.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					int postNr = Integer.parseInt(newValue.trim());
					txfBynavn.setText(Storage.getBynavn(postNr));
				} catch (Exception e) {
					txfBynavn.setText("");
				}
			}
		});
		pane.add(txfPostNr, 0, 3);

		txfBynavn = new TextField();
		txfBynavn.setPromptText("Bynavn");
		txfBynavn.setPrefWidth(10);
		txfBynavn.setDisable(true);
		pane.add(txfBynavn, 1, 3);

		// Kunde knapper
		HBox hbxKundeKnapper = new HBox();

		btnOpretKunde = new Button("Opret Kunde");
		hbxKundeKnapper.getChildren().add(btnOpretKunde);
		btnOpretKunde.setOnAction(e -> opretKundeAction());

		btnSletKunde = new Button("Slet Kunde");
		hbxKundeKnapper.getChildren().add(btnSletKunde);
		btnSletKunde.setOnAction(e -> sletKundeAction());

		hbxKundeKnapper.setSpacing(10);
		pane.add(hbxKundeKnapper, 0, 5, 2, 1);

		// REG. NUMMER
		HBox hbxRegNr = new HBox();

		Label lblRegNr = new Label("Reg. nummer");
		lblRegNr.setPadding(new Insets(0, 0, 0, 5));
		hbxRegNr.getChildren().add(lblRegNr);

		txfRegNr = new TextField();
		txfRegNr.setPromptText("RegNr");
		txfRegNr.setMaxWidth(50);
		hbxRegNr.getChildren().add(txfRegNr);

		hbxRegNr.setSpacing(10);
		//		hbxRegNr.setPadding(new Insets(20, 0, 0, 0));
		pane.add(hbxRegNr, 0, 6, 2, 1);

		// KONTONUMMER
		HBox hbxKontonummer = new HBox();

		Label lblKontonummer = new Label("Kontonummer");
		lblKontonummer.setPadding(new Insets(0, 0, 0, 5));
		hbxKontonummer.getChildren().add(lblKontonummer);

		txfKontonummer = new TextField();
		txfKontonummer.setPromptText("Kontonummer");
		hbxKontonummer.getChildren().add(txfKontonummer);

		hbxKontonummer.setSpacing(5);
		pane.add(hbxKontonummer, 0, 7, 2, 1);

		// KONTOTEKST
		txfKontotekst = new TextField();
		pane.add(txfKontotekst, 0, 8, 2, 1);

		// SALDO
		Label lblSaldo = new Label("Saldo");
		pane.add(lblSaldo, 0, 9);

		txfSaldo = new TextField();
		pane.add(txfSaldo, 1, 9);

		// KONTOTYPE
		HBox hbxKontotype = new HBox();
		tggKontotype = new ToggleGroup();

		rbnLøn = new RadioButton("Løn");
		rbnLøn.setToggleGroup(tggKontotype);
		hbxKontotype.getChildren().add(rbnLøn);

		rbnPrioritet = new RadioButton("Prioritet");
		rbnPrioritet.setToggleGroup(tggKontotype);
		hbxKontotype.getChildren().add(rbnPrioritet);

		hbxKontotype.setSpacing(10);
		pane.add(hbxKontotype, 0, 10, 2, 1);

		// LÅNERET
		Label lblLåneret = new Label("Låneret");
		pane.add(lblLåneret, 0, 11);

		txfLåneret = new TextField();
		pane.add(txfLåneret, 1, 11);

		// KONTO KNAPPER
		HBox hbxKontoKnapper = new HBox();

		btnOpretKonto = new Button("Opret Konto");
		hbxKontoKnapper.getChildren().add(btnOpretKonto);
		btnOpretKonto.setOnAction(e -> opretKontoAction());
		hbxKontoKnapper.setSpacing(10);
		pane.add(hbxKontoKnapper, 0, 13, 2, 1);

		// FEJLBESKEDER
		lblErrorKunde = new Label();
		lblErrorKunde.setTextFill(Color.RED);
		pane.add(lblErrorKunde, 0, 4, 2, 1);

		lblErrorKonto = new Label();
		lblErrorKonto.setTextFill(Color.RED);
		pane.add(lblErrorKonto, 0, 12, 2, 1);

		// LISTVIEW
		lvwData = new ListView<>();
		lvwData.setPrefWidth(400);
		lvwData.setPrefHeight(400);
		pane.add(lvwData, 2, 0, 1, 12);

		// KNAPPER TIL LISTVIEW
		HBox hbxListView = new HBox();

		btnTransaktioner = new Button("Vis transaktioner");
		hbxListView.getChildren().add(btnTransaktioner);
		btnTransaktioner.setOnAction(e -> Storage.visTransaktioner(lvwData));

		btnKonti = new Button("Vis konti");
		hbxListView.getChildren().add(btnKonti);
		btnKonti.setOnAction(e -> Storage.visKonti(lvwData));

		btnAfdelinger = new Button("Vis afdelinger");
		hbxListView.getChildren().add(btnAfdelinger);
		btnAfdelinger.setOnAction(e -> Storage.visAfdelinger(lvwData));

		hbxListView.setSpacing(10);
		hbxListView.setAlignment(Pos.CENTER);
		pane.add(hbxListView, 2, 13, 2, 1);

	}

	private void sletKundeAction() {
		lblErrorKunde.setTextFill(Color.RED);
		String cprNummer = checkCprNummer(lblErrorKunde);

		if (cprNummer.length() != 10) {
			lblErrorKunde.setText("Ugyldigt CPR-nummer.");
		} else if (!Storage.findesKunde(cprNummer)) {
			lblErrorKunde.setText("Kunde findes ikke.");
		} else {
			Storage.sletKunde(cprNummer);
			lblErrorKunde.setTextFill(Color.BLACK);
			lblErrorKunde.setText("Kunde blev slettet succesfuldt.");
		}
		lblErrorKonto.setText("");

	}

	private void opretKontoAction() {
		lblErrorKonto.setTextFill(Color.RED);

		String cprNummer = checkCprNummer(lblErrorKonto);
		String kontotekst = txfKontotekst.getText().trim();
		boolean løn = rbnLøn.isSelected() ? true : false;
		boolean prioritet = rbnPrioritet.isSelected() ? true : false;

		double saldo = -1;
		try {
			String temp = txfSaldo.getText().trim();
			saldo = temp.length() == 0 ? 0 : Double.parseDouble(temp);
		} catch (Exception e) {
			lblErrorKonto.setText("Ugyldig saldo.");
			return;
		}

		double låneret = -1;
		try {
			String temp = txfLåneret.getText().trim();
			låneret = temp.length() == 0 ? 0 : Double.parseDouble(temp);
		} catch (Exception e) {
			lblErrorKonto.setText("Ugyldig saldo.");
			return;
		}

		int regNummer = -1;
		try {
			String temp = txfRegNr.getText().trim();
			regNummer = Integer.parseInt(temp);
		} catch (Exception e) {
			lblErrorKonto.setText("Ugyldigt reg. nummer.");
			return;
		}

		long kontonummer = -1;
		try {
			String temp = txfKontonummer.getText().trim();
			if (temp.length() == 0) {
				throw new IllegalArgumentException();
			}
			kontonummer = Long.parseLong(temp);

		} catch (Exception e) {
			lblErrorKonto.setText("Ugyldigt kontonummer.");
			return;
		}

		if (cprNummer.length() != 10) {
			lblErrorKonto.setText("Ugyldigt CPR-nummer.");
		} else if (!Storage.findesKunde(cprNummer)) {
			lblErrorKonto.setText("Kunde findes ikke.");
		} else if (!Storage.findesAfdelig(regNummer)) {
			lblErrorKonto.setText("Afdeling findes ikke (indtast et andet reg. nummer).");
		} else if (Storage.findesKontoIAfdeling(regNummer, kontonummer)) {
			lblErrorKonto.setText("Der findes allerede en konto med dette kontonummer i denne afdeling.");
		} else {
			Storage.opretKonto(cprNummer, regNummer, kontonummer, kontotekst, saldo, løn, prioritet, låneret);
			lblErrorKonto.setTextFill(Color.BLACK);
			lblErrorKonto.setText("Konto blev oprettet succesfuldt.");
		}
		lblErrorKunde.setText("");

	}

	private void opretKundeAction() {
		lblErrorKunde.setTextFill(Color.RED);

		String cprNummer = checkCprNummer(lblErrorKunde);
		String navn = txfNavn.getText().trim();
		String adresse = txfAdresse.getText().trim();
		int postNr = -1;
		try {
			postNr = Integer.parseInt(txfPostNr.getText().trim());
		} catch (Exception e) {
		}

		if (cprNummer.length() != 10) {
			lblErrorKunde.setText("Ugyldigt CPR-nummer.");
		} else if (txfBynavn.getText().equals("")) {
			lblErrorKunde.setText("Ugyldigt postnummer.");
		} else if (Storage.findesKunde(cprNummer)) {
			lblErrorKunde.setText("Kunde findes allerede.");
		} else {
			Storage.opretKunde(cprNummer, navn, adresse, postNr);
			lblErrorKunde.setTextFill(Color.BLACK);
			lblErrorKunde.setText("Kunde blev oprettet succesfuldt.");
		}
		lblErrorKonto.setText("");
	}

	private String checkCprNummer(Label label) {
		String cprNummer = txfCprNr.getText().trim();
		try {
			Integer.parseInt(cprNummer);
		} catch (Exception e) {
			label.setText("Ugyldigt CPR-nummer.");
			return "";
		}
		return cprNummer;

	}
}