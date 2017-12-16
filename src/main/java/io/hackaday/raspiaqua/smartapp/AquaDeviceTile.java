package io.hackaday.raspiaqua.smartapp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import java.util.ResourceBundle;

/**
 *
 * @author svininykh-av
 */
public class AquaDeviceTile {

    private final ResourceBundle bundle;
    private final double tileWidth;
    private final double tileHeight;

    private String name;
    private Tile iconTile;
    private Tile switchTile;

    private FontAwesomeIconView iconView;

    public AquaDeviceTile(ResourceBundle bundle, double tileWidth, double tileHeight) {
        this.bundle = bundle;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tile getIconTile() {
        return iconTile;
    }

    public void setIconTile(FontAwesomeIcon icon) {
        iconView = new FontAwesomeIconView();
        iconView.setIcon(icon);
        iconView.setFill(Tile.FOREGROUND);
        iconView.setSize("64");
        this.iconTile = TileBuilder.create()
                .prefSize(tileWidth, tileHeight)
                .skinType(Tile.SkinType.CUSTOM)
                .title(String.format("%s %s", bundle.getString("key.".concat(name)), bundle.getString("key.off")))
                .graphic(iconView)
                .roundedCorners(false)
                .build();
    }

    public Tile getSwitchTile() {
        switchTile = TileBuilder.create()
                .skinType(Tile.SkinType.SWITCH)
                .prefSize(tileWidth, tileHeight)
                .title(bundle.getString("key.".concat(name)))
                .build();
        switchTile.setOnSwitchReleased((SwitchEvent) -> {
            if (switchTile.isActive()) {
                iconView.setFill(Tile.YELLOW_ORANGE);
                iconTile.setTitleColor(Tile.YELLOW_ORANGE);
                iconTile.setTitle(String.format("%s %s", bundle.getString("key.".concat(name)), bundle.getString("key.on")));
            } else {
                iconView.setFill(Tile.FOREGROUND);
                iconTile.setTitleColor(Tile.FOREGROUND);
                iconTile.setTitle(String.format("%s %s", bundle.getString("key.".concat(name)), bundle.getString("key.off")));
            }
        });
        return switchTile;
    }

    public void setSwitchTile(Tile switchTile) {
        this.switchTile = switchTile;
    }

    @Override
    public String toString() {
        return name;
    }

}
