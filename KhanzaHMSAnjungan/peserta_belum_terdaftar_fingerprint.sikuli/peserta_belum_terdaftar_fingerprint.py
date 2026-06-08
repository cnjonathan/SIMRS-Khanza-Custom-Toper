# Path to the screenshot of the notification
notification_image = "notification_image.png"

# Check if the notification exists
if exists(notification_image):
    print("Notification peserta_belum_terdaftar_fingerprint detected!")
else:
    print("Notification not detected.")