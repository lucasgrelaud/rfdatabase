# RFDatabase Application
> Simple and effective companion application for HAM radio.

## About this project
_This project is the final test for my Android Programming lecture in 2019._

This application aims to provide useful information for HAM radio about the repartition of radio
frequencies around the globe.

In the future it will provide cheatsheet, ham callsign search form, etc..

## Installation
### I. Via APK
Simply download the release APK available on the [release page][release] and sideload 
it onto your smartphone.

### II. Build it
To build this application, you will have to install Android  Studio first.

Then, types the followings lines in a prompt shell
```bash
git clone git@github.com:lucasgrelaud/rfdatabase.git
android-studio ./rfdatabase
```

## Project requirements
Here ares the projects requirement for the lecture.

| Requirement   | Details                      | State   |
| ------------- |------------------------------|--------:|
| Development   | Use of GitFlow               | ✅       |
| Architecture  |  Singleton                   | ✅       |
|               |  Design Patterns (other)     | ⬜️       |
|               |  MVC                         | ✅       |
|               |  Dependency injection        | ✅       |
|               |  SOLID, KISS, DRY            | ✅       |
| Activities    |  Activity 1 : list of items  | ✅       |
|               |  Activity 2 : item details   | ✅       |
| API           |  Call to a webservice        | ✅       |
|               |  Response caching            | ✅       |
| Improvement   |  Animation between activities| ✅       |
| Improvement   |  Push notifications          | ⬜       |

The GitFlow network graph is available [here][gitflow-network].

In addition of these requirements, some feature have been implemented :
    
   - Network connectivity detection
   - Spinner to choose the wanted country
   
## Feature
### I. First Screen : Frequency listing
On the first screen, you will see the list of frequencies available for the selected country.

Basic information are displayed such as "Frequency range" , "Frequency Span", "Frequency EIRP", 
and so on. 

![frequency-listing][frequency-listing-img]

By clicking on the country name or the down turned arrow on it right, you will be able to select a 
specific country. The listing will be updated.

![frequency-listing-spinner][frequency-listing-spinner-img]
![frequency-listing2][frequency-listing2-img]

### II. Second Screen : Frequency details
By pressing on the right button ( >| ) on a frequency row, you will access the frequency details
activity.

It will display much more information about the selected frequency and show its sub-allocation if they exists.

![details-img][details-img]
![details-sub-img][details-sub-img]

Please note that each transition is handled by a custom sliding animation.

![animation-img][animation-img]

[release]: https://github.com/lucasgrelaud/rfdatabase/releases
[gitflow-network]: https://github.com/lucasgrelaud/rfdatabase/network
[frequency-listing-img]: /doc/img/frequency-listing.png?raw=true
[frequency-listing-spinner-img]: /doc/img/frequency-listing-spinner.png?raw=true
[frequency-listing2-img]: /doc/img/frequency-listing2.png?raw=true
[details-img]: /doc/img/details.png?raw=true
[details-sub-img]: /doc/img/details-suballocation.png?raw=true
[animation-img]: /doc/img/animation.gif?raw=true