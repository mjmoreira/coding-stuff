import math

ACTIVE = "#"
INACTIVE = "."

def read_input() -> list:
    initialState = []
    while True:
        lineState = []
        line = ""
        try:
            line = input()
        except EOFError:
            break
        for state in line:
            if state == ACTIVE:
                lineState.append(True)
            else:
                lineState.append(False)
        initialState.append(lineState)
    return initialState

def print_state(state: list) -> None:
    level = -math.floor(len(state) / 2)
    for z in state:
        print("z =", level)
        level += 1
        for y in z:
            for x in y:
                if x:
                    print(ACTIVE, end="")
                else:
                    print(INACTIVE, end="")
            print()
        print()

def count_active_cubes(state: list) -> int:
    count = 0
    for z in state:
        for y in z:
            for x in y:
                if x:
                    count += 1
    return count

def cube_active_neighbours(state: list, x: int, y: int, z: int) -> int:
    active = 0
    for zi in range(z - 1, z + 2):
        if zi < 0 or zi >= len(state):
            continue
        for yi in range(y - 1, y + 2):
            if yi < 0 or yi >= len(state[zi]):
                continue
            for xi in range(x - 1, x + 2):
                if xi < 0 or xi >= len(state[zi][yi]):
                    continue
                if state[zi][yi][xi] and not (zi == z and yi == y and xi == x):
                    active += 1
    return active

def generate_next_state(currentState: list) -> list:
    nextState = []
    for zi in range(-1, len(currentState) + 1):
        nextStateZ = []
        for yi in range(-1, len(currentState[0]) + 1):
            nextStateY = []
            for xi in range(-1, len(currentState[0][0]) + 1):
                activeNeighbours = cube_active_neighbours(currentState, xi, yi, zi)
                # Only cubes that already "existed" in the previous iteration can
                # be active.
                previousState = False
                # test: Did the cube exist in the previous iteration?
                test = zi >= 0 and zi < len(currentState)
                test = test and yi >= 0 and yi < len(currentState[zi])
                test = test and xi >= 0 and xi < len(currentState[zi][yi])
                if test:
                    # The cube was active
                    if currentState[zi][yi][xi]:
                        previousState = True
                # Active cube outcomes
                if previousState == True:
                    if activeNeighbours == 2 or activeNeighbours == 3:
                        nextStateY.append(True)
                    else:
                        nextStateY.append(False)
                # Inactive cube outcomes
                else:
                    if activeNeighbours == 3:
                        nextStateY.append(True)
                    else:
                        nextStateY.append(False)
            nextStateZ.append(nextStateY)
        nextState.append(nextStateZ)
    return nextState

def main():
    # initialState[z][y][x]
    initialState = [read_input()]
    # print_state(initialState)

    state = initialState
    for i in range(0,6):
        state = generate_next_state(state)
        # print_state(state)
        # print("--------------------")
    print("Answer:", count_active_cubes(state))


if __name__ == "__main__":
    main()

