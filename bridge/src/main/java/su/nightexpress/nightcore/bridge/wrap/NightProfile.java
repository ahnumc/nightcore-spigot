package su.nightexpress.nightcore.bridge.wrap;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public interface NightProfile {

    void apply(@NonNull SkullMeta meta);

    @Nullable
    UUID getId();

    @NonNull
    Optional<UUID> id();

    @Nullable
    String getName();

    @NonNull
    Optional<String> name();

    @NonNull
    PlayerTextures getTextures();

    void setTextures(@Nullable PlayerTextures textures);

    /**
     * Returns a raw profile property when the active platform exposes it.
     * Bukkit-only implementations may return {@code null}.
     */
    @Nullable
    default NightProfileProperty getProperty(@NonNull String name) {
        return null;
    }

    /**
     * Adds or replaces a raw profile property when supported by the platform.
     * Bukkit-only implementations may safely ignore this operation.
     */
    default void setProperty(@NonNull NightProfileProperty property) {
    }

    boolean isComplete();

    @NonNull
    CompletableFuture<NightProfile> update();
}
