package me.youhavetrouble.notjustnameplates;

import me.youhavetrouble.notjustnameplates.nameplates.TeamManagementListener;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.permissions.DefaultPermissions;

public final class NotJustNameplates extends JavaPlugin {

    private static NotJustNameplates instance;
    private static NJNConfig config;
    private static long time = Long.MIN_VALUE;

    @Override
    public void onEnable() {
        instance = this;
        config = new NJNConfig(this);

        DefaultPermissions.registerPermission("notjustnameplates.seeown", "Allows a player to see their own nameplate", PermissionDefault.FALSE);

        getServer().getPluginManager().registerEvents(new TeamManagementListener(this), this);
        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            time++;
            if (config == null) return;
            config.getDisplayContents().values().forEach(displayContent -> {
                if (displayContent == null) return;
                if (displayContent.getRefreshRate() <= 0) return;
                if (time % displayContent.getRefreshRate() != 0) return;
                displayContent.advanceFrame();
            });
        }, 1, 1);
    }

    public static NotJustNameplates getInstance() {
        return instance;
    }

    public static long getTime() {
        return time;
    }

    public static NJNConfig getPluginConfig() {
        return config;
    }

}
