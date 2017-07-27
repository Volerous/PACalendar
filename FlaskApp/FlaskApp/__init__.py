from flask import Flask
from flask import render_template, jsonify
from flask import Request
app = Flask(__name__)
app.config.from_object(__name__)

@app.route("/")
def hello():
    print("hello")
    return render_template("./home.html")

@app.route("/interactive/")
def background_page():
    return render_template("interactive.html")

@app.route("/background_process")
def background_process():
    first = Request.args.get("first_name")
    last = Request.args.get("last_name")
    return jsonify(first_name=first, last_name=last)
if __name__ == "__main__":
    app.run()