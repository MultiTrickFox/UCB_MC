import java.util.List;
import java.util.Arrays;
import java.util.Collections;


class UCB_1 {


    static    int[] played_times;
    static  float[] reward_sums;
    static Double[] upp_confidences;

    static int t;


    static void init(){

        int hm_actions = ActionData.hm_actions;

        played_times = new int[hm_actions];
        reward_sums = new float[hm_actions];
        upp_confidences = new Double[hm_actions];

        for (int id = 0; id < hm_actions; id++) {
            upp_confidences[id] += 999.9;
        }

        t = 0;

    }


    static void forward(){

        


        t +=1;

    }


    static double[] get_result(){

        List<Double> upp_conf_list = Arrays.asList(upp_confidences);
        double max_val = Collections.max(upp_conf_list);
        int max_index = upp_conf_list.indexOf(max_val);

        return new double[]{max_index, max_val};
    }


}
