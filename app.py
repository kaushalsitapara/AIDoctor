from flask import Flask,request,render_template,Response,redirect, url_for, escape
import requests
import os
from io import StringIO
import urllib
from qgen import qgen
import requests
import json

app = Flask(__name__)
qs = qgen()

@app.route('/')
@app.route('/index',methods = ['POST'])
def index():
    if request.method == 'POST':
        qstring = request.form['qstring']
        dc = qs.mainSort(str(qstring))
        r = json.dumps(dc)
        loaded_r = json.loads(r)
        #type(r) #Output str
        #type(loaded_r) #Output dict
        return r
    else:
        return "DermAI Inference Server1"
if __name__ == "__main__":
	app.run()
