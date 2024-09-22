package blueprint.dynawave.teams;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamManager {
    private static final List<ServerPlayerEntity> registeredPlayers = new ArrayList<>();

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("register")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            registerPlayer(player);
                            return 1;
                        }))
        );

        dispatcher.register(CommandManager.literal("unregister")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            unregisterPlayer(player);
                            return 1;
                        }))
        );

        dispatcher.register(CommandManager.literal("setTeams")
                .then(CommandManager.argument("numTeams", IntegerArgumentType.integer(1, 4))
                        .executes(context -> {
                            int numTeams = IntegerArgumentType.getInteger(context, "numTeams");
                            assignTeams(context.getSource().getServer(), numTeams);
                            return 1;
                        }))
        );

        dispatcher.register(CommandManager.literal("clearTeams")
                .executes(context -> {
                    clearTeams(context.getSource().getServer());
                    return 1;
                })
        );
    }

    private static void registerPlayer(ServerPlayerEntity player) {
        if (!registeredPlayers.contains(player)) {
            registeredPlayers.add(player);
            player.sendMessage(Text.literal("You have been registered for the event!"), false);
        } else {
            player.sendMessage(Text.literal("You are already registered."), false);
        }
    }

    private static void unregisterPlayer(ServerPlayerEntity player) {
        if (registeredPlayers.remove(player)) {
            player.sendMessage(Text.literal("You have been unregistered from the event."), false);
        } else {
            player.sendMessage(Text.literal("You were not registered."), false);
        }
    }

    public static void assignTeams(MinecraftServer server, int numTeams) {
        if (registeredPlayers.isEmpty()) {
            server.getPlayerManager().broadcast(Text.literal("No registered players to assign teams!"), false);
            return;
        }

        Scoreboard scoreboard = server.getScoreboard();
        Collections.shuffle(registeredPlayers);

        String[] teamColors = {"BLUE", "RED", "YELLOW", "GREEN"};
        String[] teamNames = {"Team Blue", "Team Red", "Team Yellow", "Team Green"};

        clearTeams(scoreboard);

        for (int i = 0; i < registeredPlayers.size(); i++) {
            ServerPlayerEntity player = registeredPlayers.get(i);
            int teamIndex = i % numTeams;

            String teamName = teamNames[teamIndex];
            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.addTeam(teamName);
                team.setDisplayName(Text.literal(teamColors[teamIndex]).styled(style -> style.withColor(Formatting.byName(teamColors[teamIndex].toLowerCase()))));
            }

            team.getPlayerList().add(player.getName().getString());
            player.sendMessage(Text.literal("You have been assigned to " + team.getDisplayName().getString()), false);
        }

        updateTabListWithTeams(server, numTeams);
        updateTabListHeaderFooter(server);
    }

    private static void clearTeams(Scoreboard scoreboard) {
        for (Team team : scoreboard.getTeams()) {
            team.getPlayerList().clear();
        }
    }

    public static void clearTeams(MinecraftServer server) {
        clearTeams(server.getScoreboard());
        registeredPlayers.clear();
        server.getPlayerManager().broadcast(Text.literal("All teams have been cleared."), false);
    }

    public static void updateTabListWithTeams(MinecraftServer server, int numTeams) {
        Scoreboard scoreboard = server.getScoreboard();
        String[] teamNames = {"Blue", "Red", "Yellow", "Green"};
        String[] teamColors = {"§9", "§c", "§e", "§a"};

        for (int i = 0; i < numTeams; i++) {
            Team team = scoreboard.getTeam(teamNames[i]);
            if (team != null) {
                for (String playerName : team.getPlayerList()) {
                    ServerPlayerEntity player = server.getPlayerManager().getPlayer(playerName);
                    if (player != null) {
                        String displayName = teamColors[i] + teamNames[i] + " | " + player.getName().getString();

                        player.setCustomName(Text.literal(displayName));

                        PlayerListS2CPacket packet = new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, player);
                        server.getPlayerManager().sendToAll(packet);
                    }
                }
            }
        }
    }

    private static boolean isPlayerInAnyTeam(ServerPlayerEntity player, Scoreboard scoreboard) {
        for (Team team : scoreboard.getTeams()) {
            if (team.getPlayerList().contains(player.getName().getString())) {
                return true;
            }
        }
        return false;
    }

    private static void updateTabListHeaderFooter(MinecraftServer server) {
        Text header = Text.literal("§lDynawave\n§m----------------").formatted(Formatting.BOLD, Formatting.AQUA);
        int totalPlayerCount = server.getPlayerManager().getPlayerList().size();
        Text footer = Text.literal("Total Players: " + totalPlayerCount).formatted(Formatting.GRAY);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.networkHandler.sendPacket(new PlayerListHeaderS2CPacket(header, footer));
        }
    }

    private static void refreshTabList(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.networkHandler.sendPacket(new PlayerListHeaderS2CPacket(
                    Text.literal("Dynawave Teams"), Text.empty()));
        }
    }
}