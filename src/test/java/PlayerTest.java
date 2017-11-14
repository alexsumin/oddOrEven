import org.junit.Test;
import ru.alexsumin.oddoreven.model.LuckyPlayer;
import ru.alexsumin.oddoreven.model.Player;
import ru.alexsumin.oddoreven.model.SimplePlayer;

public class PlayerTest {

    @Test
    public void randomTest() {
        SimplePlayer sp = new SimplePlayer();
        System.out.println(sp.getDecision());
        System.out.println(sp.getDecision());
        System.out.println(sp.getDecision());
        System.out.println(sp.getDecision());
        System.out.println(sp.getDecision());
    }

    @Test
    public void randomLuckyTest() {
        LuckyPlayer lp = new LuckyPlayer();
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
        System.out.println(lp.getDecision());
    }


    @Test
    public void simplePlayerTest() {
        SimplePlayer sp = new SimplePlayer();

        int one = 0;
        int two = 0;
        int three = 0;

        for (int i = 0; i < 1000; i++) {
            switch (sp.getDecision()) {
                case (1): {
                    one++;
                    break;
                }
                case (2): {
                    two++;
                    break;
                }
                case (3): {
                    three++;
                    break;
                }
            }
        }
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
    }

    @Test
    public void luckyPlayerTest() {
        Player pl = new LuckyPlayer();

        int one = 0;
        int two = 0;
        int three = 0;

        for (int i = 0; i < 1000; i++) {
            switch (pl.getDecision()) {
                case (1): {
                    one++;
                    break;
                }
                case (2): {
                    two++;
                    break;
                }
                case (3): {
                    three++;
                    break;
                }
            }
        }
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
    }
}
