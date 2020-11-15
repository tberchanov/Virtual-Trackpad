import os
from os import listdir
from os.path import isfile, join


def rename_files(start_index, prefix, postfix, directory):
    files = list(filter(lambda file: isfile(join(directory, file)), listdir(directory)))
    for (index, file) in zip(range(start_index, len(files) + start_index + 1), files):
        old_name = join(directory, file)
        new_name = join(directory, prefix + str(index) + postfix)
        os.rename(old_name, new_name)


rename_files(21, "finger", ".jpg", "{HOME}/Downloads/new_fingers")