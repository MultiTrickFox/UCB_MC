import java.util.Scanner;

public class Run {


    static int how_many_actions    = 5;

    static int ucb_steps_min       = 10;
    static int ucb_steps_max       = 150;

    static int how_many_monte_carlo       = 1_000_000;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("'d' for dynamic data, else static.");
        boolean dynamic_data = sc.nextBoolean();


        ActionData.hm_actions = how_many_actions;


        for (int s = 0; s < how_many_monte_carlo; s++) {

            ActionData.initialize_data();

            Brute.init();
            double[] ret0 = Brute.get_result();
            double confident_action_brute = ret0[0];
            double expected_gain_brute    = ret0[1];


            for (int hm_steps = ucb_steps_min; hm_steps < ucb_steps_max; hm_steps++) {

                UCB_1.init();
//                UCB_Discounted.init();
//                UCB_Sliding_Window.init();

                double step_correctness_ucb       = 0.0;
                double step_correctness_ucbdisc   = 0.0;
                double step_correctness_ucbslidew = 0.0;

                for (int step = 0; step < hm_steps; step++) {

                    UCB_1.forward();
//                    UCB_Discounted.forward;
//                    UCB_Sliding_Window.forward();

                    if (dynamic_data) {

                        ActionData.alter_data();

                        Brute.init();
                        ret0 = Brute.get_result();
                        confident_action_brute = ret0[0];
                        expected_gain_brute    = ret0[1];

                    }

                }


                double[] ret1 = UCB_1.get_result();
                double confident_action_ucb1 = ret1[0];
                double expected_gain_ucb1    = ret1[1];

                double[] ret2 = UCB_Discounted.get_result();
                double confident_action_ucbdisc = ret2[0];
                double expected_gain_ucbdisc    = ret2[1];

                double[] ret3 = UCB_Sliding_Window.get_result();
                double confident_action_ucbslidew = ret3[0];
                double expected_gain_ucbslidew    = ret3[1];


                if (confident_action_ucb1      == confident_action_brute) step_correctness_ucb       +=0.5;
                if (confident_action_ucbdisc   == confident_action_brute) step_correctness_ucbdisc   +=0.5;
                if (confident_action_ucbslidew == confident_action_brute) step_correctness_ucbslidew +=0.5;

                step_correctness_ucb       +=expected_gain_ucb1 /expected_gain_brute                  *0.5;
                step_correctness_ucbdisc   +=expected_gain_ucbdisc /expected_gain_brute               *0.5;
                step_correctness_ucbslidew +=expected_gain_ucbslidew /expected_gain_brute             *0.5;

            }

        }

    }


}
