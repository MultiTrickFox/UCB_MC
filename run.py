from random import random, choices, randint
from math import log, sqrt

hm_carlo = 1_000_000


hm_actions = 12

win_rates = [random() for _ in range(hm_actions)]
gains     = [random() for _ in range(hm_actions)]
losses    = [random() for _ in range(hm_actions)]

played_times    = [0 for _ in range(hm_actions)]
reward_sums     = [0 for _ in range(hm_actions)]
upp_confidences = [999.9 for _ in range(hm_actions)]

hm_steps = 12



def run():

    t = 0

    # initial steps

    initial_actions = choices(range(hm_actions), k=randint(0, hm_actions-1))

    for id in initial_actions:
        reward = act(id)
        update(id, reward, t)

        t +=1

    # rest of

    for i in range(hm_steps):

        id_confident = argmax(upp_confidences)
        update(id_confident, act(id_confident), t)

        t +=1

    # results

    print('Monte Carlo expected:')
    expecteds = monte_carlo_expecteds(hm_carlo)
    for _, e in enumerate(expecteds):
        actual_e = win_rates[_] * gains[_] - (1-win_rates[_]) * losses[_]
        print(f'E[{_}] = {e/hm_carlo}')
        print(f'actual_e:{actual_e}')
        print('mc is ' + ('close enough.' if round(actual_e,2) == round(e,2) else 'off.'))
    max_expected = argmax(expecteds)
    print(f'max(mc) = {max_expected}')

    print('UCB-1 learned:')
    for _, (pt, rs, uc) in enumerate(zip(played_times, reward_sums, upp_confidences)):

        print(f'\t id:{_}, \n'
              f'upper_confidence:{round(uc,5)} \n'
              f'played_time:{pt} \n'
              f'reward_avg:{rs/played_times[_] if played_times[_] != 0 else None}')

    id_winner = argmax(upp_confidences)
    while played_times[id_winner] == 0:
        upp_confidences.pop(id_winner)
        next_win = argmax(upp_confidences)
        if id_winner < next_win:
            id_winner = next_win-1
        else:
            id_winner = next_win+1
    print(f'Winner : action{id_winner}')


    print('winners ' + ('match.' if id_winner == max_expected else 'do not match.'))
    print(f'e[{id_winner}]s ' + ('close enough.' if round(expecteds[id_winner]/hm_carlo,1) == round(reward_sums[id_winner]/played_times[id_winner], 1)
                                 else 'off.'))



def act(action_id):
    if random() < win_rates[action_id]:
        return losses[action_id]
    else:
        return gains[action_id]


def update(id, reward, t):
    played_times[id]   += 1
    reward_sums[id]    += reward
    upp_confidences[id] = reward_sums[id]/played_times[id] + sqrt(3/2 * log(t+1)/played_times[id])



def monte_carlo_expecteds(hm_carlo):
    expecteds = []
    for _ in range(hm_actions):
        e = 0
        for __ in range(hm_carlo):
            e += act(_)
        expecteds.append(e)

    return expecteds

def argmax(array):
    return array.index(max(array))



if __name__ == '__main__':
    run()

