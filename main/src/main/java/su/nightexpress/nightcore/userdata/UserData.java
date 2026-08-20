package su.nightexpress.nightcore.userdata;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import su.nightexpress.nightcore.bridge.wrap.NightProfile;
import su.nightexpress.nightcore.bridge.wrap.NightProfileProperty;
import su.nightexpress.nightcore.db.state.StatefulData;
import su.nightexpress.nightcore.util.Players;
import su.nightexpress.nightcore.util.profile.PlayerProfiles;

public class UserData extends StatefulData {

    private final UUID id;
    private String     name;
    private String     lastSkinUrl;
    private String     lastTextureValue;
    private String     lastTextureSignature;
    private long       lastSeen;

    private NightProfile profile;

    public UserData(@NonNull UUID id, @NonNull String name) {
        this.id = id;
        this.setName(name);
    }

    public static @NonNull UserData of(@NonNull Player player) {
        NightProfile profile = Players.getProfile(player);

        URL skin = profile.getTextures().getSkin();
        String lastSkinUrl = skin == null ? null : skin.toString();
        NightProfileProperty textures = profile.getProperty("textures");

        UserData data = new UserData(player.getUniqueId(), player.getName());
        data.setLastSkinUrl(lastSkinUrl);
        data.setLastTextureProperty(textures);
        data.updateLastSeen();
        data.refreshProfile();
        return data;
    }

    public void update(@NonNull Player player) {
        NightProfile onlineProfile = Players.getProfile(player);
        this.update(onlineProfile);
    }

    public void update(@NonNull NightProfile profile) {
        this.profile = profile;

        String profileName = profile.getName();
        if (profileName != null && !this.name.equalsIgnoreCase(profileName)) {
            this.setName(profileName);
            this.markDirty();
        }

        URL profileSkin = profile.getTextures().getSkin();
        String newSkinUrl = profileSkin == null ? null : profileSkin.toString();
        NightProfileProperty textures = profile.getProperty("textures");

        if (!java.util.Objects.equals(this.lastSkinUrl, newSkinUrl)) {
            this.lastSkinUrl = newSkinUrl;
            this.markDirty();
        }

        if (!java.util.Objects.equals(this.lastTextureValue, textures == null ? null : textures.value())
            || !java.util.Objects.equals(this.lastTextureSignature, textures == null ? null : textures.signature())) {
            this.setLastTextureProperty(textures);
            this.markDirty();
        }
    }

    public void refreshProfile() {
        this.profile = PlayerProfiles.create(this.id, this.name);

        if (this.lastTextureValue != null && !this.lastTextureValue.isBlank()) {
            this.profile.setProperty(new NightProfileProperty(
                "textures",
                this.lastTextureValue,
                this.lastTextureSignature
            ));
        }
    }

    public @NonNull NightProfile getEffectiveProfile() {
        if (this.profile == null) this.refreshProfile();

        return this.profile;
    }

    public void updateLastSeen() {
        this.setLastSeen(System.currentTimeMillis());
    }

    public @Nullable Player getPlayer() {
        return Players.getPlayer(this.id);
    }

    public @NonNull Optional<Player> player() {
        return Players.findById(this.id);
    }

    public @Nullable NightProfile getProfile() {
        return this.profile;
    }

    public @NonNull UUID getId() {
        return this.id;
    }

    public @NonNull String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @Nullable String getLastSkinUrl() {
        return this.lastSkinUrl;
    }

    public void setLastSkinUrl(@Nullable String lastSkinUrl) {
        this.lastSkinUrl = lastSkinUrl;
    }

    public @Nullable String getLastTextureValue() {
        return this.lastTextureValue;
    }

    public void setLastTextureValue(@Nullable String lastTextureValue) {
        this.lastTextureValue = lastTextureValue;
    }

    public @Nullable String getLastTextureSignature() {
        return this.lastTextureSignature;
    }

    public void setLastTextureSignature(@Nullable String lastTextureSignature) {
        this.lastTextureSignature = lastTextureSignature;
    }

    private void setLastTextureProperty(@Nullable NightProfileProperty property) {
        this.lastTextureValue = property == null ? null : property.value();
        this.lastTextureSignature = property == null ? null : property.signature();
    }

    public long getLastSeen() {
        return this.lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }
}
