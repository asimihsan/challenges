import base64
import cPickle as pickle
import codecs
import zlib


def create_names():
    with codecs.open("allCountries.txt", encoding="ascii", errors="replace") as f_in:
        with open("names.txt", "w") as f_out:
            for line in f_in:
                elems = line.split(u'\t')
                try:
                    f_out.write("%s\n" % elems[2].strip())
                except:
                    continue


def is_word_not_in_bloom_filter(word, array, k=3):
    m = len(array)
    for i in xrange(1, k + 1):
        key = word * i
        bit = (abs(hash(key))) % m
        if array[bit] is False:
            return True
    return False


def create_bloom_filter(m=12000000, k=2):
    array = [False] * m
    with open("names.txt") as f_in:
        lines = (line.strip() for line in f_in)
        for line in lines:
            for i in xrange(1, k + 1):
                key = line * i
                bit = (abs(hash(key))) % m
                array[bit] = True
    serialized = base64.encodestring(zlib.compress(pickle.dumps(array, -1), 9))
    with open("serialized.txt", "w") as f_out:
        f_out.write(serialized)


if __name__ == "__main__":
    create_bloom_filter()
