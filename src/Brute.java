import java.util.ArrayList;
import java.util.Collections;


class Brute {


    static int hm_brute_steps = 10_000_000;


    static ArrayList<Double> expecteds;
    static int hm_times_changed = 0;


    static void init(){

        expecteds = new ArrayList<>();

        for (int action = 0; action < ActionData.hm_actions; action++) {

            double reward = 0.0;

            for (int i = 0; i < hm_brute_steps; i++) {

                reward +=ActionData.act(action);

            }

            expecteds.add(reward / hm_brute_steps);

        }

    }


    static void forward(){

        for (int action = 0; action < ActionData.hm_actions; action++) {

            double reward = 0.0;

            for (int i = 0; i < hm_brute_steps; i++) {

                reward +=ActionData.act(action);

            }

            expecteds.set(action, (expecteds.get(action) * hm_times_changed + reward) / (hm_times_changed + 1));

        }

        hm_times_changed +=1;

    }


    static double[] get_result(){

        double max_val = Collections.max(expecteds);
        int max_index = expecteds.indexOf(max_val);

        return new double[]{max_index, max_val};
    }


}
