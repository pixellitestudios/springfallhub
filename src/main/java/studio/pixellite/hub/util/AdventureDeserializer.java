package studio.pixellite.hub.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * A simple utility class for deserializing legacy messages into adventure components.
 */
public final class AdventureDeserializer {
  /**
   * Deserializes a string into an adventure component.
   *
   * @param string the string to deserialize
   * @return the new component
   */
  public static Component deserialize(String string) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(string)
            .decoration(TextDecoration.ITALIC, false);
  }
}
