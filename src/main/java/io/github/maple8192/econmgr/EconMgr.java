package io.github.maple8192.econmgr;

import io.github.maple8192.econmgr.command.MoneyCommand;
import io.github.maple8192.economics.EconExtension;
import io.github.maple8192.economics.Economics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EconMgr extends JavaPlugin implements EconExtension {
    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("money")).setExecutor(new MoneyCommand(Economics.addExtension(this)));
    }
}
