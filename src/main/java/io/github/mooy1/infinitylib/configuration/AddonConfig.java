package io.github.mooy1.infinitylib.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.mooy1.infinitylib.AbstractAddon;

/**
 * A config which is able to save all of it's comments and has some additional utility methods
 *
 * @author Mooy1
 */
public final class AddonConfig extends YamlConfiguration {

    public static final String AUTO_UPDATE_COMMENT = "\n# This must be enabled to receive support!\n";

    private final Map<String, String> comments = new HashMap<>();
    private final AbstractAddon addon;
    private final File file;

    public AddonConfig(AbstractAddon addon, String name) {
        this.file = new File(addon.getDataFolder(), name);
        this.addon = addon;
        loadDefaults(name);
        reload();
    }

    public int getInt(String path, int min, int max) {
        int val = getInt(path);
        if (val < min || val > max) {
            set(path, val = getDefaults().getInt(path));
        }
        return val;
    }

    public double getDouble(String path, double min, double max) {
        double val = getDouble(path);
        if (val < min || val > max) {
            set(path, val = getDefaults().getDouble(path));
        }
        return val;
    }

    public void save() {
        try {
            save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        if (this.file.exists()) {
            try {
                load(this.file);
            } catch (InvalidConfigurationException e) {
                this.addon.log(Level.SEVERE, "There was an error loading the config '" + this.file.getPath() + "', resetting to default!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nonnull
    @Override
    public Configuration getDefaults() {
        return Objects.requireNonNull(super.getDefaults());
    }

    @Nonnull
    @Override
    protected String buildHeader() {
        return "";
    }

    @Nullable
    public String getComment(String key) {
        return this.comments.get(key);
    }

    @Override
    public void loadFromString(@Nonnull String contents) throws InvalidConfigurationException {
        super.loadFromString(contents);
        for (String key : getKeys(true)) {
            if (!this.defaults.contains(key)) {
                set(key, null);
            }
        }
        save();
    }

    @Nonnull
    @Override
    public String saveToString() {
        options().copyDefaults(true).copyHeader(false);
        String string = super.saveToString();

        String[] lines = string.split("\n");
        StringBuilder save = new StringBuilder();
        PathBuilder pathBuilder = new PathBuilder();

        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }

            // append the comment and line
            String comment = getComment(pathBuilder.append(line).build());
            if (comment != null) {
                save.append(comment);
            }
            save.append(line).append('\n');
        }

        return save.toString();
    }

    private void loadDefaults(String name) {
        BufferedReader input = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(this.addon.getResource(name),
                        () -> "No default config for " + name + "!"), Charsets.UTF_8));

        StringBuilder yamlBuilder = new StringBuilder();
        StringBuilder commentBuilder = new StringBuilder();
        PathBuilder pathBuilder = new PathBuilder();

        // Load comments
        try {
            String line;

            while ((line = input.readLine()) != null) {
                yamlBuilder.append(line).append('\n');

                if (StringUtils.isBlank(line)) {
                    continue;
                }

                if (line.contains("#")) {
                    commentBuilder.append(line).append('\n');
                    continue;
                }

                if (commentBuilder.length() == 0) {
                    pathBuilder.append(line);
                    continue;
                }

                // add comment and reset
                commentBuilder.insert(0, '\n');
                this.comments.put(pathBuilder.append(line).build(), commentBuilder.toString());
                commentBuilder = new StringBuilder();
            }

            input.close();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        YamlConfiguration defaults = new YamlConfiguration();

        // load values
        try {
            defaults.loadFromString(yamlBuilder.toString());
        } catch (InvalidConfigurationException e) {
            this.addon.log(Level.SEVERE, "There was an error loading the default config of '" + this.file.getPath() + "', report this to the developers!");
            return;
        }

        // Auto Update
        /*if (name.equals("config.yml")) {
            String path = this.addon.getAutoUpdatePath();
            if (path != null) {
                defaults.set(path, true);
                this.comments.put(path, AUTO_UPDATE_COMMENT);
            }
        }*/

        setDefaults(defaults);
    }

}
