import os
import sys
import unittest
import subprocess

src_path = os.path.abspath(os.path.join(__file__, os.pardir, os.pardir, "src"))
assert(os.path.isdir(src_path))
solver_path = os.path.join(src_path, "problem1.py")
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

    def test_terminates_on_42(self):
        for number in ["1", "2", "3", "4", "5"]:
            self.assertTrue(self.proc.poll() is None)
            self.proc.stdin.write("%s\n" % number)
            output = self.proc.stdout.readline()
            self.assertEqual(output.strip(), number)
        self.assertTrue(self.proc.poll() is None)
        self.proc.stdin.write("42\n")
        self.proc.wait()
        self.assertTrue(self.proc.poll() is not None)

