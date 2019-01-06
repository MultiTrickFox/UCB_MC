import java.util.*;


public class Run {



    static int how_many_monte_carlo       = 10_000;

    static int how_many_actions           = 5;
    static int ucb_steps_min              = 10;
    static int ucb_steps_max              = 100;
                                                    // 0 - ucb basic (for static data)
    static int simulation_id              = 0;      // 1 - ucb discounted (for dynamic data)
                                                    // 2 - ucb sliding window (for dynamic data)
    static boolean simulate_dynamic_data  = false;



    public static Object[] run_monte_carlo_suggestion(){


        ActionData.hm_actions = how_many_actions;

        int step_range = ucb_steps_max - ucb_steps_min;

        Float[] step_gains = new Float[step_range];
        for (int i = 0; i < step_range; i++) {
            step_gains[i] = (float) 0;
        }


        for (int s = 0; s < how_many_monte_carlo; s++) {
            System.out.println("monte carlo epoch : " + s);

            ActionData.initialize_data();

            Brute.init();
            double[] ret0 = Brute.get_result();
            double confident_action_brute = ret0[0];
            //double expected_gain_brute    = ret0[1];

            for (int hm_steps = ucb_steps_min; hm_steps < ucb_steps_max; hm_steps++) {

                if      (simulation_id == 0) UCB_1.init();
                // else if (simulation_id == 1) UCB_Discounted.init();
                // else if (simulation_id == 2) UCB_Sliding_Window.init();

                for (int step = 0; step < hm_steps; step++) {

                    if      (simulation_id == 0) UCB_1.forward();
                    // else if (simulation_id == 1) UCB_Discounted.forward();
                    // else if (simulation_id == 2) UCB_Sliding_Window.forward();

                    if (simulate_dynamic_data) {

                        ActionData.alter_data();

                        Brute.forward();
                        ret0 = Brute.get_result();
                        confident_action_brute = ret0[0];
                        // expected_gain_brute    = ret0[1];

                    }

                }

                double[] ret;
                if (simulation_id == 0) {
                    ret = UCB_1.get_result();
                    // double expected_rate    = ret[1];
                }
                else if (simulation_id == 1) {
                    ret = UCB_Discounted.get_result();
                    // double expected_rate    = ret1[1];
                }
                else if (simulation_id == 2) {
                    ret = UCB_Sliding_Window.get_result();
                    // double expected_rate    = ret1[1];
                }
                else {ret = new double[1];}

                double confident_action = ret[0];
                int carlo_index = hm_steps - ucb_steps_min;

                if (confident_action == confident_action_brute) step_gains[carlo_index] +=1;

            }

        }

        for (int i = 0; i < step_range; i++) {
            System.out.print("Step " + i + " : ");

            step_gains[i]      /= how_many_monte_carlo;

            System.out.println(step_gains[i]);
        }

        List<Float> carlo_list = Arrays.asList(step_gains);

        float expected_rate = Collections.max(carlo_list);
        int recommended_step = carlo_list.indexOf(expected_rate);

        return new Object[]{

                recommended_step + ucb_steps_min,
                expected_rate * 100,

        };

    }


    public static void main(String[] args) {

        Object[] results = run_monte_carlo_suggestion();

        int ucb1_optimal_steps = (int) results[0];
        float ucb1_expected_gain = (float) results[1];

        System.out.println("UCB-1 recommended steps: " + ucb1_optimal_steps);
        System.out.println("UCB-1 expected accuracy: " + ucb1_expected_gain);

    }


}
