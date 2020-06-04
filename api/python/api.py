import flask
from locations import locations

app = flask.Flask(__name__)
app.config["DEBUG"] = True
app.register_blueprint(locations)

@app.route("/api/version")
def version():
    return "1.0"

app.run(port=8080)