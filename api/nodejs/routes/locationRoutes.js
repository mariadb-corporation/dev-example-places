"use strict";

let express = require("express"),
    router = express.Router(),
    db = require('../db');

// GET all locations
router.get("/", async (req, res, next) => {
    try {
        const query = "select id, name, type, longitude, latitude, " +
                    "case when type = 'R' then concat((case when json_length(attr, '$.favorites') " +
                    "is not null then json_length(attr, '$.favorites') else 0 end), ' favorite meals') " +
                    "when type = 'A' then (case when json_value(attr, '$.lastVisitDate') is not null " +
                    "then json_value(attr, '$.lastVisitDate') else 'N/A' end) " +
                    "when type = 'S' then concat((case when json_length(attr, '$.events') is not null " +
                    "then json_length(attr, '$.events') else 0 end), ' events') end as description " +
                    "from locations";
        const rows = await db.pool.query(query);
        res.send(rows);
    } catch (err) {
        throw err;
    }
});

// POST new location
router.post("/", async (req, res, next) => {
    const location = req.body;
    try {
        const query = "insert into locations (name, description, type, latitude, longitude, attr) values (?, ?, ?, ?, ?, json_compact(?))";
        const result = await db.pool.query(query, [location.name, location.description, location.type, location.latitude, location.longitude, location.attr]);
        res.send(result);
    } catch (err) {
        throw err;
    } 
});

// GET restaurant by id
router.get("/restaurant", async (req, res, next) => {
    try {
        const id = req.query.id;
        const query = "select " +
                    "name, " +
                    "json_value(attr,'$.details.foodType') as foodType, " +
                    "json_value(attr,'$.details.menu') as menu, " +
                    "json_query(attr,'$.favorites') as favorites " +
                    "from locations " +
                    "where id = ?";
        const rows = await db.pool.query(query, [id]);
        res.send(rows[0]);
    } catch (err) {
        throw err;
    }
});

// POST new restaurant favorite
router.post("/restaurant/favorites", async (req, res, next) => {
    const favorite = req.body;
    const details = favorite.details; 
    try {
        const query = "update locations set attr = json_array_append(attr, '$.favorites', json_compact(?)) where id = ?"
        const result = await db.pool.query(query, [details, favorite.locationId]);
        res.send(result);
    } catch (err) {
        throw err;
    }
});

// GET sports venue by id
router.get("/sportsvenue", async (req, res, next) => {
    try {
        const id = req.query.id;
        const query = "select " +
                    "name, " +
                    "json_value(attr,'$.details.yearOpened') as yearOpened, " +
                    "json_value(attr,'$.details.capacity') as capacity, " +
                    "json_query(attr,'$.events') as events " +
                    "from locations " +
                    "where id = ?";
        const rows = await db.pool.query(query, [id]);
        res.send(rows[0]);
    } catch (err) {
        throw err;
    }
});

// POST new sports venue event
router.post("/sportsvenue/events", async (req, res, next) => {
    const event = req.body;
    try {
        const query = "update locations set attr = json_array_append(attr, '$.events', json_compact(?)) where id = ?";
        const result = await db.pool.query(query, [event.details, event.locationId]);
        res.send(result);
    } catch (err) {
        throw err;
    }
});

// PUT last visited an attraction
router.put("/attractions", async (req, res, next) => {
    const locationId = req.query.id;
    const lastVisitDate = req.query.dt;
    try {
        const query = "update locations set attr = json_set(attr,'$.lastVisitDate', ?) where id = ?";
        const result = await db.pool.query(query, [lastVisitDate, locationId]);
        res.send(result);
    } catch (err) {
        throw err;
    }
});

module.exports = router;