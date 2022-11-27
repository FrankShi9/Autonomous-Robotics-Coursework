import math
import random
import time


class ex2():
    def __init__(self):
        return

    def gaussian(self, var):
        b = var**2
        sum = 0.0
        for i in range(1,13):
            sum += random.uniform(-b, b)

        return (1/2) * sum

    def triangular(self,var):
        b = var**2
        sum = 0.0
        for i in range(1,3):
            sum += random.uniform(-b, b)

        return (math.sqrt(6)/2) * sum


if __name__ == "__main__":
    Ex2 = ex2()

    # Gaussian time
    t = time.time()
    t_ms = int(t * 1000)

    for i in range(1,int(1e6)):
        Ex2.gaussian(1)

    t_end = time.time()
    t_end_ms = int(t_end * 1000)
    t_period = t_end_ms - t_ms

    print(f"The gaussian time in milliseconds are: {t_period}")

    # Triangular time
    t = time.time()
    t_ms = int(t * 1000)

    for i in range(1,int(1e6)):
        Ex2.triangular(1)

    t_end = time.time()
    t_end_ms = int(t_end * 1000)
    t_period = t_end_ms - t_ms

    print(f"The triangular time in milliseconds are: {t_period}")