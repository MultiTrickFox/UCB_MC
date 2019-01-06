from random import random, choices
from math import log, sqrt


hm_actions = 10

hm_steps = 500


win_rates = [random() for _ in range(hm_actions)]
gains     = [random() for _ in range(hm_actions)]
losses    = [random() for _ in range(hm_actions)]

played_times    = [0 for _ in range(hm_actions)]
reward_sums     = [0 for _ in range(hm_actions)]
upp_confidences = [999.9 for _ in range(hm_actions)]

t = 0


def run(do_print=True):

    # initial exploration

    initial_actions = choices(range(hm_actions), k=int(hm_actions*37/100))  # k=randint(0, hm_actions - 1))

    for id in initial_actions:
        reward = act(id)
        update(id, reward)

    # main

    for i in range(hm_steps):
        id_confident = argmax(upp_confidences)
        update(id_confident, act(id_confident))

    # results

    while played_times[id_confident] == 0:
        upp_confidences.pop(id_confident)
        next_win = argmax(upp_confidences)
        if id_confident < next_win:
            id_confident = next_win-1
        else:
            id_confident = next_win+1

    expected_gain = reward_sums[id_confident] / played_times[id_confident]

    if do_print:
        print(f'Winner : action{id_confident}')

        print('UCB-1 learned:')
        for _, (pt, rs, uc) in enumerate(zip(played_times, reward_sums, upp_confidences)):
            print(f'\t id:{_}, \n'
                  f'upper_confidence:{round(uc, 5)} \n'
                  f'played_time:{pt} \n'
                  f'reward_avg:{rs / played_times[_] if played_times[_] != 0 else None}')

    return id_confident, expected_gain


def act(action_id):
    global t ; t +=1
    if random() < win_rates[action_id]:
        return losses[action_id]
    else:
        return gains[action_id]


def update(id, reward):
    played_times[id]   += 1
    reward_sums[id]    += reward
    upp_confidences[id] = reward_sums[id]/played_times[id] + sqrt(3/2 * log(t+1)/played_times[id])





def argmax(array):
    return array.index(max(array))


if __name__ == '__main__':
    run()