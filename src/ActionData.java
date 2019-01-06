import java.util.Random;
import java.util.ArrayList;


class ActionData {


    static int hm_actions = 2;

    static ArrayList<Float> win_rates;
    static ArrayList<Float>     gains;
    static ArrayList<Float>    losses;

    static private Random random;


    static void initialize_data(){

        win_rates = new ArrayList<>();
        gains     = new ArrayList<>();
        losses    = new ArrayList<>();

        random    = new Random();

        for (int id = 0; id < hm_actions; id++){

            win_rates.add(random.nextFloat());
            gains.add(random.nextFloat());
            losses.add(random.nextFloat());

        }

    }


    static void alter_data(){

        for (int id = 0; id < hm_actions; id++){

            if (random.nextFloat() < random.nextFloat()) win_rates.set(id, random.nextFloat());
            if (random.nextFloat() < random.nextFloat()) gains.set(id, random.nextFloat());
            if (random.nextFloat() < random.nextFloat()) losses.set(id, random.nextFloat());

        }

    }


}
