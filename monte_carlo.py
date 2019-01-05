from random import random

hm_carlo = 1_000_000


hm_actions = 12

win_rates = [random() for _ in range(hm_actions)]
gains     = [random() for _ in range(hm_actions)]
losses    = [random() for _ in range(hm_actions)]


def run():

    # main

    expecteds = []
    for _ in range(hm_actions):
        e = 0
        for __ in range(hm_carlo):
            if random() < win_rates[_]:
                e += losses[_]
            else:
                e += gains[_]
        expecteds.append(e)
    expecteds = [e/hm_carlo for e in expecteds]

    # results

    print('Monte Carlo expected:')
    for _, e in enumerate(expecteds):
        actual_e = win_rates[_] * gains[_] - (1-win_rates[_]) * losses[_]
        print(f'E[{_}] = {e/hm_carlo}')
        print(f'actual_e:{actual_e}')
        print('mc is ' + ('close.' if round(actual_e,1) == round(e,1) else 'off.'))
    max_expected = argmax(expecteds)
    print(f'max(mc) = {max_expected}')

    return max_expected, expecteds


def argmax(array):
    return array.index(max(array))