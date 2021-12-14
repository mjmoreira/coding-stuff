from collections import deque

OP_PLUS = "+"
OP_TIMES = "*"
OP_LIST = [OP_TIMES, OP_PLUS]


def do_operation(left, right, operator):
    if operator == OP_PLUS:
        return int(left) + int(right)
    elif operator == OP_TIMES:
        return int(left) * int(right)
    else:
        print("Unknown operator:", operator)
        return None


def evaluate_expression(expression: deque) -> int:
    '''expression must be reversed, because the list is pop()ed.'''
    acc = None
    op = None
    while len(expression) > 0:
        c = expression.popleft()
        if c == ")":
            return acc
        elif c == "(":
            if op == None:
                acc = evaluate_expression(expression)
            else:
                acc = do_operation(acc, evaluate_expression(expression), op)
        elif c.isnumeric():
            if acc == None:
                acc = c
            else:
                acc = do_operation(acc, c, op)
        elif c in OP_LIST:
            op = c
        else:
            print("Should not have been reached!", "c =", c)
    return acc


def split_expression(expression: str,
                     split_chars: list = ["+", "*", "(", ")"],
                     delete_chars: list = [" "]) -> deque:
    """This is not the most efficient way"""
    for d in delete_chars:
        expression = expression.replace(d, "")
    split = deque()
    acc = ""
    for c in expression:
        if c in split_chars:
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
