/*
import controller.player.PlayerFriendsMenuController;
import main.Client;
import model.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class FriendsMenuTest {
    Player player1 = new Player("mohamadamin", "karami", "m.karami", "abcd123", "123", "karami@gmail.com", "09121234567", 10);
    Player player2 = new Player("ali", "alavi", "a.alavi", "abcd", "123", "alavi@gmail.com", "09121234567", 12);
    Player player3 = new Player("hasan", "alavi", "h.alavi", "abc", "123", "h.alavi@gmail.com", "09121234567", 13);
    PlayerFriendsMenuController controller = new PlayerFriendsMenuController(Client.getClientInfo());

    public FriendsMenuTest() {
        player1.getFriends().add(player2);
        player1.getFriends().add(player3);
    }

    @Test
    public void showFriendsTest() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("a.alavi");
        expected.add("h.alavi");
        Assert.assertArrayEquals(expected.toArray(), controller.showFriends().toArray());
    }
}
*/
