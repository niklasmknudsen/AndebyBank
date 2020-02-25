package gui;

import java.util.Random;

import storage.Storage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.beans.value.ChangeListener;
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

	private void initContent(GridPane pane) {
		pane.setPadding(new Insets(10));
		pane.setHgap(10);
		pane.setVgap(5);

		Label lblCprNr = new Label("CPR Nummer");
		pane.add(lblCprNr, 0, 0);

		txfCprNr = new TextField();
		pane.add(txfCprNr, 1, 0);

		txfNavn = new TextField();
		txfNavn.setPromptText("Navn");
		pane.add(txfNavn, 0, 1, 2, 1);

		txfAdresse = new TextField();
		txfAdresse.setPromptText("Adresse");
		pane.add(txfAdresse, 0, 2, 2, 1);

		txfPostNr = new TextField();
		txfPostNr.setPromptText("PostNr");
		txfPostNr.setPrefWidth(10);
		pane.add(txfPostNr, 0, 3);

		txfBynavn = new TextField();
		txfBynavn.setPromptText("PostNr");
		txfBynavn.setPrefWidth(10);
		pane.add(txfBynavn, 1, 3);

		HBox hbxRegNr = new HBox();

		Label lblRegNr = new Label("Reg. nummer");
		lblRegNr.setPadding(new Insets(0, 0, 0, 5));
		hbxRegNr.getChildren().add(lblRegNr);

		txfRegNr = new TextField();
		txfRegNr.setPromptText("RegNr");
		txfRegNr.setMaxWidth(50);
		hbxRegNr.getChildren().add(txfRegNr);

		hbxRegNr.setSpacing(10);
		hbxRegNr.setPadding(new Insets(20, 0, 0, 0));
		pane.add(hbxRegNr, 0, 4, 2, 1);

		HBox hbxKonotnummer = new HBox();

		Label lblKontonummer = new Label("Kontonummer");
		lblKontonummer.setPadding(new Insets(0, 0, 0, 5));
		hbxKonotnummer.getChildren().add(lblKontonummer);

		txfKontonummer = new TextField();
		txfKontonummer.setPromptText("Kontonummer");
		hbxKonotnummer.getChildren().add(txfKontonummer);

		hbxKonotnummer.setSpacing(5);
		pane.add(hbxKonotnummer, 0, 5, 2, 1);

		txfKontotekst = new TextField();
		pane.add(txfKontotekst, 0, 6, 2, 1);

		Label lblSaldo = new Label("Saldo");
		pane.add(lblSaldo, 0, 7);

		txfSaldo = new TextField();
		pane.add(txfSaldo, 1, 7);

		HBox hbxKontotype = new HBox();
		tggKontotype = new ToggleGroup();

		rbnLøn = new RadioButton("Løn");
		rbnLøn.setToggleGroup(tggKontotype);
		hbxKontotype.getChildren().add(rbnLøn);

		rbnPrioritet = new RadioButton("Prioritet");
		rbnPrioritet.setToggleGroup(tggKontotype);
		hbxKontotype.getChildren().add(rbnPrioritet);

		hbxKontotype.setSpacing(10);
		pane.add(hbxKontotype, 0, 8);

		Label lblLåneret = new Label("Låneret");
		pane.add(lblLåneret, 0, 9);

		txfLåneret = new TextField();
		pane.add(txfLåneret, 1, 9);

		HBox hbxOpret = new HBox();

		btnOpretKunde = new Button("Opret Kunde");
		hbxOpret.getChildren().add(btnOpretKunde);
		btnOpretKunde.setOnAction(e -> opretKundeAction());

		btnOpretKonto = new Button("Opret Konto");
		hbxOpret.getChildren().add(btnOpretKonto);
		btnOpretKonto.setOnAction(e -> opretKontoAction());

		hbxOpret.setSpacing(10);
		pane.add(hbxOpret, 0, 10, 2, 1);
	}

	private void opretKontoAction() {
	}

	private void opretKundeAction() {
		String cprNummer = txfCprNr.getText();
		String navn = txfNavn.getText();
		String adresse = txfAdresse.getText();
		int postNr = Integer.parseInt(txfPostNr.getText());

		storage.opretKunde(cprNummer, navn, adresse, postNr);
	}
}