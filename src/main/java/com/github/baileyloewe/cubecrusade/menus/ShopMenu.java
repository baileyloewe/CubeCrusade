package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.MenuBoxItem;
import com.github.baileyloewe.cubecrusade.entities.Player;
import com.github.baileyloewe.cubecrusade.entities.components.UpgradeComponent;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;

import static com.github.baileyloewe.cubecrusade.GraphicsUtil.Fonts;
import static com.github.baileyloewe.cubecrusade.GraphicsUtil.drawRectAndString;

public class ShopMenu extends Menu {
    private final MenuBoxItem shopShopBox, shopUpgradeHealthBox, shopUpgradeSpeedBox, shopRefillHealthBox, shopCurrencyBox, shopBackBox;
    private final Player player;
    private final UpgradeComponent upgradeComponent;

    public ShopMenu(MenuManager menuManager, Player player) {
        super(menuManager);
        this.player = player;
        this.upgradeComponent = player.getUpgradeComponent();
        shopShopBox = new MenuBoxItem(centeredX - 75, centeredY - 200, 150, 70, "SHOP");
        shopUpgradeHealthBox = new MenuBoxItem(centeredX - 140, centeredY - 82, 280, 44, "UPGRADE HEALTH (" + player.getUpgradeComponent().getCostOfNextHealthUpgrade() + ")");
        shopUpgradeSpeedBox = new MenuBoxItem(centeredX - 130, centeredY + 13, 260, 44, "UPGRADE SPEED (" + player.getUpgradeComponent().getCostOfNextSpeedUpgrade() + ")");
        shopRefillHealthBox = new MenuBoxItem(centeredX - 130, centeredY + 108, 260, 44, "REFILL HEALTH");
        shopCurrencyBox = new MenuBoxItem(centeredX - 130, centeredY + 183, 260, 44, "GOLD: " + (int) player.getGold());
        shopBackBox = new MenuBoxItem(centeredX - 40, centeredY + 278, 80, 44, "BACK");
    }

    public void updateShopBoxSizesAndText() {
        shopUpgradeHealthBox.text = "UPGRADE HEALTH (" + upgradeComponent.getCostOfNextHealthUpgrade() + ")";

        shopUpgradeHealthBox.rect.x = centeredX - (shopUpgradeHealthBox.text.length() * 10);
        shopUpgradeHealthBox.rect.width = (shopUpgradeHealthBox.text.length() * 20);

        shopUpgradeSpeedBox.text = "UPGRADE SPEED (" + upgradeComponent.getCostOfNextSpeedUpgrade() + ")";
        shopUpgradeSpeedBox.rect.x = centeredX - (shopUpgradeSpeedBox.text.length() * 10);
        shopUpgradeSpeedBox.rect.width = (shopUpgradeSpeedBox.text.length() * 20);

        shopCurrencyBox.text = "GOLD: " + (int) player.getGold();
        shopCurrencyBox.rect.x = centeredX - (shopCurrencyBox.text.length() * 10);
        shopCurrencyBox.rect.width = (shopCurrencyBox.text.length() * 20);

        shopRefillHealthBox.text = "REFILL HEALTH (" + upgradeComponent.getCostOfNextHealthRefill() + ")";
        shopRefillHealthBox.rect.x = centeredX - (shopRefillHealthBox.text.length() * 10);
        shopRefillHealthBox.rect.width = (shopRefillHealthBox.text.length() * 20);
    }

    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(shopUpgradeHealthBox, mouseX, mouseY)) {
            GameSignals.healthUpgradePurchased.emit();
        } else if (mouseOverItem(shopUpgradeSpeedBox, mouseX, mouseY)) {
            player.getUpgradeComponent().buySpeedUpgrade();
            GameSignals.speedUpgradePurchased.emit();
        } else if (mouseOverItem(shopRefillHealthBox, mouseX, mouseY)) {
            player.getUpgradeComponent().buyHealthRefill();
            GameSignals.healthRefillPurchased.emit();
        } else if (mouseOverItem(shopBackBox, mouseX, mouseY)) {
            GameSignals.gameResumed.emit();
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
