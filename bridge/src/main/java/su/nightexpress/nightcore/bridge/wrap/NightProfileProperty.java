package su.nightexpress.nightcore.bridge.wrap;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * A platform-neutral player profile property.
 *
 * <p>The Paper profile API exposes these properties directly, while the
 * Bukkit PlayerTextures API only exposes a URL and rejects non-Mojang hosts.
 * Keeping the property value here allows custom Yggdrasil textures to be
 * persisted without going through PlayerTextures#setSkin.</p>
 */
public record NightProfileProperty(
    @NonNull String name,
    @NonNull String value,
    @Nullable String signature
) {
}
