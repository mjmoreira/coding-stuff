class Range:
    start = 0
    end = 0
    def __init__(self, start, end) -> None:
        self.start = start
        self.end = end
    def __str__(self) -> str:
        return "({},{})".format(self.start, self.end)
    def __repr__(self) -> str:
        return "Range({},{})".format(self.start, self.end)

def process_class(line, classes) -> None:
    # class: 1-3 or 5-7
    # classes["class"] = [Range(1,3), Range(5,7)]
    name, values = line.split(":")
    values = values.split("or")
    values = [v.replace(" ", "") for v in values]
    values = [[int(v) for v in val.split("-")] for val in values]
    classes[name] = [Range(start,end) for [start,end] in values]

def in_range(value, classes) -> bool:
    for val in classes.values():
        for range in val:
            if range.start <= value and range.end >= value:
                return True
    return False

def process_ticket(line, classes) -> int:
    ticket = line.split(",")
    ticket = [int(v) for v in ticket]
    result = 0
    for value in ticket:
        if not in_range(value, classes):
            result += value
    return result

classes = {}
count = 0
line = input()
while len(line) != 0:
    count += 1
    process_class(line, classes)
    line = input()
#print("Read", count, "classes.")

count = 0
input()
line = input()
while len(line) != 0:
    count += 1
    line = input()
#print("Read", count, "my tickets.")

# Added an \n at the end of the input file to not have (to handle) EOFError.
answer = 0
count = 0
input()
line = input()
while len(line) != 0:
    count += 1
    answer += process_ticket(line, classes)
    line = input()
#print("Read", count, "nearby tickets.")

print("Answer:", answer)