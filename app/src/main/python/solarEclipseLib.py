'''
Shows the occurrences of a solar eclipse in the 21st century.

Works by iterating every five minutes in the 21st century and calculating
if the separation between the Moon and the Sun is less than half of max sun
angular size + half of max moon angular size + half of earth angular size
seen from the moon: "(1952 arcseconds + 2046 arcseconds)/ 2 + (12742 / 3474)
* 2046 arcseconds / 2  in degrees"

The number is hardcoded for simplicity, for more accuracy, it
should be computed from the distance of the Moon and the Sun.
'''
import ephem
from datetime import datetime, timedelta

curtime = datetime(2024, 1, 1, 0, 0, 0)        # start time
endtime = datetime(2100, 12, 31, 23, 59, 59)   # end time
moon = ephem.Moon()
sun = ephem.Sun()
observer = ephem.Observer()
observer.elevation = -6371000    # place observer in the center of the Earth
observer.pressure = 0            # disable refraction

# Open a file in write mode to store the output
with open("solar_eclipse_data.txt", "w") as file:
    while curtime <= endtime:
        observer.date = curtime.strftime('%Y/%m/%d %H:%M:%S')

        # Compute the position of the sun and the moon with respect to the observer
        moon.compute(observer)
        sun.compute(observer)

        # Calculate separation between the moon and the sun, convert
        # it from radians to degrees
        sep = abs((float(ephem.separation(moon, sun))
                   / 0.01745329252))

        # A solar eclipse happens if Sun-Earth-Moon alignment is <1.5976°.
        # This should detect all total and partial eclipses, but will
        # include some near misses.
        if sep < 1.59754941:
            output = f"{curtime.strftime('%Y/%m/%d %H:%M:%S')} {sep}\n"
            print(output.strip())  # Display on screen
            file.write(output)    # Save to file
            # An eclipse cannot happen more than once in a day,
            # so we skip 24 hours when an eclipse is found
            curtime += timedelta(days=1)
        else:
            # Advance five minutes if an eclipse is not found
            curtime += timedelta(minutes=5)
