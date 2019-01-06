import java.util.*;

public class Run {


    static int how_many_actions    = 10;

    static int ucb_steps_min       = 5;
    static int ucb_steps_max       = 200;

    static int how_many_monte_carlo       = 1_000_000;


    public static Object[] run_monte_carlo_evaluator(){

        Scanner sc = new Scanner(System.in);


        System.out.println("'true' for dynamic data, else static.");
        boolean dynamic_data = sc.nextBoolean();


        ActionData.hm_actions = how_many_actions;

        Float[] carlo_gains_ucb1      = new Float[how_many_monte_carlo];
        Float[] carlo_gains_ucbdisc   = new Float[how_many_monte_carlo];
        Float[] carlo_gains_ucbslidew = new Float[how_many_monte_carlo];

        for (int i = 0; i < how_many_monte_carlo; i++) {

            carlo_gains_ucb1[i]      = (float) 0;
            carlo_gains_ucbdisc[i]   = (float) 0;
            carlo_gains_ucbslidew[i] = (float) 0;

        }


        for (int s = 0; s < how_many_monte_carlo; s++) {

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

                    if (dynamic_data) {

                        ActionData.alter_data();

                        Brute.init();
                        ret0 = Brute.get_result();
                        confident_action_brute = ret0[0];
                        // expected_gain_brute    = ret0[1];

                    }

                }


                double[] ret1 = UCB_1.get_result();
                double confident_action_ucb1 = ret1[0];
                // double expected_gain_ucb1    = ret1[1];

                double[] ret2 = UCB_Discounted.get_result();
                double confident_action_ucbdisc = ret2[0];
                // double expected_gain_ucbdisc    = ret2[1];

                double[] ret3 = UCB_Sliding_Window.get_result();
                double confident_action_ucbslidew = ret3[0];
                // double expected_gain_ucbslidew    = ret3[1];

                if (confident_action_ucb1      == confident_action_brute) carlo_gains_ucb1[s]       +=1;
                if (confident_action_ucbdisc   == confident_action_brute) carlo_gains_ucbdisc[s]    +=1;
                if (confident_action_ucbslidew == confident_action_brute) carlo_gains_ucbslidew[s]  +=1;

            }

        }

        for (int i = ucb_steps_min; i < ucb_steps_max; i++) {

            System.out.print("Step " + i + " : ");

            carlo_gains_ucb1[i]      /= how_many_monte_carlo;
            carlo_gains_ucbdisc[i]   /= how_many_monte_carlo;
            carlo_gains_ucbslidew[i] /= how_many_monte_carlo;

            System.out.println(carlo_gains_ucb1[i]);

        }


        List<Float> carlo_list_ucb1 = Arrays.asList(carlo_gains_ucb1);

        float expected_gain_ucb1 = Collections.max(carlo_list_ucb1);
        int recommended_step_ucb1 = carlo_list_ucb1.indexOf(expected_gain_ucb1);

        List<Float> carlo_list_ucbdisc = Arrays.asList(carlo_gains_ucb1);

        float expected_gain_ucbdisc = Collections.max(carlo_list_ucbdisc);
        int recommended_step_ucbdisc = carlo_list_ucb1.indexOf(expected_gain_ucbdisc);

        List<Float> carlo_list_ucbslidew = Arrays.asList(carlo_gains_ucb1);

        float expected_gain_ucbslidew = Collections.max(carlo_list_ucbslidew);
        int recommended_step_ucbslidew = carlo_list_ucb1.indexOf(expected_gain_ucbslidew);


        return new Object[]{

                recommended_step_ucb1,
                expected_gain_ucb1,

                recommended_step_ucbdisc,
                expected_gain_ucbdisc,

                recommended_step_ucbslidew,
                expected_gain_ucbslidew
        };

    }


    public static void main(String[] args) {

        Object[] results = run_monte_carlo_evaluator();

        int ucb1_optimal_steps = (int) results[0];
        double ucb1_expected_gain = (double) results[2];

        System.out.println("UCB-1 recommended steps: " + ucb1_optimal_steps);
        System.out.println("UCB-1 expected gain: " + ucb1_expected_gain);


        // start training.. ?

    }


}
