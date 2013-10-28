"""
http://pymotw.com/2/abc/
"""

import abc


class PluginBase(object):
    __metaclass__ = abc.ABCMeta

    @abc.abstractmethod
    def load(self, input):
        """Retrieve data from input and return another object."""
        return

    @abc.abstractmethod
    def save(self, output, data):
        """Save the data object to the output."""
        return


class PluginImplementation(object):
    def load(self, input):
        return input.read()

    def save(self, output, data):
        return output.write(data)


class SubclassImplementation(PluginBase):
    def load(self, input):
        return input.read()

    def save(self, output, data):
        return output.write(data)

PluginBase.register(PluginImplementation)


class IncompleteImplementation(PluginBase):
    def save(self, output, data):
        return output.write(data)


if __name__ == '__main__':
    print 'PluginImplementation'
    print('Subclass: %s' % issubclass(PluginImplementation, PluginBase))
    print('Instance: %s' % isinstance(PluginImplementation(), PluginBase))

    print 'SubclassImplementation'
    print('Subclass: %s' % issubclass(SubclassImplementation, PluginBase))
    print('Instance: %s' % isinstance(SubclassImplementation(), PluginBase))

    for sc in PluginBase.__subclasses__():
        print sc.__name__

    try:
        PluginBase()
    except Exception as e:
        print 'ERROR: %s' % e

    print 'IncompleteImplementation'
    print('Subclass: %s' % issubclass(IncompleteImplementation, PluginBase))
    try:
        print('Instance: %s' % isinstance(IncompleteImplementation(),
                                          PluginBase))
    except Exception as e:
        print 'ERROR: %s' % e
