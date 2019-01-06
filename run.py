from random import random

import brute_expected
import ucb_simple


hm_actions = 10

ucb_steps_upto = 100

hm_carlo = 50_000


def run():

    # sync data

    init_modules()

    tests = ['ucb_simple']

    # eval brute expected

    print('> Evaluating gains..')
    be_id, be_data = brute_expected.run()

    # apply monte_carlo(test) for test in tests

    print('                      \n',
          '0- ucb basic          \n',
          '1- ucb discounted     \n',
          '2- ucb sliding window \n'
          )

    for test in input('> Enter ucb_ids with , : ').split(','):
        fn = tests[int(test)]
        step_size, gain = monte_carlo(fn, (be_id, be_data))
        print(f'{fn}, monte carlo suggest step_size : {step_size} w/ gain : {gain}')


def monte_carlo(fn, answers, do_print=True):
    brute_max, brute_expected = answers

    step_expecteds = []
    for step in range(1, ucb_steps_upto):
        f = eval(fn)
        f.hm_steps = step

        step_exp = 0
        for _ in range(hm_carlo):

            f.t = 0
            f.played_times    = [0 for _ in range(hm_actions)]
            f.reward_sums     = [0 for _ in range(hm_actions)]
            f.upp_confidences = [999.9 for _ in range(hm_actions)]

            id, exp = eval((fn + '.run'))(do_print=False)

            step_exp += exp/brute_expected[brute_max]

            # if id == brute_max:
            #     step_exp +=1
            # elif round(exp,1) == round(brute_expected[id],1):
            #     step_exp +=0.2
            # else:
            #     step_exp -=1
        step_expecteds.append(step_exp)
        if do_print:
            print(f'> fn: {fn}, step: {step}, gain: {step_exp / hm_carlo}')

    return argmax(step_expecteds)+1, max(step_expecteds) / hm_carlo


def argmax(array):
    return array.index(max(array))


def init_modules():

    win_rates = [random() for _ in range(hm_actions)]
    gains     = [random() for _ in range(hm_actions)]
    losses    = [random() for _ in range(hm_actions)]

    brute_expected.hm_actions = hm_actions
    brute_expected.win_rates  = win_rates
    brute_expected.gains      = gains
    brute_expected.losses     = losses

    ucb_simple.hm_actions = hm_actions
    ucb_simple.win_rates  = win_rates
    ucb_simple.gains      = gains
    ucb_simple.losses     = losses

    ucb_simple.played_times    = [0 for _ in range(hm_actions)]
    ucb_simple.reward_sums     = [0 for _ in range(hm_actions)]
    ucb_simple.upp_confidences = [999.9 for _ in range(hm_actions)]

    print('> Data initialized.')



if __name__ == '__main__':
    run()

