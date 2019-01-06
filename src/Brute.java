import java.util.ArrayList;
import java.util.Collections;


class Brute {


    static int hm_brute_steps = 10_000_000;

    static ArrayList<Double> expecteds;


    static void init(){

        for (int action = 0; action < ActionData.hm_actions; action++) {

            double reward = 0.0;

            for (int i = 0; i < hm_brute_steps; i++) {

                reward +=ActionData.act(action);

            }

            expecteds.add(reward);

        }

    }


    static double[] get_result(){

        double max_val = Collections.max(expecteds);
        int max_index = expecteds.indexOf(max_val);

        double[] ret = {max_index, max_val};
        return ret;
    }


}
