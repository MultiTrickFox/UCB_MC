import java.util.*;
import static java.lang.Math.*;


class UCB_1 {


    static    int[] played_times;
    static  float[] reward_sums;
    static Double[] upp_confidences;

    static int t;

    static Random random;


    static void init(){

        random = new Random();

        int hm_actions = ActionData.hm_actions;

        played_times = new int[hm_actions];
        reward_sums = new float[hm_actions];
        upp_confidences = new Double[hm_actions];

        for (int id = 0; id < hm_actions; id++) {
            upp_confidences[id] = 999.9;
        }

        t = 0;


        ArrayList<Integer> action_ids = new ArrayList<>();
        for (int id = 0; id < hm_actions; id++) {

            action_ids.add(id);

        }

        int hm_initial_actions = random.nextInt(hm_actions-1);

        int picked_id;
        double reward;

        for (int i = 0; i < hm_initial_actions; i++) {

            picked_id = action_ids.get(i);
            reward = ActionData.act(picked_id);

            update(picked_id, reward);

        }

    }


    static void forward(){

        List<Double> upp_conf_list = Arrays.asList(upp_confidences);
        double max_val = Collections.max(upp_conf_list);

        int picked_id = upp_conf_list.indexOf(max_val);
        double reward = ActionData.act(picked_id);

        update(picked_id, reward);

    }


    static double[] get_result(){

        List<Double> upp_conf_list = new LinkedList<>(Arrays.asList(upp_confidences));

        double max_val = Collections.max(upp_conf_list);
        int max_index = upp_conf_list.indexOf(max_val);

        while (played_times[max_index] == 0) {

            upp_conf_list.remove(max_index);

            int next_max_index = upp_conf_list.indexOf(Collections.max(upp_conf_list));

            max_index = next_max_index -1;

        }

        double max_expected = reward_sums[max_index] / played_times[max_index];

        return new double[]{max_index, max_expected};
    }


    private static void update(int action_id, double reward){

        played_times[action_id]   +=1;
        reward_sums[action_id]    += reward;
        upp_confidences[action_id] = reward_sums[action_id]/played_times[action_id] +
                                     sqrt(1.5 * log(t+1)/played_times[action_id]);

        t +=1;

    }

}
