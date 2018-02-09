@echo off

cd src\shortproj\gui

javac ColorBar.java
javac SideBar.java
javac TopMenu.java

cd..

cd util

javac Context.java

cd..

javac ShortProject.java

pause