# Virtual-Trackpad

## Project description

Trackpad implementation based on computer vision algorithms. The user can move computer cursor on any surface on which a finger can be recognized.

This is monorepo of 3 projects:

**1. ml**

Project where was modified machine learning model based on this tutorial https://github.com/tensorflow/models/blob/master/research/object_detection/colab_tutorials/eager_few_shot_od_training_tflite.ipynb

ML model: SSD MobileNet V2 FPN-Lite

ML model was modified using fine-tuning to detect fingers movements on the surface.

Used tools and libraries:
* Python 3
* Pycharm IDE
* Matplotlib
* SciPy
* NumPy
* TensorFlow
* Object Detection API
* Keras
* Google Colaboratory
* TensorFlowLite
* Beautiful Soup
* LabelImg

**2. android**

Android application that is intended to use modified ML model by Transfer Learning, recognize fingers movements and than send recognition data via bluetooth to computer.

Used tools and libraries:
* Kotlin
* Android Studio
* MVVM architecture
* Android Jetpack Architecture Components(ViewModel, LiveData)
* CameraX
* Jetpack DataStore
* Hilt DI
* Jetpack Navigation
* TensorFlowLite
* Coroutines

**3. desktop**

Desktop app that receives commands from Android app via bluetooth and applies them. By command meant cursor movement and etc.

Used tools and libraries:
* Kotlin
* IntellijIDEA
* MVP architecture
* TornadoFX
* Bluez
* Coroutines
* Koin DI

Desktop app architecture:
![alt text](https://github.com/tberchanov/Virtual-Trackpad/blob/master/.readme-images/desktop-classes-uml.png?raw=true)