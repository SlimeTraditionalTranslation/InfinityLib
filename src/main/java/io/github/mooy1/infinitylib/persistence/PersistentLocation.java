package io.github.mooy1.infinitylib.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

final class PersistentLocation implements PersistentDataType<byte[], Location> {

    @Nonnull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Nonnull
    @Override
    public Class<Location> getComplexType() {
        return Location.class;
    }

    @Nonnull
    @Override
    public byte[] toPrimitive(@Nonnull Location complex, @Nonnull PersistentDataAdapterContext context) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            BukkitObjectOutputStream output = new BukkitObjectOutputStream(bytes);
            output.writeObject(complex);
            return bytes.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Nonnull
    @Override
    public Location fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream bytes = new ByteArrayInputStream(primitive);
            BukkitObjectInputStream input = new BukkitObjectInputStream(bytes);
            return (Location) input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new Location(null, 0, 0, 0);
        }
    }

}
