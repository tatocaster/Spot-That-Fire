# Spot That Fire 🔥

[Download now from Play Store!](https://play.google.com/store/apps/details?id=me.tatocaster.nasaappchallenge)


<img src="https://raw.githubusercontent.com/tatocaster/Spot-That-Fire/master/art/2.png" alt="All in one" width="300">


[Visit app website](http://spotthefire.surge.sh/)


[Visit web repo](https://github.com/mrtunii/Spot-The-Fire)


Over the past year, we have witnessed long and arduous battles against record-breaking wildfires across the world. Some of these fires burned thousands of acres of land and destroyed hundreds of homes and buildings. Additionally, smoke from the fire creates issues related to poor air quality, raising health concerns for people not just in the vicinity of the fire, but at distances hundreds of miles away.

`SpotThatFire` application solves the problem using `crowdsourcing` power. Individual citizens who care about nature will contribute and benefit from the system. The application will provide a point-based system for registered users which will give extra enthusiasm.

### What is SpotThatFire?
- Crowdsourcing platform to detect early wildfires and not only wildfires any natural disaster(coming soon!)
- Combines NASA open data + connections with government = ultimate tool
- Strong ML to detect possible areas before the fire season, generating heat map and reports
- Notify locals in the area with instructions
- Point based system, each user can achieve "the most caring" title

### How it works?
SpotThatFire offers mobile apps + web app and only a click you will be able to report a fire. Trained machine learning models filtering photos and allow only photos which fire and smokes to prevent spam. The operation is working on device + on the cloud. Application feed shows a distance between the user and the incident spot.
OpenWeatherApi will automatically be fetched for every report and will give more information to every user. From the application, a user can call emergency and get first aid information during the hazard. App also will offer offline sync in near future.

### Domain of the platform
`SpotThatFire` is intended for media rooms, individual users and government to track every little change in the region. Although this application can be used worldwide, the country of Georgia is the first place to push MVP.
Even crowdsourcing platforms need an income to survive, so monetization side of the platform can be spread in several fields:
- Media rooms
- Data mining (heatmaps, reports, predictions)

Interested media rooms can subscribe to the service and get information about early incidents 10-15 minutes earlier which will give them the spotlight.

Using data mining, the platform could give a useful database of past records, generate heatmaps, generate reports and aggregate data.

### Future
Current MVP is for the demo purpose. According to the features described above, SpotThatFire has an opportunity to evolve with useful features.

### How we built it
- The application is 100% on mBaaS - Firebase Auth, Firebase Storage, Firebase ML Kit, Firebase Firestore, Firebase Cloud Messaging, Firebase Cloud Functions
- Google Play Services
- Kotlin
- RxJava
- MVP arch
- Retrofit
- Many other 3rd party apps.

<img src="https://raw.githubusercontent.com/tatocaster/Spot-That-Fire/master/art/3.png" alt="All in one" width="300">
<img src="https://raw.githubusercontent.com/tatocaster/Spot-That-Fire/master/art/4.png" alt="All in one" width="300">


# License
```
MIT License

Copyright (c) 2018 Merabi Tato Kutalia

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```

