import math

## Read input file from stdin and transform it.
# ignore first line
input()

list = []
# transform list to integers and replace x with 1, to ease the calculation
for elem in input().split(","):
    if elem == 'x':
        list.append(1)
    else:
        list.append(int(elem))

# List elements must be co-prime

## Calculate answer
interval = list[0]
timestamp = 0
for index in range(0, len(list)):
    while (timestamp + index) % list[index] != 0:
        timestamp += interval
    interval = math.lcm(list[index], interval)

print("Answer:", timestamp)

'''
Ex: 2, 3, 5

t   values 
0  | 2| 3| 7|-- interval = 2
   |  |  |  |
2  | *|  |  |<- (t + 0) % 2 == 0, interval = lcm(interval,2) = 2, next value: 3 
   |  | *|  |<- (t + 1) % 3 == 0, interval = lcm(interval,3) = 6, next value: 7
   |  |  | x|<- (t + 2) % 7 != 0, t = t + interval
   |  |  |  |
   |  |  |  |
   |  |  |  |
8  | *|  |  |
   |  | *|  |
   |  |  | x|<- (t + 2) % 7 != 0, t = t + interval
   |  |  |  |
   |  |  |  |
   |  |  |  |
14 | *|  |  |
   |  | *|  |
   |  |  | x|<- (t + 2) % 7 != 0, t = t + interval
   |  |  |  |
   |  |  |  |
   |  |  |  |
20 | *|  |  |
   |  | *|  |
   |  |  | x|<- (t + 2) % 7 != 0, t = t + interval
   |  |  |  |
   |  |  |  |
   |  |  |  |
26 | *|  |  |
   |  | *|  |
   |  |  | *|<- (t + 2) % 7 == 0, interval = lcm(interval,7) = 42,
   |  |  |  |   no more numbers, solution found, t = 26
   |  |  |  |

Find the "interval" between timestamps that keeps all the previously iterated
"values" with the required modulus relative to the "timestamp", and find the 
"timestamp" that also has this property for the current "value". Update the
"interval" to maintain the property. Repeat until done.
'''