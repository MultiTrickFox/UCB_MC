import java.util.*;

public class Run {



    static int how_many_monte_carlo       = 10_000;

    static int how_many_actions           = 5;
    static int ucb_steps_min              = 10;
    static int ucb_steps_max              = 50;

    static boolean simulate_dynamic_data  = false;





    public static Object[] run_monte_carlo_suggestion(){

        ActionData.hm_actions = how_many_actions;

        int carlo_range = ucb_steps_max - ucb_steps_min;

        Float[] carlo_gains_ucb1      = new Float[carlo_range];
        Float[] carlo_gains_ucbdisc   = new Float[carlo_range];
        Float[] carlo_gains_ucbslidew = new Float[carlo_range];

        for (int i = 0; i < carlo_range; i++) {

            carlo_gains_ucb1[i]      = (float) 0;
            carlo_gains_ucbdisc[i]   = (float) 0;
            carlo_gains_ucbslidew[i] = (float) 0;

        }


        for (int s = 0; s < how_many_monte_carlo; s++) {

            // System.out.println("simulating carlo : " + s);

            ActionData.initialize_data();

            Brute.init();
            double[] ret0 = Brute.get_result();
            double confident_action_brute = ret0[0];
            //double expected_gain_brute    = ret0[1];


            for (int hm_steps = ucb_steps_min; hm_steps < ucb_steps_max; hm_steps++) {

                UCB_1.init();
//                UCB_Discounted.init();
//                UCB_Sliding_Window.init();

                for (int step = 0; step < hm_steps; step++) {

                    UCB_1.forward();
//                    UCB_Discounted.forward;
//                    UCB_Sliding_Window.forward();

                    if (simulate_dynamic_data) {

                        ActionData.alter_data();

                        Brute.init();
                        ret0 = Brute.get_result();
                        confident_action_brute = ret0[0];
                        // expected_gain_brute    = ret0[1];

                    }

                }


                double[] ret1 = UCB_1.get_result();
                double confident_action_ucb1 = ret1[0];
                // double expected_rate_ucb1    = ret1[1];

                double[] ret2 = UCB_Discounted.get_result();
                double confident_action_ucbdisc = ret2[0];
                // double expected_rate_ucbdisc    = ret2[1];

                double[] ret3 = UCB_Sliding_Window.get_result();
                double confident_action_ucbslidew = ret3[0];
                // double expected_rate_ucbslidew    = ret3[1];

                int carlo_id = hm_steps - ucb_steps_min;

                if (confident_action_ucb1      == confident_action_brute) carlo_gains_ucb1[carlo_id]       +=1;
                if (confident_action_ucbdisc   == confident_action_brute) carlo_gains_ucbdisc[carlo_id]    +=1;
                if (confident_action_ucbslidew == confident_action_brute) carlo_gains_ucbslidew[carlo_id]  +=1;

            }

        }

        for (int i = 0; i < carlo_range; i++) {

            // System.out.print("Step " + i + " : ");

            carlo_gains_ucb1[i]      /= how_many_monte_carlo;
            carlo_gains_ucbdisc[i]   /= how_many_monte_carlo;
            carlo_gains_ucbslidew[i] /= how_many_monte_carlo;

            // System.out.println(carlo_gains_ucb1[i]);

        }


        List<Float> carlo_list_ucb1 = Arrays.asList(carlo_gains_ucb1);
        List<Float> carlo_list_ucbdisc = Arrays.asList(carlo_gains_ucb1);
        List<Float> carlo_list_ucbslidew = Arrays.asList(carlo_gains_ucb1);

        float expected_rate_ucb1 = Collections.max(carlo_list_ucb1);
        int recommended_step_ucb1 = carlo_list_ucb1.indexOf(expected_rate_ucb1);

        float expected_rate_ucbdisc = Collections.max(carlo_list_ucbdisc);
        int recommended_step_ucbdisc = carlo_list_ucb1.indexOf(expected_rate_ucbdisc);

        float expected_rate_ucbslidew = Collections.max(carlo_list_ucbslidew);
        int recommended_step_ucbslidew = carlo_list_ucb1.indexOf(expected_rate_ucbslidew);


        return new Object[]{

                recommended_step_ucb1 + ucb_steps_min,
                expected_rate_ucb1 * 100,

                recommended_step_ucbdisc + ucb_steps_min,
                expected_rate_ucbdisc * 100,

                recommended_step_ucbslidew + ucb_steps_min,
                expected_rate_ucbslidew * 100
        };

    }


    public static void main(String[] args) {

        Object[] results = run_monte_carlo_suggestion();

        int ucb1_optimal_steps = (int) results[0];
        float ucb1_expected_gain = (float) results[1];

        System.out.println("UCB-1 recommended steps: " + ucb1_optimal_steps);
        System.out.println("UCB-1 expected accuracy: " + ucb1_expected_gain);


        // start training.. ?

    }


}
