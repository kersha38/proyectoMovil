import requests

with open('bati.PNG', 'rb') as file:
    r = requests.post('http://localhost:300/Raspberry/subirVideo', files={'file':file})