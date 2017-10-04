from flask import Flask
from flask import render_template, jsonify
from flask import request
from classes import *
import gtts
import datetime
import dateutil.parser
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


@app.route("/_insert_event")
def insert_into_event_back():
    begin_date = request.args.get("begin_date")
    end_date = request.args.get("end_date")
    all_day = request.args.get("all_day")
    print(begin_date, end_date)
    # new_event = Event(title=request.args.get("title"), )


@app.route('/_delete_event')
def delete_event_back():
    id = request.args.get("id")


@app.route("/create_event.html")
def show_create_event():
    return render_template("create_event.html")


@app.route("/create_todo.html")
def show_create_todo():
    return render_template("create_todo.html")


@app.route("/_insert_todo", methods=['GET', "POST"])
def insert_todo():
    post = request.get_json()
    # color = post["colorHex"].replace("#", "")
    if "dueDateTime" not in post:
        due_date = dateutil.parser.parse(post["dueDateTime"])
        task_to_insert = Task(
            name=post["title"], color=post["color"], due_date=due_date, completed=False, description=post["description"], priority=post["priority"])
    else:
        task_to_insert = Task(
            name=post["title"], color=post["color"], completed=False, description=post["description"], priority=post["priority"])
    Session.add(task_to_insert)
    Session.commit()
    print(task_to_insert.id)
    task_to_insert.str_tags = post["tags"]
    Session.commit()
    return jsonify(success=True, id=task_to_insert.id)


@app.route("/test")
def test():
    for i in Session.query(Task).all():
        for j in i.tags:
            print(j.title)
    return "Task.color"


@app.route("/_find_all_todo")
def find_all_todo():
    q = Session.query(Task).filter_by(completed=False).all()
    json_list = []
    for i in q:
        item = {}
        item["id"] = i.id
        item["title"] = i.name
        item["completed"] = i.completed
        item["priority"] = i.priority
        item["color"] = i.color
        item["description"] = i.description
        item["due_date"] = i.due_date
        item["tags"] = []
        for j in i.tags:
            item["tags"].append(j.title)
        json_list.append(item)
    return jsonify(success=True, todos=json_list)


@app.route("/_insert_tag", methods=["POST", "GET"])
def insert_tag():
    post = request.get_json()
    if "color" in post:
        tag_to_insert = Tag(title=post["title"], color=post["color"])
    else:
        tag_to_insert = Tag(title=post["title"])
    Session.add(tag_to_insert)
    Session.commit()
    return jsonify(success=True)


@app.route("/create_tag.html")
def create_tag():
    return render_template("create_tag.html")


@app.route("/_delete_todo", methods=["GET", "POST"])
def delete_todo():
    post = request.get_json()
    q = Session.query(Task).filter_by(id=post["id"]).all()
    Session.delete(q[0])
    Session.commit()
    return jsonify(success=True)

@app.route("/_check_todo", methods=["GET", "POST"])
def check_todo():
    post = request.get_json()
    q = Session.query(Task).filter_by(id=post["id"]).first()
    q.completed = post["completed"]
    Session.commit()
    return jsonify(success=True)

if __name__ == "__main__":
    app.run(debug=True)
