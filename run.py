from random import random

import monte_carlo
import ucb_simple


hm_actions = 5

hm_ucb_steps = 45


def run():

    # sync data

    init_modules()

    tests = ['monte_carlo.run', 'ucb_simple.run']
    ids, exps = [], []

    print(' 0- monte carlo        \n',
          '1- ucb 1              \n',
          '2- ucb discounted     \n',
          '3- ucb sliding window \n')

    for test in input('> Enter 0-3s with , : ').split(','):
        id, exp = eval(tests[int(test)])()
        ids.append(id)
        exps.append(exp)

    print('\t test \t confident_action \t expected_reward')
    for _, (id, exp) in enumerate(zip(ids, exps)):
        print(f'{tests[_][:-4]} \t {id} \t {exp}')







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

