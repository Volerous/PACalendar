from flask import Flask
from flask import render_template, jsonify
from flask import request
# from classes import *
import gtts
app = Flask(__name__)
app.config.from_object(__name__)


@app.route("/")
def hello():
    print("hello")
    return render_template("./template.html")


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


@app.route("/insert_event")
def insert_into_event():
    return render_template("insert_into_event.html")
    
@app.route("/_insert_event")
def insert_into_event_back():
    begin_date = request.args.get("begin_date")
    end_date = request.args.get("end_date")
    all_day = request.args.get("all_day")
    print(begin_date, end_date)
    # new_event = Event(title=request.args.get("title"), )
    

if __name__ == "__main__":
    app.run(debug=True)
