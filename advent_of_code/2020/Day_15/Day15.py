input_numbers = list(map(int, input().split(',')))

# Instead of iterating over a list that recorded all the turns results, we
# can just keep track of the index that the result of a turn, "number", last
# appeared.
# numbers[number] = index_of_the_turn_that_number_last_appeared
# A turn is only added to "numbers" after it can no longer shadow the data
# necessary to calculate the current turn result. (If X occurred in turn 1, and
# in the previous turn, if "numbers" was already updated with the previous turn
# data, X from turn 1 could not be found, because it had already been replaced
# with the more recent data of the previous turn).
numbers = dict()
turn = 1 # turn
last_number = input_numbers[0]
while True:
    numbers[last_number] = turn
    last_number = input_numbers[turn]
    turn += 1
    if turn >= len(input_numbers):
        break

# FINAL_TURN = 2020 # Part 1
FINAL_TURN = 30000000 # Part 2

while(turn < FINAL_TURN):
    # print(index, ":", last_number)
    current_number = -1
    if last_number in numbers.keys():
        previous_index = numbers[last_number]
        current_number = turn - previous_index
    else: # Unnecessary if current_number is initialized to 0. Just to be more explicit.
        current_number = 0
    numbers[last_number] = turn
    turn += 1
    last_number = current_number

print("Answer:", last_number)
