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
2. [Introduction to MariaDB](#introduction)
    1. [MariaDB Platform](#platform)
    2. [MariaDB SkySQL](#skysql)
    3. [Using JSON in a relational database](#json-relational)
3. [Getting started](#getting-started)
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

## Introduction to MariaDB <a name="introduction"></a>

### MariaDB Platform <a name="platform"></a>

[MariaDB Platform](https://mariadb.com/products/mariadb-platform/) integrates the former [MariaDB TX (transactions)](https://mariadb.com/products/mariadb-platform-transactional/) and [MariaDB AX (analytics)](https://mariadb.com/products/mariadb-platform-analytical/) products so developers can build modern applications by enriching transactions with real-time analytics and historical data, creating insightful experiences and compelling opportunities for customers – and for businesses, endless ways to monetize data. It’s the only enterprise open source database built for modern applications running in the cloud.

<p align="center" spacing="10">
    <kbd>
        <img src="media/platform.png" />
    </kbd>
</p>

To download and deploy MariaDB check out the instructions [here](https://mariadb.com/docs/deploy/installation/). You can also make use of the [MariaDB image available on Docker Hub](https://hub.docker.com/_/mariadb).

### MariaDB SkySQL <a name="skysql">

[SkySQL](https://mariadb.com/products/skysql/) is the first and only database-as-a-service (DBaaS) to bring the full power of MariaDB Platform to the cloud, including its support for transactional, analytical and hybrid workloads. Built on Kubernetes, and optimized for cloud infrastructure and services, SkySQL combines ease of use and self-service with enterprise reliability and world-class support – everything needed to safely run mission-critical databases in the cloud, and with enterprise governance.

[Get started with SkySQL!](https://mariadb.com/products/skysql/#get-started)

<p align="center" spacing="10">
    <kbd>
        <img src="media/skysql.png" />
    </kbd>
</p>

### Using JSON in a relational database <a name="json-relational"></a>

[JSON](https://www.json.org) is fast becoming the standard format for data interchange and for unstructured data, and MariaDB Platform (in fact, all MariaDB versions 10.2 and later) include a range of [JSON supporting functions](https://mariadb.com/topic/json/).

The Places application uses only a **single table** for all location, and uses JSON to store more specific information based on the location type.

For more information on how JSON can be used within MariaDB please check out this [blog post](https://mariadb.com/resources/blog/json-with-mariadb-10-2/)!

## Getting started <a name="getting-started"></a>

In order to run the Places application you will need to have a MariaDB instance to connect to. For more information please check out "[Get Started with MariaDB](https://mariadb.com/get-started-with-mariadb/)".

### Get the code <a name="code"></a>

Download this code directly or use [git](git-scm.org) (through CLI or a client) to retrieve the code using `git clone`:

```
$ git clone https://github.com/mariadb-corporation/dev-example-orders.git
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
        - Python (coming soon!)

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

Thanks so much for taking a look at the Places app! As this is a very simple example, there's plenty of potential for customization. Please feel free to submit PR's to the project to include your modifications!

If you have any questions, comments, or would like to contribute to this or future projects like this please reach out to us directly at developers@mariadb.com or on [Twitter](https://twitter.com/mariadb).

## License <a name="license"></a>
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)](https://opensource.org/licenses/MIT)
