import re
from os.path import join

import numpy as np
from bs4 import BeautifulSoup


# path - Path to folder with label data
# prefix - Prefix for files with label data in the `path` folder
def convert_label_data(prefix, postfix, quantity, path):
    file_labels_data = {}

    for index in range(1, quantity + 1):
        file_name = prefix + str(index) + postfix
        file_data = open(join(path, file_name), 'r').read()

        bs_data = BeautifulSoup(file_data, "xml")
        width = get_xml_body_value(bs_data.find_all("width"))
        height = get_xml_body_value(bs_data.find_all("height"))

        result_boxes = list()

        for bndbox in bs_data.find_all("bndbox"):
            parsed_box = parse_bndbox(bndbox)
            converted_box = convert_box(parsed_box, width, height)
            result_boxes.append(np.array([converted_box], dtype=np.float32))

        file_labels_data[file_name] = result_boxes

    return file_labels_data


def convert_box(box, width, height):
    return [
        percentage(box[0], height),
        percentage(box[1], width),
        percentage(box[2], height),
        percentage(box[3], width)
    ]


def percentage(target, full):
    return float(target) / float(full)


def parse_bndbox(bndbox):
    return [
        get_xml_body_value(bndbox.find_all("ymin")),
        get_xml_body_value(bndbox.find_all("xmin")),
        get_xml_body_value(bndbox.find_all("ymax")),
        get_xml_body_value(bndbox.find_all("xmax"))
    ]


def get_xml_body_value(xml_body):
    return re.search('>(.*)</', str(xml_body[0])).group(1)


if __name__ == '__main__':
    convert_label_data("finger", "xml", "{HOME}/Downloads/fingers")
