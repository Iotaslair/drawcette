@echo off

javac src\projects\shortproj\ShortProject.java -cp src\ -d bin\
javac src\projects\shortproj\gui\TopMenu.java -cp src\ -d bin\
javac src\projects\shortproj\gui\SideBar.java -cp src\ -d bin\
javac src\projects\shortproj\gui\ColorBar.java -cp src\ -d bin\

cd bin\

java projects.shortproj.ShortProject

pause