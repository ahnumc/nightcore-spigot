package su.nightexpress.nightcore.bridge.paper;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import su.nightexpress.nightcore.bridge.wrap.NightProfile;
import su.nightexpress.nightcore.bridge.wrap.NightProfileProperty;

public class PaperProfile implements NightProfile {

    private final PlayerProfile backend;

    public PaperProfile(@NonNull PlayerProfile backend) {
        this.backend = backend;
    }

    @NonNull
    public PlayerProfile getBackend() {
        return this.backend;
    }

    @Override
    public void apply(@NonNull SkullMeta meta) {
        meta.setPlayerProfile(this.backend);
    }

    @Override
    @Nullable
    public UUID getId() {
        return this.backend.getId();
    }

    @Override
    @NonNull
    public Optional<UUID> id() {
        return Optional.ofNullable(this.getId());
    }

    @Override
    @Nullable
    public String getName() {
        return this.backend.getName();
    }

    @Override
    @NonNull
    public Optional<String> name() {
        return Optional.ofNullable(this.getName());
    }

    @Override
    @NonNull
    public PlayerTextures getTextures() {
        return this.backend.getTextures();
    }

    @Override
    public void setTextures(@Nullable PlayerTextures textures) {
        this.backend.setTextures(textures);
    }

    @Override
    public @Nullable NightProfileProperty getProperty(@NonNull String name) {
        return this.backend.getProperties().stream()
            .filter(property -> property.getName().equals(name))
            .findFirst()
            .map(property -> new NightProfileProperty(
                property.getName(),
                property.getValue(),
                property.getSignature()
            ))
            .orElse(null);
    }

    @Override
    public void setProperty(@NonNull NightProfileProperty property) {
        this.backend.setProperty(new ProfileProperty(
            property.name(),
            property.value(),
            property.signature()
        ));
    }

    @Override
    public boolean isComplete() {
        return this.backend.isComplete();
    }

    @Override
    @NonNull
    public CompletableFuture<NightProfile> update() {
        return this.backend.update().thenApplyAsync(PaperProfile::new);
    }

    @Override
    public String toString() {
        return "PaperProfile{" +
            "backend=" + backend +
            '}';
    }
}
