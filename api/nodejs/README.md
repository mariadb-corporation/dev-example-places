# Places - Node.js API

1. [Environment and compatibility](#compatibility)
2. [Getting started with the app](#getting-started)
    1. [Configure the code](#configure-code)
    2. [Build the code](#build-code)
    3. [Run the app](#run-app)

## Environment and compatibility <a name="compatibility"></a>

This sample was created using the following techologies:

* [Node.js (v.12.x)](https://nodejs.org/docs/latest-v12.x/api/index.html)
* [NPM (v.6.11.3)](https://docs.npmjs.com/)

## Getting started with the app <a name="getting-started"></a>

### Configure the code <a name="configure-code"></a>

Configure the MariaDB connection by [adding an .env file to the Node.js project](https://github.com/mariadb-corporation/mariadb-connector-nodejs/blob/master/documentation/promise-api.md#security-consideration).

Example implementation:

```
DB_HOST=<host_address>
DB_PORT=<port_number>
DB_USER=<username>
DB_PASS=<password>
DB_NAME=<database>
```

**Configuring db.js**

The environmental variables from `.env` are used within the [db.js](src/db.js) for the MariaDB Node.js Connector configuration pool settings:

```javascript
var mariadb = require('mariadb');
require('dotenv').config();

const pool = mariadb.createPool({
    host: process.env.DB_HOST, 
    user: process.env.DB_USER, 
    password: process.env.DB_PASS,
    port: process.env.DB_PORT,
    multipleStatements: true,
    connectionLimit: 5
});
```

**Configuring db.js for MariaDB SkySQL**

MariaDB SkySQL uses requires SSL additions to connection. It's as easy as 1-2-3 (steps below).

```javascript
var mariadb = require('mariadb');
require('dotenv').config();

// 1.) Access the Node File System package
const fs = require("fs");

// 2.) Retrieve the Certificate Authority chain file (wherever you placed it - notice it's just in the Node project root here)
const serverCert = [fs.readFileSync("skysql_chain_t.pem", "utf8")];

var pools = [
  mariadb.createPool({
    host: process.env.DB_HOST, 
    user: process.env.DB_USER, 
    password: process.env.DB_PASS,
    port: process.env.DB_PORT,
    database: process.env.DB_NAME,
    multipleStatements: true,
    connectionLimit: 5,
    // 3.) Add an "ssl" property to the connection pool configuration, using the serverCert const defined above
    ssl: {
      ca: serverCert
    }
  })
];
```

### Build the code <a name="build-code"></a>

Once you have retrieved a copy of the code you're ready to build and run the project! However, before running the code it's important to point out that the application uses several Node Packages.

Executing the CLI command 

```
$ npm install
```

Doing this targets relative `package.json` file and [install all dependencies](https://docs.npmjs.com/downloading-and-installing-packages-locally).

**IMPORTANT**: Be sure that the Node modules are installed for the [client](../../client). This can be done manually executing the following CLI command for [client](../../client):

```
$ npm install
```

### Run the app <a name="run-app"></a>

Once you've pulled down the code and have verified that all of the required Node packages are installed you're ready to run the application! 

1. Execute the following CLI command 

```bash
$ npm start
```

2. Open a browser window and navigate to http://localhost:3000.
