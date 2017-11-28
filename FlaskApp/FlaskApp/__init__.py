from flask import Flask
from flask import render_template, jsonify, abort
from flask import request
from classes import *
import gtts
from sqlalchemy import desc
import datetime
import dateutil.parser
from copy import copy
app = Flask(__name__)
app.config.from_object(__name__)


@app.route("/")
def hello():
    return render_template("./template.html")


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
    if post["due_date"] != "null":
        due_date = dateutil.parser.parse(post["due_date"])
        task_to_insert = Task(
            name=post["title"], color=post["color"], due_date=due_date, completed=False, description=post["description"], priority=post["priority"])
    else:
        task_to_insert = Task(
            name=post["title"], color=post["color"], completed=False, description=post["description"], priority=post["priority"])
    Session.add(task_to_insert)
    Session.commit()
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
    q = Session.query(Task).filter_by(completed=False).order_by(desc(Task.id)).all()
    json_list = []
    for i in q:
        item = {}
        item["id"] = i.id
        item["title"] = i.name
        item["completed"] = i.completed
        item["priority"] = i.priority
        item["color"] = i.color
        item["description"] = i.description
        if i.due_date != None:
            item["due_date"] = i.due_date.isoformat()
        else:
            item["due_date"] = i.due_date
        item["tags"] = []
        for j in i.tags:
            j_dict = {"title": j.title, "color": j.color}
            item["tags"].append(j_dict)
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


@app.route("/_edit_todo", methods=["POST", "GET"])
def edit_todo():
    post = request.get_json()
    q = Session.query(Task).filter_by(id=post["id"]).all()
    q[0].title = post["title"]
    q[0].priority = post["priority"]
    q[0].color = post["color"]
    q[0].completed = post["completed"]
    q[0].description = post["description"]
    q[0].due_date = post["due_date"]
    q[0].str_tags = post["tags"]
    Session.commit()
    return jsonify(success=True)


@app.route("/_find_tag/<string:tag_part>", methods=["GET"])
def find_tag(tag_part):
    tags = Session.query(Tag).filter(Tag.title.contains(tag_part)).all()
    ret = []
    for tag in tags:
        ret.append({"title": tag.title, "color": tag.color})
    return jsonify(ret=ret)


@app.route('/webhook', methods=['POST'])
def webhook():
    if request.method == 'POST':
        print(request.json)
        return '', 200
    else:
        abort(400)


@app.route("/_check_notifications", methods=["GET"])
def _todo_notifications():
    q = Session.query(Task).filter(Task.due_date - datetime.datetime.now()
                                   <= datetime.timedelta(minutes=1)).order_by(Task.name).all()
    for i in q:
        print(i.__dict__)
    return jsonify(ret=q)


if __name__ == "__main__":
    app.run(debug=True)
