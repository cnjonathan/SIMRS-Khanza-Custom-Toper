# Import the necessary modules from SikuliX
from sikuli import *
import time
import threading

# Find the input fields and the login button in the image
start_menu = "start_menu.png"
app_bpjs = "app_bpjs.png"
username_field = "username_field.png"
password_field = "password_field.png"
login_button = "login_button.png"

def worker():
    #print("Thread dimulai")
    click(start_menu)
    click(app_bpjs)
    time.sleep(5)
    #print("Thread selesai setelah 5 detik")
    # Menemukan elemen yang ingin digeser
    elemen = "elemen.png"
    region = find(elemen)
    
    # Menentukan lokasi tujuan
    tujuan = Location(region.x + 500, region.y)
    
    # Menggeser elemen ke lokasi tujuan
    dragDrop(region, tujuan)

    time.sleep(5)
    
    # Click on the username field and type the username
    click(username_field)
    type("Ninik-1125R001")
    
    # Click on the password field and type the password
    click(password_field)
    type("Ninik123#")
    
    # Click the login button
    click(login_button)

# Membuat dan memulai thread
thread = threading.Thread(target=worker)
thread.start()


