import os
import sys
import unittest
import subprocess
import random

src_path = os.path.abspath(os.path.join(__file__, os.pardir, os.pardir, "src"))
assert(os.path.isdir(src_path))
sys.path.append(src_path)
import problem127

solver_path = os.path.join(src_path, "problem127.py")
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

    def test_merge_lists_1(self):
        l1 = [1, 2, 3, 4, 5]
        l2 = [5, 9, 1, 4, 11]
        rv = problem127.merge_lists(l1, l2)
        self.assertEqual([1, 2, 3, 4, 5, 9, 11], rv)

    def test_trim_list_1(self):
        l1 = [10, 11, 12, 15, 20, 21, 22, 23, 24, 29]
        delta = 0.1
        rv = problem127.trim_list(l1, delta)
        self.assertEqual([10, 12, 15, 20, 23, 29], rv)

    def test_approximate_subset_sum_1(self):
        l1 = [104, 102, 201, 101]
        t = 308
        epsilon = 0.40
        rv = problem127.approximate_subset_sum(l1, t, epsilon)
        self.assertEqual(302, rv)

    def test_approximate_subset_sum_and_respective_elements_1(self):
        l1 = [104, 102, 201, 101]
        t = 308
        epsilon = 0.40
        rv = problem127.approximate_subset_sum_and_respective_elements(l1, t, epsilon)
        self.assertEqual(302, rv[0])
        self.assertEqual([2, 3], rv[1])

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
        self.assertEqual(['1', '3'], output)

    def test_finishes_in_timely_fashion(self):
        self.proc.stdin.write("100\n")
        for i in xrange(100):
            self.assertTrue(self.proc.poll() is None)
            number = random.randint(0, 5000)
            self.proc.stdin.write("%s\n" % number)
        print "finished writing..."
        self.assertTrue(self.proc.poll() is None)
        output = []
        while True:
            line = self.proc.stdout.readline()
            if not line:
                break
            output.append(line.strip())
        print output
