from flask import Flask
from flask import render_template, jsonify
from flask import request
from flask.ext.sqlalchemy import SQLAlchemy
app = Flask(__name__)
app.config.from_object(__name__)


@app.route("/")
def hello():
    print("hello")
    return render_template("./home.html")


@app.route("/interactive/", methods=["GET", "POST"])
def background_page():
    print(request.method)
    return render_template("interactive.html")


@app.route("/background_process", methods=['POST', 'GET'])
def background_process():
    first = request.args.get("first_name")
    last = request.args.get("last_name")
    print(first, last)
    return jsonify(first_name=first, last_name=last)


if __name__ == "__main__":
    app.run()