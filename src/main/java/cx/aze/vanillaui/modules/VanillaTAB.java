package cx.aze.vanillaui.modules;

import cx.aze.vanillaui.VanillaUI;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VanillaTAB extends Module {
    private final SettingGroup sgColors = settings.createGroup("Colors");

    private static final Pattern rankRegex = Pattern.compile("^\\[(.*?)\\]\\s*(.*)$");

    public final Setting<Boolean> rankColors = sgColors.add(new BoolSetting.Builder()
        .name("rank-colors")
        .description("Use rank colors in the tablist.")
        .defaultValue(true)
        .build()
    );

    public final Setting<SettingColor> defaultColor = sgColors.add(new ColorSetting.Builder()
        .name("default-color")
        .defaultValue(new SettingColor(255, 255, 255))
        .visible(rankColors::get)
        .build()
    );

    public final Setting<SettingColor> primeColor = sgColors.add(new ColorSetting.Builder()
        .name("prime-color")
        .defaultValue(new SettingColor(84, 252, 252))
        .visible(rankColors::get)
        .build()
    );

    public final Setting<SettingColor> eliteColor = sgColors.add(new ColorSetting.Builder()
        .name("elite-color")
        .defaultValue(new SettingColor(239, 239, 80))
        .visible(rankColors::get)
        .build()
    );

    public final Setting<SettingColor> apexColor = sgColors.add(new ColorSetting.Builder()
        .name("apex-color")
        .defaultValue(new SettingColor(252, 168, 0))
        .visible(rankColors::get)
        .build()
    );

    public final Setting<SettingColor> legendColor = sgColors.add(new ColorSetting.Builder()
        .name("legend-color")
        .defaultValue(new SettingColor(255, 246, 143))
        .visible(rankColors::get)
        .build()
    );

    public VanillaTAB() {
        super(VanillaUI.CATEGORY, "vanilla-tab", "Makes the tablist clean & vanilla-like.");
    }

    public Text getPlayerName(PlayerListEntry entry) {
        assert entry.getDisplayName() != null;
        String raw = entry.getDisplayName().getString();

        Matcher m = rankRegex.matcher(raw);

        String rank = null;
        String username = raw;

        if (m.matches()) {
            rank = m.group(1);
            username = m.group(2);
        }

        int color = getRankColor(rank);

        MutableText clean = Text.literal(username)
            .styled(s -> s.withColor(color));

        return clean;
    }

    private int getRankColor(String rank) {
        if (!rankColors.get() || rank == null)
            return defaultColor.get().getPacked();

        String r = rank.toLowerCase();

        if (r.contains("apex")) return apexColor.get().getPacked();
        if (r.contains("legend")) return legendColor.get().getPacked();
        if (r.contains("elite")) return eliteColor.get().getPacked();
        if (r.contains("prime")) return primeColor.get().getPacked();

        return defaultColor.get().getPacked();
    }
}
