import sys
import numpy as np
import matplotlib.pyplot as plt
from operator import add
from functools import reduce

def get_size(obj, seen=None):
    """Recursively finds size of objects"""
    size = sys.getsizeof(obj)
    if seen is None:
        seen = set()
    obj_id = id(obj)
    if obj_id in seen:
        return 0
    # Important mark as seen *before* entering recursion to gracefully handle
    # self-referential objects
    seen.add(obj_id)
    if isinstance(obj, dict):
        size += sum([get_size(v, seen) for v in obj.values()])
        size += sum([get_size(k, seen) for k in obj.keys()])
    elif hasattr(obj, '__dict__'):
        size += get_size(obj.__dict__, seen)
    elif hasattr(obj, '__iter__') and not isinstance(obj, (str, bytes, bytearray)):
        size += sum([get_size(i, seen) for i in obj])
    return size

def rle_encode(dane):
    encode = []
    encode.append(len(dane.shape))

    for i in range(len(dane.shape)):
        encode.append(dane.shape[i])

    dane = dane.flatten()
    counter = 1

    for i in range(len(dane)):
        if i != len(dane) - 1 and dane[i] == dane[i + 1]:
            counter += 1
        elif counter == 1:
            encode.append(1)
            encode.append(dane[i])
        else:
            encode.append(counter)
            encode.append(dane[i])
            counter = 1

    return encode.copy()

def rle_decode(dane):
    decode = []
    zakres = 1

    if dane[0] != 1:
        zakres = dane[0] + 1
        wym = (dane[1:zakres])

    for i in range(zakres, len(dane), 2):
        decode.extend(dane[i] * [dane[i+1]])
    if dane[0] != 1:
        decode = np.array(decode).reshape(wym)

    return decode.copy()

def split(image):
    half_split = np.array_split(image, 2)
    res = map(lambda x: np.array_split(x, 2, axis=1), half_split)

    return reduce(add, res)

def pixels_qual_check(pixels):
    one_pixel = pixels[0][0]
    for i in range(pixels.shape[0]):
        for j in range(pixels.shape[1]):
            if np.array_equal(one_pixel, pixels[i][j]):
                continue
            else:
                return False

    return True

class QuadTree:
    def compression(self, img, level=0):
        self.level = level
        self.final = True
        self.resolution = (img.shape[0], img.shape[1])

        if img.size == 0:
            self.pixel = np.array([0, 0, 0])
            return self

        self.pixel = img[0][0]

        if not pixels_qual_check(img):
            parts = split(img)
            self.final = False

            self.part1 = QuadTree().compression(parts[0], level + 1)
            self.part2 = QuadTree().compression(parts[1], level + 1)
            self.part3 = QuadTree().compression(parts[2], level + 1)
            self.part4 = QuadTree().compression(parts[3], level + 1)

        return self

    def decompression(self):
        if self.final or self.level == 20:
            return np.tile(self.pixel, (*self.resolution, 1))

        top = np.concatenate((self.part1.decompression(), self.part2.decompression()), axis=1)
        bot = np.concatenate((self.part3.decompression(), self.part4.decompression()), axis=1)
        alll = np.concatenate((top, bot), axis=0)

        return alll

if __name__ == '__main__':
    img = plt.imread('kolorowe.jpg').astype(int)
    size_oryginal = get_size(img.copy())
    print('Kolorowe zdjęcie')
    print('Rozmiar oryginalnego obrazu: ', size_oryginal)
    zakodowane = rle_encode(img.copy())
    odkodowane = rle_decode(zakodowane)
    size_zakodowane = get_size(zakodowane)
    size_odkodowane = get_size(odkodowane)
    print('Rozmiar zakodowanego obrazu: ', size_zakodowane)
    print('Rozmiar odkodowanego obrazu: ', size_odkodowane)
    print('Stopień kompresji: ', np.round(size_odkodowane/size_zakodowane, 4), ' , czyli: ', np.round(size_zakodowane/size_odkodowane*100), '%')

    fig = plt.figure(figsize=(10, 6))
    fig.suptitle('Kolorowe zdjęcie', fontsize=12)

    plt.subplot(1, 2, 1)
    plt.gca().set_title('Oryginalny obraz', fontsize=10)
    plt.imshow(img)

    plt.subplot(1, 2, 2)
    plt.gca().set_title('Odkodowany obraz', fontsize=10)
    plt.imshow(odkodowane)
    plt.show()

    # Quad tree

    img = plt.imread('kolorowe.jpg').astype(int)
    size_oryginal = get_size(img.copy())
    print('Kolorowe zdjęcie')
    print('Rozmiar oryginalnego obrazu: ', size_oryginal)
    new_img = QuadTree()

    zakodowane = new_img.compression(img)
    odkodowane = new_img.decompression()
    size_zakodowane = get_size(zakodowane)
    size_odkodowane = get_size(odkodowane)
    print('Rozmiar zakodowanego obrazu: ', size_zakodowane)
    print('Rozmiar odkodowanego obrazu: ', size_odkodowane)
    print('Stopień kompresji: ', np.round(size_odkodowane/size_zakodowane, 4), ' , czyli: ', np.round(size_zakodowane/size_odkodowane*100), '%')

    fig = plt.figure(figsize=(10, 6))
    fig.suptitle('Kolorowe zdjęcie', fontsize=12)

    plt.subplot(1, 2, 1)
    plt.gca().set_title('Oryginalny obraz', fontsize=10)
    plt.imshow(img)

    plt.subplot(1, 2, 2)
    plt.gca().set_title('Odkodowany obraz', fontsize=10)
    plt.imshow(odkodowane)
    plt.show()