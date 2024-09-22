package blueprint.dynawave.teams;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
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

    // Command to register players
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("register")
                .requires(source -> source.hasPermissionLevel(2)) // Require OP level
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            registerPlayer(player);
                            return 1;
                        }))
        );

        dispatcher.register(CommandManager.literal("unregister")
                .requires(source -> source.hasPermissionLevel(2)) // Require OP level
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

    // Register a player for the event
    private static void registerPlayer(ServerPlayerEntity player) {
        if (!registeredPlayers.contains(player)) {
            registeredPlayers.add(player);
            player.sendMessage(Text.literal("You have been registered for the event!"), false);
        } else {
            player.sendMessage(Text.literal("You are already registered."), false);
        }
    }

    // Unregister a player from the event
    private static void unregisterPlayer(ServerPlayerEntity player) {
        if (registeredPlayers.remove(player)) {
            player.sendMessage(Text.literal("You have been unregistered from the event."), false);
        } else {
            player.sendMessage(Text.literal("You were not registered."), false);
        }
    }

    // Assign teams to registered players
    public static void assignTeams(MinecraftServer server, int numTeams) {
        if (registeredPlayers.isEmpty()) {
            server.getPlayerManager().broadcast(Text.literal("No registered players to assign teams!"), false);
            return;
        }

        Scoreboard scoreboard = server.getScoreboard();
        Collections.shuffle(registeredPlayers);  // Shuffle for random team assignment

        String[] teamColors = {"BLUE", "RED", "YELLOW", "GREEN"};
        String[] teamNames = {"Team Blue", "Team Red", "Team Yellow", "Team Green"};

        // Clear old teams before assigning new ones
        clearTeams(scoreboard);

        // Assign players to teams
        for (int i = 0; i < registeredPlayers.size(); i++) {
            ServerPlayerEntity player = registeredPlayers.get(i);
            int teamIndex = i % numTeams;  // Cycle through available teams

            // Get or create the team
            String teamName = teamNames[teamIndex];
            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.addTeam(teamName);
                team.setDisplayName(Text.literal(teamColors[teamIndex]).styled(style -> style.withColor(Formatting.byName(teamColors[teamIndex].toLowerCase()))));
            }

            // Add the player to the team
            team.getPlayerList().add(player.getName().getString());
            player.sendMessage(Text.literal("You have been assigned to " + team.getDisplayName().getString()), false);
        }

        // Update tab list after assigning teams
        updateTabListWithTeams(server, numTeams);
        updateTabListHeaderFooter(server);  // Set the header/footer
    }

    // Clear old teams
    private static void clearTeams(Scoreboard scoreboard) {
        for (Team team : scoreboard.getTeams()) {
            team.getPlayerList().clear();  // Clear players from each team
        }
    }

    // Clear teams and reset player assignments
    public static void clearTeams(MinecraftServer server) {
        clearTeams(server.getScoreboard());
        registeredPlayers.clear();  // Clear the list of registered players
        server.getPlayerManager().broadcast(Text.literal("All teams have been cleared."), false);  // Notify players
    }

    public static void updateTabListWithTeams(MinecraftServer server, int numTeams) {
        Scoreboard scoreboard = server.getScoreboard();
        String[] teamNames = {"Blue", "Red", "Yellow", "Green"}; // Team names without "Team"
        String[] teamColors = {"§9", "§c", "§e", "§a"}; // Color codes for blue, red, yellow, green

        // Loop through each team and update the tab list
        for (int i = 0; i < numTeams; i++) {
            Team team = scoreboard.getTeam(teamNames[i]);
            if (team != null) {
                for (String playerName : team.getPlayerList()) {
                    ServerPlayerEntity player = server.getPlayerManager().getPlayer(playerName);
                    if (player != null) {
                        // Set the player's custom display name to TEAMNAME|PLAYERNAME
                        String displayName = teamNames[i] + " | " + player.getName().getString();
                        player.setCustomName(Text.literal(teamColors[i] + displayName));
                        player.setCustomNameVisible(true); // Show custom name in tab list
                    }
                }
            }
        }
    }

    // Helper function to check if a player is in any team
    private static boolean isPlayerInAnyTeam(ServerPlayerEntity player, Scoreboard scoreboard) {
        for (Team team : scoreboard.getTeams()) {
            if (team.getPlayerList().contains(player.getName().getString())) {
                return true;
            }
        }
        return false;
    }
    // Method to update tab list with custom header and footer
    private static void updateTabListHeaderFooter(MinecraftServer server) {
        Text header = Text.literal("§lDynawave Teams").formatted(Formatting.BOLD, Formatting.AQUA); // Set the header
        Text footer = Text.literal(""); // No footer in this case

        // Send header and footer to each player
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.networkHandler.sendPacket(new PlayerListHeaderS2CPacket(header, footer));
        }
    }

    // Send packets to all players to ensure tab list is updated
    private static void refreshTabList(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.networkHandler.sendPacket(new PlayerListHeaderS2CPacket(
                    Text.literal("Dynawave Teams"), Text.empty()));  // Header with no footer
        }
    }
}