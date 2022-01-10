# Places

**Places** is a web application backed by the power of the power, performance, and simplicity of [MariaDB platform](https://mariadb.com/products/mariadb-platform/), allows you to record all of your favorite locations!

<p align="center">
    <kbd>
        <img src="media/map.png" />
    </kbd>
</p>

This README will walk you through the steps for getting the Places web application up and running using MariaDB. To ensure success, please follow the instructions in order.

**Note:** The code provided within this repository is completely open source. Please feel free to use it as you see fit.

# Table of Contents
1. [Requirements](#requirements)
2. [Getting started with MariaDB and JSON](#introduction)
3. [Getting started with the app](#getting-started)
    1. [Get the code](#code)
    2. [Create the schema](#schema)
    3. [Anatomy of the app](#app)
    4. [Build and run the app](#build-run)
4. [JSON Data Models](#data-models)
5. [Support and Contribution](#support-contribution)
6. [License](#license)

## Requirements <a name="requirements"></a>

This sample application, no matter which API project you target, will requires the following to be installed/enabled on your machine:

* [MariaDB Client](https://mariadb.com/products/skysql/docs/clients/), used to connect to MariaDB instances.

## Getting started with MariaDB and JSON <a name="introduction"></a>

Set up a MariaDB database, loaded with the data this sample needs, using the [MariaDB JSON Quickstart](https://github.com/mariadb-developers/mariadb-json-quickstart), before continuing to the next step.

## Getting started with the app <a name="getting-started"></a>

In order to run the Places application you will need to have a MariaDB instance to connect to. For more information please check out "[Get Started with MariaDB](https://mariadb.com/get-started-with-mariadb/)".

### Get the code <a name="code"></a>

Download this code directly or use [git](git-scm.org) (through CLI or a client) to retrieve the code using `git clone`:

```
$ git clone https://github.com/mariadb-corporation/dev-example-places.git
```

### Create the schema <a name="schema"></a>

[Connect to the database](https://mariadb.com/kb/en/connecting-to-mariadb/) using CLI or a client and execute the following:

```sql
CREATE TABLE `locations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(500) DEFAULT '',
  `type` char(1) NOT NULL DEFAULT '',
  `latitude` decimal(9,6) NOT NULL,
  `longitude` decimal(9,6) NOT NULL,
  `attr` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`attr`)),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### Anatomy of the app <a name="app"></a>

This application is made of two parts:

* Client
    - communicates with the API.
    - is a React.js project located in the [client](client) folder.
* API
    - uses a MariaDB Connector to connect to MariaDB.
    - contains multiple projects, located in the [api](api) folder.
        - [Node.js](api/nodejs)
        - [Python](api/python)

See the README's in [client](client/README.md) and [api](api/README.md) for more information on how to get started!

### Build and run the app <a name="build-run"></a>

   1. Nagivate to the [client](client) folder and execute the following CLI command before proceeding:

      ```
      $ npm install
      ```

   2. Configure the MariaDB connection by an [environmental](https://www.npmjs.com/package/dotenv) (called `.env`) file within the `client` folder.

      ```
      $ touch .env
      ```

      Then add the key `REACT_APP_GOOGLE_API_KEY` and a [Google Maps API Key](https://developers.google.com/maps/documentation/javascript/get-api-key).

      ```
      REACT_APP_GOOGLE_API_KEY=<google_api_key_here>
      ```

      **Note:** The `REACT_APP_GOOGLE_API_KEY` environmental variable is used [here](client/src/components/MapContainer.js#L250).

   3. Pick an [API](api) project and follow the instructions of the README to build and run the API project.

   4. Navigate to the [client](client) folder and execute the following CLI command to start the React.js application.

      ```bash 
      $ npm start
      ``` 

   5. Open a browser window and navigate to http://localhost:3000.

## JSON Data Models <a name="data-models"></a>

Below are samples of the data model per Location Type. 

**Attraction**
```json
{
   "category":"Landmark",
   "lastVisitDate":"11/5/2019"
}
```

**Location**
```json
{
   "details":{
      "foodType":"Pizza",
      "menu":"www.giodanos.com/menu"
   },
   "favorites":[
      {
         "description":"Classic Chicago",
         "price":24.99
      },
      {
         "description":"Salad",
         "price":9.99
      }
   ]
}
```

**Sports Venue**
```json
{
   "details":{
      "yearOpened":1994,
      "capacity":23500
   },
   "events":[
      {
         "date":"10/18/2019",
         "description":"Bulls vs Celtics"
      },
      {
         "date":"10/21/2019",
         "description":"Bulls vs Lakers"
      },
      {
         "date":"11/5/2019",
         "description":"Bulls vs Bucks"
      },
      {
         "date":"11/5/2019",
         "description":"Blackhawks vs Blues"
      }
   ]
}
```

## Support and Contribution <a name="support-contribution"></a>

Please feel free to submit PR's, issues or requests to this project project or projects within the [official MariaDB Corporation GitHub organization](https://github.com/mariadb-corporation).

If you have any other questions, comments, or looking for more information on MariaDB please check out:

* [MariaDB Developer Hub](https://mariadb.com/developers)
* [MariaDB Community Slack](https://r.mariadb.com/join-community-slack)

Or reach out to us diretly via:

* [developers@mariadb.com](mailto:developers@mariadb.com)
* [MariaDB Twitter](https://twitter.com/mariadb)

## License <a name="license"></a>
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)](https://opensource.org/licenses/MIT)
