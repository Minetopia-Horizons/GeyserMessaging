package nl.mthorizons.messaging.configs;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigSerializable
public final class MessagingConfig {

    @Comment("""
             Any character added to this list will be blocked within Text Displays, this will allow for less weird
             looking text in the display names.
            """)
    private List<String> blockedCharacters = new ArrayList<>();

}
