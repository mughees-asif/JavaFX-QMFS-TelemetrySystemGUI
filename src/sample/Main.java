package sample;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.Section;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private Gauge fuelGauge, gForceGauge, RPMGauge, tempGauge;
    private Gauge gForceGraph, fuelGraph, RPMGraph, tempGraph;

    @Override
    public void init() {

        /*<--------------------------------------------------> */
        /*<--------------------> Gauges <--------------------> */
        // TODO: Separate into different classes
        // TODO: Make a new gauge for measuring G-FORCE
        /*<--------------------------------------------------> */
        /*<--------------------------------------------------> */

        /*<--------------------> GForceGauge <--------------------> */
        // use modern skin
        gForceGauge = GaugeBuilder.create().
                skinType(Gauge.SkinType.MODERN).
                title("G-Force").
                prefSize(100,100).
                unit("")
                .build();
//            gauge.setUnit("UNIT");
//            gauge.setDecimals(0);
//            gauge.setValueColor(Color.WHITE);
//            gauge.setTitleColor(Color.WHITE);
//            gauge.setSubTitleColor(Color.WHITE);
//            gauge.setBarColor(Color.rgb(0, 214, 215));
//            gauge.setNeedleColor(Color.WHITE);
//            gauge.setThresholdColor(Color.rgb(204, 0, 0));
//            gauge.setTickLabelColor(Color.rgb(151, 151, 151));
//            gauge.setTickMarkColor(Color.BLACK);
//            gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);



        /*<--------------------> RPM Gauge <--------------------> */
        RPMGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.SIMPLE)
                .subTitle("\tx1000 rev/min")
                .prefSize(125,175)
                .sections(new Section(0, 8, "0", Color.web("#FFFFFF")),
                        new Section(8, 10, "1", Color.web("#FFCCCC")),
                        new Section(10, 12, "2", Color.web("#FF9999")),
                        new Section(12, 13, "3", Color.web("#FF6666")),
                        new Section(13, 14, "4", Color.web("#FF3333")),
                        new Section(14, 16, "5", Color.web("#FF0000")))
                .sectionsVisible(true)
                .sectionIconsVisible(true)
                .maxValue(16)
                .title("RPM")
                .threshold(16)
                .animated(true)
                .build();



        /*<--------------------> Fuel Gauge <--------------------> */
        fuelGauge = GaugeBuilder.create()
                .title("Fuel")
                .skinType(Gauge.SkinType.HORIZONTAL)
                .prefSize(150, 200)
                .foregroundBaseColor(Color.WHITESMOKE)
                .animated(true)
                .shadowsEnabled(true)
                .valueVisible(false)
                .needleColor(Color.rgb(255, 10, 1))
                .needleShape(Gauge.NeedleShape.ANGLED)
                .needleSize(Gauge.NeedleSize.THICK)
                .minorTickMarksVisible(false)
                .mediumTickMarksVisible(false)
                .sectionsVisible(true)
                .sections(new Section(0, 2, Color.rgb(255, 10, 1)))
                .minValue(0)
                .maxValue(10)
                .angleRange(90)
                .customTickLabelsEnabled(true)
                .customTickLabels("Empty", "", "", "", "", "1/2", "", "", "", "", "Full")
                .build();

        /*<--------------------> Temperature Gauge <--------------------> */
         tempGauge = GaugeBuilder.create()
                 .skinType(Gauge.SkinType.LCD)
                 .prefSize(15,5)
                 .animated(true)
                 .title("Temperature")
                 .subTitle("Engine")
                 .unit("\u00B0C")
                 .lcdDesign(LcdDesign.GREEN_BLACK)
                 .thresholdVisible(true)
                 .threshold(50)
                 .build();

        /*<------------------------------------------------------------> */
        /*<--------------------> Analysing Graphs <--------------------> */
        // TODO: Separate into different classes
        /*<------------------------------------------------------------> */
        /*<------------------------------------------------------------> */

        /*<--------------------> Speed graph <--------------------> */
        gForceGraph = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .foregroundBaseColor(Color.WHITE)
                .animated(true)
                .maxValue(100)
                .minValue(0)
                .threshold(50)
                .title("Speed")
                .build();

        /*<--------------------> Fuel Graph <--------------------> */
        fuelGraph = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .foregroundBaseColor(Color.WHITE)
                .needleColor(Color.RED)
                .animated(true)
                .threshold(75)
                .foregroundBaseColor(Color.BLACK)
                .backgroundPaint(Color.LIGHTBLUE)
                .title("Fuel")
                .build();

        /*<--------------------> RPM Graph <--------------------> */
        RPMGraph = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .foregroundBaseColor(Color.WHITE)
                .needleColor(Color.RED)
                .animated(true)
                .threshold(75)
                .title("RPM")
                .build();

        /*<--------------------> Temperature Graph <--------------------> */
        tempGraph = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .foregroundBaseColor(Color.WHITE)
                .needleColor(Color.RED)
                .animated(true)
                .threshold(75)
                .foregroundBaseColor(Color.BLACK)
                .backgroundPaint(Color.LIGHTBLUE)
                .title("Temperature")
                .build();

    }

    @Override
    public void start(Stage stage) throws IOException {
        /*<--------------------> Gauges - HBox <--------------------> */
        HBox hBoxGauges = new HBox(15);

        Image image = new Image(new FileInputStream("images/QMFSLogo.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setX(50);
        imageView.setY(50);
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);

        VBox vBoxImageFuel = new VBox(75);
        vBoxImageFuel.getChildren().addAll(imageView, fuelGauge);
        vBoxImageFuel.setPadding(new Insets(0, 20, 0, 20));

        hBoxGauges.getChildren().addAll(vBoxImageFuel, RPMGauge, tempGauge);
        hBoxGauges.setPadding(new Insets(0, 20, 0, 20));
        HBox.setHgrow(fuelGauge, Priority.ALWAYS);
        HBox.setHgrow(tempGauge, Priority.ALWAYS);
        HBox.setHgrow(RPMGauge, Priority.ALWAYS);
        hBoxGauges.setAlignment(Pos.CENTER);

        /*<--------------------> Buttons - HBox <--------------------> */
        HBox hBoxButton = new HBox(15);

        Button readButton = new Button("Start");
        readButton.addEventHandler(ActionEvent.ACTION, (event) -> {
            InfiniteGaugeData randomData = new InfiniteGaugeData(
                    fuelGauge, RPMGauge, tempGauge,
                    fuelGraph, RPMGraph, tempGraph);
            randomData.start();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    randomData.run();
                }
            }, 0, 1000);
        });

        Button exitButton = new Button("Exit");
        exitButton.addEventHandler(ActionEvent.ACTION, (event) -> {
                System.exit(0);
        });

        readButton.getStyleClass().add("buttons");
        exitButton.getStyleClass().add("buttons");
        hBoxButton.setPadding(new Insets(20, 20, 0, 0));
        HBox.setHgrow(hBoxButton, Priority.ALWAYS);

        hBoxButton.getChildren().addAll(readButton, exitButton);
        hBoxButton.setAlignment(Pos.CENTER_RIGHT);

        /*<--------------------> Analysing graphs - StackPane <--------------------> */
        VBox bottomVBox = new VBox(10);

        HBox analysingGraphsHBox = new HBox(110);
        analysingGraphsHBox.getChildren().addAll(fuelGraph, RPMGraph, tempGraph);
        analysingGraphsHBox.setAlignment(Pos.CENTER);

        bottomVBox.getChildren().addAll(analysingGraphsHBox);
        analysingGraphsHBox.setPadding(new Insets(20,20,20,20));
        analysingGraphsHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(fuelGraph, Priority.ALWAYS);
        HBox.setHgrow(gForceGraph, Priority.ALWAYS);
        HBox.setHgrow(RPMGraph, Priority.ALWAYS);
        HBox.setHgrow(tempGraph, Priority.ALWAYS);

        /*<--------------------> Main layout - BorderPane <--------------------> */
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(hBoxButton);
        borderPane.setCenter(hBoxGauges);
        borderPane.setBottom(bottomVBox);
        borderPane.getStyleClass().add("borderpane");

        /*<--------------------> Main Scene <--------------------> */
        Scene scene = new Scene(borderPane, 1500, 800);
        scene.getStylesheets().add("sample/styles.css");
        stage.setTitle("Data Viewer - QMFS\u00a9");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        launch(args);
    }
}