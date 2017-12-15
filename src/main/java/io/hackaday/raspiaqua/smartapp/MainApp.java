package io.hackaday.raspiaqua.smartapp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final double TILE_SIZE = 150;
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.UILocales");
    private final AquaDevice light = new AquaDevice(bundle, TILE_SIZE, TILE_SIZE / 2);
    private final AquaDevice compressor = new AquaDevice(bundle, TILE_SIZE, TILE_SIZE / 2);
    private final AquaDevice heater = new AquaDevice(bundle, TILE_SIZE, TILE_SIZE / 2);
    private final AquaDevice filter = new AquaDevice(bundle, TILE_SIZE, TILE_SIZE / 2);

    @Override
    public void init() {
        light.setName("light");
        light.setIconTile(FontAwesomeIcon.LIGHTBULB_ALT);

        compressor.setName("compressor");
        compressor.setIconTile(FontAwesomeIcon.COGS);

        heater.setName("heater");
        heater.setIconTile(FontAwesomeIcon.PLUG);

        filter.setName("filter");
        filter.setIconTile(FontAwesomeIcon.FILTER);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FlowGridPane pane = new FlowGridPane(4, 2,
                light.getIconTile(), compressor.getIconTile(),
                heater.getIconTile(), filter.getIconTile(),
                light.getSwitchTile(), compressor.getSwitchTile(),
                heater.getSwitchTile(), filter.getSwitchTile()
        );
        pane.setHgap(5);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));
        pane.setBackground(new Background(new BackgroundFill(Tile.BACKGROUND.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);

        stage.setTitle(bundle.getString("key.title"));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
