### Internet radio metrics

Intent was to provide insights into what's being played on internet radio stations - i.e. what's hot in a sub-genre of sub-genre. 

Does this through scraping web content then populating a database and serving back content though a read-only API, i.e. what is the most played song in June 2016 on such and such station.

In reality mainly used as a sandbox project for experimenting with APIs

##### Api design 

`Get` _internet_radio/stations_

- returns a list of all the internet radio stations added to the database

`Get` _internet_radio/station/{id}/presenters_

- returns a list of all the presenters at this station

`Get` _internet_radio/station/{id}/presenter/{id}/tracks_

- returns all the tracks played by this presenter

`Get` _internet_radio/station/{id}/presenter/{id}/programmes_

- returns a list of all the programmes played by this presenter 

`Get` _internet_radio/station/{id}/presenter/{id}/programme/{id}/tracks_

- returns all the tracks played on this programmes by this presenter

Metrics e.g. most played tracks achieved through use of query params (for now...)