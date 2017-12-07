from flask import Flask
from flask import render_template, jsonify, abort
from flask import request
from classes import *
import gtts
from sqlalchemy import desc
import sqlalchemy
import datetime
import dateutil.parser
from copy import copy
from flask_restful import Resource, Api, reqparse
import sys
app = Flask(__name__)
app.config.from_object(__name__)
rest_api = Api(app)
parser = reqparse.RequestParser()
parser.add_argument("color", type=str)
parser.add_argument("title", type=str)
parser.add_argument("tags", action='append', type=dict)
parser.add_argument("description", type=str)
parser.add_argument('due_date', type=str)
parser.add_argument("completed", type=bool)
parser.add_argument("priority", type=int)
parser.add_argument("name", type=str)


class Todo_Resorce(Resource):
    def get(self, todo_id=None):
        print(todo_id)
        if todo_id == None:
            q = Session.query(Task).filter_by(
                completed=False).order_by(desc(Task.id)).all()
            json_list = []
            for i in q:
                item = {}
                item["id"] = i.id
                item["title"] = i.title
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
            return {"success": True, "todos": json_list}
        else:
            q = Session.query(Task).filter_by(id=todo_id).first()
            item = {}
            item["id"] = q.id
            item["title"] = q.title
            item["completed"] = q.completed
            item["priority"] = q.priority
            item["color"] = q.color
            item["description"] = q.description
            if q.due_date != None:
                item["due_date"] = q.due_date.isoformat()
            else:
                item["due_date"] = q.due_date
            item["tags"] = []
            for j in q.tags:
                j_dict = {"title": j.title, "color": j.color}
                item["tags"].append(j_dict)
            return {"success": True, "todo": item}

    def post(self):
        post = parser.parse_args()
        if post["due_date"] != "null":
            due_date = dateutil.parser.parse(post["due_date"])
            task_to_insert = Task(
                title=post["title"], color=post["color"], due_date=due_date, completed=False, description=post["description"], priority=post["priority"])
        else:
            task_to_insert = Task(
                title=post["title"], color=post["color"], completed=False, description=post["description"], priority=post["priority"])
        try:
            Session.add(task_to_insert)
            Session.commit()
            task_to_insert.str_tags = post["tags"]
            Session.commit()
        except sqlalchemy.exc.InvalidRequestError:
            Session.rollback()
        except sqlalchemy.exc.OperationalError:
            Session.rollback()
        return {"success": True, "id": task_to_insert.id}

    def delete(self, todo_id):
        try:
            q = Session.query(Task).filter_by(id=todo_id).first()
            Session.delete(q)
            Session.commit()
        except:
            return {"success": True, "message": "removal failed"}
        return {"success": True, "todo_id": todo_id}

    def put(self, todo_id):
        post = parser.parse_args()
        q = Session.query(Task).filter_by(id=todo_id).first()
        for key in q.attrs:
            if key in post and post[key] != None:
                setattr(q, key, post[key])
        Session.commit()
        return {"success": True}


class Tag_Resource(Resource):
    def get(self, tag_part=None):
        if tag_part == None:
            tags = Session.query(Tag).all()
        else:
            tags = Session.query(Tag).filter(
                Tag.title.contains(tag_part)).all()
        ret = []
        for tag in tags:
            ret.append({"id": tag.id, "title": tag.title, "color": tag.color})
        return {"success": True, "ret": ret}

    def post(self):
        post = parser.parse_args()
        if post["color"] != None:
            tag_to_insert = Tag(title=post["title"], color=post["color"])
        else:
            tag_to_insert = Tag(title=post["title"])
        Session.add(tag_to_insert)
        Session.commit()
        return {"success": True, "id": tag_to_insert.id}

    def put(self, tag_id):
        post = parser.parse_args()
        q = Session.query(Tag).filter_by(id=tag_id).first()
        for attr in q.attrs:
            if attr in post and post[attr] != None:
                setattr(q, attr, post[attr])
        return {"success": True}

    def delete(self, tag_id):
        q = Session.query(Tag).filter_by(id=tag_id).first()
        Session.delete(q)
        Session.commit()
        return {"success": True}


@app.route("/")
def hello():
    return render_template("./template.html")


@app.route("/create_event.html")
def show_create_event():
    return render_template("create_event.html")


@app.route("/create_todo.html")
def show_create_todo():
    return render_template("create_todo.html")


@app.route("/create_tag.html")
def create_tag_html():
    return render_template("create_tag.html")


@app.route("/_insert_event")
def insert_into_event_back():
    begin_date = request.args.get("begin_date")
    end_date = request.args.get("end_date")
    all_day = request.args.get("all_day")


@app.route('/_delete_event')
def delete_event_back():
    id = request.args.get("id")


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
                                   <= datetime.timedelta(minutes=1)).order_by(Task.title).all()
    for i in q:
        print(i.__dict__)
    return jsonify(ret=q)


rest_api.add_resource(Todo_Resorce, "/_todo", "/_todo/<string:todo_id>")
rest_api.add_resource(Tag_Resource, "/_tag/",
                      "/_tag/<string:tag_part>", "/_tag/<int:tag_id>")
if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0')
