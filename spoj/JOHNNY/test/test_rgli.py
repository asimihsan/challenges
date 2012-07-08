import os
import sys
import unittest
import subprocess
import random

src_path = os.path.abspath(os.path.join(__file__, os.pardir, os.pardir, "src"))
assert(os.path.isdir(src_path))
sys.path.append(src_path)
import rgli

solver_path = os.path.join(src_path, "rgli.py")
assert(os.path.isfile(solver_path))

class TestProblem1(unittest.TestCase):
    def setUp(self):
        self.proc = subprocess.Popen(solver_path,
                                     shell = True,
                                     stdin = subprocess.PIPE,
                                     stdout = subprocess.PIPE)
        self.assertTrue(self.proc.poll() is None)

    def tearDown(self):
        if self.proc.poll() is None:
            self.proc.terminate()

    def test_basic_problem_1(self):
        elements = [5, 8, 4]
        rv = rgli.rgli(elements, sum(elements)/2, 50)
        print rv

    def test_basic_problem_2(self):
        elements = [2, 5, 7, 11, 12]
        rv = rgli.rgli(elements, sum(elements)/2, 50)
        print rv

    def test_gives_answer_for_basic_problem(self):
        for number in [3, 5, 8, 4]:
            self.assertTrue(self.proc.poll() is None)
            self.proc.stdin.write("%s\n" % number)
        self.assertTrue(self.proc.poll() is None)
        output = []
        while True:
            line = self.proc.stdout.readline()
            if not line:
                break
            output.append(line.strip())
        print output

    def test_finishes_in_timely_fashion(self):
        self.proc.stdin.write("5000\n")
        for i in xrange(5000):
            self.assertTrue(self.proc.poll() is None)
            number = random.randint(0, 100000)
            self.proc.stdin.write("%s\n" % number)
            self.proc.stdin.flush()
        print "finished writing..."
        self.assertTrue(self.proc.poll() is None)
        output = []
        while True:
            line = self.proc.stdout.readline()
            if not line:
                break
            output.append(line.strip())
        print output

