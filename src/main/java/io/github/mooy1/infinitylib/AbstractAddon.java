package io.github.mooy1.infinitylib;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.Getter;

//import org.bstats.bukkit.Metrics;
//import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.mooy1.infinitylib.commands.CommandManager;
import io.github.mooy1.infinitylib.configuration.AddonConfig;
import io.github.mooy1.infinitylib.slimefun.utils.TickerUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
//import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;

/**
 * Extend this in your main plugin class
 * 
 * @author Mooy1
 */
public abstract class AbstractAddon extends JavaPlugin implements SlimefunAddon {
    
    private final String bugTrackerURL = "https://github.com/" + getGithubPath().substring(0, getGithubPath().lastIndexOf('/')) + "/issues";
    
    @Getter
    private int globalTick;
    
    @Getter
    private AddonConfig config;

    /**
     * Main Constructor
     */
    public AbstractAddon() {
        
    }

    /**
     * Mock Bukkit Constructor
     */
    @ParametersAreNonnullByDefault
    public AbstractAddon(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }
    
    @Override
    @OverridingMethodsMustInvokeSuper
    public void onEnable() {
        
        // config
        this.config = new AddonConfig(this, "config.yml");
        //this.config.getDefaults().set(getAutoUpdatePath(), true);
        //this.config.addComment(getAutoUpdatePath(), "\n# This must be enabled to receive support!\n");

        // auto update
        /*if (this.config.getBoolean(getAutoUpdatePath())) {
            if (getDescription().getVersion().startsWith("DEV - ")) {
                new GitHubBuildsUpdater(this, getFile(), getGithubPath()).start();
            }
        } else {
            runSync(() -> log(
                    "#######################################",
                    "Auto Updates have been disabled for " + getName(),
                    "You will receive no support for bugs",
                    "Until you update to the latest version!",
                    "#######################################"
            ));
        }*/

        // metrics
        /*Metrics metrics = setupMetrics();
        if (metrics != null) {
            String autoUpdates = String.valueOf(this.config.getBoolean("auto-update"));
            metrics.addCustomChart(new SimplePie("auto_updates", () -> autoUpdates));
        }*/

        // global ticker
        scheduleRepeatingSync(() -> this.globalTick++, TickerUtils.TICKS);

        // commands
        CommandManager.register(this, getName(), getSubCommands());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onLoad() {
        // default to not logging world settings
        Bukkit.spigot().getConfig().set("world-settings.default.verbose", false);
    }

    /**
     * return your metrics or null
     */
    //@Nullable
    //protected abstract Metrics setupMetrics();

    /**
     * return the github path in the format user/repo/branch, for example Mooy1/InfinityExpansion/master
     */
    @Nonnull
    protected abstract String getGithubPath();

    /**
     * return your sub commands, use Arrays.asList(Commands...)
     */
    @Nonnull
    protected abstract List<AbstractCommand> getSubCommands();

    /**
     * Override this if you have a different path
     */
    //@Nonnull
    /*protected String getAutoUpdatePath() {
        return "auto-update";
    }*/

    @Nonnull
    @Override
    public final JavaPlugin getJavaPlugin() {
        return this;
    }
    
    @Nonnull
    @Override
    public final String getBugTrackerURL() {
        return this.bugTrackerURL;
    }

    public final NamespacedKey getKey(String s) {
        return new NamespacedKey(this, s);
    }

    public final void log(String... messages) {
        log(Level.INFO, messages);
    }

    public final void log(Level level, String... messages) {
        for (String msg : messages) {
            getLogger().log(level, msg);
        }
    }

    public final void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public final void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(this, runnable);
    }

    public final void runSync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public final void scheduleRepeatingSync(Runnable runnable, long interval) {
        Bukkit.getScheduler().runTaskTimer(this, runnable, 0, interval);
    }

    public final void scheduleRepeatingSync(Runnable runnable, long delay, long interval) {
        Bukkit.getScheduler().runTaskTimer(this, runnable, delay, interval);
    }

    public final void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(this, runnable);
    }

    public final void runAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, runnable, delay);
    }

    public final void scheduleRepeatingAsync(Runnable runnable, long interval) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, 0, interval);
    }

    public final void scheduleRepeatingAsync(Runnable runnable, long delay, long interval) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, interval);
    }

}
