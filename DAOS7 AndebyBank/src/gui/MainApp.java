package gui;

import java.beans.EventHandler;
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
	private Storage storage = new Storage();
	private TextField txfCprNr, txfNavn, txfAdresse, txfPostNr, txfBynavn, txfRegNr, txfKontonummer, txfKontotekst,
			txfSaldo, txfLåneret;
	private ToggleGroup tggKontotype;
	private RadioButton rbnLøn, rbnPrioritet;
	private Button btnOpretKunde, btnOpretKonto;
	private Label lblError;

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
					txfBynavn.setText(storage.getBynavn(postNr));
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
		hbxOpret.setPadding(new Insets(20, 0, 0, 0));

		btnOpretKunde = new Button("Opret Kunde");
		hbxOpret.getChildren().add(btnOpretKunde);
		btnOpretKunde.setOnAction(e -> opretKundeAction());

		btnOpretKonto = new Button("Opret Konto");
		hbxOpret.getChildren().add(btnOpretKonto);
		btnOpretKonto.setOnAction(e -> opretKontoAction());

		hbxOpret.setSpacing(10);
		pane.add(hbxOpret, 0, 11, 2, 1);

		// FEJLBESKED
		lblError = new Label();
		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 4, 2, 1);
	}

	private void opretKontoAction() {
	}

	private void opretKundeAction() {
		String cprNummer = txfCprNr.getText();
		String navn = txfNavn.getText().trim();
		String adresse = txfAdresse.getText().trim();
		int postNr = 0;
		try {
			postNr = Integer.parseInt(txfPostNr.getText().trim());
		} catch (Exception e) {
		}

		lblError.setTextFill(Color.RED);

		try {
			Integer.parseInt(cprNummer);
		} catch (Exception e) {
			lblError.setText("Ugyldigt CPR-nummer.");
		}

		if (cprNummer.length() != 10) {
			lblError.setText("Ugyldigt CPR-nummer.");
		} else if (txfBynavn.getText().equals("")) {
			lblError.setText("Ugyldigt postnummer.");
		} else if (storage.findesKunde(cprNummer)) {
			lblError.setText("Kunde findes allerede.");
		} else {
			storage.opretKunde(cprNummer, navn, adresse, postNr);
			lblError.setTextFill(Color.BLACK);
			lblError.setText("Kunde blev oprettet succesfuldt.");

		}
	}
}