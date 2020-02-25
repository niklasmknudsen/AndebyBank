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

public class MainApp extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() {
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Festival administration of volunteers");
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
	private Button btnOpretKunde, btnOpretKonto;
	private Label lblErrorOpretKunde, lblErrorOpretKonto;

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
		pane.add(hbxRegNr, 0, 5, 2, 1);

		// KONTONUMMER
		HBox hbxKontonummer = new HBox();

		Label lblKontonummer = new Label("Kontonummer");
		lblKontonummer.setPadding(new Insets(0, 0, 0, 5));
		hbxKontonummer.getChildren().add(lblKontonummer);

		txfKontonummer = new TextField();
		txfKontonummer.setPromptText("Kontonummer");
		hbxKontonummer.getChildren().add(txfKontonummer);

		hbxKontonummer.setSpacing(5);
		pane.add(hbxKontonummer, 0, 6, 2, 1);

		// KONTOTEKST
		txfKontotekst = new TextField();
		pane.add(txfKontotekst, 0, 7, 2, 1);

		// SALDO
		Label lblSaldo = new Label("Saldo");
		pane.add(lblSaldo, 0, 8);

		txfSaldo = new TextField();
		pane.add(txfSaldo, 1, 8);

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
		pane.add(hbxKontotype, 0, 9, 2, 1);

		// LÅNERET
		Label lblLåneret = new Label("Låneret");
		pane.add(lblLåneret, 0, 10);

		txfLåneret = new TextField();
		pane.add(txfLåneret, 1, 10);

		// OPRET KNAPPER
		HBox hbxOpret = new HBox();

		btnOpretKunde = new Button("Opret Kunde");
		hbxOpret.getChildren().add(btnOpretKunde);
		btnOpretKunde.setOnAction(e -> opretKundeAction());

		btnOpretKonto = new Button("Opret Konto");
		hbxOpret.getChildren().add(btnOpretKonto);
		btnOpretKonto.setOnAction(e -> opretKontoAction());

		hbxOpret.setSpacing(10);
		pane.add(hbxOpret, 0, 12, 2, 1);

		// FEJLBESKEDER
		lblErrorOpretKunde = new Label();
		lblErrorOpretKunde.setTextFill(Color.RED);
		pane.add(lblErrorOpretKunde, 0, 4, 2, 1);

		lblErrorOpretKonto = new Label();
		lblErrorOpretKonto.setTextFill(Color.RED);
		pane.add(lblErrorOpretKonto, 0, 11, 2, 1);
	}

	private void opretKontoAction() {
		lblErrorOpretKonto.setTextFill(Color.RED);

		String cprNummer = checkCprNummer(lblErrorOpretKonto);
		String kontotekst = txfKontotekst.getText().trim();
		boolean løn = rbnLøn.isSelected() ? true : false;
		boolean prioritet = rbnPrioritet.isSelected() ? true : false;

		double saldo = -1;
		try {
			String temp = txfSaldo.getText().trim();
			saldo = temp.length() == 0 ? 0 : Double.parseDouble(temp);
		} catch (Exception e) {
			lblErrorOpretKonto.setText("Ugyldig saldo.");
		}

		double låneret = -1;
		try {
			String temp = txfLåneret.getText().trim();
			låneret = temp.length() == 0 ? 0 : Double.parseDouble(temp);
		} catch (Exception e) {
			lblErrorOpretKonto.setText("Ugyldig saldo.");
		}

		int regNummer = -1;
		try {
			String temp = txfRegNr.getText().trim();
			regNummer = Integer.parseInt(temp);
		} catch (Exception e) {
			lblErrorOpretKonto.setText("Ugyldig reg. nummer.");
		}

		long kontonummer = -1;
		try {
			String temp = txfKontonummer.getText().trim();
			kontonummer = Long.parseLong(temp);
		} catch (Exception e) {
			lblErrorOpretKonto.setText("Ugyldig kontonummer.");
		}

		if (cprNummer.length() != 10) {
			lblErrorOpretKonto.setText("Ugyldigt CPR-nummer.");
		} else if (!Storage.findesKunde(cprNummer)) {
			lblErrorOpretKonto.setText("Kunde findes ikke.");
		} else if (!Storage.findesAfdelig(regNummer)) {
			lblErrorOpretKonto.setText("Afdeling findes ikke (indtast et andet reg. nummer).");
		} else if (Storage.findesKontoIAfdeling(regNummer, kontonummer)) {
			lblErrorOpretKonto.setText("Der findes allerede en konto med dette kontonummer i denne afdeling.");
		} else {
			Storage.opretKonto(cprNummer, regNummer, kontonummer, kontotekst, saldo, løn, prioritet, låneret);
			lblErrorOpretKonto.setTextFill(Color.BLACK);
			lblErrorOpretKonto.setText("Kunde blev oprettet succesfuldt.");
		}
		lblErrorOpretKunde.setText("");

	}

	private void opretKundeAction() {
		lblErrorOpretKunde.setTextFill(Color.RED);

		String cprNummer = checkCprNummer(lblErrorOpretKunde);
		String navn = txfNavn.getText().trim();
		String adresse = txfAdresse.getText().trim();
		int postNr = -1;
		try {
			postNr = Integer.parseInt(txfPostNr.getText().trim());
		} catch (Exception e) {
		}

		if (cprNummer.length() != 10) {
			lblErrorOpretKunde.setText("Ugyldigt CPR-nummer.");
		} else if (txfBynavn.getText().equals("")) {
			lblErrorOpretKunde.setText("Ugyldigt postnummer.");
		} else if (Storage.findesKunde(cprNummer)) {
			lblErrorOpretKunde.setText("Kunde findes allerede.");
		} else {
			Storage.opretKunde(cprNummer, navn, adresse, postNr);
			lblErrorOpretKunde.setTextFill(Color.BLACK);
			lblErrorOpretKunde.setText("Kunde blev oprettet succesfuldt.");
		}
		lblErrorOpretKonto.setText("");
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