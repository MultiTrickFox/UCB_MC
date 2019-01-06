public class Run {


    static int how_many_actions    = 5;

    static int ucb_steps_min = 10;
    static int ucb_steps_max = 150;


    public static void main(String[] args){

        ActionData.hm_actions = how_many_actions;
        ActionData.initialize_data();




        // ActionData.alter_data()
    }

}
