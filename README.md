# Weather Forecast

## 1. Project setup

Clone this project and open in Android studio

``` 
git clone https://github.com/punnoket/weather-forecast.git
```

## 2. Project structure
### App

* base
* constant 
* di
* extension
* feature
  * search
  * wholeday
* network
	* interceptor
	* model
  * repository
  * service
* provider

#### 1.1 base

Contains all the base file (BaseFragment, BaseActivity, ...)

#### 1.2 Model

Contains the Enum class 

#### 1.3 di

In project use depency injection usually using Hilt

#### 1.4 extension

Contains all the extension using in app

#### 1.5 feature

Contains all the feature in app (Search city page, Whole day page).

#### 1.6 network

Contains all the network related files (Service, Retrofit, Repository..)

## 2. How to run unit test

Run test in Android studio or using command line below

``` 
./gradlew test
```

## 3. Demo

https://user-images.githubusercontent.com/21025399/205349293-b89a6b49-fae5-4c38-982e-fada36d12793.mov




