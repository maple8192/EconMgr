package io.github.maple8192.econmgr.command;

import io.github.maple8192.economics.EconCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class MoneyCommand implements CommandExecutor {
    private final EconCore core;

    public MoneyCommand(EconCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only player can execute this command.");
                return true;
            }
            player.sendMessage(core.get(player.getUniqueId()).map(BigDecimal::toString).orElse("Balance unset"));
            return true;
        }

        var player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            return false;
        }

        if (args.length < 2) {
            return false;
        }

        return switch (args[1]) {
            case "get" -> {
                player.sendMessage(core.get(player.getUniqueId()).map(BigDecimal::toString).orElse("Balance unset"));
                yield true;
            }
            case "set" -> {
                if (args.length < 3) {
                    yield false;
                }

                try {
                    var value = new BigDecimal(args[2]);
                    var ret = core.set(player.getUniqueId(), value);
                    if (!ret) {
                        player.sendMessage("DB not exist");
                    }
                    player.sendMessage("Balance set to " + value);
                } catch (NumberFormatException ex) {
                    player.sendMessage("Not a number");
                }

                yield true;
            }
            default -> false;
        };
    }
}
