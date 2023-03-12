package seedu.address.model;

import java.io.IOException;

import seedu.address.logic.commands.Command;
import seedu.address.model.entity.shop.Shop;
import seedu.address.storage.Serializer;
import seedu.address.ui.Ui;

public class Model {

    private static final String SHOP_FILE_NAME = "shop.ser";
    private static final String LOAD_ERROR_MSG = "There was an error loading stored data.";
    private final Serializer<Shop> shopSerializer;
    private final Shop shop;
    private final ShopParser shopParser;

    private boolean isExit = false;

    public Model() {
        this.shopSerializer = Serializer.of(Shop.class, SHOP_FILE_NAME);
        this.shopParser = null //TODO
        Shop temp = null;
        try {
            temp = this.shopSerializer.load();
        } catch (IOException ex) {
            temp = new Shop();
        } catch (ClassCastException | ClassNotFoundException ex) {
            Ui.showReply(LOAD_ERROR_MSG);
            temp = new Shop();
        }
        this.shop = temp;
    }

    public void run() {
        while (!isExit) {
            String userInput = Ui.getUserInput();
            Command command = shopParser.parse(userInput);
            command.execute(shop, shopSerializer);
            if (command.isExit()) {
                this.isExit = true;
            }
        }
    }
}