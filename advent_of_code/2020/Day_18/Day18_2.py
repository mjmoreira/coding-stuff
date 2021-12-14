from collections import deque


OP_PLUS = "+"
OP_TIMES = "*"
OP_SET = set([OP_TIMES, OP_PLUS])
OP_LEFT_ROUND_BRACKET = "("
OP_RIGHT_ROUND_BRACKET = ")"
OP_PRIORITIES = dict([("+", 10), ("*", 5)])


def do_operation(term1, term2, operator):
    if operator == OP_PLUS:
        return int(term1) + int(term2)
    elif operator == OP_TIMES:
        return int(term1) * int(term2)
    else:
        print("Unknown operator:", operator)
        return None


def make_operations(values: deque, operators: deque) -> None:
    """Only works with 2 operators."""
    if len(operators) == 1 and len(values) == 2:
        op = operators.popleft()
        val1 = values.popleft()
        val2 = values.popleft()
        values.appendleft(do_operation(val1, val2, op))
    elif len(operators) == 2 and len(values) == 3:
        if OP_PRIORITIES[operators[0]] >= OP_PRIORITIES[operators[1]]:
            op = operators.popleft()
            val1 = values.popleft()
            val2 = values.popleft()
            values.appendleft(do_operation(val1, val2, op))
        else: # OP_PRIORITIES[operators[0]] < OP_PRIORITIES[operators[1]]:
            op = operators.pop()
            val2 = values.pop()
            val1 = values.pop()
            values.append(do_operation(val1, val2, op))
            # After doing the +, there can be another + after, and we need the
            # value of this + for the next +, so we cann't do more calculations
            # at this point.


def split_brackets_expression(expression: deque) -> deque:
    count = 0
    out = deque()
    if len(expression) > 0:
        e = expression.popleft()
        if e != OP_LEFT_ROUND_BRACKET:
            print("split_brackets_expression(): Expression does not start with a bracket.")
            return None
        count += 1
        while (len(expression) > 0 and count > 0):
            e = expression.popleft()
            if e == OP_LEFT_ROUND_BRACKET:
                count += 1
            elif e == OP_RIGHT_ROUND_BRACKET:
                count -= 1
            out.append(e)
        if len(out) > 0:
            # Remove the closing bracket because the opening one is omitted.
            out.pop()
    return out


def evaluate_expression(expression: deque) -> int:
    values = deque()
    operators = deque()
    while len(expression) > 0:
        e = str(expression.popleft())
        if e in OP_SET:
            operators.append(e)
        elif e == OP_LEFT_ROUND_BRACKET:
            expression.appendleft(e)
            values.append(evaluate_expression(split_brackets_expression(expression)))
        elif e.isnumeric():
            values.append(int(e))
        else:
            print("evaluate_expression(): '" + e + "' is not operator, bracket or number")
        # Now make the computations that can be performed.
        # We must have at least 2 operators in queue to perform computations,
        # unless we have reached the end of the expression.
        # Cases:
        # ++ -> safe to perform both
        # +* or ** -> safe to perform the first
        # *+ -> safe to perform the last
        # When there is only a * operation in the queue, because it has the
        # lowest priority it is never safe to perform, unless it is the last
        # operation to do of the expression. In all the other cases, we must know
        # first what comes next.
        if len(operators) >= 2 and len(values) >= 3:
            # Because each step of the loop, either "operators" is updated or
            # "values" is updated, we can have operators = [*, +] and
            # values = [1, 2], which is not a valid state for computations.
            make_operations(values, operators)
    make_operations(values, operators)
    return values.pop()


def split_expression(expression: str,
                     split_chars: list = ["+", "*", "(", ")"],
                     delete_chars: list = [" "]) -> deque:
    split = deque()
    acc = ""
    for c in expression:
        if c in delete_chars:
            if len(acc) > 0:
                split.append(acc)
                acc = ""
        elif c in split_chars:
            if len(acc) > 0:
                # with c = "(" or c = ")", acc = ""
                split.append(acc)
                acc = ""
            split.append(c)
        else:
            acc = acc + c
    if len(acc) > 0:
        split.append(acc)
    return split


def main() -> None:
    answer_accumulator = 0
    line = ""
    while True:
        try:
            line = input()
        except EOFError:
            break;
        expression = split_expression(line)
        answer_accumulator += evaluate_expression(expression)
    print("Answer:", answer_accumulator)


if __name__ == "__main__":
    main()
