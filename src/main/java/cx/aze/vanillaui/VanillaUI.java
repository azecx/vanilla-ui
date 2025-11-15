package cx.aze.vanillaui;

import com.mojang.logging.LogUtils;
import cx.aze.vanillaui.modules.VanillaChat;
import cx.aze.vanillaui.modules.VanillaTAB;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class VanillaUI extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Vanilla UI");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Vanilla UI");

        // Modules
        Modules.get().add(new VanillaChat());
        Modules.get().add(new VanillaTAB());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "cx.aze.vanillaui";
    }
}
