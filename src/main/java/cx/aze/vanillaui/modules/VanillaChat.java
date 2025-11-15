package cx.aze.vanillaui.modules;

import cx.aze.vanillaui.VanillaUI;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VanillaChat extends Module {
    private final SettingGroup sgColors = this.settings.createGroup("Colors");
    private final SettingGroup sgText = this.settings.createGroup("Username");


    private static final Pattern chatRegex = Pattern.compile("^(?:\\[(.*?)\\]\\s*)?([A-Za-z0-9_]+) » (.+)$");

    public final Setting<String> usernamePrefix = sgText.add(new StringSetting.Builder()
        .name("username-prefix")
        .description("Prefix of the username.")
        .defaultValue("<")
        .build()
    );

    public final Setting<String> usernameSuffix = sgText.add(new StringSetting.Builder()
        .name("username-suffix")
        .description("Suffix of the username.")
        .defaultValue("> ")
        .build()
    );

    public final Setting<Boolean> greenText = sgColors.add(new BoolSetting.Builder()
        .name("green-text")
        .description("Iconic > greentext from 4chan.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> rankColors = sgColors.add(new BoolSetting.Builder()
        .name("rank-colors")
        .description("Display customizable rank colors.")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> defaultColor = sgColors.add(new ColorSetting.Builder()
        .name("default-color")
        .description("Default/No Rank color")
        .defaultValue(new SettingColor(255, 255, 255))
        .visible(rankColors::get)
        .build()
    );

    private final Setting<SettingColor> primeColor = sgColors.add(new ColorSetting.Builder()
        .name("prime-color")
        .description("Prime Rank color")
        .defaultValue(new SettingColor(84, 252, 252))
        .visible(rankColors::get)
        .build()
    );

    private final Setting<SettingColor> eliteColor = sgColors.add(new ColorSetting.Builder()
        .name("elite-color")
        .description("Elite Rank color")
        .defaultValue(new SettingColor(239, 239, 80))
        .visible(rankColors::get)
        .build()
    );

    private final Setting<SettingColor> apexColor = sgColors.add(new ColorSetting.Builder()
        .name("apex-color")
        .description("Apex Rank color")
        .defaultValue(new SettingColor(252, 168, 0))
        .visible(rankColors::get)
        .build()
    );

    private final Setting<SettingColor> legendColor = sgColors.add(new ColorSetting.Builder()
        .name("legend-color")
        .description("Legend Rank color")
        .defaultValue(new SettingColor(255, 246, 143))
        .visible(rankColors::get)
        .build()
    );

    private final Setting<SettingColor> youtubeColor = sgColors.add(new ColorSetting.Builder()
        .name("youtube-color")
        .description("Youtube Rank color")
        .defaultValue(new SettingColor(240, 107, 107))
        .visible(rankColors::get)
        .build()
    );

    public final Setting<SettingColor> ownerColor = sgColors.add(new ColorSetting.Builder()
        .name("owner-color")
        .defaultValue(new SettingColor(139, 0, 0))
        .visible(rankColors::get)
        .build()
    );


    public VanillaChat() {
        super(VanillaUI.CATEGORY, "vanilla-chat", "A module to make the appearance of 6b6t chat look more vanilla.");
    }


    @EventHandler
    public void onMessageReceive(ReceiveMessageEvent e) {
        String text = e.getMessage().getString();
        Matcher m = chatRegex.matcher(text);

        if (!m.matches()) return;

        String rank = m.group(1);
        String username = m.group(2);
        String message = m.group(3);

        MutableText user = Text.literal(username)
            .styled(style -> style.withColor(getRankColor(rank).toTextColor()));

        MutableText msg = Text.literal(message)
            .styled(style -> style.withColor(message.startsWith(">") && greenText.get() ? Formatting.GREEN : Formatting.WHITE));

        MutableText finalMsg = Text.literal(usernamePrefix.get())
            .append(user)
            .append(Text.literal(usernameSuffix.get()))
            .append(msg);

        e.setMessage(finalMsg);
    }

    private Color getRankColor(String rank) {
        if(!rankColors.get()) return Color.WHITE;
        if(rank == null) return defaultColor.get();

        String r = rank.toLowerCase();

        if (r.contains("youtube") || r.contains("yt")) return youtubeColor.get();
        if (r.contains("apex")) return apexColor.get();
        if (r.contains("legend")) return legendColor.get();
        if (r.contains("elite")) return eliteColor.get();
        if (r.contains("prime")) return primeColor.get();
        if (r.contains("owner")) return ownerColor.get();

        return defaultColor.get();
    }

}
