from random import random

import monte_carlo
import ucb_simple


hm_actions = 5

hm_ucb_steps = 45


def run():

    # sync data

    init_modules()

    tests = [monte_carlo.run. ucb_simple.run]
    ids, exps = [], []

    for test in input('Enter 0-3s with , : ').split(','):
        id, exp = tests[int(test)]()
        ids.append(id)
        exps.append(exp)

    # # apply monte carlo
    #
    # id_mc, exp_mc = monte_carlo.run()
    #
    # # apply ucb-1
    #
    # id_ucb, exp_ucb = ucb_simple.run()
    #
    # # results
    #
    # print(f'\t action_id \t expected_gain \n',
    #       f'monte carlo : \t {id_mc} \t {exp_mc[id_mc]} \n',
    #       f'ucb-1: : \t {id_ucb} \t {exp_ucb} \n'
    #       )
    #
    #
    # print('winners ' + ('match.' if id_mc == id_ucb else 'do not match.'))










def argmax(array):
    return array.index(max(array))


def init_modules():

    win_rates = [random() for _ in range(hm_actions)]
    gains     = [random() for _ in range(hm_actions)]
    losses    = [random() for _ in range(hm_actions)]

    monte_carlo.hm_actions = hm_actions
    monte_carlo.win_rates  = win_rates
    monte_carlo.gains      = gains
    monte_carlo.losses      = losses

    ucb_simple.hm_actions = hm_actions
    ucb_simple.hm_steps   = hm_ucb_steps
    ucb_simple.win_rates  = win_rates
    ucb_simple.gains      = gains
    ucb_simple.losses     = losses

    ucb_simple.played_times    = [0 for _ in range(hm_actions)]
    ucb_simple.reward_sums     = [0 for _ in range(hm_actions)]
    ucb_simple.upp_confidences = [999.9 for _ in range(hm_actions)]





if __name__ == '__main__':
    run()

