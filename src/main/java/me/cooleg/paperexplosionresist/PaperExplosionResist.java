package me.cooleg.paperexplosionresist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperExplosionResist extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        final ConfigurationSection section = getConfig().getConfigurationSection("config");
        if (section == null) {
            Bukkit.getLogger().severe(ChatColor.RED + "Your config is either broken or empty."); Bukkit.getPluginManager().disablePlugin(this);}
        for (String s : section.getKeys(false)) {
            ModifyExplosionResistance.setResistance(s, (float) section.getDouble(s));
        }
    }

    @Override
    public void onDisable() {
        ModifyExplosionResistance.resetResistances();
    }
}
