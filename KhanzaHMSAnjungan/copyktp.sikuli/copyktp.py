click(Pattern("1721718358132.png").targetOffset(-2,0))
click(Pattern("1721718412753.png").targetOffset(-156,3))

# Membuat instance Screen
screen = Screen()

# Simulasi menekan Ctrl+V untuk paste
screen.type("v", KeyModifier.CTRL)