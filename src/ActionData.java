import java.util.Random;
import java.util.ArrayList;


class ActionData {


    static int hm_actions = 2;

    static ArrayList<Double> win_rates;
    static ArrayList<Double>     gains;
    static ArrayList<Double>    losses;

    static private Random random;


    static void initialize_data(){

        win_rates = new ArrayList<>();
        gains     = new ArrayList<>();
        losses    = new ArrayList<>();

        random    = new Random();

        for (int id = 0; id < hm_actions; id++){

            win_rates.add(random.nextDouble());
            gains.add(random.nextDouble());
            losses.add(random.nextDouble());

        }

    }


    static void alter_data(){

        for (int id = 0; id < hm_actions; id++){

            if (random.nextDouble() < random.nextDouble()) win_rates.set(id, random.nextDouble());
            if (random.nextDouble() < random.nextDouble()) gains.set(id, random.nextDouble());
            if (random.nextDouble() < random.nextDouble()) losses.set(id, random.nextDouble());

        }

    }


    static double act(int action_id){
        
        if (random.nextDouble() > win_rates.get(action_id)) return gains.get(action_id);
        else                                                return losses.get(action_id);
            
    }

    
}
