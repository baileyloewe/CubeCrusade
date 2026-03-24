package com.main.menus;

import com.main.*;
import java.awt.*;
import static com.main.GraphicsUtil.*;
import static com.main.GraphicsUtil.drawRectAndString;

public class ShopMenu extends Menu {
    private final MenuBoxItem shopShopBox, shopUpgradeHealthBox, shopUpgradeSpeedBox, shopRefillHealthBox, shopCurrencyBox, shopBackBox;

    public ShopMenu(MenuManager menuManager) {
        super(menuManager);
        shopShopBox = new MenuBoxItem(centeredX - 75, centeredY - 200, 150, 70, "SHOP");
        shopUpgradeHealthBox = new MenuBoxItem(centeredX - 140, centeredY - 82, 280, 44, "UPGRADE HEALTH (" + upgrade.getCostOfNextHealthUpgrade() + ")");
        shopUpgradeSpeedBox = new MenuBoxItem(centeredX - 130, centeredY + 13, 260, 44, "UPGRADE SPEED (" + upgrade.getCostOfNextSpeedUpgrade() + ")");
        shopRefillHealthBox = new MenuBoxItem(centeredX - 130, centeredY + 108, 260, 44, "REFILL HEALTH");
        shopCurrencyBox = new MenuBoxItem(centeredX - 130, centeredY + 183, 260, 44, "GOLD: " + (int) upgrade.getPlayerCurrency());
        shopBackBox = new MenuBoxItem(centeredX - 40, centeredY + 278, 80, 44, "BACK");
    }

    public void updateShopBoxSizesAndText() {
        shopUpgradeHealthBox.text = "UPGRADE HEALTH (" + upgrade.getCostOfNextHealthUpgrade() + ")";

        shopUpgradeHealthBox.rect.x = centeredX - (shopUpgradeHealthBox.text.length() * 10);
        shopUpgradeHealthBox.rect.width = (shopUpgradeHealthBox.text.length() * 20);

        shopUpgradeSpeedBox.text = "UPGRADE SPEED (" + upgrade.getCostOfNextSpeedUpgrade() + ")";
        shopUpgradeSpeedBox.rect.x = centeredX - (shopUpgradeSpeedBox.text.length() * 10);
        shopUpgradeSpeedBox.rect.width = (shopUpgradeSpeedBox.text.length() * 20);

        shopCurrencyBox.text = "GOLD: " + (int) upgrade.getPlayerCurrency();
        shopCurrencyBox.rect.x = centeredX - (shopCurrencyBox.text.length() * 10);
        shopCurrencyBox.rect.width = (shopCurrencyBox.text.length() * 20);

        shopRefillHealthBox.text = "REFILL HEALTH (" + upgrade.getCostOfNextHealthRefill() + ")";
        shopRefillHealthBox.rect.x = centeredX - (shopRefillHealthBox.text.length() * 10);
        shopRefillHealthBox.rect.width = (shopRefillHealthBox.text.length() * 20);
    }

    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(shopUpgradeHealthBox, mouseX, mouseY)) {
            upgrade.buyHealthUpgrade();
        } else if (mouseOverItem(shopUpgradeSpeedBox, mouseX, mouseY)) {
            upgrade.buySpeedUpgrade();
        } else if (mouseOverItem(shopRefillHealthBox, mouseX, mouseY)) {
            upgrade.buyHealthRefill();
        } else if (mouseOverItem(shopBackBox, mouseX, mouseY)) {
            mediator.getGame().gameState = Game.STATE.Game;
        }
    }

    public void render(Graphics g) {
        drawRectAndString(g, shopShopBox, Fonts.LARGE);
        drawRectAndString(g, shopUpgradeHealthBox, Fonts.MEDIUM);
        drawRectAndString(g, shopUpgradeSpeedBox, Fonts.MEDIUM);
        drawRectAndString(g, shopRefillHealthBox, Fonts.MEDIUM);
        drawRectAndString(g, shopCurrencyBox, Fonts.MEDIUM);
        drawRectAndString(g, shopBackBox, Fonts.MEDIUM);
        updateShopBoxSizesAndText();
    }
}
