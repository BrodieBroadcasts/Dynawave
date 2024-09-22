package blueprint.dynawave.course;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceManager {
    public static final RaceManager INSTANCE = new RaceManager();
    private final Map<PlayerEntity, CourseState> playerCourseStates = new HashMap<>();
    private final List<PlayerEntity> finishOrder = new ArrayList<>();

    public static RaceManager getInstance() {
        return INSTANCE;
    }


    public void handleCheckpoint(PlayerEntity player, int checkpointType) {
        CourseState courseState = playerCourseStates.getOrDefault(player, new CourseState());
        playerCourseStates.put(player, courseState);

        if (checkpointType == 0) {
            startCourse(player);
        } else if (checkpointType >= 1 && checkpointType <= 9) {
            endCourse(player, checkpointType);
        }
    }

    private void startCourse(PlayerEntity player) {
        if (!playerCourseStates.containsKey(player)) {
            playerCourseStates.put(player, new CourseState());
            notifyPlayers("The course has started! Good luck, " + player.getName().getString() + "!");
        }
    }

    private void endCourse(PlayerEntity player, int checkpointType) {
        if (!finishOrder.contains(player)) {
            finishOrder.add(player); // Add player to finish order
            calculatePoints(player, finishOrder.indexOf(player) + 1); // Calculate points based on place
        }

        playerCourseStates.remove(player); // Remove player from active race state
    }
        private void calculatePoints (PlayerEntity player,int place) {
            int points = calculatePointsForPlace(place);
            // Notify player of points scored
            player.sendMessage(Text.literal("You finished in place " + place + " and earned " + points + " points!"), false);
        }

    private int calculatePointsForPlace(int place) {
        // Define points based on place, adjust as needed
        switch (place) {
            case 1: return 10; // First place
            case 2: return 7;  // Second place
            case 3: return 5;  // Third place
            default: return 1;  // All other places
        }
    }

    private void resetRace() {
        playerCourseStates.clear();
        finishOrder.clear();
    }

    private void notifyPlayers(String message) {
        for (PlayerEntity player : playerCourseStates.keySet()) {
            player.sendMessage(Text.literal(message), false);
        }
    }
    private static class CourseState {
        // You can store any course-specific data here, if needed
    }
}
