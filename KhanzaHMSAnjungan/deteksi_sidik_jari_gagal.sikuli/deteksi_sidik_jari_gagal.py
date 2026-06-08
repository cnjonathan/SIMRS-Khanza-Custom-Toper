# Import sikuli module
from sikuli import *

# Path to the screenshot of the notification
notification_image_1 = "notification_image_1.png"

notification_image_2 = "notification_image_2.png"

notification_image_3 = "notification_image_3.png"

notifikasi_sukses = "notifikasi_sukses.png"

button_batal = "button_batal.png"

button_x = "button_x.png"

# Check if the notification exists once
if exists(notification_image_1):
    print("Notifikasi Gagal detected!")
    # You can add actions here, like clicking the notification
    click(button_batal)
    click(button_x)

if exists(notification_image_2):
    print("Notifikasi Gagal detected!")
    # You can add actions here, like clicking the notification
    click(button_batal)
    click(button_x)

if exists(notification_image_3):
    print("Notifikasi Gagal detected!")
    # You can add actions here, like clicking the notification
    click(button_batal)
    click(button_x)
    
if exists(notifikasi_sukses):
    print("Notifikasi Sukses Detected")
    click("1721450123208.png")
else:
    print("Notification not detected.")
