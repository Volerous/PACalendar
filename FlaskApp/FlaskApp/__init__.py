from flask import Flask
from flask import render_template, jsonify
app = Flask(__name__)
app.config.from_object(__name__)

@app.route("/")
def hello():
    print("hello")
    return render_template("./home.html")



if __name__ == "__main__":
    app.run()