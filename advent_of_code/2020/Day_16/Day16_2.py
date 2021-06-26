class Range:
    start = 0
    end = 0

    def __init__(self, start: int, end: int) -> None:
        self.start = start
        self.end = end

    def contained(self, value: int) -> bool:
        return self.start <= value and self.end >= value

    def __repr__(self) -> str:
        return "Range({},{})".format(self.start, self.end)


class TicketField:
    className = None
    ranges = None

    def __init__(self, className: str, ranges: list) -> None:
        self.className = className
        self.ranges = ranges

    def valid(self, value: int) -> bool:
        '''
        Is the value valid for this ticket field?
        '''
        ret = False
        for range in self.ranges:
            ret = ret or range.contained(value)
        return ret
    
    def __repr__(self) -> str:
        # just works for 2 ranges
        return "TicketField({},{}-{},{}-{})".format(self.className, self.ranges[0].start,
                self.ranges[0].end, self.ranges[1].start, self.ranges[1].end)


def process_class(line, fields: list) -> None:
    # class: 1-3 or 5-7
    # fields = [TicketField("class_name", [Range(1,3), Range(5,7)]), ...]
    name, values = line.split(":")
    values = values.split("or")
    values = [v.replace(" ", "") for v in values]
    values = [[int(v) for v in val.split("-")] for val in values]
    fields.append(TicketField(name, [Range(start,end) for [start,end] in values]))

def in_range(value: int, fields: list) -> bool:
    # field is a TicketField
    for field in fields:
        for range in field.ranges:
            if range.start <= value and range.end >= value:
                return True
    return False

def transform_ticket(line: str):
    ticket = line.split(",")
    ticket = [int(v) for v in ticket]
    return ticket

def valid_ticket(line: str, fields: list) -> bool:
    ticket = transform_ticket(line)
    for value in ticket:
        if not in_range(value, fields):
            return False
    return True

def update_valid_fields(ticket, validFields):
    for position in range(0, len(ticket)):
        value = ticket[position]
        fields = validFields[position]
        delete = []
        for field in fields:
            if not field.valid(value):
                delete.append(field)
        # if (len(delete) > 0):
        #     print("value", value, "delete", delete)
        for v in delete:
            fields.remove(v)

def find_ticket_order_r(assignmentOrder: list, validFieldsForPosition: list,
                        fieldsAssigned: set, assignmentOrderIndex: int,
                        fieldPosition: dict) -> bool:

    if (assignmentOrderIndex >= len(assignmentOrder)):
        return True
    ticketPosition, nFieldsPossible = assignmentOrder[assignmentOrderIndex]
    fieldsPossible = set(validFieldsForPosition[ticketPosition])
    availableFieldPossibilities = fieldsPossible - fieldsAssigned
    for field in availableFieldPossibilities:
        fieldsAssigned.add(field)
        success = find_ticket_order_r(assignmentOrder, validFieldsForPosition,
                                      fieldsAssigned, assignmentOrderIndex + 1,
                                      fieldPosition)
        if success:
            fieldPosition[ticketPosition] = field
            return True
        fieldsAssigned.remove(field)
    return False

def find_ticket_order(validFieldsForEachTicketPosition: list) -> dict():
    # Order by which the assignments of the (possible) fields to a ticket
    # position are tried.
    # assignmentOrder = [(position_index, n_possibilities)]
    assignmentOrder = list( zip(range(0, len(validFieldsForEachTicketPosition)),
                                map(len, validFieldsForEachTicketPosition)) )

    # Sorting "assignmentOrder" by ascending number of possible fields of the
    # ticket position, guarantees that the fields with less possibilities are set
    # first. This reduces the amount of combinations that do not work out that are
    # tried, because we start the assignment by the most constrained positions.
    # (For example, if one of the positions can only be one field, and this field
    # is also a possibility for all other positions, considering those other
    # possibilities is just a waste of time, because those field-position
    # assignments will not produce valid tickets).
    assignmentOrder.sort(key=lambda k: k[1])

    # fieldPosition[position_index] = TicketField()
    fieldPosition = dict()
    find_ticket_order_r(assignmentOrder, validFieldsForEachTicketPosition,
                        set(), 0, fieldPosition)

    if len(fieldPosition) != len(validFieldsForEachTicketPosition):
        return None
    return fieldPosition

def main():
    fields = []
    count = 0
    line = input()
    while len(line) != 0:
        count += 1
        process_class(line, fields)
        line = input()

    count = 0
    input() # read "your ticket:"
    line = input()
    myTicket = transform_ticket(line)
    while len(line) != 0:
        count += 1
        line = input()

    # Added an \n at the end of the input file to not have (to handle) EOFError.
    invalid = 0
    count = 0
    # validFieldsForTicketPosition = [[TicketField(), ...], [TicketField(), ...], ...]
    validFieldsForTicketPosition = [fields.copy() for i in range(0, len(myTicket))]
    input() # read "nearby tickets:"
    line = input()
    while len(line) != 0:
        count += 1
        if valid_ticket(line, fields):
            update_valid_fields(transform_ticket(line), validFieldsForTicketPosition)
        else:
            invalid += 1
        line = input()
    #print("Read", count, "nearby tickets.")

    print("Invalid tickets:", invalid)


    # Find the position of each ticket field.
    # To find it, we make suppositions about what the field of each position is,
    # until we can find a valid combination for all positions.
    response = find_ticket_order(validFieldsForTicketPosition)
    if (response == None):
        print("Failed to find a valid ticket field position assignment.")
        exit(1)

    print("Found a valid ticket field position assignment!")

    print("Ticket field positions:")
    for i in range(0,len(response)):
        print(i + 1, response[i].className)

    # print("My Ticket fields:")
    # for i in range(0,len(response)):
    #     print(i+1, myTicket[i])

    answer = 1
    for i in range(0, len(response)):
        if response[i].className.startswith("departure"):
            answer *= myTicket[i]
    print("Part 2 answer:", answer)

if __name__ == "__main__":
    main()