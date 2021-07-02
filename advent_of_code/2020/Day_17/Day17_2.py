# While unnecessary, it works for N-dimensional instances.

from math import floor

ACTIVE = "#"
INACTIVE = "."

# DIMENSION >= 2
DIMENSION = 4

# Distance to the neighbour cubes that affect the next state of a cube.
NEIGHBOUR_RANGE = 1

N_CYCLES = 6

# Names for positional coordinates after x and y: (x,y,z,w,a,b,c,e,d,e,f,g)
# (Make sure that DIMENSION_NAMES has enough names for the DIMENSION set)
DIMENSION_NAMES = ['z','w','a','b','c','d','e','f','g']

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


def positional_coordinate_string(indices: list):
    s = []
    for i in range(0, len(indices)):
        s.append("{} = {}".format(DIMENSION_NAMES[len(indices) - 1 - i],
                                  indices[i]))
    # Makes the string: s[0] + ", " + s[1] + ", " + s[2]
    return ", ".join(s)


def print_state(state: list) -> None:
    print_state_r(state)

def print_state_r(state: list, depth: int = 0, indices: list = []) -> None:
    if DIMENSION - depth > 2:
        index = -floor(len(state) / 2)
        for di in state:
            indices.append(index)
            print_state_r(di, depth + 1)
            indices.pop()
            index += 1
    else:
        print(positional_coordinate_string(indices) + ":")
        for y in state:
            for x in y:
                if x:
                    print(ACTIVE, end="")
                else:
                    print(INACTIVE, end="")
            print()
        print()


# Example: indices = [1,2,3,4] returns l[1][2][3][4]
# For indices outside the range, returns the closest. (For example l[-1] -> l[0]).
def get_list(l: list, indices: list):
    if len(indices) == 0:
        return l
    else:
        if indices[0] >= len(l):
            return get_list(l[-1], indices[1:])
        elif indices[0] < 0:
            return get_list(l[0], indices[1:])
        else:
            return get_list(l[indices[0]], indices[1:])


def count_active_cubes(state: list) -> int:
    return count_active_cubes_r(state)

def count_active_cubes_r(state: list, depth: int = 1) -> int:
    count = 0
    if DIMENSION > depth:
        for d in state:
            count += count_active_cubes_r(d, depth + 1)
    else:
        for e in state:
            if e:
                count += 1
    return count


# To generate the next state, it is necessary to check the number of neighbours
# active in the current state. To check the number of active neighbours,
# it is required to verify the state of some positions that are not stored, but
# are known to be inactive.
# location: [z,y,x]
def cube_active_neighbours(state: list, location: list) -> int:
    return cube_active_neighbours_r(state, location, [])

# state: list -- complete d-cube state
# location: list -- coordinate list of the selected d-cube to calculate the neighbours
# selected: list -- indexes of all the dimensions already selected
def cube_active_neighbours_r(state: list, location: list, selected: list) -> int:
    active = 0
    localState = get_list(state, selected)
    d = location[len(selected)]
    for di in range(d - 1, d + 2):
        selected.append(di)
        # Only cubes that are represented in the "state" can be active.
        if di >= 0 and di < len(localState):
            if DIMENSION - len(selected) > 0:
                active += cube_active_neighbours_r(state, location, selected)
            else:
                if localState[di] and (selected != location):
                    active += 1
        selected.pop()
    return active


def generate_next_state(currentState: list):
    return generate_next_state_r(currentState, [], [])

# To support the arbitrary dimension capability, the "selected" keeps track of
# the indices of currentState.
# For example selected = [1,2,3,4] -> currentState[1][2][3][4]
# "maximum" stores the size of the lists indexed by selected: is required to
# check if an index is inside the bounds
def generate_next_state_r(currentState: list, selected: list, maximum: list):
    state = get_list(currentState, selected)
    response = []
    maximum.append(len(state))
    if DIMENSION - len(selected) > 1:
        for di in range(-NEIGHBOUR_RANGE, len(state) + NEIGHBOUR_RANGE):
            selected.append(di)
            response.append(generate_next_state_r(currentState, selected, maximum))
            selected.pop()
    else:
        # Improve execution speed TODO: since DIMENSION >= 2, instead of relying
        # on recursion, could implement 2d directly:
        # for yi in range(-NEIGHBOUR_RANGE, len(state) + NEIGHBOUR_RANGE):
        #    selected.append(yi)
        #    var maximum needs update?
        #    for xi in range(-NEIGHBOUR_RANGE, len(state[yi]) + NEIGHBOUR_RANGE):
        #        ...
        #    selected.pop()
        for xi in range(-NEIGHBOUR_RANGE, len(state) + NEIGHBOUR_RANGE):
            selected.append(xi)
            activeNeighbours = cube_active_neighbours(currentState, selected)
            # Only cubes that already "existed" in the previous iteration can
            # be active.
            previousState = False
            # exist: Did the cube exist in the previous iteration?
            exist = True
            for i in range(0, len(selected)):
                exist = exist and selected[i] >= 0 and selected[i] < maximum[i]
            if exist:
                # Was the cube was active?
                if state[xi]:
                    previousState = True
            # Active cube outcomes
            if previousState == True:
                if activeNeighbours == 2 or activeNeighbours == 3:
                    response.append(True)
                else:
                    response.append(False)
            # Inactive cube outcomes
            else:
                if activeNeighbours == 3:
                    response.append(True)
                else:
                    response.append(False)
            selected.pop() # remove xi
    maximum.pop()
    return response


def main():
    # initialState[w][z][y][x]
    initialState = read_input()
    for i in range(2, DIMENSION):
        initialState = [initialState]
    # print_state(initialState)

    state = initialState
    for i in range(0, N_CYCLES):
        # print("Cycle:", i)
        state = generate_next_state(state)
        # print_state(state)
        # print("-------------------")
    print("Answer:", count_active_cubes(state))


if __name__ == "__main__":
    main()
