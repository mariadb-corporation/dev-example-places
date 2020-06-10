import sys
import simplejson as json
import mariadb
import os
import flask
from flask import request
from flask import Blueprint
from dotenv import load_dotenv

load_dotenv()

locations = Blueprint('locations', __name__)

config = {
    'host': os.getenv("DB_HOST"),
    'port': int(os.getenv("DB_PORT")),
    'user': os.getenv("DB_USER"),
    'password': os.getenv("DB_PASS"),
    'database': os.getenv("DB_NAME")
}

@locations.route('/api/locations', methods=['GET','POST','PUT','DELETE'])
def index():
   conn = mariadb.connect(**config)
   cur = conn.cursor()
   json_data = []

   if request.method == 'GET':
    query = "select id, name, type, longitude, latitude, " \
            "case when type = 'R' then concat((case when json_length(attr, '$.favorites') " \
            "is not null then json_length(attr, '$.favorites') else 0 end), ' favorite meals') " \
            "when type = 'A' then (case when json_value(attr, '$.lastVisitDate') is not null " \
            "then json_value(attr, '$.lastVisitDate') else 'N/A' end) " \
            "when type = 'S' then concat((case when json_length(attr, '$.events') is not null " \
            "then json_length(attr, '$.events') else 0 end), ' events') end as description " \
            "from locations"
    cur.execute(query)
    row_headers=[x[0] for x in cur.description] 
    rv = cur.fetchall()
    for result in rv:
        json_data.append(dict(zip(row_headers,result)))
   
   if request.method == 'POST':
       name = request.json['name']
       description = request.json['description']
       type = request.json['type']
       latitude = request.json['latitude']
       longitude = request.json['longitude']
       attr = request.json['attr']
       query = "insert into locations (name, description, type, latitude, longitude, attr) values (?, ?, ?, ?, ?, json_compact(?))"
       cur.execute(query,(name, description, type, latitude, longitude, attr))
       json_data = { 'success': True }

   return json.dumps(json_data), 200, {'ContentType':'application/json'} 

@locations.route('/api/locations/restaurant', methods=['GET'])
def get_restaurant():
   id = request.args.get('id')
   conn = mariadb.connect(**config)
   cur = conn.cursor()
   json_data = []
   query = "select " \
            "name, " \
            "json_value(attr,'$.details.foodType') as foodType, " \
            "json_value(attr,'$.details.menu') as menu, " \
            "json_query(attr,'$.favorites') as favorites " \
            "from locations " \
            "where id = ?"
   cur.execute(query, [id])
   row_headers=[x[0] for x in cur.description] 
   result = cur.fetchone()
   json_obj=dict(zip(row_headers,result))
   return json.dumps(json_obj), 200, {'ContentType':'application/json'} 

@locations.route('/api/locations/restaurant/favorites', methods=['POST'])
def add_restaurant_favorite():
    id = request.json['locationId']
    details = request.json['details']
    conn = mariadb.connect(**config)
    cur = conn.cursor()
    query = "update locations set attr = json_array_append(attr, '$.favorites', json_compact(?)) where id = ?"
    cur.execute(query,[details,id])
    return json.dumps({ 'success': True }), 200, {'ContentType':'application/json'} 

@locations.route('/api/locations/sportsvenue', methods=['GET'])
def get_sportsvenue():
   id = request.args.get('id')
   conn = mariadb.connect(**config)
   cur = conn.cursor()
   json_data = []
   query = "select " \
            "name, " \
            "json_value(attr,'$.details.yearOpened') as yearOpened, " \
            "json_value(attr,'$.details.capacity') as capacity, " \
            "json_query(attr,'$.events') as events " \
            "from locations " \
            "where id = ?"
   cur.execute(query, [id])
   print(query)
   row_headers=[x[0] for x in cur.description] 
   result = cur.fetchone()
   json_obj=dict(zip(row_headers,result))
   return json.dumps(json_obj), 200, {'ContentType':'application/json'} 

@locations.route('/api/locations/sportsvenue/events', methods=['POST'])
def add_sportsvenue_event():
    id = request.json['locationId']
    details = request.json['details']
    conn = mariadb.connect(**config)
    cur = conn.cursor()
    query = "update locations set attr = json_array_append(attr, '$.events', json_compact(?)) where id = ?"
    cur.execute(query,[details,id])
    return json.dumps({ 'success': True }), 200, {'ContentType':'application/json'} 

@locations.route('/api/locations/attractions', methods=['PUT'])
def attractions():
    locationId = request.args.get('id')
    lastVisitDate = request.args.get('dt')
    conn = mariadb.connect(**config)
    cur = conn.cursor()
    query = "update locations set attr = json_set(attr,'$.lastVisitDate', ?) where id = ?"
    cur.execute(query,[lastVisitDate,locationId])
    return json.dumps({ 'success': True }), 200, {'ContentType':'application/json'} 