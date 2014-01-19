import re
import sys

pattern = re.compile("^[09]+$")


def special_multiple(n):
    cnt = n
    while True:
        if cnt % 9 == 0 or cnt % 10 == 0:
            if pattern.match(str(cnt)):
                break
        cnt += n
    print(cnt)


if __name__ == "__main__":
    lines = (int(line.strip()) for line in sys.stdin)
    lines.next()
    map(special_multiple, lines)
